package infinityitemeditor.json;

import lombok.Getter;
import net.minecraft.network.chat.TranslatableComponent;

import java.net.MalformedURLException;
import java.net.URL;

public enum MinecraftHeadsCategory {
    // @formatter:off
    alphabet("alphabet"),
    animals("animals"),
    blocks("blocks"),
    decoration("decoration"),
    fooddrink("food-drinks"),
    humans("humans"),
    humanoid("humanoid"),
    miscellaneous("miscellaneous"),
    monsters("monsters"),
    plants("plants");
    // @formatter:on


    @Getter
    private final String name;


    MinecraftHeadsCategory(String name) {
        this.name = name;
    }


    public TranslatableComponent getTranslationKey() {
        return new TranslatableComponent("gui.headcollection.category." + name);
    }


    public URL getURL() throws MalformedURLException {
        return new URL(MinecraftHeads.API_URL + name);
    }
}
