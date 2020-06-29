package pl.allegro.tech.hermes.management.domain.topic;

import pl.allegro.tech.hermes.api.Topic;

public interface BrokerTopicManagement {

    void createTopic(Topic topic, Integer partitions, Integer replicationFactor);

    void removeTopic(Topic topic);

    void updateTopic(Topic topic);

    boolean topicExists(Topic topic);

}
