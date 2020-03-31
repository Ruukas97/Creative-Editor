package creativeeditor.json;

import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

public class MinecraftHeadsResponse {
    private UUID uuid;
    private String name;
    private String value;


    public UUID getUUID() {
        return uuid;
    }


    public String getName() {
        return name;
    }


    public String getValue() {
        return value;
    }


    public PropertyMap getProperties() {
        PropertyMap map = new PropertyMap();
        map.put( "textures", new Property( "textures", value ) );
        return map;
    }
    
    public GameProfile getGameProfile() {
        GameProfile profile = new GameProfile( uuid, name );
        profile.getProperties().putAll( getProperties() );
        return profile;
    }
}
