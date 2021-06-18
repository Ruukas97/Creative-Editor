package creativeeditor;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import creativeeditor.config.Config;
import creativeeditor.events.KeyInputHandler;
import creativeeditor.events.PlayerNameplateHandler;
import creativeeditor.events.ScreenHandler;
import creativeeditor.events.TooltipHandler;
import creativeeditor.render.ArmorStandRendering;
import creativeeditor.render.HeadRenderer;
import creativeeditor.render.ShieldRenderer;
import creativeeditor.resourcepack.ResourcePacks;
import creativeeditor.styles.StyleManager;
import creativeeditor.tab.CreativeTabs;
import creativeeditor.util.ReflectionUtils;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.Items;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod( CreativeEditor.MODID )
public class CreativeEditor {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "creativeeditor";
    public static final String NAME = "Creative Editor";

    public static boolean BARRIER_VISIBLE = false;
    public static final boolean DEBUG = true;

    public static Path DATAPATH = FMLPaths.GAMEDIR.get().resolve( MODID.concat( "-data" ) );


    public CreativeEditor() {
        final ModLoadingContext context = ModLoadingContext.get();

        LOGGER.info( "Registering config" );
        context.registerConfig( ModConfig.Type.CLIENT, Config.CLIENT );
        Config.loadConfig( Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve( MODID.concat( ".toml" ) ) );
        StyleManager.loadConfig();

        KeyInputHandler.init();
        CreativeTabs.init();
        ResourcePacks.init();

        // Register Events
        LOGGER.info( "Registering events" );
        MinecraftForge.EVENT_BUS.register( new ScreenHandler() );
        MinecraftForge.EVENT_BUS.register( new PlayerNameplateHandler() );
        MinecraftForge.EVENT_BUS.register( new TooltipHandler() );
        MinecraftForge.EVENT_BUS.register( new KeyInputHandler() );
        // MinecraftForge.EVENT_BUS.register( new NetworkHandler() );

        if (Config.SPECTRUM_SHIELD_ENABLED.get())
            ReflectionUtils.setTeisr( Items.SHIELD, () -> ShieldRenderer::new );

        ReflectionUtils.setTeisr( Items.PLAYER_HEAD, () -> HeadRenderer::new );

        if (Items.ARMOR_STAND instanceof ArmorStandItem)
            ArmorStandRendering.addPropertyOverrides( (ArmorStandItem) Items.ARMOR_STAND );
    }
}
