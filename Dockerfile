# 빌드 단계
FROM gradle:7.6-jdk11-alpine AS builder
WORKDIR /build

# gradle 빌드에 필요한 파일 복사
COPY build.gradle settings.gradle gradlew gradlew.bat /build/
COPY gradle /build/gradle
RUN ./gradlew build -x test

# 애플리케이션 빌드
COPY src /build/src
RUN ./gradlew build -x test

# 런타임 단계
FROM openjdk:11-jre-slim
WORKDIR /app

# 빌드된 jar 파일 복사
COPY --from=builder /build/build/libs/*.jar /app/slog.jar

# 환경변수 설정 (옵션)
ENV SPRING_PROFILES_ACTIVE=prod

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/slog.jar"]