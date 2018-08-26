package com.A.vm.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum FeatureValueEnum {
	STRING("string"), INTEGER("integer"), BOOLEAN("boolean");
	public String desc;
	private static final Map<String, String> LOOKUP = new HashMap<String, String>();

	FeatureValueEnum(final String desc) {
		this.desc = desc;
	}

	static {
		for (FeatureValueEnum s : EnumSet.allOf(FeatureValueEnum.class)) {
			LOOKUP.put(s.getDesc(), s.name());
		}
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(final String desc) {
		this.desc = desc;
	}

	public static String get(final String code) {
		return LOOKUP.get(code);
	}
}

