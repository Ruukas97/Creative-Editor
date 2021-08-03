package creativeeditor.json;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import net.minecraft.item.ItemStack;

import javax.annotation.Nullable;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumMap;

public class MinecraftHeads {
    public static final String API_URL = "https://minecraft-heads.com/scripts/api.php?cat=";
    private static final EnumMap<MinecraftHeadsCategory, ArrayList<CachedHead>> CACHED_HEADS = new EnumMap<>(MinecraftHeadsCategory.class);


    public static ArrayList<CachedHead> getHeads(MinecraftHeadsCategory category) {
        ArrayList<CachedHead> list = CACHED_HEADS.get(category);

        if (list == null) {
            list = loadCategoryItemStack(category);

            if (list == null) {
                return new ArrayList<>();
            }
        }

        return list;
    }


    public static ArrayList<ItemStack> createItemStacks(MinecraftHeadsCategory category) {
        ArrayList<CachedHead> list = CACHED_HEADS.get(category);

        if (list == null) {
            list = loadCategoryItemStack(category);

            if (list == null) {
                return new ArrayList<>();
            }
        }

        long startTime = System.nanoTime();
        ArrayList<ItemStack> stacks = new ArrayList<>();
        for (CachedHead head : list) {
            stacks.add(head.getItemStack());
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Took " + duration + "ms to generate head itemstacks.");

        return stacks;
    }


    public static boolean isLoaded(MinecraftHeadsCategory category) {
        return CACHED_HEADS.containsKey(category);
    }


    @Nullable
    private static ArrayList<CachedHead> loadCategoryItemStack(MinecraftHeadsCategory category) {
        try {

            MinecraftHeadsResponse[] response = readCategory(category);
            ArrayList<CachedHead> list = new ArrayList<>(response.length);

            long startTime = System.nanoTime();
            for (MinecraftHeadsResponse element : response) {
                list.add(new CachedHead(element));
            }
            long endTime = System.nanoTime();
            long duration = (endTime - startTime) / 1000000;
            System.out.println("Took " + duration + "ms to convert to CachedHead.");

            if (!list.isEmpty())
                CACHED_HEADS.put(category, list);
            return list;
        } catch (IOException | JsonParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static MinecraftHeadsResponse[] readCategory(MinecraftHeadsCategory category) throws IOException, JsonParseException {
        long startTime = System.nanoTime();
        InputStreamReader reader = new InputStreamReader(category.getURL().openStream());
        Gson gson = new Gson();
        MinecraftHeadsResponse[] response = gson.fromJson(reader, MinecraftHeadsResponse[].class);
        reader.close();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1000000;
        System.out.println("Took " + duration + "ms to download category.");
        return response;
    }
}
