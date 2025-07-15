export interface Artist {
  id: string;
  name: string;
  image: string;
  spotifyUrl: string;
}

export async function fetchTopArtists(token: string): Promise<Artist[]> {
  const response = await fetch('http://localhost:8080/user/favorites/artists', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    throw new Error('Failed to fetch top artists');
  }

  return response.json();
}

export default fetchTopArtists;
