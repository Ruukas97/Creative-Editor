package infinityitemeditor.util;

import infinityitemeditor.screen.nbt.Cursor;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWCursorEnterCallback;

public class CursorService {
    @Getter
    private static CursorService INSTANCE;

    private final long window;
    GLFWCursorEnterCallback callback;

    @Getter
    private Cursor activeCursor;

    private CursorService(Minecraft mc) {
        window = mc.getWindow().getWindow();
        activeCursor = Cursor.DEFAULT;
    }

    public static CursorService init(Minecraft mc) {
        if (INSTANCE == null) {
            INSTANCE = new CursorService(mc);
        }
        return INSTANCE;
    }


    public void setCursor(Cursor cursor) {
        if (cursor == Cursor.DEFAULT || cursor == null) {
            resetCursor();
            return;
        }
        activeCursor = cursor;
        GLFW.glfwSetCursor(window, activeCursor.getHandle());
        if (callback == null) {
            callback = GLFW.glfwSetCursorEnterCallback(window, this::enterCallback);
        }
    }

    public void resetCursor() {
        activeCursor = Cursor.DEFAULT;
        GLFW.glfwSetCursor(window, 0);
    }

    private void enterCallback(long windowHandle, boolean entered) {
        if (entered) {
            resetCursor();
        }
    }
}
