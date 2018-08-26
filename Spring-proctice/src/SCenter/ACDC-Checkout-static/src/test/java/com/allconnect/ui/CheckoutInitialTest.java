package com.AL.ui;

import org.junit.Test;

import com.AL.ui.util.JsonUtil;
import com.AL.ui.vo.CKOInitialVo;

public class CKOInitialTest {

	private static final JsonUtil<CKOInitialVo> util = new JsonUtil<CKOInitialVo>();

	@Test
	public void testCKOInitial() {

		CKOInitialVo vo = new CKOInitialVo();

		vo.setStatus("CKOReady");
		vo.setOrderId("002");
		vo.getLineItems().add(1L);
		vo.getLineItems().add(2L);

		vo.getParams().add("shortForm=N");
		vo.getParams().add("includeSubmit=N");
		vo.getParams().add("cancelOnError=Y");

		String x = util.convert(vo, "CKO", CKOInitialVo.class);

		CKOInitialVo vo2 = util.convert(x, "CKO",
				CKOInitialVo.class);
		System.out.println(x);
		System.out.println(vo2.toString());

	}

}