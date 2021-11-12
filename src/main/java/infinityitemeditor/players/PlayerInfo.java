package infinityitemeditor.players;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import infinityitemeditor.InfinityItemEditor;
import lombok.Getter;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

public class PlayerInfo {
    private static final Path PATH = InfinityItemEditor.DATAPATH.resolve("players");
    private static final HashMap<UUID, PlayerInfo> CACHE = new HashMap<>();

    @Getter
    private final String namePlate;


    private PlayerInfo(UUID uuid) throws JsonParseException, IOException {
        CACHE.put(uuid, this);
        PlayerInfoResponse resp = readPlayerInfo(uuid);
        namePlate = resp != null ? resp.getNameplate() : null;
    }


    public static PlayerInfo getByUUID(UUID uuid) {
        try {
            return CACHE.getOrDefault(uuid, new PlayerInfo(uuid));
        } catch (JsonParseException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    private static PlayerInfoResponse readPlayerInfo(UUID uuid) throws IOException, JsonParseException {
        File file = PATH.resolve(uuid.toString() + ".json").toFile();
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (!file.exists()) {
            file.createNewFile();
        }
        Reader reader = new FileReader(file);
        Gson gson = new Gson();
        PlayerInfoResponse response = gson.fromJson(reader, PlayerInfoResponse.class);
        reader.close();

        return response;
    }
}
