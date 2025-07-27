import { render, screen, fireEvent, waitFor } from '@testing-library/react'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import Layout from '../components/pages/Layout'
import { AuthContext } from '../components/auth/AuthContext'
import { ChakraProvider, defaultSystem } from '@chakra-ui/react'
import { MemoryRouter } from 'react-router-dom'
import axios from 'axios'
import '@testing-library/jest-dom'

// 🔧 Mock axios global
vi.mock('axios')
const mockedAxios = axios as unknown as { get: ReturnType<typeof vi.fn> }

// 🔧 Mock useNavigate
const mockNavigate = vi.fn()
vi.mock('react-router-dom', async (importActual) => {
  const actual = await importActual<any>()
  return {
    ...actual,
    useNavigate: () => mockNavigate,
  }
})

// 🔧 Helper para envolver layout en providers
const renderLayout = (authValue: any) => {
  render(
    <ChakraProvider value={defaultSystem}>
      <AuthContext.Provider value={authValue}>
        <MemoryRouter>
          <Layout />
        </MemoryRouter>
      </AuthContext.Provider>
    </ChakraProvider>
  )
}

describe('Layout component', () => {
  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('renders SearchBar and Logout button', async () => {
    mockedAxios.get.mockResolvedValueOnce({ data: [] })
    renderLayout({ accessToken: 'token', logout: vi.fn(), isLoading: false })

    await waitFor(() => {
      expect(screen.getByRole('button', { name: /logout/i })).toBeInTheDocument()
      expect(screen.getByPlaceholderText(/search/i)).toBeInTheDocument()
    })
  })

  it('fetches and displays top artists', async () => {
    mockedAxios.get.mockResolvedValueOnce({
      data: [
        { id: '1', name: 'Artist 1', image: 'img1.jpg', spotifyUrl: 'url1' },
        { id: '2', name: 'Artist 2', image: 'img2.jpg', spotifyUrl: 'url2' },
      ]
    })

    renderLayout({ accessToken: 'token', logout: vi.fn(), isLoading: false })

    expect(screen.getByText(/your top artists/i)).toBeInTheDocument()
    expect(screen.getByTestId('spinner')).toBeInTheDocument()

    await waitFor(() => {
      expect(screen.getByText('Artist 1')).toBeInTheDocument()
      expect(screen.getByText('Artist 2')).toBeInTheDocument()
    })
  })

  it('shows ResultSection when search query is entered', async () => {
    mockedAxios.get
      .mockResolvedValueOnce({ data: [] }) // top artists
      .mockResolvedValueOnce({ data: { tracks: [{ id: 't1', name: 'Track 1' }] } }) // search

    renderLayout({ accessToken: 'token', logout: vi.fn(), isLoading: false })

    const input = screen.getByPlaceholderText(/search/i)
    fireEvent.change(input, { target: { value: 'track' } })

    await waitFor(() => {
      expect(mockedAxios.get).toHaveBeenCalledWith(
        expect.stringContaining('/spotify/search?query=track'),
        expect.any(Object)
      )
    })

    expect(screen.getByText(/track 1/i)).toBeInTheDocument()
  })

  it('calls logout and navigates on button click', async () => {
    mockedAxios.get.mockResolvedValueOnce({ data: [] })
    const mockLogout = vi.fn()
    renderLayout({ accessToken: 'token', logout: mockLogout, isLoading: false })

    const logoutButton = await screen.findByRole('button', { name: /logout/i })
    fireEvent.click(logoutButton)

    expect(mockLogout).toHaveBeenCalled()
    expect(mockNavigate).toHaveBeenCalledWith('/')
  })
})
