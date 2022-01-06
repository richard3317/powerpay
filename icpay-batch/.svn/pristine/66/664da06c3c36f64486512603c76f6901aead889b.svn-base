package com.icpay.payment.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileServiceImpl implements FileService {

	@Override
	public byte[] getFile(String fileName) throws IOException {
		return FileUtils.readFileToByteArray(new File(fileName));
	}

}
