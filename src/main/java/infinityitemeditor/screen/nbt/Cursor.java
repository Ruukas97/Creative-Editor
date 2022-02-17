package infinityitemeditor.screen.nbt;

import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.InfinityItemEditor;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.VanillaPack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWImage;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public enum Cursor {
    DEFAULT(null, 0, 0),
    HORIZONTAL_RESIZE("horizontal_resize.png", 8, 8),
    VERTICAL_RESIZE("vertical_resize.png", 8, 8),
    DIAGONAL_RESIZE_NWSE("nwse_resize.png", 8, 8),
    DIAGONAL_RESIZE_NESW("nesw_resize.png", 8, 8);

    @Getter
    private final long handle;

    Cursor(String texture, int xhot, int yhot) {
        if (texture == null) {
            this.handle = 0;
            return;
        }
        long handle1;
        try {
            ResourceLocation loc = new ResourceLocation(InfinityItemEditor.MODID, "textures/gui/cursors/" + texture);
            VanillaPack vanillaPack = Minecraft.getInstance().getClientPackSource().getVanillaPack();
            InputStream inputstream = vanillaPack.getResource(ResourcePackType.CLIENT_RESOURCES, loc);
            MemoryStack memorystack = MemoryStack.stackPush();

            IntBuffer width = memorystack.mallocInt(1);
            IntBuffer height = memorystack.mallocInt(1);
            IntBuffer channels = memorystack.mallocInt(1);
            GLFWImage.Buffer buffer = GLFWImage.mallocStack(1, memorystack);
            ByteBuffer bytebuffer = this.readCursorPixels(inputstream, width, height, channels);
            if (bytebuffer == null) {
                throw new IllegalStateException("Could not load icon: " + STBImage.stbi_failure_reason());
            }

            buffer.position(0);
            buffer.width(width.get(0));
            buffer.height(height.get(0));
            buffer.pixels(bytebuffer);

            handle1 = GLFW.glfwCreateCursor(buffer.get(0), xhot, yhot);
        } catch (IOException exception) {
            handle1 = 0;
        }
        this.handle = handle1;
    }

    @Nullable
    private ByteBuffer readCursorPixels(InputStream inputStream, IntBuffer intBuffer, IntBuffer p_198111_3_, IntBuffer p_198111_4_) throws IOException {
        RenderSystem.assertThread(RenderSystem::isInInitPhase);
        ByteBuffer bytebuffer = null;

        ByteBuffer bytebuffer1;
        try {
            bytebuffer = TextureUtil.readResource(inputStream);
            bytebuffer.rewind();
            bytebuffer1 = STBImage.stbi_load_from_memory(bytebuffer, intBuffer, p_198111_3_, p_198111_4_, 0);
        } finally {
            if (bytebuffer != null) {
                MemoryUtil.memFree(bytebuffer);
            }

        }
        return bytebuffer1;
    }
}
