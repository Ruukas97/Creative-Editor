package infinityitemeditor.tab;

import infinityitemeditor.InfinityItemEditor;
import infinityitemeditor.config.Config;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.TagEnchantment;
import infinityitemeditor.json.MinecraftHeadsCategory;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CreativeTabs {
    public static void init() {
        InfinityItemEditor.LOGGER.info("Adding Creative Tabs");
        new TabUnavailable();

        if (Config.NEARBYBLOCKS_TAB_ENABLED.get())
            new TabNearbyBlocks();

        if (Config.LOADEDTILEENTITIES_TAB_ENABLED.get()) {
            new TabLoadedTileEntities();
        }

        if (Config.HEAD_TABS_ENABLED.get()) {
            for (MinecraftHeadsCategory cat : MinecraftHeadsCategory.values()) {
                new TabHead(cat);
            }
        }

        DataItem slowfalling_potion = new DataItem(new ItemStack(Items.POTION));
        slowfalling_potion.getTag().getPotion().set("minecraft:long_slow_falling");
        DataItem slowfalling_arrow = new DataItem(new ItemStack(Items.TIPPED_ARROW));
        slowfalling_arrow.getTag().getPotion().set("minecraft:long_slow_falling");

        DataItem turtlemaster_potion = new DataItem(new ItemStack(Items.POTION));
        turtlemaster_potion.getTag().getPotion().set("minecraft:long_turtle_master");
        DataItem turtlemaster_arrow = new DataItem(new ItemStack(Items.TIPPED_ARROW));
        turtlemaster_arrow.getTag().getPotion().set("minecraft:long_turtle_master");

        DataItem channeling_enchantment = new DataItem(new ItemStack(Items.ENCHANTED_BOOK));
        channeling_enchantment.getTag().getStoredEnchantments().add(new TagEnchantment(Enchantments.CHANNELING, 1));
        DataItem impailing_enchantment = new DataItem(new ItemStack(Items.ENCHANTED_BOOK));
        impailing_enchantment.getTag().getStoredEnchantments().add(new TagEnchantment(Enchantments.IMPALING, 5));
        DataItem loyalty_enchantment = new DataItem(new ItemStack(Items.ENCHANTED_BOOK));
        loyalty_enchantment.getTag().getStoredEnchantments().add(new TagEnchantment(Enchantments.LOYALTY, 3));
        DataItem riptide_enchantment = new DataItem(new ItemStack(Items.ENCHANTED_BOOK));
        riptide_enchantment.getTag().getStoredEnchantments().add(new TagEnchantment(Enchantments.RIPTIDE, 3));

        // @formatter:off
        ItemStack[] aquaticItems = {
                new ItemStack(Items.BLUE_ICE),
                new ItemStack(Items.BIRCH_BUTTON),
                new ItemStack(Items.SPRUCE_BUTTON),
                new ItemStack(Items.JUNGLE_BUTTON),
                new ItemStack(Items.ACACIA_BUTTON),
                new ItemStack(Items.DARK_OAK_BUTTON),
                new ItemStack(Items.BIRCH_PRESSURE_PLATE),
                new ItemStack(Items.SPRUCE_PRESSURE_PLATE),
                new ItemStack(Items.JUNGLE_PRESSURE_PLATE),
                new ItemStack(Items.ACACIA_PRESSURE_PLATE),
                new ItemStack(Items.DARK_OAK_PRESSURE_PLATE),
                new ItemStack(Items.BIRCH_TRAPDOOR),
                new ItemStack(Items.SPRUCE_TRAPDOOR),
                new ItemStack(Items.JUNGLE_TRAPDOOR),
                new ItemStack(Items.ACACIA_TRAPDOOR),
                new ItemStack(Items.DARK_OAK_TRAPDOOR),
                new ItemStack(Items.CARVED_PUMPKIN),
                new ItemStack(Items.CONDUIT),
                new ItemStack(Items.TUBE_CORAL),
                new ItemStack(Items.BRAIN_CORAL),
                new ItemStack(Items.BUBBLE_CORAL),
                new ItemStack(Items.FIRE_CORAL),
                new ItemStack(Items.HORN_CORAL),
                new ItemStack(Items.DEAD_TUBE_CORAL),
                new ItemStack(Items.DEAD_BRAIN_CORAL),
                new ItemStack(Items.DEAD_BUBBLE_CORAL),
                new ItemStack(Items.DEAD_FIRE_CORAL),
                new ItemStack(Items.DEAD_HORN_CORAL),
                new ItemStack(Items.TUBE_CORAL_BLOCK),
                new ItemStack(Items.BRAIN_CORAL_BLOCK),
                new ItemStack(Items.BUBBLE_CORAL_BLOCK),
                new ItemStack(Items.FIRE_CORAL_BLOCK),
                new ItemStack(Items.HORN_CORAL_BLOCK),
                new ItemStack(Items.DEAD_TUBE_CORAL_BLOCK),
                new ItemStack(Items.DEAD_BRAIN_CORAL_BLOCK),
                new ItemStack(Items.DEAD_BUBBLE_CORAL_BLOCK),
                new ItemStack(Items.DEAD_FIRE_CORAL_BLOCK),
                new ItemStack(Items.DEAD_HORN_CORAL_BLOCK),
                new ItemStack(Items.TUBE_CORAL_FAN),
                new ItemStack(Items.BRAIN_CORAL_FAN),
                new ItemStack(Items.BUBBLE_CORAL_FAN),
                new ItemStack(Items.FIRE_CORAL_FAN),
                new ItemStack(Items.HORN_CORAL_FAN),
                new ItemStack(Items.DEAD_TUBE_CORAL_FAN),
                new ItemStack(Items.DEAD_BRAIN_CORAL_FAN),
                new ItemStack(Items.DEAD_BUBBLE_CORAL_FAN),
                new ItemStack(Items.DEAD_FIRE_CORAL_FAN),
                new ItemStack(Items.DEAD_HORN_CORAL_FAN),
                new ItemStack(Items.DRIED_KELP_BLOCK),
                new ItemStack(Items.KELP),
                new ItemStack(Items.DRIED_KELP),
                new ItemStack(Items.PRISMARINE_STAIRS),
                new ItemStack(Items.PRISMARINE_SLAB),
                new ItemStack(Items.DARK_PRISMARINE_STAIRS),
                new ItemStack(Items.DARK_PRISMARINE_SLAB),
                new ItemStack(Items.PRISMARINE_BRICK_STAIRS),
                new ItemStack(Items.PRISMARINE_BRICK_SLAB),
                new ItemStack(Items.SEAGRASS),
                new ItemStack(Items.SEA_PICKLE),
                new ItemStack(Items.PURPLE_SHULKER_BOX),
                new ItemStack(Items.STRIPPED_OAK_LOG),
                new ItemStack(Items.STRIPPED_SPRUCE_LOG),
                new ItemStack(Items.STRIPPED_BIRCH_LOG),
                new ItemStack(Items.STRIPPED_JUNGLE_LOG),
                new ItemStack(Items.STRIPPED_ACACIA_LOG),
                new ItemStack(Items.STRIPPED_DARK_OAK_LOG),
                new ItemStack(Items.STRIPPED_OAK_WOOD),
                new ItemStack(Items.STRIPPED_SPRUCE_WOOD),
                new ItemStack(Items.STRIPPED_BIRCH_WOOD),
                new ItemStack(Items.STRIPPED_JUNGLE_WOOD),
                new ItemStack(Items.STRIPPED_ACACIA_WOOD),
                new ItemStack(Items.STRIPPED_DARK_OAK_WOOD),
                new ItemStack(Items.OAK_WOOD),
                new ItemStack(Items.SPRUCE_WOOD),
                new ItemStack(Items.BIRCH_WOOD),
                new ItemStack(Items.JUNGLE_WOOD),
                new ItemStack(Items.ACACIA_WOOD),
                new ItemStack(Items.DARK_OAK_WOOD),
                new ItemStack(Items.TURTLE_EGG),
                slowfalling_potion.getItemStack(),
                turtlemaster_potion.getItemStack(),
                slowfalling_arrow.getItemStack(),
                turtlemaster_arrow.getItemStack(),
                new ItemStack(Items.COD_BUCKET),
                new ItemStack(Items.SALMON_BUCKET),
                new ItemStack(Items.PUFFERFISH_BUCKET),
                new ItemStack(Items.TROPICAL_FISH_BUCKET),
                new ItemStack(Items.HEART_OF_THE_SEA),
                new ItemStack(Items.BROWN_MUSHROOM_BLOCK),
                new ItemStack(Items.RED_MUSHROOM_BLOCK),
                new ItemStack(Items.MUSHROOM_STEM),
                new ItemStack(Items.NAUTILUS_SHELL),
                new ItemStack(Items.PETRIFIED_OAK_SLAB),
                new ItemStack(Items.PHANTOM_MEMBRANE),
                new ItemStack(Items.SCUTE),
                new ItemStack(Items.SMOOTH_STONE),
                new ItemStack(Items.SMOOTH_QUARTZ),
                new ItemStack(Items.SMOOTH_SANDSTONE),
                new ItemStack(Items.SMOOTH_RED_SANDSTONE),
                new ItemStack(Items.SMOOTH_STONE_SLAB),
                new ItemStack(Items.DROWNED_SPAWN_EGG),
                new ItemStack(Items.PHANTOM_SPAWN_EGG),
                new ItemStack(Items.DOLPHIN_SPAWN_EGG),
                new ItemStack(Items.TURTLE_SPAWN_EGG),
                new ItemStack(Items.COD_SPAWN_EGG),
                new ItemStack(Items.SALMON_SPAWN_EGG),
                new ItemStack(Items.PUFFERFISH_SPAWN_EGG),
                new ItemStack(Items.TROPICAL_FISH_SPAWN_EGG),
                new ItemStack(Items.TRIDENT),
                new ItemStack(Items.TURTLE_HELMET),
                channeling_enchantment.getItemStack(),
                impailing_enchantment.getItemStack(),
                loyalty_enchantment.getItemStack(),
                riptide_enchantment.getItemStack()
        };
        // formatter:on

        new TabItemList("aquatic", new ItemStack(Items.TRIDENT), aquaticItems).setHasSearchBar(false);


        DataItem globe_banner = new DataItem(new ItemStack(Items.BLUE_BANNER));
        //globe_banner.getTag().getBanner().getPatterns().add( new TagBannerPattern( BannerPattern.GLOBE, DyeColor.LIME ) );

        // @formatter:off
        ItemStack[] villageItems = {
                new ItemStack(Items.BAMBOO),
                new ItemStack(Items.BARREL),
                new ItemStack(Items.BELL),
                new ItemStack(Items.BLAST_FURNACE),
                new ItemStack(Items.CAMPFIRE),
                new ItemStack(Items.CARTOGRAPHY_TABLE),
                new ItemStack(Items.FLETCHING_TABLE),
                new ItemStack(Items.CARTOGRAPHY_TABLE),
                new ItemStack(Items.CORNFLOWER),
                new ItemStack(Items.LILY_OF_THE_VALLEY),
                new ItemStack(Items.WITHER_ROSE),
                new ItemStack(Items.GRINDSTONE),
                new ItemStack(Items.JIGSAW),
                new ItemStack(Items.LANTERN),
                new ItemStack(Items.LECTERN),
                new ItemStack(Items.LOOM),
                new ItemStack(Items.SCAFFOLDING),
                new ItemStack(Items.SPRUCE_SIGN),
                new ItemStack(Items.BIRCH_SIGN),
                new ItemStack(Items.JUNGLE_SIGN),
                new ItemStack(Items.ACACIA_SIGN),
                new ItemStack(Items.DARK_OAK_SIGN),
                new ItemStack(Items.ANDESITE_SLAB),
                new ItemStack(Items.POLISHED_ANDESITE_SLAB),
                new ItemStack(Items.DIORITE_SLAB),
                new ItemStack(Items.POLISHED_DIORITE_SLAB),
                new ItemStack(Items.GRANITE_SLAB),
                new ItemStack(Items.POLISHED_GRANITE_SLAB),
                new ItemStack(Items.MOSSY_STONE_BRICK_SLAB),
                new ItemStack(Items.MOSSY_COBBLESTONE_SLAB),
                new ItemStack(Items.SMOOTH_SANDSTONE_SLAB),
                new ItemStack(Items.SMOOTH_RED_SANDSTONE_SLAB),
                new ItemStack(Items.SMOOTH_QUARTZ_SLAB),
                new ItemStack(Items.RED_NETHER_BRICK_SLAB),
                new ItemStack(Items.END_STONE_BRICK_SLAB),
                new ItemStack(Items.ANDESITE_STAIRS),
                new ItemStack(Items.POLISHED_ANDESITE_STAIRS),
                new ItemStack(Items.DIORITE_STAIRS),
                new ItemStack(Items.POLISHED_DIORITE_STAIRS),
                new ItemStack(Items.GRANITE_STAIRS),
                new ItemStack(Items.POLISHED_GRANITE_STAIRS),
                new ItemStack(Items.MOSSY_STONE_BRICK_STAIRS),
                new ItemStack(Items.MOSSY_COBBLESTONE_STAIRS),
                new ItemStack(Items.SMOOTH_SANDSTONE_STAIRS),
                new ItemStack(Items.SMOOTH_RED_SANDSTONE_STAIRS),
                new ItemStack(Items.SMOOTH_QUARTZ_STAIRS),
                new ItemStack(Items.RED_NETHER_BRICK_STAIRS),
                new ItemStack(Items.END_STONE_BRICK_STAIRS),
                new ItemStack(Items.SMITHING_TABLE),
                new ItemStack(Items.SMOKER),
                new ItemStack(Items.SWEET_BERRIES),
                new ItemStack(Items.BRICK_WALL),
                new ItemStack(Items.ANDESITE_WALL),
                new ItemStack(Items.BRICK_WALL),
                new ItemStack(Items.DIORITE_WALL),
                new ItemStack(Items.GRANITE_WALL),
                new ItemStack(Items.PRISMARINE_WALL),
                new ItemStack(Items.STONE_BRICK_WALL),
                new ItemStack(Items.MOSSY_STONE_BRICK_WALL),
                new ItemStack(Items.SANDSTONE_WALL),
                new ItemStack(Items.RED_SANDSTONE_WALL),
                new ItemStack(Items.NETHER_BRICK_WALL),
                new ItemStack(Items.RED_NETHER_BRICK_WALL),
                new ItemStack(Items.END_STONE_BRICK_WALL),
                globe_banner.getItemStack(),
                new ItemStack(Items.CROSSBOW),
                new ItemStack(Items.BLUE_DYE),
                new ItemStack(Items.BROWN_DYE),
                new ItemStack(Items.BLACK_DYE),
                new ItemStack(Items.WHITE_DYE),
                new ItemStack(Items.LEATHER_HORSE_ARMOR),
                new ItemStack(Items.CAT_SPAWN_EGG),
                new ItemStack(Items.FOX_SPAWN_EGG),
                new ItemStack(Items.PANDA_SPAWN_EGG),
                new ItemStack(Items.PILLAGER_SPAWN_EGG),
                new ItemStack(Items.RAVAGER_SPAWN_EGG),
                new ItemStack(Items.TRADER_LLAMA_SPAWN_EGG),
                new ItemStack(Items.WANDERING_TRADER_SPAWN_EGG),
                new ItemStack(Items.SUSPICIOUS_STEW)
        };
        // @formatter:on

        new TabItemList("villageandpillage", new ItemStack(Items.CROSSBOW), villageItems).setHasSearchBar(false);
    }
}
