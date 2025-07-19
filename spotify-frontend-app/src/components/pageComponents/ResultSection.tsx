import { Box, Heading, Image, SimpleGrid, Text } from '@chakra-ui/react';
import { Link as RouterLink } from 'react-router-dom';

const ResultSection = ({ results }: { results: any }) => {
  const tracks = Array.isArray(results?.tracks) ? results.tracks : [];
  const artists = Array.isArray(results?.artists) ? results.artists : [];
  const albums = Array.isArray(results?.albums) ? results.albums : [];

  return (
    <Box mt={6}>
      {/* Tracks */}
      {tracks.length > 0 && (
        <Box>
          <Heading size="2xl" mb={3}>Tracks</Heading>
          <SimpleGrid columns={[1, 2, 3]} gap={4}>
            {tracks.map((track: any) => (
              <RouterLink key={track.id} to={`/artist/${track.artistId}`}>
                <Box borderWidth="1px" borderRadius="md" p={4} _hover={{ bg: 'gray.50', cursor: 'pointer' }}>
                  <Image src={track.albumImage} alt={track.name} boxSize="80px" mb={2} />
                  <Text fontWeight="bold">{track.name}</Text>
                  <Text fontSize="sm">{track.artist}</Text>
                </Box>
              </RouterLink>
            ))}
          </SimpleGrid>
        </Box>
      )}

      {/* Artists */}
      {artists.length > 0 && (
        <Box mt={8}>
          <Heading size="2xl" mb={3}>Artists</Heading>
          <SimpleGrid columns={[1, 2, 3]} gap={4}>
            {artists.map((artist: any) => (
              <RouterLink key={artist.id} to={`/artist/${artist.id}`}>
                <Box borderWidth="1px" borderRadius="md" p={4} textAlign="center" _hover={{ bg: 'gray.50', cursor: 'pointer' }}>
                  <Image src={artist.image} alt={artist.name} boxSize="80px" mb={2} borderRadius="full" />
                  <Text fontWeight="bold">{artist.name}</Text>
                </Box>
              </RouterLink>
            ))}
          </SimpleGrid>
        </Box>
      )}

      {/* Albums */}
      {albums.length > 0 && (
        <Box mt={8}>
          <Heading size="2xl" mb={3}>Albums</Heading>
          <SimpleGrid columns={[1, 2, 3]} gap={4}>
            {albums.map((album: any) => (
              <RouterLink key={album.id} to={`/album/${album.id}`}>
                <Box borderWidth="1px" borderRadius="md" p={4} _hover={{ bg: 'gray.50', cursor: 'pointer' }}>
                  <Image src={album.image} alt={album.name} boxSize="80px" mb={2} />
                  <Text fontWeight="bold">{album.name}</Text>
                  <Text fontSize="sm">{album.artist}</Text>
                </Box>
              </RouterLink>
            ))}
          </SimpleGrid>
        </Box>
      )}
    </Box>
  );
};

export default ResultSection;
