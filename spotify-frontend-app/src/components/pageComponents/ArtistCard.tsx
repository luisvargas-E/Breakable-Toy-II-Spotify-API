// components/ArtistCard.tsx
import { Box, Image, Link, Text } from '@chakra-ui/react';

interface Artist {
  id: string;
  name: string;
  image: string;
  spotifyUrl: string;
}

const ArtistCard = ({ artist }: { artist: Artist }) => (
  <Box
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
      boxSize="120px"
      mx="auto"
      mb={3}
      objectFit="cover"
    />
    <Text fontWeight="bold">{artist.name}</Text>
    <Link
      href={artist.spotifyUrl}
      color="teal.500"
      fontSize="sm"
      target="_blank"
      rel="noopener noreferrer"
    >
      View on Spotify
    </Link>
  </Box>
);

export default ArtistCard;