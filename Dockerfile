FROM suranagivinod/openjdk8
EXPOSE 8080:8080
RUN mkdir /app
COPY ./build/install/blog/ /app/
WORKDIR /app/bin
CMD ["./blog"]