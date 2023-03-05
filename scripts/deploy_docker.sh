#!/bin/bash

# (1) Docker 이미지를 S3에서 다운로드
aws s3 cp s3://$S3_BUCKET_NAME/slog-${{ github.sha }}.tar.gz slog-${{ github.sha }}.tar.gz

# (2) Docker 이미지를 로드
docker load < slog-${{ github.sha }}.tar.gz

# (3) 이전에 실행중인 컨테이너가 있다면 삭제
docker stop slog || true
docker rm slog || true

# (4) Docker 컨테이너 실행
docker run -d --name slog -p 8080:8080 slog:${{ github.sha }}