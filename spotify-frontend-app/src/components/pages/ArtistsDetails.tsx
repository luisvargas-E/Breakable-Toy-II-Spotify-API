import {
  Box,
  Heading,
  Image,
  Text,
  Button,
  SimpleGrid,
  Flex,
} from '@chakra-ui/react';
import { useParams, useNavigate } from 'react-router-dom';
import { useEffect, useState } from 'react';
import axios from 'axios';
import { useAuth } from '../auth/AuthContext';

const ArtistDetailsPage = () => {
  const { artistId } = useParams<{ artistId: string }>();
  const navigate = useNavigate();
  const [artist, setArtist] = useState<any>(null);
  const [tracks, setTracks] = useState<any[]>([]);
  const [albums, setAlbums] = useState<any[]>([]);
  const { accessToken } = useAuth();

  useEffect(() => {
    const fetchDetails = async () => {
      console.log("Artist ID desde useParams:", artistId);
      try {
        const res = await axios.get(`http://localhost:8080/artists/${artistId}`, {
  headers: { Authorization: `Bearer ${accessToken}` },
});

        console.log("Respuesta del backend:", res.data);

        // Adaptar estructura del artista
        setArtist({
          id: res.data.id,
          name: res.data.name,
          popularity: res.data.popularity,
          followers: { total: res.data.followers },
          images: [{ url: res.data.imageUrl }],
        });

        setTracks(res.data.topTracks || []);
        setAlbums(res.data.albums || []);
      } catch (error) {
        console.error('Error fetching artist details', error);
      }
    };

    fetchDetails();
  }, [artistId]);

  if (!artist) return <Text p={6}>Loading...</Text>;

  return (
    <Box p={6}>
      <Button onClick={() => navigate(-1)} mb={6}>
        Go back
      </Button>

      <Flex align="center" gap={6} flexWrap="wrap">
        {artist.images && artist.images.length > 0 && (
          <Image
            src={artist.images[0].url}
            alt={artist.name}
            maxW="200px"
            borderRadius="md"
          />
        )}
        <Box>
          <Heading>{artist.name}</Heading>
          {artist.followers && (
            <Text mt={2}>
              Followers: {artist.followers.total?.toLocaleString()}
            </Text>
          )}
          <Text>Popularity: {artist.popularity}</Text>
        </Box>
      </Flex>

      <Heading mt={10} mb={4}>
        Popular Songs
      </Heading>
      <SimpleGrid columns={[1, 2]} gap={4}>
        {tracks.map((track, index) => (
          <Flex
            key={track.id}
            p={3}
            borderWidth="1px"
            borderRadius="md"
            align="center"
            gap={4}
            flexWrap="wrap"
          >
            <Text fontWeight="bold" minW="24px">{index + 1}.</Text>
            {track.album?.images?.length > 0 && (
              <Image
                src={track.album.images[0].url}
                alt={track.name}
                boxSize="60px"
                objectFit="cover"
                borderRadius="md"
              />
            )}
            <Box>
              <Text fontWeight="semibold">{track.name}</Text>
              <Text fontSize="sm" color="gray.500">
                Plays: {track.popularity?.toLocaleString()} | Duration: {Math.floor(track.duration_ms / 60000)}:{String(Math.floor((track.duration_ms % 60000) / 1000)).padStart(2, '0')}
              </Text>
            </Box>
          </Flex>
        ))}
      </SimpleGrid>

      <Heading mt={10} mb={4}>
        Discography
      </Heading>
      <SimpleGrid columns={[1, 2, 3, 4]} gap={4}>
        {albums.map((album) => (
          <Box
            key={album.id}
            borderWidth="1px"
            borderRadius="lg"
            overflow="hidden"
            p={3}
            textAlign="center"
          >
            {album.images?.length > 0 && (
              <Image
                src={album.images[0].url}
                alt={album.name}
                mx="auto"
                h="120px"
                objectFit="cover"
                borderRadius="md"
              />
            )}
            <Text mt={2} fontWeight="bold">
              {album.name}
            </Text>
            <Text fontSize="sm" color="gray.500">
              {album.release_date?.slice(0, 4)}
            </Text>
          </Box>
        ))}
      </SimpleGrid>
    </Box>
  );
};

export default ArtistDetailsPage;
