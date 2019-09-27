/**
 * This file represents a kafka agent to send messages in a specific topic
 * 
 */
package br.org.pti.broker.service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.json.simple.JSONObject;
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

		for (int i = 0; i < 5; i++) { // create 5 threads
			threadPool.submit(new Runnable() {
				public void run() {
					long increment = 0L;
					Random temperature = new Random();
					Date currentTimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
					String uniqueID = UUID.randomUUID().toString();
					JSONObject message = new JSONObject();
					Integer year = Calendar.getInstance().get(Calendar.YEAR);

					message.put("device_id", uniqueID);
					message.put("data_name", "temperatura");
					message.put("data_release_date", currentTimestamp.getTime());
					message.put("data_release_year", year);
					message.put("data_value", temperature.nextFloat() * (50 - 0));

					increment = increment + 1L;
					LOG.info("Sendind message='{}' to topic='{}' and number of message = " + increment + "",
							message.toString(), topic);
					kafkaTemplate.send(topic, message.toString());

				}
			});
		} // more unrelated code is not show (thread shutdown, etc.)
	}
}
