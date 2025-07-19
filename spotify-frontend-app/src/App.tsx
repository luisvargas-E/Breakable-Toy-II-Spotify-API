import { ChakraProvider, defaultSystem } from '@chakra-ui/react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import ProtectedRoute from './components/auth/ProtectedRoute';
import Login from './components/pages/Login';
import Callback from './components/pageComponents/Callback';
import Layout from './components/pages/Layout';
import { AuthProvider } from './components/auth/AuthContext';
import ArtistDetailsPage from './components/pages/ArtistsDetails';
import AlbumDetailsPage from './components/pages/AlbumDetails';

function App() {
  return (
    <ChakraProvider value={defaultSystem}>
      <AuthProvider>
        <BrowserRouter>
          <Routes>
            {/* Ruta pública */}
            <Route path="/" element={<Login />} />
            <Route path="/callback" element={<Callback />} />

            {/* Rutas protegidas */}
            <Route
              path="/home"
              element={
                <ProtectedRoute>
                  <Layout />
                </ProtectedRoute>
              }
            />
            <Route
              path="/artist/:artistId"
              element={
                <ProtectedRoute>
                  <ArtistDetailsPage />
                </ProtectedRoute>
              }
            />
            <Route
              path="/album/:id"
              element={
                <ProtectedRoute>
                  <AlbumDetailsPage />
                </ProtectedRoute>
              }
            />
          </Routes>
        </BrowserRouter>
      </AuthProvider>
    </ChakraProvider>
  );
}

export default App;
