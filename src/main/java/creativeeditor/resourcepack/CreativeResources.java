package creativeeditor.resourcepack;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.base.Joiner;

import creativeeditor.CreativeEditor;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.moddiscovery.ModFile;
import net.minecraftforge.fml.packs.DelegatableResourcePack;

public class CreativeResources extends DelegatableResourcePack {
    private final ModFile modFile = ModList.get().getModFileById( CreativeEditor.MODID ).getFile();
    private final String id;


    public CreativeResources(final String id) {
        super( new File( id ) );
        this.id = id;
    }


    public ModFile getModFile() {
        return this.modFile;
    }


    @Override
    public String getName() {
        return "CE - Armor Stands";
    }


    @Override
    public InputStream getInputStream( String name ) throws IOException {
        final Path path = modFile.getLocator().findPath( modFile, id, name );
        return Files.newInputStream( path, StandardOpenOption.READ );
    }


    @Override
    public boolean resourceExists( String name ) {
        return Files.exists( modFile.getLocator().findPath( modFile, id, name ) );
    }


    @Override
    public Collection<ResourceLocation> getAllResourceLocations( ResourcePackType type, String pathIn, int maxDepth, Predicate<String> filter ) {
        try {
            Path root = modFile.getLocator().findPath( modFile, id, type.getDirectoryName() ).toAbsolutePath();
            Path inputPath = root.getFileSystem().getPath( pathIn );

            return Files.walk( root ).
                    map( path -> root.relativize( path.toAbsolutePath() ) ).
                    filter( path -> path.getNameCount() > 1 && path.getNameCount() - 1 <= maxDepth ). // Make sure the depth is within bounds, ignoring domain
                    filter( path -> !path.toString().endsWith( ".mcmeta" ) ). // Ignore .mcmeta files
                    filter( path -> path.subpath( 1, path.getNameCount() ).startsWith( inputPath ) ). // Make sure the target path is inside this one (again ignoring domain)
                    filter( path -> filter.test( path.getFileName().toString() ) ). // Test the file name against the predicate
                    // Finally we need to form the RL, so use the first name as the domain, and the
                    // rest as the path
                    // It is VERY IMPORTANT that we do not rely on Path.toString as this is
                    // inconsistent between operating systems
                    // Join the path names ourselves to force forward slashes
                    map( path -> new ResourceLocation( path.getName( 0 ).toString(), Joiner.on( '/' ).join( path.subpath( 1, Math.min( maxDepth, path.getNameCount() ) ) ) ) ).collect( Collectors.toList() );
        }
        catch (IOException e) {
            return Collections.emptyList();
        }
    }


    @Override
    public Set<String> getResourceNamespaces( ResourcePackType type ) {
        try {
            Path root = modFile.getLocator().findPath( modFile, id, type.getDirectoryName() ).toAbsolutePath();
            return Files.walk( root, 1 ).map( path -> root.relativize( path.toAbsolutePath() ) ).filter( path -> path.getNameCount() > 0 ) // skip the root entry
                    .map( p -> p.toString().replaceAll( "/$", "" ) ) // remove the trailing slash, if present
                    .filter( s -> !s.isEmpty() ) // filter empty strings, otherwise empty strings default to minecraft in
                                                 // ResourceLocations
                    .collect( Collectors.toSet() );
        }
        catch (IOException e) {
            return Collections.emptySet();
        }
    }


    public InputStream getResourceStream( ResourcePackType type, ResourceLocation location ) throws IOException {
        if (location.getPath().startsWith( "lang/" )) {
            return super.getResourceStream( ResourcePackType.CLIENT_RESOURCES, location );
        }
        else {
            return super.getResourceStream( type, location );
        }
    }


    public boolean resourceExists( ResourcePackType type, ResourceLocation location ) {
        if (location.getPath().startsWith( "lang/" )) {
            return super.resourceExists( ResourcePackType.CLIENT_RESOURCES, location );
        }
        else {
            return super.resourceExists( type, location );
        }
    }


    @Override
    public void close() throws IOException {

    }
}
