prerequisite:
1. JDK >= 11
2. Maven 
3. MYSQL DB

Step-step
1. git clone
2. run sql-script
3. cd source
4. mvn compile quarkus:dev

ENDPOINT AND SAMPLE REQUEST
1. Movie List
curl --location 'localhost:8080/Movies'

2. Movie Detail
curl --location 'localhost:8080/Movies/4'

3. Add Movie
curl --location 'localhost:8080/Movies' \
--header 'Content-Type: application/json' \
--data '{
    "id": 1,
    "title": "Pengabdi Setan 2 Comunion",
    "description": "dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
    "rating": 7.0,
    "image": "",
    "created_at": "2022-08-01 10:56:31",
    "updated_at": "2022-08-13 09:30:23"
}'

4. Update Movie
curl --location --request PATCH 'localhost:8080/Movies/2' \
--header 'Content-Type: application/json' \
--data '{
    "id": 3,
    "title": "Updated Pengabdi Setan 2 Comunion",
    "description": "dalah sebuah film horor Indonesia tahun 2022 yang disutradarai dan ditulis oleh Joko Anwar sebagai sekuel dari film tahun 2017, Pengabdi Setan.",
    "rating": 7.0,
    "image": "",
    "created_at": "2022-08-01 10:56:31",
    "updated_at": "2022-08-13 09:30:23"
}'

5. Delete Movie
curl --location --request DELETE 'localhost:8080/Movies/3'