/**
 * This file represents a kafka agent to send messages in a specific topic
 * The topic can be modified or specified on application.properties file
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

	/**
	 * Create an agent to send 200 messages per second to broker
	 */
	public void createThreads() {
		ExecutorService threadPool = Executors.newFixedThreadPool(5);

		for (int i = 0; i < 15; i++) { // create 5 threads
			threadPool.submit(new Runnable() {
				public void run() {
					try {
						while (true) {
							long increment = 0L;
							Random temperature = new Random();
							Date currentTimestamp = new Date();
							JSONObject message = new JSONObject();
							Integer year = getRandomNumberInRange();
							Long uniqueID = generateLongId();
							LocalDate randomDate = generateRandomDate(year);
							Timestamp randomDateTime = Timestamp.valueOf(randomDate.atStartOfDay());

							message.put("device_id", uniqueID);
							message.put("data_name", generateSensorName());
							message.put("data_release_date", randomDateTime.getTime());
							message.put("data_release_year", year);
							message.put("data_value", temperature.nextFloat() * (50 - 0));

							increment = increment + 1L;
							LOG.info("Sendind message='{}' to topic='{}' and number of message = " + increment + "",
									message.toString(), topic);
							kafkaTemplate.send(topic, message.toString());
							Thread.sleep(1000);
						}
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			});
		} // more unrelated code is not show (thread shutdown, etc.)
	}

	/**
	 * @return Long
	 */
	public Long generateLongId() {
		long leftLimit = 1L;
		long rightLimit = 20L;
		long generatedLong = leftLimit + (long) (Math.random() * (rightLimit - leftLimit));
		return generatedLong;
	}

	/**
	 * @return String
	 */
	public String generateSensorName() {
		String[] arr = { "temperatura", "umidadae", "pressao", "gas", "luminosidade", "proximidade", "corrente",
				"vibracao", "luz", "frequencia" };
		Random r = new Random();
		int randomNumber = r.nextInt(arr.length);
		return arr[randomNumber];
	}

	/**
	 * @return int
	 */
	public int getRandomNumberInRange() {
		Random r = new Random();
		return r.nextInt((2019 - 2015) + 1) + 2015;
	}

	/**
	 * @param start
	 * @param end
	 * @return int
	 */
	public int createRandomIntBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}

	/**
	 * @param year
	 * @return LocalDate
	 */
	public LocalDate generateRandomDate(int year) {
		int day = createRandomIntBetween(1, 28);
		int month = createRandomIntBetween(1, 12);
		return LocalDate.of(year, month, day);
	}

}
