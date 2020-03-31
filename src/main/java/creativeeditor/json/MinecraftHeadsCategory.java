package creativeeditor.json;

import java.net.MalformedURLException;
import java.net.URL;

import lombok.Getter;

public enum MinecraftHeadsCategory {
    alphabet( "alphabet" ), animals( "animals" ), blocks( "blocks" ), decoration( "decoration" ), fooddrink( "food-drink" ), humans( "humans" ), humanoid( "humanoid" ), miscellaneous( "miscellaneous" ), monsters( "monsters" ), plants( "plants" );


    private @Getter String name;


    MinecraftHeadsCategory(String name) {
        this.name = name;
    }
    
    public URL getURL() throws MalformedURLException {
        return new URL(MinecraftHeads.API_URL + name);
    }
}
