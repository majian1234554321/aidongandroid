package com.leyuan.commonlibrary.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {
	public static String readString(File file) throws IOException {
		StringBuilder content = new StringBuilder();
		InputStream instream = new FileInputStream(file);
		InputStreamReader read = new InputStreamReader(instream);
		BufferedReader buffer = new BufferedReader(read);
		String line;
		while ((line = buffer.readLine()) != null) {
			content.append(line).append("\n");
		}
		read.close();
		instream.close();
		return content.toString();
	}

	public static String readString(InputStream instream) throws IOException {
		StringBuilder content = new StringBuilder();
		InputStreamReader read = new InputStreamReader(instream);
		BufferedReader buffer = new BufferedReader(read);
		String line;
		while ((line = buffer.readLine()) != null) {
			content.append(line).append("\n");
		}
		read.close();
		instream.close();
		return content.toString();
	}

	public static void writeString(String content, String file) throws IOException {
//		OutputStream outStream = new FileOutputStream(file);
//		OutputStreamWriter writer = new OutputStreamWriter(outStream);
//		BufferedWriter buffer = new BufferedWriter(writer);
//		buffer.write(content);
//
//		writer.close();
//		outStream.close();

		FileOutputStream out = new FileOutputStream(file);
		out.write(content.getBytes());
		out.close();
	}
}