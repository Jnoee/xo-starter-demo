FROM hub.sz.cntest.com:5000/centos7.4-jdk8

ENV WORK_PATH /home/dev/demo
ENV APP_NAME @project.build.finalName@.@project.packaging@
ENV APP_VERSION @project.version@

EXPOSE 8000

ENV spring.profiles.active test

COPY $APP_NAME $WORK_PATH/

WORKDIR $WORK_PATH

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom", "-server"]
CMD ["-jar", "@project.build.finalName@.@project.packaging@"]

HEALTHCHECK CMD curl -fs localhost:8000/actuator/health || exit 1