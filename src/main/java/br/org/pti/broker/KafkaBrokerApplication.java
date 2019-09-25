/**
 * This application offers a Kafka producer implementation
 * 
 */
package br.org.pti.broker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.org.pti.broker.service.SenderService;

/**
 * @author fernando.luiz
 *
 */
@SpringBootApplication
public class KafkaBrokerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaBrokerApplication.class, args);
	}

	@Autowired
	private SenderService senderService;

	/**
	 * Start the kafka broker
	 */
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		senderService.send();
	}

}

