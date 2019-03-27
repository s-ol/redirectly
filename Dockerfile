FROM java:8-alpine AS build-env
ADD https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein /bin/lein
RUN apk update && apk add bash openssl
RUN /bin/chmod +x /bin/lein && lein
COPY . /build
RUN cd /build && lein uberjar

FROM java:8-alpine
RUN mkdir -p /app/config
COPY --from=build-env /build/target/redirectly-*-standalone.jar /app/redirectly-standalone.jar
CMD java -cp /app/config:/app/redirectly-standalone.jar redirectly.deploy
EXPOSE 3000
