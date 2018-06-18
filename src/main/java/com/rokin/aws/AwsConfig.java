package com.rokin.aws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.kinesis.clientlibrary.lib.worker.KinesisClientLibConfiguration;

@Configuration
public class AwsConfig {

	@Autowired
	private AwsCredentials awsCredentials;

	public AWSCredentials getAwsCredentials() {
		return new BasicAWSCredentials(awsCredentials.getAccessKey(), awsCredentials.getAccessSecret());
	}

	@Bean
	public KinesisClientLibConfiguration kinesisClientLibConfiguration() {
		return new KinesisClientLibConfiguration("RokinKinesis", awsCredentials.getKinesis().getStreamName(), new AWSStaticCredentialsProvider(getAwsCredentials()), "rokin-kinesis")
				.withRegionName(awsCredentials.getKinesis().getRegion());
	}
	
}
