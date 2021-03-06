package com.A.productResults.util;

import com.A.html.Fieldset;
import com.A.V.gateway.util.JaxbUtil;

public enum HtmlBuilder {

	INSTANCE;

	private static final String DEFAULT_NAMESPACE = "ns2";

	public String toString(Fieldset fieldset) {

		JaxbUtil<Fieldset> util = new JaxbUtil<Fieldset>();
		return util.toCleanString(fieldset, DEFAULT_NAMESPACE, Fieldset.class);

	}
}
