import {
  Box,
  Button,
  Flex,
  Heading,
  Image,
  Spinner,
  Text,
} from '@chakra-ui/react';
import { useEffect, useState } from 'react';
import { useParams, useNavigate, Link as RouterLink } from 'react-router-dom';
import axios from 'axios';

interface Track {
  id: string;
  name: string;
  artist: string;
  artistId: string;
  albumImage: string;
  spotifyUrl: string;
}

interface Album {
  id: string;
  name: string;
  imageUrl: string;
  releaseYear: string;
  totalTracks: number;
  totalDuration: string;
  artistName: string;
  tracks: Track[];
}

const formatDuration = (ms: number) => {
  const minutes = Math.floor(ms / 60000);
  const seconds = Math.floor((ms % 60000) / 1000)
    .toString()
    .padStart(2, '0');
  return `${minutes}:${seconds}`;
};

const AlbumDetailsPage = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [album, setAlbum] = useState<Album | null>(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    if (!id) return;

    axios
      .get<Album>(`http://localhost:8080/albums/${id}`)
      .then((res) => {
        setAlbum(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error('Error fetching album details:', err);
        setLoading(false);
      });
  }, [id]);

  if (loading) return <Spinner m={10} />;

  if (!album) return <Text p={6}>Album not found</Text>;

  return (
    <Box p={6}>
      <Button onClick={() => navigate(-1)} mb={6}>
        Go back
      </Button>

      <Flex gap={6} flexWrap="wrap" mb={8}>
        {album.imageUrl && (
          <Image
            src={album.imageUrl}
            alt={album.name}
            maxW="200px"
            borderRadius="md"
          />
        )}
        <Box>
          <Heading>{album.name}</Heading>
          <Text mt={2}>Year: {album.releaseYear}</Text>
          <Text>Total Songs: {album.totalTracks}</Text>
          <Text>Total Duration: {album.totalDuration}</Text>
          <Text mt={2}>
            Artist:{' '}
            <RouterLink to={`/artist/${album.tracks[0]?.artistId}`}>
              <Text as="span" color="teal.500" fontWeight="semibold">
                {album.artistName}
              </Text>
            </RouterLink>
          </Text>
        </Box>
      </Flex>

      <Heading size="md" mb={4}>
        Album Songs
      </Heading>

      <Box>
        {album.tracks.map((track, index) => (
          <Flex
            key={track.id}
            justify="space-between"
            align="center"
            p={3}
            borderWidth="1px"
            borderRadius="md"
            mb={2}
            flexWrap="wrap"
          >
            <Text fontWeight="bold" w="30px">
              {index + 1}
            </Text>
            <Box flex="1">
             
                <Text fontWeight="semibold" color="teal.500">
                  {track.name}
                </Text>
              
              <Text fontSize="sm" color="gray.500">
                {track.artist}
              </Text>
            </Box>
            <Text fontSize="sm">
              {/* Show duration if available */}
              {'durationMs' in track && typeof (track as any).durationMs === 'number'
                ? formatDuration((track as any).durationMs)
                : ''}
            </Text>
          </Flex>
        ))}
      </Box>
    </Box>
  );
};

export default AlbumDetailsPage;
