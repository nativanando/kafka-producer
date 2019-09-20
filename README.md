# Create a topic as example

docker exec -ti container-id kafka-topics --create --topic second-data-topic --partitions 2 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181
docker exec -ti 3fab8db0fd87 kafka-topics --create --topic first-data-topic --partitions 2 --replication-factor 1 --if-not-exists --zookeeper zookeeper:2181

docker exec -ti 3fab8db0fd87 kafka-topics --describe --topic first-data-topic --zookeeper zookeeper:2181

docker exec -ti 3fab8db0fd87 kafka-console-consumer --bootstrap-server kafka:9092 --topic first-data-topic --from-beginning

