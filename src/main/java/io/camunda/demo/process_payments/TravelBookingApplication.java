package io.camunda.demo.process_payments;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.camunda.zeebe.client.ZeebeClient;
import io.camunda.zeebe.spring.client.annotation.Deployment;

@SpringBootApplication
@Deployment(resources = "classpath:travel-booking.scenario.bpmn")
public class TravelBookingApplication implements CommandLineRunner {

	private static final Logger LOG = LoggerFactory.getLogger(TravelBookingApplication.class);

	@Autowired
	private ZeebeClient zeebeClient;

	public static void main(String[] args) {
		SpringApplication.run(TravelBookingApplication.class, args);
	}

	@Override
	public void run(final String... args) {
		var bpmnProcessId = "travel-booking.scenario";
		var event = zeebeClient.newCreateInstanceCommand()
				.bpmnProcessId(bpmnProcessId)
				.latestVersion()
				.variables(Map.of("test", 123))
				.send()
				.join();
		LOG.info("started a process instance: {}", event.getProcessInstanceKey());
	}
}
