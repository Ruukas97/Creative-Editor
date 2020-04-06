package creativeeditor.json;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.EnumMap;

import javax.annotation.Nullable;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;

import creativeeditor.data.DataItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class MinecraftHeads {
    public static final String API_URL = "https://minecraft-heads.com/scripts/api.php?cat=";
    private static final EnumMap<MinecraftHeadsCategory, ArrayList<ItemStack>> CACHED_HEADS = new EnumMap<>( MinecraftHeadsCategory.class );


    public static ArrayList<ItemStack> getHeads( MinecraftHeadsCategory category ) {
        ArrayList<ItemStack> list = CACHED_HEADS.get( category );

        if (list == null) {
            list = loadCategoryItemStack( category );

            if (list == null) {
                return new ArrayList<>();
            }
        }

        return list;
    }


    public static boolean isLoaded( MinecraftHeadsCategory category ) {
        return CACHED_HEADS.containsKey( category );
    }


    @Nullable
    private static ArrayList<ItemStack> loadCategoryItemStack( MinecraftHeadsCategory category ) {
        try {
            MinecraftHeadsResponse[] response = readCategory( category );
            ArrayList<ItemStack> list = new ArrayList<>( response.length );

            for (MinecraftHeadsResponse element : response) {
                DataItem head = new DataItem( new ItemStack( Items.PLAYER_HEAD ) );
                head.getDisplayNameTag().set( element.getName() );
                head.getTag().getSkullOwner().set( element );;
                list.add( head.getItemStack() );
            }

            if (!list.isEmpty())
                CACHED_HEADS.put( category, list );
            return list;
        }
        catch (IOException | JsonParseException e) {
            e.printStackTrace();
        }

        return null;
    }


    private static MinecraftHeadsResponse[] readCategory( MinecraftHeadsCategory category ) throws IOException, JsonParseException {
        InputStreamReader reader = new InputStreamReader( category.getURL().openStream() );
        Gson gson = new Gson();
        MinecraftHeadsResponse[] response = gson.fromJson( reader, MinecraftHeadsResponse[].class );
        reader.close();

        return response;
    }
}
