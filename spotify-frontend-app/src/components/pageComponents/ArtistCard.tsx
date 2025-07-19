import { Box, Image, Text } from '@chakra-ui/react';
import { Link } from 'react-router-dom';

interface Artist {
  id: string;
  name: string;
  image: string;
  spotifyUrl: string;
}

const ArtistCard = ({ artist }: { artist: Artist }) => (
  <Link to={`/artist/${artist.id}`}>
    <Box
      borderWidth="1px"
      borderRadius="lg"
      overflow="hidden"
      p={8}
      textAlign="center"
      cursor="pointer"
      _hover={{ shadow: 'md', bg: 'gray.50' }}
    >
      <Image
        src={artist.image}
        alt={artist.name}
        borderRadius="full"
        boxSize="140px"
        mx="auto"
        mb={3}
        objectFit="cover"
      />
      <Text fontWeight="bold">{artist.name}</Text>
    </Box>
  </Link>
);

export default ArtistCard;
