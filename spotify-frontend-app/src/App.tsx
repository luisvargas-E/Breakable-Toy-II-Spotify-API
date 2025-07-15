import { ChakraProvider, defaultSystem } from '@chakra-ui/react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './components/pages/Login';
import Callback from './components/pages/Callback';
import Dashboard from './components/Dashboard/Dashboard';
import { AuthProvider } from './components/auth/AuthContext';

function App() {
  return (
    <ChakraProvider value={defaultSystem}>
      <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/callback" element={<Callback />} />
          <Route path="/dashboard" element={<Dashboard />} />
        </Routes>
      </BrowserRouter>
      </AuthProvider>
    </ChakraProvider>
  );
}

export default App;
