package eig.prospectingpicks.util;

import org.apache.commons.lang3.StringUtils;

public class OreCounter {
	public OreTagConfig ore;
	public int count = 0;
	public int rawCount = 0;

	public OreCounter(OreTagConfig ore) {
		this.ore = ore;
	}

	public String getTranslationKey() {
		return this.ore.translationKey;
	}

	public String getFallbackName() {
		String name = this.ore.ore.replace('_', ' ');
		return StringUtils.capitalize(name);
	}
}
