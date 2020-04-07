package creativeeditor.config;

import java.nio.file.Path;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;

import creativeeditor.CreativeEditor;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber( modid = CreativeEditor.MODID )
public class Config {
    public static String CATEGORY_GENERAL = "general";
    public static String CATEGORY_FEATURES = "features";
    public static String CATEGORY_TABS = "tabs";
    public static String CATEGORY_INTERNAL = "internal";

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec CLIENT;

    public static ForgeConfigSpec.BooleanValue SPECTRUM_SHIELD_ENABLED;
    public static ForgeConfigSpec.BooleanValue HEAD_TABS_ENABLED;
    public static ForgeConfigSpec.BooleanValue NEARBYBLOCKS_TAB_ENABLED;
    public static ForgeConfigSpec.BooleanValue NEARBYBLOCKS_TAB_MULTIPLESTACKS;
    public static ForgeConfigSpec.IntValue NEARBYBLOCKS_TAB_RADIUS;
    
    public static ForgeConfigSpec.IntValue MAIN_LEFT_TAB;
    public static ForgeConfigSpec.IntValue MAIN_RIGHT_TAB;
    public static ForgeConfigSpec.IntValue ACTIVESTYLE;


    static {
        BUILDER.comment( "General Settings" ).push( CATEGORY_GENERAL );

        BUILDER.pop();

        BUILDER.comment( "Feature Settings" ).push( CATEGORY_FEATURES );
        SPECTRUM_SHIELD_ENABLED = BUILDER.comment( "Whether or not the Spectrum Shield should be enabled." ).define( "spectrumShieldEnabled", true );
        BUILDER.pop();
        
        BUILDER.comment( "Creative Tab Settings" ).push( CATEGORY_TABS );
        HEAD_TABS_ENABLED = BUILDER.comment( "Whether or not the minecraft-heads Creative Tabs should be enabled." ).define( "headTabsEnabled", true );
        NEARBYBLOCKS_TAB_ENABLED = BUILDER.comment( "Whether or not the tab showing nearby blocks should be enabled." ).define( "nearbyBlocksTabEnabled", true );
        NEARBYBLOCKS_TAB_MULTIPLESTACKS = BUILDER.comment( "If the nearby blocks tab should add multiple stacks for blocks that occur more than 64 times, or limit at one stack." ).define( "nearbyBlocksTabMultipleStacks", true );
        NEARBYBLOCKS_TAB_RADIUS = BUILDER.comment( "The radius of the area the nearby blocks tab searches for blocks in." ).defineInRange( "nearbyBlocksTabRadius", 16, 1, 128 );
        BUILDER.pop();
        
        BUILDER.comment( "Internal - Don't change manually" ).push( CATEGORY_TABS );
        MAIN_LEFT_TAB = BUILDER.comment( "Which tab the editor was last on." ).defineInRange( "mainLeftTab", 0, 0, 2 );
        MAIN_RIGHT_TAB = BUILDER.comment( "Which tab the editor was last on." ).defineInRange( "mainRightTab", 0, 0, 2 );
        ACTIVESTYLE = BUILDER.comment( "Which style is currently being used." ).defineInRange( "activeStyle", 0, 0, 20 );
        BUILDER.pop();
        
        CLIENT = BUILDER.build();
    }
    
    public static void loadConfig(ForgeConfigSpec spec, Path path) {

        final CommentedFileConfig configData = CommentedFileConfig.builder(path)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        spec.setConfig(configData);
    }

    @SubscribeEvent
    public static void onLoad( final ModConfig.Loading configEvent ) {
    }


    @SubscribeEvent
    public static void onReload( final ModConfig.ConfigReloading configEvent ) {
    }
}
