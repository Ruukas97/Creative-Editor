package creativeeditor.events;

import creativeeditor.screen.armorstand.ArmorstandContainer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TextureStitchHandler {

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre e) {
        e.addSprite(ArmorstandContainer.EMPTY_ARMOR_SLOT_SWORD);
    }

}
