FROM gcr.io/distroless/java25-debian13@sha256:6558dff7db91bf89d34254014dec8ff1f0a3e13f48a575d5a51ce01d1597d671
WORKDIR /app
COPY build/install/*/lib /lib
ENV JAVA_OPTS="-Dlogback.configurationFile=logback.xml"
ENV TZ="Europe/Oslo"
EXPOSE 8080
USER nonroot
ENTRYPOINT ["java", "-cp", "/lib/*", "io.github.mikaojk.ApplicationKt"]
