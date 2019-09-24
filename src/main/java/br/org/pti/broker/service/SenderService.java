/**
 * This file represents a kafka agent to send messages in a specific topic
 * 
 */
package br.org.pti.broker.service;

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
public class SenderService {

	private static final Logger LOG = LoggerFactory.getLogger(SenderService.class);

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	@Value("${app.topic.foo}")
	private String topic;

	/**
	 * Send a message to kafkaTemplate
	 * 
	 * @param A message to be send
	 * @return
	 */
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
