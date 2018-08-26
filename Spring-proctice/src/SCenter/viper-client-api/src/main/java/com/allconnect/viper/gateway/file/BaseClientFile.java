package com.A.V.gateway.file;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import com.A.V.gateway.BaseClientService;

public abstract class BaseClientFile<T> extends BaseClientService<T> {

	@Override
	public InputStream getInputStream(String url) {
		InputStream is = null;

		try {
			is = new FileInputStream(url);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return is;
	}

	@Override
	public OutputStream getOutputStream(String url) {

		OutputStream f1 = null;

		try {
			f1 = new FileOutputStream(url);
			return f1;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return f1;
	}
}
