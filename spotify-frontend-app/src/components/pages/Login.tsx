// src/pages/Login.tsx
import { Box, Button, Heading } from '@chakra-ui/react';

const Login = () => {
  const handleLogin = () => {
    window.location.href = 'http://localhost:8080/auth/spotify';
  };

  return (
    <Box 
      display="flex" 
      alignItems="center" 
      justifyContent="center" 
      height="100vh"
      bg="purple.800"
      color="white"
      >
     
        <Button 
          size="lg"
          px={8}
          py={6}
          bg="green.500"
          color="white"
          _hover={{ bg: "green.600" }}
          onClick={handleLogin}
          >
          Login
        </Button>
      
    </Box>
  );
};

export default Login;
