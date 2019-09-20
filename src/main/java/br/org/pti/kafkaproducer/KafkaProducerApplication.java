/**
 * This application offers a Kafka producer implementation
 * 
 */
package br.org.pti.kafkaproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import br.org.pti.kafkaproducer.producer.Sender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

/**
 * @author fernando.luiz
 *
 */
@SpringBootApplication
public class KafkaProducerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(KafkaProducerApplication.class, args);
	}
	
	@Autowired
	private Sender sender;
	
	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		sender.send("Spring kafka producer example");
	}

}
