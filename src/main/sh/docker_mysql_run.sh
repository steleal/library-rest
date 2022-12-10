#!/usr/bin/env bash
docker stop mysql-library > /dev/null || true
docker rm -f mysql-library > /dev/null || true
docker run -d \
        --name mysql-library \
        -e MYSQL_ROOT_PASSWORD=12345 \
        -e MYSQL_DATABASE=library \
        -p 3306:3306 \
        mysql