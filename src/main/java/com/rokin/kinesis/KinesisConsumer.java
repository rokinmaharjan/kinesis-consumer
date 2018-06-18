package com.rokin.kinesis;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.kinesis.clientlibrary.lib.worker.Worker;
import com.rokin.aws.AwsConfig;

@Component
public class KinesisConsumer {
	@Autowired
	private RecordProcessorFactory recordProcessorFactory;
	
	@Autowired
	private AwsConfig awsConfig;
	
	private static final Logger logger = LoggerFactory.getLogger(KinesisConsumer.class);
	
	@PostConstruct
	public void runKinesisConsumer() {
		logger.info("Creating Kinesis worker");
		final Worker worker = new Worker.Builder()
			    .recordProcessorFactory(recordProcessorFactory)
			    .config(awsConfig.kinesisClientLibConfiguration())
			    .build();
		
		worker.run();
	}

}
