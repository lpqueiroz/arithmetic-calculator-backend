# Install Java and set the JAVA_HOME variable
FROM openjdk:17-slim
ENV JAVA_HOME /usr/lib/jvm/java-17-openjdk
ENV PATH $PATH:/usr/lib/jvm/java-17-openjdk/jre/bin:/usr/lib/jvm/java-17-openjdk/bin

ENV SBT_VERSION 1.3.3

# Install curl and vim
RUN \
  apt-get update && \
  apt-get -y install curl && \
  apt-get -y install vim

# Install both scala and sbt
RUN \
  curl -L -o sbt-$SBT_VERSION.deb https://repo.scala-sbt.org/scalasbt/debian/sbt-$SBT_VERSION.deb && \
  dpkg -i sbt-$SBT_VERSION.deb && \
  rm sbt-$SBT_VERSION.deb && \
  apt-get update && \
  apt-get -y install sbt

WORKDIR /var/www
COPY . /var/www

EXPOSE 9000

CMD ["sbt", "run"]