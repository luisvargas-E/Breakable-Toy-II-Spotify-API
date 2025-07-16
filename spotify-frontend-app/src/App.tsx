import { ChakraProvider, defaultSystem } from '@chakra-ui/react';
import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Login from './components/pages/Login';
import Callback from './components/pageComponents/Callback';
import Layout from './components/pages/Layout';
import { AuthProvider } from './components/auth/AuthContext';

function App() {
  return (
    <ChakraProvider value={defaultSystem}>
      <AuthProvider>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/callback" element={<Callback />} />
          <Route path="/home" element={<Layout />} />
        </Routes>
      </BrowserRouter>
      </AuthProvider>
    </ChakraProvider>
  );
}

export default App;
