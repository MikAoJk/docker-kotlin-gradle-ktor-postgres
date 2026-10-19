FROM gcr.io/distroless/java25-debian13@sha256:655518e68ffb50b94ba8caadc1133a61dbcd32c20ad256a209b12dd2239a465c
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
