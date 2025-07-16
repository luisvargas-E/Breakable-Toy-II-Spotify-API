import { Box, Input } from '@chakra-ui/react';
import { useState, useEffect } from 'react';

interface SearchBarProps {
  onSearch: (query: string) => void;
}

const SearchBar = ({ onSearch }: SearchBarProps) => {
  const [query, setQuery] = useState('');

  useEffect(() => {
    const delayDebounce = setTimeout(() => {
      onSearch(query);
    }, 400);

    return () => clearTimeout(delayDebounce);
  }, [query]);

  return (
    <Box maxW="600px" mx="auto">
      <Input
        type="text"
        placeholder="Search by artist, song, or album..."
        value={query}
        onChange={(e) => setQuery(e.target.value)}
        borderRadius="full"
      />
    </Box>
  );
};

export default SearchBar;