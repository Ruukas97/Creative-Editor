package creativeeditor.eventhandlers;

import com.mojang.blaze3d.platform.GlStateManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.PlayerRenderer;
import net.minecraft.client.renderer.entity.model.PlayerModel;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

//@EventBusSubscriber( modid = CreativeEditor.MODID, value = { Dist.CLIENT } )
public class PlayerNameplateHandler {
    @SubscribeEvent
    public void onPreRenderPlayerSpecial( RenderLivingEvent.Specials.Pre<AbstractClientPlayerEntity, PlayerModel<AbstractClientPlayerEntity>> e ) {
        if (!(e.getRenderer() instanceof PlayerRenderer && e.getEntity() instanceof AbstractClientPlayerEntity))
            return;

        PlayerRenderer renderer = (PlayerRenderer) e.getRenderer();
        AbstractClientPlayerEntity player = (AbstractClientPlayerEntity) e.getEntity();

        if (!canRenderName( renderer, player ))
            return;

        double squaredDistance = player.getDistanceSq( renderer.getRenderManager().info.getProjectedView() );
        int limitDistance = player.shouldRenderSneaking() ? 32 : 64;
        if (squaredDistance <= limitDistance * limitDistance) {
            String s = player.getDisplayName().getFormattedText();
            GlStateManager.alphaFunc( 516, 0.1F );
            renderEntityName( renderer, player, e.getX(), e.getY(), e.getZ(), 0, s, squaredDistance, false );
        }
        if(squaredDistance < 40) {
            renderEntityName( renderer, player, e.getX(), e.getY(), e.getZ(), -10, "\\u00a7cCE Developer", squaredDistance, false );
        }
        
        e.setCanceled( true );
    }


    protected void renderEntityName( PlayerRenderer renderer, AbstractClientPlayerEntity entityIn, double x, double y, double z, int height, String name, double distanceSq, boolean checkScoreboard ) {
        if (checkScoreboard && distanceSq < 100.0D) {
            Scoreboard scoreboard = entityIn.getWorldScoreboard();
            ScoreObjective scoreobjective = scoreboard.getObjectiveInDisplaySlot( 2 );
            if (scoreobjective != null) {
                Score score = scoreboard.getOrCreateScore( entityIn.getScoreboardName(), scoreobjective );
                renderLivingLabel( renderer, entityIn, score.getScorePoints() + " " + scoreobjective.getDisplayName().getFormattedText(), x, y, z, -10, 64 );
                y += (double) (9.0F * 1.15F * 0.025F);
            }
        }

        renderLivingLabel( renderer, entityIn, name, x, y, z, height, 64 );
    }


    protected void renderLivingLabel( PlayerRenderer renderer, AbstractClientPlayerEntity entityIn, String str, double x, double y, double z, int height, int maxDistance ) {
        double d0 = entityIn.getDistanceSq( renderer.getRenderManager().info.getProjectedView() );
        if (!(d0 > (double) (maxDistance * maxDistance))) {
            boolean flag = entityIn.shouldRenderSneaking();
            float f = renderer.getRenderManager().playerViewY;
            float f1 = renderer.getRenderManager().playerViewX;
            float f2 = entityIn.getHeight() + 0.5F - (flag ? 0.25F : 0.0F);
            GameRenderer.drawNameplate( renderer.getRenderManager().getFontRenderer(), str, (float) x, (float) y + f2, (float) z, height, f, f1, flag );
        }
    }


    public boolean canRenderName( PlayerRenderer renderer, AbstractClientPlayerEntity player ) {
        Minecraft mc = Minecraft.getInstance();
        ClientPlayerEntity clientplayerentity = mc.player;
        boolean isInvisible = !player.isInvisibleToPlayer( clientplayerentity );
        if (player != clientplayerentity) {

            Team renderTeam = player.getTeam();
            Team ownTeam = clientplayerentity.getTeam();
            if (renderTeam != null) {
                Team.Visible team$visible = renderTeam.getNameTagVisibility();
                switch (team$visible) {
                case ALWAYS:
                    return isInvisible;
                case NEVER:
                    return false;
                case HIDE_FOR_OTHER_TEAMS:
                    return ownTeam == null ? isInvisible : renderTeam.isSameTeam( ownTeam ) && (renderTeam.getSeeFriendlyInvisiblesEnabled() || isInvisible);
                case HIDE_FOR_OWN_TEAM:
                    return ownTeam == null ? isInvisible : !renderTeam.isSameTeam( ownTeam ) && isInvisible;
                default:
                    return true;
                }
            }
        }
        else
            return true;

        return Minecraft.isGuiEnabled() && player != renderer.getRenderManager().info.getRenderViewEntity() && isInvisible && !player.isBeingRidden();
    }
}
