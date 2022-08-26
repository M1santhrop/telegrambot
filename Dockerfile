FROM adoptopenjdk/openjdk11:ubi
ARG JAR_FILE=target/*.jar
ENV BOT_NAME=test_javarush_m1santhrop_bot
ENV BOT_TOKEN=5781602266:AAF9Wi1WYfCH8Wes6Xp-a1KS1UvK-tufwvQ
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-Dbot.username=${BOT_NAME}", "-Dbot.token=${BOT_TOKEN}", "-jar", "/app.jar"]
