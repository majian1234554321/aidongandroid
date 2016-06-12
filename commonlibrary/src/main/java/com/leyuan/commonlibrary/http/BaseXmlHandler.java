package com.leyuan.commonlibrary.http;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public abstract class BaseXmlHandler<T> extends DefaultHandler implements IHandler<T>{
	
	protected StringBuffer buffer = new StringBuffer();
	
	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		buffer.append(ch, start, length);
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {}
	
	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		clearBuffer();
	}

	protected void clearBuffer() {
		buffer.delete(0, buffer.length());
	}

	protected String getBuffer() {
		return buffer.toString().trim();
	}

	@Override
	public abstract T getParsedData();

	

}
