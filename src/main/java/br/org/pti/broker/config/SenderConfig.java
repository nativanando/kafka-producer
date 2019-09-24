/**
 * Configuration file to established the communication between the kafka sender and a kafka instance
 * 
 */
package br.org.pti.broker.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author fernando.luiz
 *
 */
@Configuration
public class SenderConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServers;

	/**
	 * Set up the Kafka broker properties
	 * 
	 * @return
	 */
	@Bean
	public Map<String, Object> producerConfigs() {
		Map<String, Object> props = new HashMap();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		return props;
	}

	/**
	 * Create a producer instance
	 * 
	 * @return
	 */
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		return new DefaultKafkaProducerFactory<>(producerConfigs());
	}

	/**
	 * Available kafka high-level operations.
	 * 
	 * @return
	 */
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

}
