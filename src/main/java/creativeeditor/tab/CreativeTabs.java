package creativeeditor.tab;

import creativeeditor.CreativeEditor;
import creativeeditor.config.Config;
import creativeeditor.json.MinecraftHeadsCategory;

public class CreativeTabs {
    public static void init() {
        CreativeEditor.LOGGER.info( "Adding Creative Tabs" );
        new TabUnavailable();

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
}
