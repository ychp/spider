FROM openjdk:8
ADD spider.jar application.jar

ENV TZ=Asia/Shanghai
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

EXPOSE 8200

ENTRYPOINT ["sh","-c", "java -Duser.timezone=Asia/Shanghai -jar application.jar --spring.profiles.active=prod"]