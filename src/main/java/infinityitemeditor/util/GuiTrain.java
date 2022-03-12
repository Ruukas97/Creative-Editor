package infinityitemeditor.util;

import net.minecraft.util.ResourceLocation;
import infinityitemeditor.InfinityItemEditor;

import java.util.Random;

public class GuiTrain {
    public ResourceLocation texture;
    public float red;
    public float green;
    public float blue;
    public float speed;

    static ResourceLocation[] textures = { new ResourceLocation( InfinityItemEditor.MODID, "textures/trains/train_one.png" ),
            new ResourceLocation( InfinityItemEditor.MODID, "textures/trains/train_two.png" ),
            new ResourceLocation( InfinityItemEditor.MODID, "textures/trains/train_three.png" ),
            new ResourceLocation( InfinityItemEditor.MODID, "textures/trains/train_four.png" ),
            new ResourceLocation( InfinityItemEditor.MODID, "textures/trains/train_five.png" ),
            new ResourceLocation( InfinityItemEditor.MODID, "textures/trains/train_six.png" ),
            new ResourceLocation( InfinityItemEditor.MODID, "textures/trains/train_seven.png" ),
            new ResourceLocation( InfinityItemEditor.MODID, "textures/trains/train_eight.png" ) };


    public GuiTrain(ResourceLocation type, float colorRed, float colorGreen, float colorBlue, float trainSpeed)
    {
        texture = type;
        red = colorRed;
        green = colorGreen;
        blue = colorBlue;
        speed = trainSpeed;
    }


    public static GuiTrain randomTrain()
    {
        Random random = new Random();
        ResourceLocation type = textures[ random.nextInt( textures.length ) ];
        float trainSpeed = random.nextInt( 15 ) + 5;
        return new GuiTrain( type, (float)Math.random(), (float)Math.random(), (float)Math.random(), trainSpeed );
    }
}
