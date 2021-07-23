//package creativeeditor.resourcepack;
//
//import java.util.Map;
//
//import creativeeditor.CreativeEditor;
//import net.minecraft.client.Minecraft;
//import net.minecraft.resources.IPackFinder;
//import net.minecraft.resources.ResourcePackInfo;
//import net.minecraft.resources.ResourcePackInfo.IFactory;
//
//public class ResourcePacks {
//    public static void init() {
//        CreativeEditor.LOGGER.info( "Initializaing Resource Packs" );
//        Minecraft.getInstance().getResourcePackRepository().addPackFinder( new IPackFinder() {
//            @Override
//            public <T extends ResourcePackInfo> void addPackInfosToMap( Map<String, T> map, IFactory<T> fac ) {
//                map.computeIfAbsent( "armor_stand_variants", key -> ResourcePackInfo.create( key, true, () -> new CreativeResources( key ), fac, ResourcePackInfo.Priority.TOP ) );
//            }
//        } );
//    }
//}
