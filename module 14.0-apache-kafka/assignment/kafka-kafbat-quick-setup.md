# Apache Kafka 4.3.0 + Kafbat UI — Ubuntu/WSL Quick Setup

This guide is for a **single-node local Kafka 4.3.0 KRaft setup** installed directly in Ubuntu/WSL, with **Kafbat UI running in Docker**.

> This setup follows the current Apache Kafka KRaft quick-start approach: generate a cluster UUID, format the storage with `--standalone`, start Kafka, create a topic, and then use Kafbat UI for visual administration.

## 1. Environment

Current setup:

```text
OS             : Ubuntu / WSL
Kafka          : 4.3.0
Kafka location : /opt/kafka
Kafka mode     : KRaft
Broker         : localhost:9092
Controller     : localhost:9093
Kafka data     : /tmp/kraft-combined-logs
Kafbat UI      : Docker
Kafbat UI       : http://localhost:8080
```

Check Kafka:

```bash
/opt/kafka/bin/kafka-topics.sh --version
```

Expected:

```text
4.3.0
```

Kafka requires Java 17+ for current releases.

Check Java:

```bash
java -version
```

---

# 2. Kafka Directory

Kafka is installed here:

```text
/opt/kafka
```

Useful directories:

```text
/opt/kafka/
├── bin/
│   ├── kafka-server-start.sh
│   ├── kafka-server-stop.sh
│   ├── kafka-storage.sh
│   ├── kafka-topics.sh
│   ├── kafka-console-producer.sh
│   ├── kafka-console-consumer.sh
│   └── kafka-consumer-groups.sh
│
└── config/
    ├── server.properties
    ├── broker.properties
    └── controller.properties
```

Use absolute paths if you are not inside `/opt/kafka`.

For example:

```bash
/opt/kafka/bin/kafka-topics.sh --version
```

Do not use:

```bash
/opt/bin/kafka-topics.sh
```

because Kafka is installed under `/opt/kafka/bin`.

---

# 3. Check Kafka KRaft Configuration

Check the important settings:

```bash
grep -E '^(process.roles|node.id|controller.quorum|listeners|advertised.listeners|log.dirs)' \
/opt/kafka/config/server.properties
```

For the current single-node setup, the important properties are:

```properties
process.roles=broker,controller
node.id=1
controller.quorum.bootstrap.servers=localhost:9093
listeners=PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093
log.dirs=/tmp/kraft-combined-logs
```

---

# 4. Fresh Kafka KRaft Setup

Use this section only when creating/resetting a local Kafka cluster.

## 4.1 Stop Kafka

If Kafka is running in the foreground:

```text
Ctrl+C
```

Check:

```bash
ps aux | grep '[k]afka'
```

## 4.2 Remove old local Kafka data

**Warning:** this deletes the local Kafka topics/messages and metadata.

```bash
rm -rf /tmp/kraft-combined-logs
```

## 4.3 Generate a new cluster UUID

From any directory:

```bash
KAFKA_CLUSTER_ID="$(/opt/kafka/bin/kafka-storage.sh random-uuid)"
```

Display it:

```bash
echo "$KAFKA_CLUSTER_ID"
```

Do not type the `$` prompt shown in documentation examples.

## 4.4 Format the KRaft storage

Because this is a fresh single-node KRaft environment:

```bash
/opt/kafka/bin/kafka-storage.sh format \
  --standalone \
  -t "$KAFKA_CLUSTER_ID" \
  -c /opt/kafka/config/server.properties
```

Verify:

```bash
ls -l /tmp/kraft-combined-logs
```

You should see:

```text
meta.properties
```

---

# 5. Start Kafka

Start the broker/controller:

```bash
/opt/kafka/bin/kafka-server-start.sh \
  /opt/kafka/config/server.properties
```

Keep this terminal running.

Open another Ubuntu/WSL terminal for Kafka CLI commands.

---

# 6. Verify Kafka Is Running

Check the process:

```bash
ps aux | grep '[k]afka'
```

Check ports:

```bash
ss -ltnp | grep -E '9092|9093'
```

Expected:

```text
9092  Kafka broker
9093  KRaft controller
```

Test the broker:

```bash
/opt/kafka/bin/kafka-topics.sh \
  --bootstrap-server localhost:9092 \
  --list
```

If no topics exist, the command can return an empty result. That is normal.

---

# 7. Create a Topic

Create a simple topic:

```bash
/opt/kafka/bin/kafka-topics.sh \
  --create \
  --topic test-events \
  --bootstrap-server localhost:9092
```

For an explicit learning setup with 3 partitions:

```bash
/opt/kafka/bin/kafka-topics.sh \
  --create \
  --topic test-events \
  --partitions 3 \
  --replication-factor 1 \
  --bootstrap-server localhost:9092
```

A single Kafka broker can use replication factor `1`. You need multiple brokers for replication factor `>1`.

---

# 8. List Topics

```bash
/opt/kafka/bin/kafka-topics.sh \
  --list \
  --bootstrap-server localhost:9092
```

Expected:

```text
test-events
```

---

# 9. Describe Topic and Partitions

```bash
/opt/kafka/bin/kafka-topics.sh \
  --describe \
  --topic test-events \
  --bootstrap-server localhost:9092
```

Example:

```text
Topic: test-events
PartitionCount: 3
ReplicationFactor: 1

Partition: 0
Partition: 1
Partition: 2
```

Important fields:

| Field | Meaning |
|---|---|
| PartitionCount | Number of partitions |
| ReplicationFactor | Number of copies of each partition |
| Leader | Broker currently leading the partition |
| Replicas | Brokers storing the partition |
| Isr | In-sync replicas |

---

# 10. Producer

Start a console producer:

```bash
/opt/kafka/bin/kafka-console-producer.sh \
  --topic test-events \
  --bootstrap-server localhost:9092
```

Enter:

```text
Hello Kafka
Order created
Payment completed
```

Press Enter after each message.

---

# 11. Consumer

Open another terminal:

```bash
/opt/kafka/bin/kafka-console-consumer.sh \
  --topic test-events \
  --bootstrap-server localhost:9092 \
  --from-beginning
```

You should see:

```text
Hello Kafka
Order created
Payment completed
```

---

# 12. Consumer Group

Start a consumer using a group:

```bash
/opt/kafka/bin/kafka-console-consumer.sh \
  --topic test-events \
  --bootstrap-server localhost:9092 \
  --group test-consumer-group
```

List groups:

```bash
/opt/kafka/bin/kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --list
```

Describe a group:

```bash
/opt/kafka/bin/kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --describe \
  --group test-consumer-group
```

This is useful for learning:

```text
GROUP
TOPIC
PARTITION
CURRENT-OFFSET
LOG-END-OFFSET
LAG
```

---

# 13. Kafbat UI

Kafbat UI is a web interface for managing and monitoring Kafka.

It can be used to:

- View Kafka clusters
- View brokers
- Create/delete topics
- View partitions
- View topic configuration
- Produce messages
- Consume/view messages
- View consumer groups
- Inspect consumer lag
- View offsets
- Manage Kafka Connect when configured
- Work with Schema Registry when configured

Official project:

https://github.com/kafbat/kafka-ui

---

# 14. Important: Kafka Runs in WSL, Kafbat Runs in Docker

Your architecture is:

```text
Windows
   |
   +-- WSL Ubuntu
   |      |
   |      +-- Kafka 4.3.0
   |             |
   |             +-- Broker :9092
   |             +-- Controller :9093
   |
   +-- Docker
          |
          +-- Kafbat UI :8080
```

Do **not** configure Kafbat's Docker container to use:

```text
localhost:9092
```

inside the container.

Inside a container, `localhost` means the container itself, not your WSL Kafka process.

For a clean local setup, expose a dedicated Docker-accessible Kafka listener.

---

# 15. Recommended Kafka Listener Configuration for Kafbat

Keep the normal local listener:

```properties
listeners=PLAINTEXT://0.0.0.0:9092,CONTROLLER://0.0.0.0:9093
```

Add a Docker listener on port `29092`.

Your listener configuration should be conceptually:

```properties
listeners=PLAINTEXT://0.0.0.0:9092,DOCKER://0.0.0.0:29092,CONTROLLER://0.0.0.0:9093

advertised.listeners=PLAINTEXT://localhost:9092,DOCKER://host.docker.internal:29092

listener.security.protocol.map=PLAINTEXT:PLAINTEXT,DOCKER:PLAINTEXT,CONTROLLER:PLAINTEXT
```

Keep the existing KRaft controller settings unchanged.

For example:

```properties
process.roles=broker,controller
node.id=1
controller.quorum.bootstrap.servers=localhost:9093
controller.listener.names=CONTROLLER
inter.broker.listener.name=PLAINTEXT
```

The important idea is:

```text
Ubuntu/WSL applications
        |
        v
localhost:9092

Docker applications
        |
        v
host.docker.internal:29092
```

After changing listener configuration, restart Kafka.

---

# 16. Start Kafbat UI

Pull the image:

```bash
docker pull ghcr.io/kafbat/kafka-ui:latest
```

Run Kafbat:

```bash
docker run -d \
  --name kafbat-ui \
  --add-host=host.docker.internal:host-gateway \
  -p 8080:8080 \
  -e DYNAMIC_CONFIG_ENABLED=true \
  -e KAFKA_CLUSTERS_0_NAME=local-kafka \
  -e KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS=host.docker.internal:29092 \
  ghcr.io/kafbat/kafka-ui:latest
```

Check:

```bash
docker ps
```

Check Kafbat logs:

```bash
docker logs -f kafbat-ui
```

---

# 17. Open Kafbat UI

Open in your browser:

```text
http://localhost:8080
```

You should see:

```text
local-kafka
```

Select the cluster.

You can then inspect:

```text
Clusters
  |
  +-- Brokers
  |
  +-- Topics
  |     |
  |     +-- test-events
  |           |
  |           +-- Partitions
  |           +-- Messages
  |           +-- Configuration
  |
  +-- Consumer Groups
```

---

# 18. Create Topic From Kafbat

In Kafbat:

```text
Topics
   ↓
Create Topic
```

Example:

```text
Topic Name       : orders
Partitions       : 3
Replication      : 1
```

For this single-broker setup:

```text
Replication Factor = 1
```

Do not use replication factor `3` until you have at least three Kafka brokers.

---

# 19. Produce a Message From Kafbat

Open:

```text
Topics
  ↓
test-events
  ↓
Messages
```

Use the produce/send message option.

Example:

```json
{
  "orderId": 1001,
  "customer": "Arun",
  "status": "CREATED"
}
```

For learning, you can also send simple text:

```text
Hello from Kafbat
```

---

# 20. Verify the Same Message Using Kafka CLI

After producing through Kafbat:

```bash
/opt/kafka/bin/kafka-console-consumer.sh \
  --topic test-events \
  --bootstrap-server localhost:9092 \
  --from-beginning
```

This demonstrates that Kafbat is only a UI/client connecting to the same Kafka broker.

```text
Kafbat UI
    |
    | Kafka protocol
    v
Kafka Broker
    |
    v
test-events
    |
    +-- Partition 0
    +-- Partition 1
    +-- Partition 2
```

---

# 21. Check Kafbat Container

Check status:

```bash
docker ps
```

Check logs:

```bash
docker logs kafbat-ui
```

Stop:

```bash
docker stop kafbat-ui
```

Start again:

```bash
docker start kafbat-ui
```

Remove:

```bash
docker rm -f kafbat-ui
```

---

# 22. Useful Kafka CLI Commands

## List topics

```bash
/opt/kafka/bin/kafka-topics.sh \
  --list \
  --bootstrap-server localhost:9092
```

## Create topic

```bash
/opt/kafka/bin/kafka-topics.sh \
  --create \
  --topic my-topic \
  --partitions 3 \
  --replication-factor 1 \
  --bootstrap-server localhost:9092
```

## Describe topic

```bash
/opt/kafka/bin/kafka-topics.sh \
  --describe \
  --topic my-topic \
  --bootstrap-server localhost:9092
```

## Delete topic

```bash
/opt/kafka/bin/kafka-topics.sh \
  --delete \
  --topic my-topic \
  --bootstrap-server localhost:9092
```

## Produce

```bash
/opt/kafka/bin/kafka-console-producer.sh \
  --topic my-topic \
  --bootstrap-server localhost:9092
```

## Consume

```bash
/opt/kafka/bin/kafka-console-consumer.sh \
  --topic my-topic \
  --bootstrap-server localhost:9092
```

## Consume from beginning

```bash
/opt/kafka/bin/kafka-console-consumer.sh \
  --topic my-topic \
  --bootstrap-server localhost:9092 \
  --from-beginning
```

## List consumer groups

```bash
/opt/kafka/bin/kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --list
```

## Describe consumer group

```bash
/opt/kafka/bin/kafka-consumer-groups.sh \
  --bootstrap-server localhost:9092 \
  --describe \
  --group my-group
```

---

# 23. Check Kafka Broker Version

```bash
/opt/kafka/bin/kafka-topics.sh --version
```

Expected:

```text
4.3.0
```

---

# 24. Stop Kafka

If Kafka is running in the foreground:

```text
Ctrl+C
```

Kafka's local quickstart also uses `Ctrl+C` to stop the broker.

---

# 25. Reset Local Kafka Completely

**Warning: destructive operation. Deletes local Kafka data.**

Stop Kafka first.

Then:

```bash
rm -rf /tmp/kraft-combined-logs
```

Generate a new cluster ID:

```bash
KAFKA_CLUSTER_ID="$(/opt/kafka/bin/kafka-storage.sh random-uuid)"
```

Format:

```bash
/opt/kafka/bin/kafka-storage.sh format \
  --standalone \
  -t "$KAFKA_CLUSTER_ID" \
  -c /opt/kafka/config/server.properties
```

Start again:

```bash
/opt/kafka/bin/kafka-server-start.sh \
  /opt/kafka/config/server.properties
```

---

# 26. Troubleshooting

## `No readable meta.properties files found`

The KRaft storage has not been formatted.

Use:

```bash
KAFKA_CLUSTER_ID="$(/opt/kafka/bin/kafka-storage.sh random-uuid)"
```

Then:

```bash
/opt/kafka/bin/kafka-storage.sh format \
  --standalone \
  -t "$KAFKA_CLUSTER_ID" \
  -c /opt/kafka/config/server.properties
```

## `Topic does not exist`

List topics:

```bash
/opt/kafka/bin/kafka-topics.sh \
  --list \
  --bootstrap-server localhost:9092
```

Create the topic if required.

## `Connection refused`

Check Kafka:

```bash
ps aux | grep '[k]afka'
```

Check port:

```bash
ss -ltnp | grep 9092
```

## Kafbat cannot connect to Kafka

Check:

```bash
docker logs kafbat-ui
```

Verify that Kafbat uses:

```text
host.docker.internal:29092
```

and that Kafka advertises:

```text
DOCKER://host.docker.internal:29092
```

Also verify that the Docker listener is included in:

```properties
listeners=
advertised.listeners=
listener.security.protocol.map=
```

---

# 27. Recommended Learning Sequence

Use this setup to learn Kafka in this order:

```text
1. Kafka Broker
       ↓
2. KRaft
       ↓
3. Topic
       ↓
4. Partition
       ↓
5. Producer
       ↓
6. Consumer
       ↓
7. Consumer Group
       ↓
8. Offset
       ↓
9. Consumer Lag
       ↓
10. Partition Assignment
       ↓
11. Replication
       ↓
12. Multiple Brokers
       ↓
13. Retention
       ↓
14. Compression
       ↓
15. Producer Acknowledgements
       ↓
16. Idempotent Producer
       ↓
17. Transactions
       ↓
18. Kafka Connect
       ↓
19. Kafka Streams
       ↓
20. Spring Boot + Kafka
```

---

# 28. Architecture for This Local Setup

```text
                    Windows
                       |
             +---------+---------+
             |                   |
          WSL/Ubuntu           Docker
             |                   |
             |                Kafbat UI
             |                  :8080
             |                   |
             |                   |
          Kafka 4.3.0 <----------+
             |
       +-----+------+
       |            |
    Broker       Controller
    :9092          :9093
       |
       +----------------+
       |                |
   test-events        orders
       |
   +---+---+---+
   |   |   |   |
  P0  P1  P2  ...
```

## Key addresses

| Component | Address |
|---|---|
| Kafka from Ubuntu/WSL | `localhost:9092` |
| Kafka controller | `localhost:9093` |
| Kafka from Docker | `host.docker.internal:29092` |
| Kafbat UI | `http://localhost:8080` |
| Kafka installation | `/opt/kafka` |
| Kafka data | `/tmp/kraft-combined-logs` |

---

## Official references

- Apache Kafka Quickstart: https://kafka.apache.org/quickstart/
- Apache Kafka KRaft documentation: https://kafka.apache.org/40/operations/kraft/
- Kafbat UI: https://github.com/kafbat/kafka-ui
- Kafbat UI configuration: https://ui.docs.kafbat.io/configuration/misc-configuration-properties
