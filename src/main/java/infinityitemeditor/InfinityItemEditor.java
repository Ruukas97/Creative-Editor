package infinityitemeditor;

import infinityitemeditor.config.Config;
import infinityitemeditor.events.*;
import infinityitemeditor.render.ArmorStandRendering;
import infinityitemeditor.render.HeadRenderer;
import infinityitemeditor.render.ShieldRenderer;
import infinityitemeditor.saving.SaveService;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.tab.CreativeTabs;
import infinityitemeditor.util.CursorService;
import infinityitemeditor.util.ReflectionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.file.Path;

@Mod(InfinityItemEditor.MODID)
public class InfinityItemEditor {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "infinityitemeditor";
    public static final String NAME = "Infinity Item Editor";

    public static boolean BARRIER_VISIBLE = false;
    public static final boolean DEBUG = true;

    public static Path DATAPATH = FMLPaths.GAMEDIR.get().resolve(MODID.concat("-data"));

    public InfinityItemEditor() {
        final ModLoadingContext context = ModLoadingContext.get();

        LOGGER.info("Registering config");
        context.registerConfig(ModConfig.Type.CLIENT, Config.CLIENT);
        Config.loadConfig(Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve(MODID.concat(".toml")));
        StyleManager.loadConfig();

        KeyInputHandler.init();
        CreativeTabs.init();

        // Register Events
        LOGGER.info("Registering events");
        MinecraftForge.EVENT_BUS.register(new ScreenHandler());
        MinecraftForge.EVENT_BUS.register(new PlayerNameplateHandler());
        MinecraftForge.EVENT_BUS.register(new TooltipHandler());
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        MinecraftForge.EVENT_BUS.register(new EntityHandler());
        FMLJavaModLoadingContext.get().getModEventBus().register(new TextureStitchHandler());
//         MinecraftForge.EVENT_BUS.register( new NetworkHandler() );

        if (Config.SPECTRUM_SHIELD_ENABLED.get())
            ReflectionUtils.setTeisr(Items.SHIELD, () -> ShieldRenderer::new);

        ReflectionUtils.setTeisr(Items.PLAYER_HEAD, () -> HeadRenderer::new);

        if (Items.ARMOR_STAND instanceof ArmorStandItem)
            ArmorStandRendering.addPropertyOverrides((ArmorStandItem) Items.ARMOR_STAND);

        SaveService.getInstance();
        CursorService.init(Minecraft.getInstance());
    }
}
