package com.A.V.gateway.soap;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import com.A.V.gateway.BaseClientService;

public abstract class BaseClientSoap<T> extends BaseClientService<T> {

	@Override
	public InputStream getInputStream(String objAsString) {
		InputStream is = null;

		try {
			is = new ByteArrayInputStream(objAsString.getBytes());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return is;
	}

	@Override
	public OutputStream getOutputStream(String objAsString) {

		OutputStream f1 = null;

		try {
			f1 = new ByteArrayOutputStream();
			return f1;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return f1;
	}
}
