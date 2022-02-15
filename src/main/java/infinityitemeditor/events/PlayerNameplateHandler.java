package infinityitemeditor.events;

import net.minecraftforge.client.event.RenderNameplateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

//@EventBusSubscriber( modid = CreativeEditor.MODID, value = { Dist.CLIENT } )
public class PlayerNameplateHandler {
    @SubscribeEvent
    public void onPreRenderPlayerSpecial(RenderNameplateEvent e) {
        /*Minecraft mc = Minecraft.getInstance();
        if (!(e.getRenderer() instanceof PlayerRenderer && e.getEntity() instanceof AbstractClientPlayer) || mc.screen != null && mc.screen instanceof ContainerScreen<?>)
            return;

        PlayerRenderer renderer = (PlayerRenderer) e.getRenderer();
        AbstractClientPlayer player = (AbstractClientPlayer) e.getEntity();

        if (!canRenderName( renderer, player ))
            return;

        double squaredDistance = player.getDistanceSq( renderer.getRenderManager().info.getProjectedView() );
        int limitDistance = player.isDiscrete() ? 32 : 64;
        if (squaredDistance <= limitDistance * limitDistance) {
            String s = player.getDisplayName().getFormattedText();
            GlStateManager.alphaFunc( 516, 0.1F );
            renderEntityName( renderer, player, e.getX(), e.getY(), e.getZ(), 0, s, squaredDistance, false );
        }
        if (squaredDistance < 40) {
            PlayerInfo info = PlayerInfo.getByUUID( player.getUniqueID() );
            if (info != null && info.getNamePlate() != null && !info.getNamePlate().isEmpty()) {
                renderEntityName( renderer, player, e.getX(), e.getY(), e.getZ(), -10, info.getNamePlate(), squaredDistance, false );
            }
        }

        e.setCanceled( true );*/
        //TODO remake
    }
}
