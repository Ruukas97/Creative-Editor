package creativeeditor.events;

import creativeeditor.screen.container.EquipmentContainer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class TextureStitchHandler {

    @SubscribeEvent
    public void onTextureStitch(TextureStitchEvent.Pre e) {
        e.addSprite(EquipmentContainer.EMPTY_ARMOR_SLOT_SWORD);
    }

}
