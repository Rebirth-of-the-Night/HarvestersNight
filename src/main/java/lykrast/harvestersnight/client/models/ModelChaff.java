package lykrast.harvestersnight.client.models;// Made with Blockbench 5.2.1
// Exported for Minecraft version 1.7 - 1.12
// Paste this class into your mod and generate all required imports


import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import net.minecraft.util.math.MathHelper;

public class ModelChaff extends ModelBiped {
    private final ModelRenderer head;
    private final ModelRenderer headTendril;
    private final ModelRenderer headTendril2;
    private final ModelRenderer body;
    private final ModelRenderer rightArm;
    private final ModelRenderer leftArm;

    public ModelChaff() {
        textureWidth = 32;
        textureHeight = 32;

        head = new ModelRenderer(this);
        head.setRotationPoint(0.0F, 18.0F, 0.0F);
        head.cubeList.add(new ModelBox(head, 0, 0, -2.5F, -5.0F, -2.5F, 5, 5, 5, 0.0F, false));

        headTendril = new ModelRenderer(this);
        headTendril.setRotationPoint(0.0F, -6.1162F, 4.5981F);
        head.addChild(headTendril);
        setRotationAngle(headTendril, -0.3927F, 0.0F, 0.0F);
        headTendril.cubeList.add(new ModelBox(headTendril, 2, 0, -1.0F, 0.0F, 0.0F, 2, 2, 5, 0.0F, false));

        headTendril2 = new ModelRenderer(this);
        headTendril2.setRotationPoint(0.0F, 4.0F, 5.0F);
        head.addChild(headTendril2);
        setRotationAngle(headTendril2, 0.3927F, 0.0F, 0.0F);
        headTendril2.cubeList.add(new ModelBox(headTendril2, 1, -1, -1.5F, -10.0F, -2.5F, 3, 3, 6, 0.0F, false));

        body = new ModelRenderer(this);
        body.setRotationPoint(0.0F, 18.0F, 0.0F);
        body.cubeList.add(new ModelBox(body, 0, 10, -1.5F, 0.0F, -1.0F, 3, 4, 2, 0.0F, false));
        body.cubeList.add(new ModelBox(body, 0, 16, -1.5F, 1.0F, -1.0F, 3, 5, 2, -0.2F, false));

        rightArm = new ModelRenderer(this);
        rightArm.setRotationPoint(-2.0F, 18.5F, 0.0F);
        rightArm.cubeList.add(new ModelBox(rightArm, 23, 0, -1.5F, -0.5F, -1.0F, 2, 4, 2, 0.0F, false));

        leftArm = new ModelRenderer(this);
        leftArm.setRotationPoint(2.0F, 18.5F, 0.0F);
        leftArm.cubeList.add(new ModelBox(leftArm, 23, 6, -0.5F, -0.5F, -1.0F, 2, 4, 2, 0.0F, false));
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        body.render(f5);
        head.render(f5);
        rightArm.render(f5);
        leftArm.render(f5);
    }

    @Override
    public void setRotationAngles(float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, Entity entityIn)
    {
        boolean flag = entityIn instanceof EntityLivingBase && ((EntityLivingBase)entityIn).getTicksElytraFlying() > 4;
        this.head.rotateAngleY = netHeadYaw * 0.017453292F;

        if (flag)
        {
            this.head.rotateAngleX = -((float)Math.PI / 4F);
        }
        else
        {
            this.head.rotateAngleX = headPitch * 0.017453292F;
        }

        this.body.rotateAngleY = 0.0F;
        float f = 1.0F;

        if (flag)
        {
            f = (float)(entityIn.motionX * entityIn.motionX + entityIn.motionY * entityIn.motionY + entityIn.motionZ * entityIn.motionZ);
            f = f / 0.2F;
            f = f * f * f;
        }

        if (f < 1.0F)
        {
            f = 1.0F;
        }

        this.rightArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F + (float)Math.PI) * 2.0F * limbSwingAmount * 0.5F / f;
        this.leftArm.rotateAngleX = MathHelper.cos(limbSwing * 0.6662F) * 2.0F * limbSwingAmount * 0.5F / f;
        this.rightArm.rotateAngleZ = 0.0F;
        this.leftArm.rotateAngleZ = 0.0F;

        if (this.isRiding)
        {
            this.rightArm.rotateAngleX += -((float)Math.PI / 5F);
            this.leftArm.rotateAngleX += -((float)Math.PI / 5F);
        }

        this.rightArm.rotateAngleY = 0.0F;
        this.rightArm.rotateAngleZ = 0.0F;

        switch (this.leftArmPose)
        {
            case EMPTY:
                this.leftArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.leftArm.rotateAngleX = this.leftArm.rotateAngleX * 0.5F - 0.9424779F;
                this.leftArm.rotateAngleY = 0.5235988F;
                break;
            case ITEM:
                this.leftArm.rotateAngleX = this.leftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
                this.leftArm.rotateAngleY = 0.0F;
        }

        switch (this.rightArmPose)
        {
            case EMPTY:
                this.rightArm.rotateAngleY = 0.0F;
                break;
            case BLOCK:
                this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - 0.9424779F;
                this.rightArm.rotateAngleY = -0.5235988F;
                break;
            case ITEM:
                this.rightArm.rotateAngleX = this.rightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F);
                this.rightArm.rotateAngleY = 0.0F;
        }

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
        this.body.rotateAngleX = 0.0F;

        this.rightArm.rotateAngleZ += MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.leftArm.rotateAngleZ -= MathHelper.cos(ageInTicks * 0.09F) * 0.05F + 0.05F;
        this.rightArm.rotateAngleX += MathHelper.sin(ageInTicks * 0.067F) * 0.05F;
        this.leftArm.rotateAngleX -= MathHelper.sin(ageInTicks * 0.067F) * 0.05F;

        if (this.rightArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
        {
            this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY;
            this.leftArm.rotateAngleY = 0.1F + this.head.rotateAngleY + 0.4F;
            this.rightArm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
            this.leftArm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
        }
        else if (this.leftArmPose == ModelBiped.ArmPose.BOW_AND_ARROW)
        {
            this.rightArm.rotateAngleY = -0.1F + this.head.rotateAngleY - 0.4F;
            this.leftArm.rotateAngleY = 0.1F + this.head.rotateAngleY;
            this.rightArm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
            this.leftArm.rotateAngleX = -((float)Math.PI / 2F) + this.head.rotateAngleX;
        }
    }

    public void setModelAttributes(ModelBase model)
    {
        super.setModelAttributes(model);

        if (model instanceof ModelBiped)
        {
            ModelBiped modelbiped = (ModelBiped)model;
            this.leftArmPose = modelbiped.leftArmPose;
            this.rightArmPose = modelbiped.rightArmPose;
        }
    }

    public void setVisible(boolean visible)
    {
        this.head.showModel = visible;
        this.body.showModel = visible;
        this.rightArm.showModel = visible;
        this.leftArm.showModel = visible;
    }

    public void postRenderArm(float scale, EnumHandSide side)
    {
        this.getArmForSide(side).postRender(scale);
    }

    protected ModelRenderer getArmForSide(EnumHandSide side)
    {
        return side == EnumHandSide.LEFT ? this.leftArm : this.rightArm;
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

    public void setRotationAngle(ModelRenderer modelRenderer, float x, float y, float z) {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}