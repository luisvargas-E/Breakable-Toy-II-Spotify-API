import { Box, Heading, SimpleGrid, Spinner, Text } from '@chakra-ui/react';
import { useEffect, useState } from 'react';
import axios from 'axios';
import SearchBar from '../pageComponents/SearchBar';
import { useAuth } from '../auth/AuthContext';
import ArtistCard from '../pageComponents/ArtistCard';
import ResultSection from '../pageComponents/ResultSection';

interface Artist {
  id: string;
  name: string;
  image: string;
  spotifyUrl: string;
}

const Layout = () => {
  const { accessToken } = useAuth();
  const [topArtists, setTopArtists] = useState<Artist[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchResults, setSearchResults] = useState<any>(null);
  const [query, setQuery] = useState('');

  useEffect(() => {
    if (!accessToken) return;
    axios
      .get<Artist[]>('http://localhost:8080/user/favorites/artists', {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .then((res) => {
        setTopArtists(res.data);
        setLoading(false);
      })
      .catch((err) => {
        console.error('Error fetching top artists:', err);
        setLoading(false);
      });
  }, [accessToken]);

  const handleSearch = (searchQuery: string) => {
    setQuery(searchQuery);

    if (!searchQuery.trim()) {
      setSearchResults(null);
      return;
    }

    axios
      .get(`http://localhost:8080/spotify/search?query=${searchQuery}&type=artist,track,album`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      })
      .then((res) => setSearchResults(res.data))
      .catch((err) => console.error('Error searching:', err));
  };

  if (!accessToken) {
    return <Text color="red.500">Please login first.</Text>;
  }

  return (
    <Box p={6}>
      <SearchBar onSearch={handleSearch} />

      {query.trim() === '' ? (
        <>
          <Heading mt={8} size="md">
            Your Top Artists
          </Heading>
          {loading ? (
            <Spinner mt={4} />
          ) : (
            <SimpleGrid columns={5} gap={4} mt={4}>
              {topArtists.map((artist) => (
                <ArtistCard key={artist.id} artist={artist} />
              ))}
            </SimpleGrid>
          )}
        </>
      ) : (
        <>
          <Heading mt={8} size="md">Top Result</Heading>
          <ResultSection results={searchResults} />
        </>
      )}
    </Box>
  );
};

export default Layout;
