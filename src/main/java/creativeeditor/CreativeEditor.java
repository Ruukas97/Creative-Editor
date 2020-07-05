package creativeeditor;

import java.nio.file.Path;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import creativeeditor.config.Config;
import creativeeditor.handlers.KeyInputHandler;
import creativeeditor.handlers.PlayerNameplateHandler;
import creativeeditor.handlers.ScreenHandler;
import creativeeditor.handlers.TooltipHandler;
import creativeeditor.resourcepack.ResourcePacks;
import creativeeditor.styles.StyleManager;
import creativeeditor.tab.CreativeTabs;
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

    public static boolean BARRIER_VISIBLE = true;
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

        /*if (Config.SPECTRUM_SHIELD_ENABLED.get())
            ReflectionUtils.setTeisr( Items.SHIELD, () -> ShieldRenderer::new );

        if (Items.ARMOR_STAND instanceof ArmorStandItem)
            ArmorStandRendering.addPropertyOverrides( (ArmorStandItem) Items.ARMOR_STAND );
            */
        //TODO redo
    }
}
