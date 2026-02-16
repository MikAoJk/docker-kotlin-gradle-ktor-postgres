FROM gcr.io/distroless/java21-debian12@sha256:3525cafa2114ed879d3554acbbd3ba1b388e3cd3e8833ff58713ba133b0e1173
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
