#!/usr/bin/env bash
# Удаляем контейнер. Если он отсутствует, то игнорируем ошибку удаления
docker rm -f postgres-library 2>/dev/null || true

# Запускаем контейнер
docker run \
    --name postgres-library \
    -d \
    -p 5432:5432/tcp \
    -e POSTGRES_USER=root \
    -e POSTGRES_PASSWORD=12345 \
    -e POSTGRES_DB=library \
    postgres:11.1 postgres -N 200

# Показываем статус контейнера с базой данных
docker ps -a -f name=postgres-library