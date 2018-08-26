package com.AL.ui.vo;

import java.util.ArrayList;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

@XStreamAlias("order")
public class OrderVo {

	@XStreamImplicit
	private List<LineItemVo> lineItems = new ArrayList<LineItemVo>();
	private String externalId;

 
	public List<LineItemVo> getLineItems() {
		return lineItems;
	}

	public void setLineItems(List<LineItemVo> lineItems) {
		this.lineItems = lineItems;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

}
