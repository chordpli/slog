#!/usr/bin/env bash

# Build and tag Docker image
docker build -t slog:${{ github.sha }} .
docker tag slog:${{ github.sha }} slog:latest

# Push Docker image to Docker Hub or your private registry
docker push slog:${{ github.sha }}
docker push slog:latest

# Stop and remove existing container (if exists)
docker stop slog || true
docker rm slog || true

# Run new container with the new image
docker run -d -p 8080:8080 --name slog slog:${{ github.sha }}