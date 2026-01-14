package eig.prospectingpicks.util;

public class OreTagPair {
	public final String ore;
	public final String rawOre;

	public OreTagPair(String entry) {
		String[] sp = entry.split(";");
		this.ore = sp[0];
		this.rawOre = sp[1];
	}
}