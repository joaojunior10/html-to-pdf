
Convert HTML to PDF without losing format using wkhtmltopdf 0.12.6 (https://wkhtmltopdf.org)

Dependencies:
Linux or MacOS
https://wkhtmltopdf.org/downloads.html

Image with Ubuntu 20.04, OpenJDK-8 and wkhtmltopdf 0.12.6:
https://github.com/joaojunior10/wkhtmltopdf-image
https://hub.docker.com/repository/docker/joaojunior10/wkhtmltopdf

Build:
mvn clean install

Run:
mvn spring-boot:run

Run with docker:
mvn clean install
./build-docker.sh
./run-docker.sh

Usage:
curl --location --request POST 'http://localhost:8080/' \
--header 'Content-Type: text/plain' \
--data-raw '<html>'