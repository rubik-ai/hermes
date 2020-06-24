## Build a jar 
./gradlew clean distZip -Pdistribution

# Run on local machine

## Run Management
cd hermes-management/build/distributions/ && unzip hermes-management-1.4.7-SNAPSHOT.zip && cd - 
./hermes-management/build/distributions/hermes-management-1.4.7-SNAPSHOT/bin/hermes-management

## Run Consumers
cd hermes-consumers/build/distributions/ && unzip hermes-consumers-1.4.7-SNAPSHOT.zip && cd - 
./hermes-consumers/build/distributions/hermes-consumers-1.4.7-SNAPSHOT/bin/hermes-consumers

## Run Frontend
cd hermes-frontend/build/distributions/ && unzip hermes-frontend-1.4.7-SNAPSHOT.zip && cd - 
./hermes-frontend/build/distributions/hermes-frontend-1.4.7-SNAPSHOT/bin/hermes-frontend


# Run with docker

## Build docker image
docker build -t rubiklabs/hermes:0.0.1 .

## Run Management
docker run --rm -it \
                -e KAFKA_BOOTSTRAP_SERVERS=kafka:9092 \
                -e KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 \
                -e HERMES_MANAGEMENT_OPTS="-Dschema.repository.serverUrl=http://registry:8081" \
                -p 8090:8090 \
                rubiklabs/hermes:latest /opt/hermes-management/bin/hermes-management

## Run Consumer
docker run --rm -it \
                -e HERMES_CONSUMERS_OPTS="-Dzookeeper.connect.string=zk:2181 -Dkafka.broker.list=kafka:9092 -Dschema.repository.serverUrl=http://registry:8081" \
                -p 8000:8000 \
                rubiklabs/hermes:latest /opt/hermes-consumers/bin/hermes-consumers

