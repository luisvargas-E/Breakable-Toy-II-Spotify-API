# Etapa 1: Construcción del proyecto
FROM node:20-alpine AS build
WORKDIR /app
COPY . .
RUN npm install && npm run build

# Etapa 2: Imagen ligera de producción usando Nginx
FROM nginx:stable-alpine
COPY --from=build /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
