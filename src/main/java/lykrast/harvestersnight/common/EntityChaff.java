package lykrast.harvestersnight.common;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityChaff extends EntityVex {
    public EntityChaff(World worldIn) {
        super(worldIn);
        setSize(0.6F, 2.8F);
        this.isImmuneToFire = false;
        this.setSize(0.4F, 0.8F);
        experienceValue = 0;
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(4.0D);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty)
    {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.IRON_HOE));
        this.setDropChance(EntityEquipmentSlot.MAINHAND, 0.0F);
    }
}
