package com.rokin.kinesis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessor;
import com.amazonaws.services.kinesis.clientlibrary.interfaces.v2.IRecordProcessorFactory;

@Component
public class RecordProcessorFactory implements IRecordProcessorFactory {

	@Autowired
	private RecordProcessor recordProcessor;
	
	@Override
	public IRecordProcessor createProcessor() {
		return recordProcessor;
	}

}
