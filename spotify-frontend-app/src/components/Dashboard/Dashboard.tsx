import { useEffect, useState } from 'react';
import {
  Box,
  Heading,
  Image,
  Link,
  SimpleGrid,
  Spinner,
  Text
} from '@chakra-ui/react';
import { useAuth } from '../auth/AuthContext';
import axios from 'axios';

interface Artist {
  id: string;
  name: string;
  image: string;
  spotifyUrl: string;
}

const Dashboard = () => {
  const { accessToken } = useAuth();
  const [artists, setArtists] = useState<Artist[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!accessToken) return;

    axios
      .get<Artist[]>('http://localhost:8080/user/favorites/artists', {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      })
      .then((res) => {
        setArtists(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error('Error fetching artists:', err);
        setLoading(false);
      });
  }, [accessToken]);

  if (!accessToken) {
    return (
      <Box textAlign="center" mt={10}>
        <Text fontSize="xl" color="red.500">Please login first.</Text>
      </Box>
    );
  }

  if (loading) {
    return (
      <Box textAlign="center" mt={10}>
        <Spinner size="xl" />
        <Text mt={4}>Loading your top artists...</Text>
      </Box>
    );
  }

  return (
    <Box p={6}>
      <Heading mb={6} textAlign="center">
        Your Top 10 Artists
      </Heading>
      <SimpleGrid columns={5} gap={6}>
  {artists.map((artist) => (
    <Box
      key={artist.id}
      borderWidth="1px"
      borderRadius="lg"
      overflow="hidden"
      p={4}
      textAlign="center"
      _hover={{ shadow: 'md' }}
    >
      <Image
        src={artist.image}
        alt={artist.name}
        borderRadius="full"
        boxSize="150px"
        mx="auto"
        mb={4}
        objectFit="cover"
      />
      <Box display="flex" flexDirection="column" alignItems="center" gap={2}>
        <Text fontWeight="bold" fontSize="lg">
          {artist.name}
        </Text>
        <Link
          href={artist.spotifyUrl}
          color="teal.500"
          target="_blank"
          rel="noopener noreferrer"
        >
          View on Spotify
        </Link>
      </Box>
    </Box>
  ))}
</SimpleGrid>

    </Box>
  );
};

export default Dashboard;
