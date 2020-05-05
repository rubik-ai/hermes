package pl.allegro.tech.hermes.integration;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryOneTime;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.zookeeper.KeeperException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import spock.lang.Shared;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Collection;
import java.util.concurrent.TimeUnit;


public class ZookeeperSecureTest extends IntegrationTest {

    @ClassRule
    public static DockerComposeContainer environment =
            new DockerComposeContainer(new File("src/integration/resources/zk-secure-compose.yml"))
                    .withExposedService("zookeeper_1", 32181);

    @Test
    public void testProducerConsumer() throws Exception {
        environment.start();

        String zkhost = environment.getServiceHost("zookeeper_1", 32181);
        Integer zkport = environment.getServicePort("zookeeper_1", 32181);

        CuratorFramework client = CuratorFrameworkFactory.builder()
                .connectString(zkhost + ":" + zkport.toString())
                .authorization("digest", "super:D/InIHSb7yEEbrWz8b9l71RjZJU=".getBytes())
                .retryPolicy(new RetryOneTime(10))
                .build();

        client.blockUntilConnected(1, TimeUnit.SECONDS);
        client.start();

        try {
            client.create().forPath("/test");
            client.setData().forPath("/test", "test".getBytes());
        } catch (KeeperException.NoAuthException e) {
            Assert.fail("Auth failed");
        } catch (Exception e) {
            throw e;
        }
        client.close();
    }
}
