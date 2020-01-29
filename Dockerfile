FROM openjdk:8-jre-alpine
COPY . /app
WORKDIR /app
CMD java  -agentlib:jdwp=transport=dt_socket,address=20799,suspend=n,server=y -jar target/*.jar