package com.A.ui.domain;

import java.util.List;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

@XStreamAlias("lookupList")
public class WebLookupCollection {

	final static XStream jsonStream = new XStream(new JettisonMappedXmlDriver());
	final static XStream xstream = new XStream();

	static {
		jsonStream.alias("lookups", WebLookupCollection.class);
		jsonStream.alias("items", List.class);
		jsonStream.alias("item", WebLookup.class);
		jsonStream.setMode(XStream.NO_REFERENCES);

		xstream.alias("lookups", WebLookupCollection.class);
		xstream.alias("items", List.class);
		xstream.alias("item", WebLookup.class);
		xstream.setMode(XStream.NO_REFERENCES);

	}
 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@XStreamImplicit(itemFieldName = "lookupList")
	private List<WebLookup> lookupList;

	public String getAsXML() { 
		return xstream.toXML(this );
	}

	public String getAsJSON() {

		return jsonStream.toXML(this );
	}
	
	
	public String getListAsXML() { 
		return xstream.toXML(this.getLookupList());
	}

	public String getListAsJSON() {

		return jsonStream.toXML(this.getLookupList());
	}

	public List<WebLookup> getLookupList() {
		return lookupList;
	}

	public void setLookupList(List<WebLookup> lookupList) {
		this.lookupList = lookupList;
	}

	 
}
