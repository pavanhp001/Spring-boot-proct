package com.A.util;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

public class BufferedImageHttpMessageConverter implements
		HttpMessageConverter<BufferedImage> {

	public List<MediaType> getSupportedMediaTypes() {
		return Collections.singletonList(new MediaType("image", "jpeg"));
	}

	public boolean supports(Class<? extends BufferedImage> clazz) {
		return BufferedImage.class.equals(clazz);
	}

	public void write(BufferedImage image, HttpOutputMessage message)
			throws IOException {
		throw new UnsupportedOperationException("Not implemented");
	}

	@Override
	public boolean canRead(Class<?> arg0, MediaType arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean canWrite(Class<?> arg0, MediaType arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public BufferedImage read(Class<? extends BufferedImage> arg0,
			HttpInputMessage inputMessage) throws IOException,
			HttpMessageNotReadableException {
		// TODO Auto-generated method stub
		return ImageIO.read(inputMessage.getBody());
	}

	@Override
	public void write(BufferedImage arg0, MediaType arg1, HttpOutputMessage arg2)
			throws IOException, HttpMessageNotWritableException {
		// TODO Auto-generated method stub

	}

}