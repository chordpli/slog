#!/bin/bash

# Download Docker image from S3 bucket
aws s3 cp s3://my-work-project/slog-${{ github.sha }}.tar.gz .

# Load Docker image into local Docker registry
docker load < slog-${{ github.sha }}.tar.gz

# Stop and remove any existing containers
docker stop slog
docker rm slog

# Run a new container using the newly loaded Docker image
docker run -d -p 8080:8080 --name slog slog:${{ github.sha }}