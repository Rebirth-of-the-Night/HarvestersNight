package lykrast.harvestersnight.client.models;// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import lykrast.harvestersnight.common.EntityHarvester;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelHarvester extends ModelBiped {
    private final ModelRenderer body;
    private final ModelRenderer shawl;
    private final ModelRenderer shawlMiddle;
    private final ModelRenderer clavicle;
    private final ModelRenderer leftArm;
    private final ModelRenderer lowerLeftArm;
    private final ModelRenderer scythe;
    private final ModelRenderer rightArm;
    private final ModelRenderer lowerRightArm;
    private final ModelRenderer neck;
    private final ModelRenderer head;
    private final ModelRenderer skull;
    private final ModelRenderer comb;
    private final ModelRenderer combRight;
    private final ModelRenderer combRightDetail;
    private final ModelRenderer combLeft;
    private final ModelRenderer combLeftDetail;
    private final ModelRenderer torso;
    private final ModelRenderer skirt;
    private final ModelRenderer torsoSkirt;
    private final ModelRenderer torsoDetailFront;
    private final ModelRenderer torsoDetailRight;
    private final ModelRenderer torsoDetailLeft;
    private final ModelRenderer torsoDetailBack;
    public ModelBiped.ArmPose leftArmPose;
    public ModelBiped.ArmPose rightArmPose;

    public ModelHarvester() {
        textureWidth = 128;
        textureHeight = 128;
        this.leftArmPose = ModelBiped.ArmPose.EMPTY;
        this.rightArmPose = ModelBiped.ArmPose.EMPTY;

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 24.0F, 0.0F);


        shawl = new ModelRenderer(this);
        shawl.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(shawl);
        shawl.cubeList.add(new ModelBox(shawl, 64, 0, -16.0F, -55.0F, 6.0F, 32, 20, 0, 0.0F, false));
        shawl.cubeList.add(new ModelBox(shawl, 82, 34, -11.0F, -29.6424F, -6.9343F, 22, 14, 0, 0.0F, false));

        shawlMiddle = new ModelRenderer(this);
        shawlMiddle.setRotationPoint(0.0F, -35.0F, 6.0F);
        shawl.addChild(shawlMiddle);
        setRotationAngle(shawlMiddle, -1.1781F, 0.0F, 0.0F);
        shawlMiddle.cubeList.add(new ModelBox(shawlMiddle, 82, 20, -11.0F, 0.0F, 0.0F, 22, 14, 0, 0.0F, false));

        clavicle = new ModelRenderer(this);
        clavicle.setRotationPoint(0.0F, -29.0F, 4.0F);
        body.addChild(clavicle);
        clavicle.cubeList.add(new ModelBox(clavicle, 42, 122, -20.0F, -1.0F, -2.0F, 40, 3, 3, 0.0F, false));

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(13.0F, 0.0F, -0.5F);
        clavicle.addChild(leftArm);
        leftArm.cubeList.add(new ModelBox(leftArm, 0, 105, -1.0F, -8.5F, -1.0F, 2, 21, 2, 0.0F, false));

        lowerLeftArm = new ModelRenderer(this);
        lowerLeftArm.setRotationPoint(0.0F, 12.5F, 0.0F);
        leftArm.addChild(lowerLeftArm);
        setRotationAngle(lowerLeftArm, 0, 0, 0);
        lowerLeftArm.cubeList.add(new ModelBox(lowerLeftArm, 88, 95, -1.0F, 0.0F, -12.0F, 2, 2, 17, 0.0F, false));

        scythe = new ModelRenderer(this);
        scythe.setRotationPoint(0.0F, -12.5F, 0.0F);
        lowerLeftArm.addChild(scythe);
        scythe.cubeList.add(new ModelBox(scythe, 94, 67, 0.0F, 8.5F, -23.0F, 0, 11, 12, 0.0F, false));

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(-13.0F, 0.5F, -0.5F);
        clavicle.addChild(rightArm);
        rightArm.cubeList.add(new ModelBox(rightArm, 0, 105, -1.0F, -9.0F, -1.0F, 2, 21, 2, 0.0F, false));

        lowerRightArm = new ModelRenderer(this);
        lowerRightArm.setRotationPoint(0.0F, 13.0F, 0.0F);
        rightArm.addChild(lowerRightArm);
        setRotationAngle(lowerRightArm, -25, 0, 0);
        lowerRightArm.cubeList.add(new ModelBox(lowerRightArm, 88, 95, -1.0F, -1.0F, -12.0F, 2, 2, 17, 0.0F, false));

        neck = new ModelRenderer(this);
        neck.setRotationPoint(0.0F, -30.0F, -0.0142F);
        body.addChild(neck);
        neck.cubeList.add(new ModelBox(neck, 7, 16, -2.0F, -4.0F, -2.0F, 4, 4, 4, 0.0F, false));

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, -3.0F, 0.0F);
        neck.addChild(head);
        setRotationAngle(head, 0.1745F, 0.0F, 0.0F);


        skull = new ModelRenderer(this);
        skull.setRotationPoint(0.0F, -3.0F, 1.4142F);
        head.addChild(skull);
        setRotationAngle(skull, 0.0F, 0.7854F, 0.0F);
        skull.cubeList.add(new ModelBox(skull, 1, 1, -2.0F, -5.0F, -5.0F, 7, 8, 7, 0.0F, false));

        comb = new ModelRenderer(this);
        comb.setRotationPoint(0.0F, -8.0F, -5.5355F);
        head.addChild(comb);


        combRight = new ModelRenderer(this);
        combRight.setRotationPoint(0.0F, 0.0F, 0.0F);
        comb.addChild(combRight);
        setRotationAngle(combRight, -0.7006F, 0.3897F, -1.1475F);
        combRight.cubeList.add(new ModelBox(combRight, 30, 1, -3.9539F, -10.9276F, -0.0958F, 4, 11, 4, 0.0F, false));

        combRightDetail = new ModelRenderer(this);
        combRightDetail.setRotationPoint(0.0F, -8.0F, 0.0F);
        combRight.addChild(combRightDetail);
        combRightDetail.cubeList.add(new ModelBox(combRightDetail, 47, 17, -5.9539F, -5.9276F, -0.0958F, 6, 3, 4, 0.0F, false));

        combLeft = new ModelRenderer(this);
        combLeft.setRotationPoint(0.0F, 0.0F, 0.0F);
        comb.addChild(combLeft);
        setRotationAngle(combLeft, -0.7006F, -0.3897F, 1.1475F);
        combLeft.cubeList.add(new ModelBox(combLeft, 46, 1, -0.0461F, -10.9276F, -0.0958F, 4, 11, 4, 0.0F, false));

        combLeftDetail = new ModelRenderer(this);
        combLeftDetail.setRotationPoint(0.0F, -8.0F, 0.0F);
        combLeft.addChild(combLeftDetail);
        combLeftDetail.cubeList.add(new ModelBox(combLeftDetail, 27, 17, -0.0461F, -5.9276F, -0.0958F, 6, 3, 4, 0.0F, false));

        torso = new ModelRenderer(this);
        torso.setRotationPoint(0.0F, 0.0F, 0.0F);
        body.addChild(torso);
        torso.cubeList.add(new ModelBox(torso, 8, 27, -5.0F, -30.0F, -4.0F, 10, 13, 6, 0.0F, false));
        torso.cubeList.add(new ModelBox(torso, 44, 38, -4.0F, -17.0F, -4.0F, 8, 2, 6, 0.0F, false));

        skirt = new ModelRenderer(this);
        skirt.setRotationPoint(0.0F, 0.0F, 0.0F);
        torso.addChild(skirt);


        torsoSkirt = new ModelRenderer(this);
        torsoSkirt.setRotationPoint(0.0F, -15.0F, -4.0F);
        skirt.addChild(torsoSkirt);
        setRotationAngle(torsoSkirt, 0.3927F, 0.0F, 0.0F);
        torsoSkirt.cubeList.add(new ModelBox(torsoSkirt, 32, 50, -3.0F, 0.0F, 0.0F, 6, 9, 6, 0.0F, true));

        torsoDetailFront = new ModelRenderer(this);
        torsoDetailFront.setRotationPoint(0.0F, 0.0F, 0.0F);
        skirt.addChild(torsoDetailFront);
        torsoDetailFront.cubeList.add(new ModelBox(torsoDetailFront, 36, 65, -6.0F, -11.6872F, -1.5454F, 13, 13, 0, 0.0F, false));

        torsoDetailRight = new ModelRenderer(this);
        torsoDetailRight.setRotationPoint(-2.0F, -11.9906F, 0.4937F);
        torsoDetailFront.addChild(torsoDetailRight);
        setRotationAngle(torsoDetailRight, 1.9635F, -1.1781F, -1.5708F);
        torsoDetailRight.cubeList.add(new ModelBox(torsoDetailRight, 36, 65, -6.5F, -0.5F, -0.1F, 13, 13, 0, 0.0F, false));

        torsoDetailLeft = new ModelRenderer(this);
        torsoDetailLeft.setRotationPoint(2.0F, -11.9906F, 0.4937F);
        torsoDetailFront.addChild(torsoDetailLeft);
        setRotationAngle(torsoDetailLeft, 1.1781F, -1.1781F, -1.5708F);
        torsoDetailLeft.cubeList.add(new ModelBox(torsoDetailLeft, 36, 65, -6.5F, -0.5F, -0.1F, 13, 13, 0, 0.0F, false));

        torsoDetailBack = new ModelRenderer(this);
        torsoDetailBack.setRotationPoint(0.5F, -13.2179F, 2.1501F);
        torsoDetailFront.addChild(torsoDetailBack);
        setRotationAngle(torsoDetailBack, 0.7854F, 0.0F, 0.0F);
        torsoDetailBack.cubeList.add(new ModelBox(torsoDetailBack, 36, 65, -6.5F, 0.0F, 0.0F, 13, 13, 0, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        body.render(f5);
    }

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }

    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {   EntityHarvester harvester = (EntityHarvester)entityIn;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;
        this.head.rotateAngleX = headPitch * 0.017453292F;
        this.lowerLeftArm.rotateAngleX = 0;
        this.lowerRightArm.rotateAngleX = 0;
        this.lowerRightArm.rotateAngleZ = 0;

        this.body.rotateAngleY = 0.0F;


        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F;
        this.rightArm.rotateAngleY = 0.0F;
        this.rightArm.rotateAngleZ = 0.0F;

        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F;
        this.leftArm.rotateAngleZ = 0.0F;


        if (this.swingProgress > 0.0F)
        {
            EnumHandSide enumhandside = this.getMainHand(entityIn);
            ModelRenderer modelrenderer = this.getArmForSide(enumhandside);
            float f1 = this.swingProgress;
            this.body.rotateAngleY = MathHelper.sin(MathHelper.sqrt(f1) * ((float)Math.PI * 2F)) * 0.2F;

            if (enumhandside == EnumHandSide.LEFT)
            {
                this.body.rotateAngleY *= -1.0F;
            }

            this.rightArm.rotationPointZ = MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.rightArm.rotationPointX = -MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointZ = -MathHelper.sin(this.body.rotateAngleY) * 5.0F;
            this.leftArm.rotationPointX = MathHelper.cos(this.body.rotateAngleY) * 5.0F;
            this.rightArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleY += this.body.rotateAngleY;
            this.leftArm.rotateAngleX += this.body.rotateAngleY;
            f1 = 1.0F - this.swingProgress;
            f1 = f1 * f1;
            f1 = f1 * f1;
            f1 = 1.0F - f1;
            float f2 = MathHelper.sin(f1 * (float)Math.PI);
            float f3 = MathHelper.sin(this.swingProgress * (float)Math.PI) * -(this.head.rotateAngleX - 0.7F) * 0.75F;
            modelrenderer.rotateAngleX = (float)((double)modelrenderer.rotateAngleX - ((double)f2 * 1.2D + (double)f3));
            modelrenderer.rotateAngleY += this.body.rotateAngleY * 2.0F;
            modelrenderer.rotateAngleZ += MathHelper.sin(this.swingProgress * (float)Math.PI) * -0.4F;
        }


        if(!harvester.isChargingAnimation()) {
            this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.02F;
            this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        }

        this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.02F;
        this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        if(harvester.isChargingAnimation()) {
            rightArm.rotateAngleX = 0.7417649F;
            rightArm.rotateAngleZ = 1.003564F;
            lowerRightArm.rotateAngleX = -2.3998277F;
            lowerRightArm.rotateAngleY = -0.1309F;
            lowerRightArm.rotateAngleZ = 3.14159F;
        }
        if(harvester.isCastingAnimation()){
            leftArm.rotateAngleX = 3.7699115F;
            lowerLeftArm.rotateAngleX = 3.7699115F/3;
        }
        if(harvester.isTauntingAnimation()){
            leftArm.rotateAngleX = (float) (-46 * (Math.PI/180));;
            leftArm.rotateAngleY = (float) (35 * (Math.PI/180));
            leftArm.rotateAngleZ = (float) (-30 * (Math.PI/180));;

            lowerLeftArm.rotateAngleX = (float) (-25 * (Math.PI/180));
        }

    }

    public void postRenderArm(float scale, EnumHandSide side)
    {
        this.getArmForSide(side).postRender(scale);
    }

    protected ModelRenderer getArmForSide(EnumHandSide side)
    {
        return side == EnumHandSide.LEFT ? this.rightArm : this.rightArm;
    }

    protected EnumHandSide getMainHand(Entity entityIn)
    {
        if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
            EnumHandSide enumhandside = entitylivingbase.getPrimaryHand();
            return entitylivingbase.swingingHand == EnumHand.MAIN_HAND ? enumhandside : enumhandside.opposite();
        }
        else
        {
            return EnumHandSide.RIGHT;
        }
    }
}