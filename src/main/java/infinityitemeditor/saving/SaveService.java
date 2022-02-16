package infinityitemeditor.saving;

import infinityitemeditor.InfinityItemEditor;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SaveService {
    private static SaveService INSTANCE;

    public static SaveService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SaveService(Minecraft.getInstance().gameDirectory);
        }
        return INSTANCE;
    }

    @Getter
    private final File dataDir;

    //Item Collections
    @Getter
    private final File itemCollectionsDir;
    @Getter
    private final List<DataItemCollection> itemCollections;


    public SaveService(File file) {
        this.dataDir = new File(file, InfinityItemEditor.MODID + "-data");
        this.itemCollectionsDir = new File(dataDir, "ItemCollections");
        this.itemCollections = new ArrayList<>();
        load();
    }

    private void load() {
        loadItemCollections();
    }

    private void loadItemCollections() {
        Map<File, CompoundNBT> nbts = loadDir(itemCollectionsDir);
        if (nbts == null) {
            return;
        }
        itemCollections.clear();
        for (Map.Entry<File, CompoundNBT> entry : nbts.entrySet()) {
            itemCollections.add(new DataItemCollection(entry.getKey(), entry.getValue()));
        }
    }

    private void saveItemCollections() {
        for (DataItemCollection collection : itemCollections) {
            InfinityItemEditor.LOGGER.info("Saving ItemCollections");
            try {
                collection.save();
            } catch (Exception exception) {
                InfinityItemEditor.LOGGER.error("Failed to save ItemCollection " + collection.getName().get() + " to " + collection.getFile().getName());
            }
        }
    }

    private void addItemCollection(DataItemCollection collection) {
        itemCollections.add(collection);
    }

    private Map<File, CompoundNBT> loadDir(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            return null;
        }
        File[] files = dir.listFiles();
        if (files == null) {
            return null;
        }
        HashMap<File, CompoundNBT> loadedNBT = new HashMap<>();
        for (File file : files) {
            try {
                CompoundNBT nbt = loadFile(file);
                if (nbt == null) {
                    continue;
                }
                loadedNBT.put(file, nbt);
            } catch (Exception exception) {
                InfinityItemEditor.LOGGER.error("Failed to load file: " + file.getName(), exception);
            }
        }
        return loadedNBT;
    }

    public CompoundNBT loadFile(File file) throws IOException {
        CompoundNBT nbt = CompressedStreamTools.read(file);
        if (nbt == null) {
            nbt = new CompoundNBT();
        }
        return nbt;
    }
}
