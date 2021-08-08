package creativeeditor.screen.widgets;

import net.minecraft.client.Minecraft;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;

public class ColorButton extends StyledButton {

    private String s;

    public ColorButton(int x, int y, int width, int height, String s) {
        super(x, y, width, height, new StringTextComponent(s), t -> t.onPress());
        this.s = s;
    }

    public void onPress() {
        System.out.println(s);
        Minecraft mc = Minecraft.getInstance();
        mc.keyboardHandler.keyPress(mc.getWindow().getWindow(), GLFW.GLFW_KEY_SEMICOLON, 0, 0, 0);
//        InputMappings.setupKeyboardCallbacks(mc.getWindow().getWindow(), (p_228001_1_, p_228001_3_, p_228001_4_, p_228001_5_, p_228001_6_) -> {
//            mc.execute(() -> {
//                mc.keyboardHandler.keyPress(p_228001_1_, p_228001_3_, p_228001_4_, p_228001_5_, p_228001_6_);
//            });
//        }
    }
}
