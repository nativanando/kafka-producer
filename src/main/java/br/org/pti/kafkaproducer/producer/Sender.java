/**
 * This file represents a kafka agent to send messages in a specific topic
 * 
 */
package br.org.pti.kafkaproducer.producer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author fernando.luiz
 *
 */
@Service
public class Sender {
	
	private static final Logger LOG = LoggerFactory.getLogger(Sender.class);
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	@Value("${app.topic.foo}")
	private String topic;
	
	public void send(String message) {
		try {
			LOG.info("Sendind message='{}' to topic='{}'", message, topic);
			kafkaTemplate.send(topic, message);	
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(e.getMessage());
		}
	}
}
