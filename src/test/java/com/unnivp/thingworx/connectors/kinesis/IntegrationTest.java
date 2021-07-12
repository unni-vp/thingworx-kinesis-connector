package com.unnivp.thingworx.connectors.kinesis;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.unnivp.thingworx.connectors.kinesis.consumer.KinesisMessageSink;
import com.unnivp.thingworx.connectors.kinesis.domain.TelemetryServiceRequest;
import com.unnivp.thingworx.connectors.kinesis.util.JSONUtil;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	@Autowired
	private KinesisMessageSink kinesisMessageSink;

	@Test
	public void testKinesisConsumer_Success() {

		kinesisMessageSink.consume(JSONUtil.getJsonString(new TelemetryServiceRequest()));

	}
	
	@Test
	public void testKinesisConsumer_ThrowsException() {
		
	    Throwable exception = assertThrows(IllegalArgumentException.class, () -> kinesisMessageSink.consume(null));
	    assertEquals("Payload must not be null", exception.getMessage());
	}

}
