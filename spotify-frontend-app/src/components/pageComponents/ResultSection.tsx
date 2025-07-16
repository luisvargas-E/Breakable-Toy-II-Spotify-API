// components/ResultSection.tsx
import { Box, Heading, Image, Link, SimpleGrid, Text } from '@chakra-ui/react';

const ResultSection = ({ results }: { results: any }) => {
  const tracks = Array.isArray(results?.tracks) ? results.tracks : [];
  const artists = Array.isArray(results?.artists) ? results.artists : [];
  const albums = Array.isArray(results?.albums) ? results.albums : [];

  return (
    <Box mt={6}>
      {/* Tracks */}
      {tracks.length > 0 && (
        <Box>
          <Heading size="md" mb={3}>Tracks</Heading>
          <SimpleGrid columns={[1, 2, 3]} gap={4}>
            {tracks.map((track: any) => (
              <Box key={track.id} borderWidth="1px" borderRadius="md" p={4}>
                <Image src={track.albumImage} alt={track.name} boxSize="80px" mb={2} />
                <Text fontWeight="bold">{track.name}</Text>
                <Text fontSize="sm">{track.artist}</Text>
                <Link href={track.spotifyUrl} target="_blank" color="teal.500">Play on Spotify</Link>
              </Box>
            ))}
          </SimpleGrid>
        </Box>
      )}

      {/* Artists */}
      {artists.length > 0 && (
        <Box mt={8}>
          <Heading size="md" mb={3}>Artists</Heading>
          <SimpleGrid columns={[1, 2, 3]} gap={4}>
            {artists.map((artist: any) => (
              <Box key={artist.id} borderWidth="1px" borderRadius="md" p={4}>
                <Image src={artist.image} alt={artist.name} boxSize="80px" mb={2} borderRadius="full" />
                <Text fontWeight="bold">{artist.name}</Text>
                <Link href={artist.spotifyUrl} target="_blank" color="teal.500">View on Spotify</Link>
              </Box>
            ))}
          </SimpleGrid>
        </Box>
      )}

      {/* Albums */}
      {albums.length > 0 && (
        <Box mt={8}>
          <Heading size="md" mb={3}>Albums</Heading>
          <SimpleGrid columns={[1, 2, 3]} gap={4}>
            {albums.map((album: any) => (
              <Box key={album.id} borderWidth="1px" borderRadius="md" p={4}>
                <Image src={album.image} alt={album.name} boxSize="80px" mb={2} />
                <Text fontWeight="bold">{album.name}</Text>
                <Text fontSize="sm">{album.artist}</Text>
                <Link href={album.spotifyUrl} target="_blank" color="teal.500">View Album</Link>
              </Box>
            ))}
          </SimpleGrid>
        </Box>
      )}
    </Box>
  );
};

export default ResultSection;
