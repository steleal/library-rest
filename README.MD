## Курсовой проект "Библиотека"

Web Api на базе REST.
### Сборка
Требования для сборки: JDK 8+
Сборка проекта:
```shell
./mvnw install -DskipTests
```

### Запуск проекта.
Все пути в командах - относительно папки проекта.
Для запуска проекта с демо-базой требуется докер.
Путь к базе менять в `application.xml`.
Запуск докер-контейнера с демо-базой:
```shell
./src/main/sh/docker_postgres_run.sh
```
Запуск собранного приложения:
```shell
java -jar -Dfile.encoding=UTF-8 ./target/library-rest-0.0.1-SNAPSHOT.jar
```

### Примеры запросов cUrl:
```
curl --request GET 'http://localhost:8080/library/api/authors'

curl --request GET 'http://localhost:8080/library/api/authors/3'

curl --request POST 'http://localhost:8080/library/api/authors' \
--header 'Content-Type: application/json' \
--data-raw '    {
"fullName": "Новый автор",
"birthday": 2000
}'

curl --request PUT 'http://localhost:8080/library/api/authors/5' \
--header 'Content-Type: application/json' \
--data-raw '    {
"fullName": "Петров-Водкин"
}'

curl --request DELETE 'http://localhost:8080/library/api/authors/6'



curl --request GET 'http://localhost:8080/library/api/books'

curl --request GET 'http://localhost:8080/library/api/books/8'

curl --request POST 'http://localhost:8080/library/api/books' \
--header 'Content-Type: application/json' \
--data-raw '{
"title": "Добавленная книга",
"authorId": 4,
"genreId": 2,
"publisherId": 2,
"publishYear": 2001
}'

curl --request PUT 'http://localhost:8080/library/api/books/9' \
--header 'Content-Type: application/json' \
--data-raw '{
"title": "Обновленная книга",
"authorId": 14,
"genreId": 2,
"publisherId": 2,
"publishYear": 2001
}'

curl --request DELETE 'http://localhost:8080/library/api/books/9'
```


