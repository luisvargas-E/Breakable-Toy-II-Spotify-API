import { render, screen, fireEvent } from '@testing-library/react'
import { describe, it, expect, vi } from 'vitest'
import { ChakraProvider, defaultSystem } from '@chakra-ui/react'
import Login from '../components/pages/Login'
import '@testing-library/jest-dom'

// Simula import.meta.env
vi.stubGlobal('import.meta', {
  env: {
    VITE_BACKEND_URL: 'http://localhost:8080'
  }
})

// Helper para envolver en ChakraProvider
const renderWithChakra = (ui: React.ReactElement) => {
  return render(<ChakraProvider value={defaultSystem}>{ui}</ChakraProvider>)
}

describe('Login Page', () => {
  it('renders the login button', () => {
    renderWithChakra(<Login />)
    const button = screen.getByRole('button', { name: /login/i })
    expect(button).toBeInTheDocument()
  })

  it('redirects to the backend on button click', () => {
    // Guarda referencia al location original
    const originalLocation = window.location

    // Mockea location SOLO para este test
    Object.defineProperty(window, 'location', {
      configurable: true, // permite restaurar
      writable: true,
      value: { href: '' } as any, // objeto mínimo con href
    })

    renderWithChakra(<Login />)
    const button = screen.getByRole('button', { name: /login/i })
    fireEvent.click(button)

    expect(window.location.href).toBe('http://localhost:8080/auth/spotify')

    // Restaura el objeto Location real
    Object.defineProperty(window, 'location', {
      configurable: true,
      writable: true,
      value: originalLocation as any,
    })
  })
})
