package creativeeditor.particle;

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
