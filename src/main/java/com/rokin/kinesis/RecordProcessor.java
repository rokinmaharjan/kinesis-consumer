package com.rokin.kinesis;

import java.lang.reflect.Type;
import java.nio.ByteBuffer;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.types.InitializationInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ProcessRecordsInput;
import com.amazonaws.services.kinesis.clientlibrary.types.ShutdownInput;
import com.amazonaws.services.kinesis.model.Record;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.rokin.hero.Hero;

@Component
public class RecordProcessor implements IRecordProcessor{
	
	private static final Logger logger = LoggerFactory.getLogger(RecordProcessor.class);

	@Override
	public void initialize(InitializationInput initializationInput) {
		logger.info("Initializing Kinesis Consumer");
	}

	@Override
	public void processRecords(ProcessRecordsInput processRecordsInput) {
		for (Record record : processRecordsInput.getRecords()) {

			logger.info("Processing Record For Partition Key : {} ", record.getPartitionKey());
			
			String originalData = "";
			try {
				byte[] b = new byte[record.getData().remaining()];

				ByteBuffer byteBuf = record.getData().get(b);
				originalData = new String(byteBuf.array(), "UTF-8");

				logger.info("Data from kinesis stream : {}", originalData);
				
				Type listType = new TypeToken<List<Hero>>() {}.getType();
				List<Hero> heroes = new Gson().fromJson(originalData, listType);
				
				heroes.forEach(System.out::println);
			} catch (Exception e) {
				logger.error("Error parsing record {}", e);
				System.exit(1);
			}
		}
	}

	@Override
	public void shutdown(ShutdownInput shutdownInput) {
		logger.info("Shutting down Kinesis Consumer. Reason : {}", shutdownInput.getShutdownReason());
		
		try {
            shutdownInput.getCheckpointer().checkpoint();
        } catch (Exception e) {
        	logger.error("Error while trying to checkpoint during Shutdown", e);
        }
		
	}

}
