# 🎵 Spotify Dashboard  
**Breakable Toy II**

Spotify Dashboard with a **Spring Boot backend** and a **React + TypeScript frontend**.  
It uses the Spotify Web API to let users explore their favorite artists, albums, and more.

---

## ✨ Features

- 🔐 OAuth 2.0 authentication flow using the Spotify Web API  
- 🎤 Display the user’s top 10 artists  
- 📄 Show detailed information about a specific artist  
- 💿 View album details and track list  
- 🔍 Search for artists, albums, playlists, or tracks  
- 🖥️ Intuitive and responsive UI using Chakra UI  

---

## 📁 Project Structure

```
SpotifyAPI/
├── backend/    # REST API built with Spring Boot
└── frontend/   # Web app built with React and TypeScript
```

---

## ✅ Requirements

- Docker & Docker Compose  
- Spotify Developer account:
  - Client ID  
  - Client Secret  

---

## 🚀 How to Run

### 1️⃣ Set your credentials in `docker-compose.yml`

Go to the root of the project and open `docker-compose.yml`.  
Fill in your Spotify credentials:

```yaml
services:
  backend:
    environment:
      - SPOTIFY_CLIENT_ID=your_spotify_client_id
      - SPOTIFY_CLIENT_SECRET=your_spotify_client_secret
      - SPOTIFY_REDIRECT_URI=http://localhost:8080/auth/spotify/callback
      - SPOTIFY_AUTHORIZE_URL=https://accounts.spotify.com/authorize
      - SPOTIFY_TOKEN_URL=https://accounts.spotify.com/api/token
      - SPOTIFY_SCOPES=user-top-read user-read-email
```

---

### 2️⃣ Run the app

```bash
docker-compose up --build
```

- Frontend: http://localhost:5173  
- Backend: http://localhost:8080

---

## 🧪 Testing

### Backend

```bash
cd backend
mvn test
```

### Frontend

```bash
cd frontend
npm run test
```

---

## 🛠 Tech Stack

- **Frontend**: React, TypeScript, Chakra UI  
- **Backend**: Java 21, Spring Boot, RestTemplate, ObjectMapper  
- **Auth**: OAuth 2.0 (Authorization Code Flow)  
- **Testing**: Mockito, Vitest  
- **Deployment**: Docker, Docker Compose


