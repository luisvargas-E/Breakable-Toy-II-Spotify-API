// src/pages/Login.tsx
import { Box, Button, Heading } from '@chakra-ui/react';
import { Typewriter } from 'react-simple-typewriter';

const Login = () => {
  const handleLogin = () => {
    window.location.href = `${import.meta.env.VITE_BACKEND_URL}/auth/spotify`;
  };

  const messages = [
    'Your music journey starts here',
    'Explore your favorite tracks, artists, and albums',
    'Ready to discover what Spotify knows about your music taste?',
    'Click below and let the rhythm guide you',
    'Your personalized music universe awaits',
    'From top hits to hidden gems — let’s go find them',
  ];

  return (
    <Box
      display="flex"
      flexDirection="column"
      alignItems="center"
      justifyContent="center"
      height="100vh"
      
      background="linear-gradient(to bottom right, #1DB954, #191414)"
      px={4}
      gap={8} // separación vertical entre elementos
    >
      <Heading size="4xl" textAlign="center" color="white" minH="64px">
        <Typewriter
          words={messages}
          loop={true}
          cursor
          cursorStyle="|"
          typeSpeed={40}
          deleteSpeed={30}
          delaySpeed={2000}
        />
      </Heading>

      <Button
        size="lg"
        px={8}
        py={6}
        bg="black"
        color="white"
        _hover={{ bg: 'green.500' }}
        onClick={handleLogin}
      >
        Login
      </Button>
    </Box>
  );
};

export default Login;
