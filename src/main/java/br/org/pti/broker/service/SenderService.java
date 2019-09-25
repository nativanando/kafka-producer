/**
 * This file represents a kafka agent to send messages in a specific topic
 * 
 */
package br.org.pti.broker.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
	public void send() {
		try {
			createThreads();
		} catch (Exception e) {
			// TODO: handle exception
			LOG.error(e.getMessage());
		}
	}
	
	public void createThreads() {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 5; i++) { //create 5 threads
		   threadPool.submit(new Runnable() {
		       public void run() {
					long increment = 0L;
					while (true) {
						Random tempreature = new Random();
						DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
						Date date = new Date();
						String uniqueID = UUID.randomUUID().toString();
						String newMessage = "{'uuid':"+uniqueID+", 'value':"+tempreature.nextFloat() * (50 - 0)+", 'ts': "+dateFormat.format(date)+". 'sensor:' 'temperature'}";
						increment = increment + 1L;
						LOG.info("Sendind message='{}' to topic='{}' and number of message = "+increment+"", newMessage, topic);
						kafkaTemplate.send(topic, newMessage);
					}
		       }
		   });
		}//more unrelated code is not show (thread shutdown, etc.)
	}
}
