package creativeeditor;

import java.nio.file.Path;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.glfw.GLFW;

import creativeeditor.config.Config;
import creativeeditor.data.DataItem;
import creativeeditor.eventhandlers.PlayerNameplateHandler;
import creativeeditor.eventhandlers.ScreenHandler;
import creativeeditor.eventhandlers.TooltipHandler;
import creativeeditor.json.MinecraftHeadsCategory;
import creativeeditor.pack.CreativeResources;
import creativeeditor.players.PlayerInfo;
import creativeeditor.render.ArmorStandRendering;
import creativeeditor.render.ShieldRenderer;
import creativeeditor.screen.HeadCollectionScreen;
import creativeeditor.screen.ItemInspectorScreen;
import creativeeditor.screen.MainScreen;
import creativeeditor.screen.ScreenPlayerInspector;
import creativeeditor.screen.TextEditorScreen;
import creativeeditor.styles.StyleManager;
import creativeeditor.tab.TabHead;
import creativeeditor.tab.TabLoadedTileEntities;
import creativeeditor.tab.TabNearbyBlocks;
import creativeeditor.tab.TabUnavailable;
import creativeeditor.util.ReflectionUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.resources.IPackFinder;
import net.minecraft.resources.ResourcePackInfo;
import net.minecraft.resources.ResourcePackInfo.IFactory;
import net.minecraft.util.Hand;
import net.minecraftforge.client.event.InputEvent.KeyInputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

@Mod( CreativeEditor.MODID )
public class CreativeEditor {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MODID = "creativeeditor";
    public static final String NAME = "Creative Editor";

    private static KeyBinding OPEN_EDITOR_KEY;
    private static KeyBinding PLAYER_INSPECT;
    private static KeyBinding OFF_HAND_SWING;
    public static KeyBinding HEAD_COLLECTION;

    public static Path DATAPATH = FMLPaths.GAMEDIR.get().resolve( MODID.concat( "-data" ) );

    @SuppressWarnings( "unused" )
    private ItemGroup tabUnavailable;


    public CreativeEditor() {
        final ModLoadingContext context = ModLoadingContext.get();

        LOGGER.info( "Registering config" );
        context.registerConfig( ModConfig.Type.CLIENT, Config.CLIENT );
        Config.loadConfig( Config.CLIENT, FMLPaths.CONFIGDIR.get().resolve( MODID.concat( ".toml" ) ) );
        StyleManager.loadConfig();

        LOGGER.info( "Registering keybindings" );
        OPEN_EDITOR_KEY = registerKeybind( "editor", GLFW.GLFW_KEY_U );
        PLAYER_INSPECT = registerKeybind( "inspector", GLFW.GLFW_KEY_G );
        OFF_HAND_SWING = registerKeybind( "offhandswing", InputMappings.INPUT_INVALID.getKeyCode() );
        HEAD_COLLECTION = registerKeybind( "headcollection", GLFW.GLFW_KEY_V );


        // Register Events
        LOGGER.info( "Registering events" );
        registerEventHandler( new ScreenHandler() );
        registerEventHandler( new PlayerNameplateHandler() );
        registerEventHandler( new TooltipHandler() );
        registerEventHandler( this );


        // Register Creative Tabs
        registerTabs();

        if (Config.SPECTRUM_SHIELD_ENABLED.get())
            ReflectionUtils.setTeisr( Items.SHIELD, () -> ShieldRenderer::new );

        if (Items.ARMOR_STAND instanceof ArmorStandItem)
            ArmorStandRendering.addPropertyOverrides( (ArmorStandItem) Items.ARMOR_STAND );

        Minecraft.getInstance().getResourcePackList().addPackFinder( new IPackFinder() {
            @Override
            public <T extends ResourcePackInfo> void addPackInfosToMap( Map<String, T> map, IFactory<T> fac ) {
                map.computeIfAbsent( "armor_stand_variants", key -> ResourcePackInfo.createResourcePack( key, true, () -> new CreativeResources(key), fac, ResourcePackInfo.Priority.TOP ) );
            }
        } );
    }


    private KeyBinding registerKeybind( String name, int keyCode ) {
        KeyBinding key = new KeyBinding( "key." + name, keyCode, NAME );
        ClientRegistry.registerKeyBinding( key );
        return key;
    }


    private void registerEventHandler( Object target ) {
        MinecraftForge.EVENT_BUS.register( target );
    }


    private void registerTabs() {
        LOGGER.info( "Adding Creative Tabs" );
        tabUnavailable = new TabUnavailable();

        if (Config.NEARBYBLOCKS_TAB_ENABLED.get())
            new TabNearbyBlocks();

        if (Config.LOADEDTILEENTITIES_TAB_ENABLED.get()) {
            new TabLoadedTileEntities();
        }

        if (Config.HEAD_TABS_ENABLED.get()) {
            for (MinecraftHeadsCategory cat : MinecraftHeadsCategory.values()) {
                new TabHead( cat );
            }
        }
    }


    @SubscribeEvent
    public void onKeyInput( final KeyInputEvent event ) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.world == null || event.getAction() != GLFW.GLFW_PRESS || mc.currentScreen instanceof ChatScreen || (mc.currentScreen != null && !(mc.currentScreen instanceof ContainerScreen<?>)))
            return;

        if (event.getKey() == OPEN_EDITOR_KEY.getKey().getKeyCode()) {
            PlayerInfo info = PlayerInfo.getByUUID( mc.player.getUniqueID() );
            if (info != null)
                System.out.println( "NamePlate: " + info.getNamePlate() );
            mc.displayGuiScreen( new MainScreen( mc.currentScreen, new DataItem( mc.player.getHeldItemMainhand() ) ) );
        }

        else if (event.getKey() == PLAYER_INSPECT.getKey().getKeyCode()) {
            Entity entity = mc.pointedEntity;
            if (entity != null) {
                mc.displayGuiScreen( new ScreenPlayerInspector( mc.currentScreen, mc.player ) );
            }
            else {
                mc.displayGuiScreen( new ItemInspectorScreen( mc.currentScreen, new DataItem( mc.player.getHeldItemMainhand() ) ) );
            }
        }
        else if (event.getKey() == OFF_HAND_SWING.getKey().getKeyCode()) {
            mc.player.swingArm( Hand.OFF_HAND );
            mc.displayGuiScreen( new TextEditorScreen( mc.currentScreen ) );
        }
        else if (event.getKey() == HEAD_COLLECTION.getKey().getKeyCode()) {
            mc.displayGuiScreen( new HeadCollectionScreen( mc.currentScreen ) );
        }
    }
}
