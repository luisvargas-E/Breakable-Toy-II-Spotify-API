const baseUrl = import.meta.env.VITE_BACKEND_URL || 'http://localhost:8080';

export const login = () => {
  window.location.href = `${baseUrl}/auth/spotify/login`;
};
