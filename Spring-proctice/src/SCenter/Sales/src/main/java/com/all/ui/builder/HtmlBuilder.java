package com.AL.ui.builder;

import com.AL.html.Fieldset;
import com.AL.V.gateway.util.JaxbUtil;

public enum HtmlBuilder {

	INSTANCE;

	private static final String DEFAULT_NAMESPACE = "ns2";

	public String toString(Fieldset fieldset) {

		JaxbUtil<Fieldset> util = new JaxbUtil<Fieldset>();
		return util.toCleanString(fieldset, DEFAULT_NAMESPACE, Fieldset.class);

	}
}
