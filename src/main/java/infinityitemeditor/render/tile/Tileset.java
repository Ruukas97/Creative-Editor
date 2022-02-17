package infinityitemeditor.render.tile;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.InfinityItemEditor;
import infinityitemeditor.util.ColorUtils;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public class Tileset {
    public static final Tileset SLOT = new Tileset(new ResourceLocation(InfinityItemEditor.MODID, "textures/gui/slot.png"), 18, 1, 5);

    @Getter
    private final ResourceLocation resourceLocation;
    @Getter
    private final int size;
    @Getter
    private final int spacing;
    @Getter
    private final int borderSize;

    public Tileset(ResourceLocation resourceLocation, int size, int spacing, int borderSize) {
        this.resourceLocation = resourceLocation;
        this.size = size;
        this.spacing = spacing;
        this.borderSize = borderSize;
    }

    public void renderTiles(Minecraft mc, MatrixStack matrix, int x, int y, int xCount, int yCount, ColorUtils.Color color) {
        mc.getTextureManager().bind(resourceLocation);
        RenderSystem.color3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);

        int width = size * xCount;
        int height = size * yCount;

        int mainX = x + borderSize;
        int mainY = y + borderSize;

        int mainEndX = mainX + width;
        int mainEndY = mainY + height;

        int borderOffset = borderSize + size + spacing * 2;

        //Top left
        AbstractGui.blit(matrix,
                x, y,
                0, 0,
                borderSize, borderSize,
                32, 32
        );

        //Top right
        AbstractGui.blit(matrix,
                mainEndX, y,
                borderOffset, 0,
                borderSize, borderSize,
                32, 32
        );

        //Bottom left
        AbstractGui.blit(matrix,
                x, mainEndY,
                0, borderOffset,
                borderSize, borderSize,
                32, 32
        );

        //Bottom right
        AbstractGui.blit(matrix,
                mainEndX, mainEndY,
                borderOffset, borderOffset,
                borderSize, borderSize,
                32, 32
        );


        int tileOffset = borderSize + spacing;
        //Tiles
        for (int row = 0; row < yCount; row++) {
            //Left Border
            AbstractGui.blit(matrix,
                    x, mainY + size * row,
                    0, tileOffset,
                    borderSize, size,
                    32, 32
            );

            //Right Border
            AbstractGui.blit(matrix,
                    mainEndX, mainY + size * row,
                    borderOffset, tileOffset,
                    borderSize, size,
                    32, 32
            );

            for (int column = 0; column < xCount; column++) {
                //Top Border
                AbstractGui.blit(matrix,
                        mainX + size * column, y,
                        tileOffset, 0,
                        size, borderSize,
                        32, 32
                );

                //Bottom Border
                AbstractGui.blit(matrix,
                        mainX + size * column, mainEndY,
                        tileOffset, borderOffset,
                        size, borderSize,
                        32, 32
                );

                //Tile
                AbstractGui.blit(matrix,
                        mainX + size * column, mainY + size * row,
                        tileOffset, tileOffset,
                        size, size,
                        32, 32
                );
            }
        }
        RenderSystem.color3f(1f, 1f, 1f);
    }
}
