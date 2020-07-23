package creativeeditor.json;

import java.net.MalformedURLException;
import java.net.URL;

import lombok.Getter;

public enum MinecraftHeadsCategory {
    // @formatter:off
    alphabet( "alphabet" ),
    animals( "animals" ),
    blocks( "blocks" ),
    decoration( "decoration" ),
    fooddrink( "food-drinks" ),
    humans( "humans" ),
    humanoid( "humanoid" ),
    miscellaneous( "miscellaneous" ),
    monsters( "monsters" ),
    plants( "plants" );
    // @formatter:on


    @Getter
    private String name;


    MinecraftHeadsCategory(String name) {
        this.name = name;
    }


    public String getTranslationKey() {
        return "gui.headcollection.category." + name;
    }


    public URL getURL() throws MalformedURLException {
        return new URL( MinecraftHeads.API_URL + name );
    }
}
