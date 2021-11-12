package infinityitemeditor.json;

import lombok.Getter;
import net.minecraft.util.text.TranslationTextComponent;

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


    public TranslationTextComponent getTranslationKey() {
        return new TranslationTextComponent("gui.headcollection.category." + name);
    }


    public URL getURL() throws MalformedURLException {
        return new URL(MinecraftHeads.API_URL + name);
    }
}
