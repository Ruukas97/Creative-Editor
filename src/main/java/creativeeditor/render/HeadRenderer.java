package creativeeditor.render;

import java.util.Map;
import java.util.Random;
import java.util.UUID;

import net.minecraft.client.renderer.model.ItemCameraTransforms;
import org.apache.commons.lang3.StringUtils;

import com.google.common.cache.LoadingCache;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftProfileTexture.Type;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.block.AbstractSkullBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SkullBlock;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.model.GenericHeadModel;
import net.minecraft.client.renderer.entity.model.HumanoidHeadModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.profiler.Snooper;
import net.minecraft.tileentity.SkullTileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class HeadRenderer extends ItemStackTileEntityRenderer {
    public static final LazyValue<LoadingCache<GameProfile, Map<Type, MinecraftProfileTexture>>> skinLoader = new LazyValue<>(() -> {
        return ObfuscationReflectionHelper.getPrivateValue(SkinManager.class, Minecraft.getInstance().getSkinManager(), "insecureSkinCache");
    });
    private static final GenericHeadModel headModel = new HumanoidHeadModel();
    private static final ResourceLocation headSkin = DefaultPlayerSkin.getDefaultSkin();
    private static final Random rand = new Random();
    private static final FPSSnooper fpsSnooper = new FPSSnooper();


    @Override
    public void renderByItem(ItemStack itemStackIn, ItemCameraTransforms.TransformType transformType, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int combinedLightIn, int combinedOverlayIn) {
        Item item = itemStackIn.getItem();
        if (item instanceof BlockItem) {
            Block block = ((BlockItem) item).getBlock();
            if (block instanceof AbstractSkullBlock) {
                GameProfile gameprofile = null;
                if (itemStackIn.hasTag()) {
                    CompoundNBT compoundnbt = itemStackIn.getTag();
                    if (compoundnbt.contains("SkullOwner", 10)) {
                        gameprofile = NBTUtil.readGameProfile(compoundnbt.getCompound("SkullOwner"));
                    } else if (compoundnbt.contains("SkullOwner", 8) && !StringUtils.isBlank(compoundnbt.getString("SkullOwner"))) {
                        GameProfile gameprofile1 = new GameProfile(null, compoundnbt.getString("SkullOwner"));
                        gameprofile = SkullTileEntity.updateGameprofile(gameprofile1);
                        compoundnbt.remove("SkullOwner");
                        compoundnbt.put("SkullOwner", NBTUtil.writeGameProfile(new CompoundNBT(), gameprofile));
                    }
                }

                SkullBlock.ISkullType type = ((AbstractSkullBlock) block).getType();
                if (type == SkullBlock.Types.PLAYER)
                    render(null, 180.0F, gameprofile, 0.0F, matrixStackIn, bufferIn, combinedLightIn);
                else
                    SkullTileEntityRenderer.renderSkull((Direction) null, 180.0F, type, gameprofile, 0.0F, matrixStackIn, bufferIn, combinedLightIn);
            }
        }
    }


    /**
     * Modified version of
     * {@link net.minecraft.client.renderer.tileentity.SkullTileEntityRenderer#render}
     */
    public static void render(Direction directionIn, float rotation, GameProfile profile, float animationProgress, MatrixStack matrixStackIn, IRenderTypeBuffer buffer, int combinedLight) {
        matrixStackIn.pushPose();
        if (directionIn == null) {
            matrixStackIn.translate(0.5D, 0.0D, 0.5D);
        } else {
            switch (directionIn) {
                case NORTH:
                    matrixStackIn.translate(0.5D, 0.25D, 0.74F);
                    break;
                case SOUTH:
                    matrixStackIn.translate(0.5D, 0.25D, 0.26F);
                    break;
                case WEST:
                    matrixStackIn.translate(0.74F, 0.25D, 0.5D);
                    break;
                case EAST:
                default:
                    matrixStackIn.translate(0.26F, 0.25D, 0.5D);
            }
        }

        matrixStackIn.scale(-1.0F, -1.0F, 1.0F);
        IVertexBuilder ivertexbuilder = buffer.getBuffer(getRenderType(profile));
        headModel.setupAnim(animationProgress, (Util.getMillis() / 30 % 360), 0.0F);
        headModel.renderToBuffer(matrixStackIn, ivertexbuilder, combinedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        matrixStackIn.popPose();
    }


    private static RenderType getRenderType(GameProfile profile) {
        if (profile != null) {
            Minecraft mc = Minecraft.getInstance();
            LoadingCache<GameProfile, Map<Type, MinecraftProfileTexture>> loader = skinLoader.get();
            Map<Type, MinecraftProfileTexture> map = loader.getIfPresent(profile);
            // map = loader.getUnchecked( profile );
            RenderType res = null;
            if (map != null && map.containsKey(Type.SKIN)) {
                res = RenderType.entityTranslucent( mc.getSkinManager().registerTexture( map.get( Type.SKIN ), Type.SKIN ));
            } else {
                // loader.getUnchecked
                res = RenderType.entityCutoutNoCull(DefaultPlayerSkin.getDefaultSkin(profile.getId()));
            }

            return res;
        } else {
            return RenderType.entityCutoutNoCull(headSkin);
        }
    }


    public static void loadSkin(MinecraftProfileTexture profileTexture, SkinManager.ISkinAvailableCallback skinAvailableCallback, boolean requireSecure) {
        Runnable runnable = () -> {
            Minecraft mc = Minecraft.getInstance();
            mc.execute(() -> {
                RenderSystem.recordRenderCall(() -> {
                    long startTime = System.nanoTime();
                    mc.getSkinManager().registerTexture( profileTexture, Type.SKIN);
                    long endTime = System.nanoTime();
                    long duration = (endTime - startTime);
                    // System.out.println( "Loading one skin took " + duration + "ns." );
                });
            });
        };
        Util.backgroundExecutor().execute( runnable );
    }


    static class FPSSnooper extends Snooper {
        int fps = -1;
        long lastGet = Util.getMillis();


        public FPSSnooper() {
            super("client", Minecraft.getInstance(), Util.getMillis());
        }


        public int getFps() {
            long now = Util.getMillis();
            if (now - lastGet > 5000) {
                Minecraft.getInstance().populateSnooper(this);
                lastGet = now;
            }
            return fps;
        }


        @Override
        public void setDynamicData(String statName, Object statValue) {
            if (statName.equals("fps")) {
                fps = (int) statValue;
            }
        }
    }
}
