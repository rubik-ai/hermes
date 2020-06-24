FROM rubiklabs/openjdk:8-jre

MAINTAINER rubik

RUN apt-get update \
  && apt-get install unzip wget bash -y

ENV MANAGEMENT_ARCHIVE_NAME="hermes-management-latest.zip"

ENV CONSUMERS_ARCHIVE_NAME="hermes-consumer-latest.zip"

ADD hermes-management/build/distributions/hermes-management-*-SNAPSHOT.zip "/tmp/${MANAGEMENT_ARCHIVE_NAME}"

ADD hermes-consumers/build/distributions/hermes-consumers-*-SNAPSHOT.zip "/tmp/${CONSUMERS_ARCHIVE_NAME}"

RUN  unzip -q "/tmp/${MANAGEMENT_ARCHIVE_NAME}" -d /opt \
  && rm "/tmp/${MANAGEMENT_ARCHIVE_NAME}" \
  && mv /opt/hermes-management-* /opt/hermes-management

RUN  unzip -q "/tmp/${CONSUMERS_ARCHIVE_NAME}" -d /opt \
  && rm "/tmp/${CONSUMERS_ARCHIVE_NAME}" \
  && mv /opt/hermes-consumers-* /opt/hermes-consumers

ENV SPRING_CONFIG_LOCATION="file:///etc/hermes/management.yaml"
ADD hermes-management/src/main/resources/application.yaml /etc/hermes/management.yaml

