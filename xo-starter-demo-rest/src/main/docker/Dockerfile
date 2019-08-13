FROM jnoee/java:8

ENV WORK_PATH /home/apps/@project.artifactId@
ENV APP_NAME @project.build.finalName@.@project.packaging@

ENV JAVA_SERVER_OPTS -Djava.security.egd=file:/dev/./urandom -server

COPY $APP_NAME $WORK_PATH/

WORKDIR $WORK_PATH

ENTRYPOINT java $JAVA_SERVER_OPTS $JAVA_OPTS -jar $APP_NAME

HEALTHCHECK CMD curl -fs localhost:8080/actuator/health || exit 1