FROM openjdk:8-jre-alpine
RUN apk update && apk upgrade && apk add netcat-openbsd
RUN mkdir -p /usr/local/zipkin-server
ADD @project.build.finalName@.jar /usr/local/zipkin-server/
ADD run.sh run.sh
RUN chmod +x run.sh
CMD ./run.sh
