package creativeeditor.screen;

import creativeeditor.widgets.StyledButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.util.text.TranslationTextComponent;

public class PlayerScreen extends ParentScreen {

    public PlayerScreen(Screen lastScreen) {
        super( new TranslationTextComponent( "gui.player" ), lastScreen );
    }


    @Override
    public void init( Minecraft p_init_1_, int p_init_2_, int p_init_3_ ) {
        super.init( p_init_1_, p_init_2_, p_init_3_ );
        PlayerAbilities playerAb = mc.player.abilities;
        addButton( new StyledButton( 10, 10, 10, 10, I18n.format( "gui.player.flight" ), t -> {
            if (playerAb.isCreativeMode || mc.isSingleplayer()) {
                if (playerAb.isFlying) {
                    playerAb.allowFlying = false;
                    playerAb.isFlying = false;
                }
                else {
                    playerAb.allowFlying = true;
                    playerAb.isFlying = !mc.player.onGround;
                }
            }
        } ) );

        /*
         * SliderCount countSlider = addButton(new SliderCount(width / 2 + 5, 60, 50,
         * 20, playerAb.getWalkSpeed(), s -> { playerAb.setWalkSpeed(s.getCount()); }));
         * countSlider.setMessage("" + playerAb.getWalkSpeed());
         */
    }


}
