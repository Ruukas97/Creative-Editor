package creativeeditor.util;

import java.util.UUID;

public class MinecraftHead {

	private String name;
	private String uuid;
	private String value;
	
	public String getName() {
		return name;
	}

	public UUID getUUID() {
		return UUID.fromString( uuid );
	}

	public String getValue() {
		return value;
	}
}
