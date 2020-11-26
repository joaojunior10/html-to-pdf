FROM joaojunior10/wkhtmltopdf:0.12.6
LABEL maintainer="jfjunior10@gmail.com"

ADD target/htmltopdf.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
EXPOSE 8080
