package creativeeditor.json;

import java.util.UUID;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import com.mojang.authlib.properties.PropertyMap;

import lombok.Getter;

public class MinecraftHeadsResponse {
    private String uuid;
    private @Getter
    String name;
    private @Getter
    String value;


    public UUID getUUID() {
        try {
            return UUID.fromString( uuid );
        }
        catch (IllegalArgumentException e) {
            try {
                return UUID.fromString( uuid.replace( " ", "" ) );

            }
            catch (Exception e2) {
                return UUID.randomUUID();
            }
        }
    }


    public PropertyMap getProperties() {
        PropertyMap map = new PropertyMap();
        map.put( "textures", new Property( "textures", getValue() ) );
        return map;
    }


    public GameProfile getGameProfile() {
        GameProfile profile = new GameProfile( getUUID(), getName() );
        profile.getProperties().putAll( getProperties() );
        return profile;
    }
}
