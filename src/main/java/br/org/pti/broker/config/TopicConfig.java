/**
 * 
 */
package br.org.pti.broker.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * @author fernando.luiz
 *
 */
@Configuration
public class TopicConfig {

	@Value("${spring.kafka.bootstrap-servers}")
	private String bootstrapServer;

	/**
	 * Create an admin config to create the default topic
	 * 
	 * @return KafkaAdmin
	 */
	@Bean
	public KafkaAdmin admin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);
		return new KafkaAdmin(configs);
	}

	/**
	 * Create an default topic when the application is started
	 * 
	 * @return NewTopic
	 */
	@Bean
	public NewTopic defaultTopic() {
		return new NewTopic("default_topic_app", 2, (short) 1);
	}

}
