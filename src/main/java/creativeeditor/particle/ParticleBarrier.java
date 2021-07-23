package creativeeditor.particle;

import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

//public class ParticleBarrier extends SpriteTexturedParticle {
//    private ParticleBarrier(ClientWorld world, double x, double y, double z, IItemProvider item) {
//        super(world , x, y, z);
//        this.setSprite(Minecraft.getInstance().getItemRenderer().getItemModelMesher().getParticleIcon(item));
//        this.particleGravity = 0.0F;
//        this.maxAge = 80;
//        this.canCollide = false;
//     }
//
//
//    public IParticleRenderType getRenderType() {
//        return IParticleRenderType.TERRAIN_SHEET;
//    }
//
//
//    public float getScale( float p_217561_1_ ) {
//        return 0.5F;
//    }
//
//
//    @OnlyIn( Dist.CLIENT )
//    public static class Factory implements IParticleFactory<BasicParticleType> {
//        public Particle makeParticle( BasicParticleType typeIn, World worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed ) {
//            return new ParticleBarrier( worldIn, x, y, z, Blocks.BARRIER.asItem() );
//        }
//    }
//}
