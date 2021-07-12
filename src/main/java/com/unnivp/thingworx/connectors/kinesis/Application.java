package com.unnivp.thingworx.connectors.kinesis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Application startup class for the Spring boot executable.
 * 
 * @author unnivp
 * 
 */
@EnableJms
@EnableScheduling
@EnableConfigurationProperties
@SpringBootApplication
public class Application extends SpringBootServletInitializer {

	private static final Logger logger = LoggerFactory.getLogger(SpringBootServletInitializer.class);

	public static void main(String[] args) {

		SpringApplication.run(Application.class, args);
		logger.info("ThingWorx Kinesis Connector Started");
	}

	/**
	 * Message Converter Bean to serialize message content to json using TextMessage
	 * @return MessageConverter bean
	 */
	@Bean
	public MessageConverter jacksonJmsMessageConverter() {
		
		MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
		converter.setTargetType(MessageType.TEXT);
		converter.setTypeIdPropertyName("_type");
		return converter;
	}

}
