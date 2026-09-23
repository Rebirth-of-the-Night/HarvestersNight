package lykrast.harvestersnight.common;

import javax.annotation.Nullable;


import net.minecraft.util.text.TextComponentString;
import org.apache.commons.lang3.ArrayUtils;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo;
import net.minecraft.world.BossInfoServer;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class EntityHarvester extends EntityMob {
	public static final ResourceLocation LOOT = new ResourceLocation(HarvestersNight.MODID, "entities/harvester");
    protected static final DataParameter<Integer> CURRENT_STATE = EntityDataManager.createKey(EntityHarvester.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> PREV_STATE = EntityDataManager.createKey(EntityHarvester.class, DataSerializers.VARINT);
	protected static final DataParameter<Integer> ANIM_STATE = EntityDataManager.createKey(EntityHarvester.class, DataSerializers.VARINT);

	private final BossInfoServer bossInfo = new BossInfoServer(getDisplayName(), BossInfo.Color.YELLOW, BossInfo.Overlay.PROGRESS);
	private float chargeMultiplier = 1;
	private float fangSpeed = 1;
	private float fangDuration = 1;
	private float tauntLength = 1;
	private float circleSpeed = 1;


	public EntityHarvester(World worldIn) {
		super(worldIn);
        setSize(0.6F, 2.8F);
        experienceValue = 50;
        moveHelper = new AIMoveControl(this);
	}

	//If you look carefully it is very similar to the Mourner from Defiled Lands
	//Which already had a lot of stuff from Vexes
	@Override
	protected void initEntityAI() {
        tasks.addTask(1, new EntityAISwimming(this));
		//tasks.addTask(2, new AICircleAttack(this));
		tasks.addTask(3, new AIChargeAttack(this));
        tasks.addTask(4, new AIClawAttack(this));
        //tasks.addTask(5, new AITaunt(this));
		//tasks.addTask(6, new AISummon(this));
        tasks.addTask(9, new EntityAIWatchClosest(this, EntityPlayer.class, 3.0F, 1.0F));
        tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    	targetTasks.addTask(1, new EntityAINearestAttackableTarget<EntityPlayer>(this, EntityPlayer.class, false));
        targetTasks.addTask(2, new EntityAIHurtByTarget(this, false, new Class[0]));
    }

	@Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
		getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.8D);
    }

	@Override
    protected void entityInit() {
        super.entityInit();
        dataManager.register(CURRENT_STATE, -1);
		dataManager.register(PREV_STATE, -1);
		dataManager.register(ANIM_STATE, -1);
    }

	@Override
	public void move(MoverType type, double x, double y, double z) {
        super.move(type, x, y, z);
        doBlockCollisions();
    }

	@Override
    public void onUpdate() {
        noClip = true;
        super.onUpdate();
        noClip = false;
        setNoGravity(true);
    }

	@Override
	public void onLivingUpdate() {
		//Disappear in sunlight when it has no attack target
		if (world.isDaytime() && !world.isRemote && getAttackTarget() == null)
        {
            float f = getBrightness();

            if (f > 0.5F && rand.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && world.canSeeSky(new BlockPos(posX, posY + getEyeHeight(), posZ)))
            {
                boolean flag = true;
                ItemStack itemstack = getItemStackFromSlot(EntityEquipmentSlot.HEAD);

                if (!itemstack.isEmpty())
                {
                    if (itemstack.isItemStackDamageable())
                    {
                        itemstack.setItemDamage(itemstack.getItemDamage() + rand.nextInt(2));

                        if (itemstack.getItemDamage() >= itemstack.getMaxDamage())
                        {
                            renderBrokenItemStack(itemstack);
                            setItemStackToSlot(EntityEquipmentSlot.HEAD, ItemStack.EMPTY);
                        }
                    }

                    flag = false;
                }

                if (flag) setDead();
            }
        }

		if(this.getHealth() <= this.getMaxHealth()/2){
			chargeMultiplier = 1.3F;
			fangSpeed = 0.6F;
			fangDuration = 1.5F;
			tauntLength = 0.6F;
			circleSpeed = 1.3F;
		}
		super.onLivingUpdate();
	}

	@Override
	protected void updateAITasks() {
		super.updateAITasks();
		bossInfo.setPercent(getHealth() / getMaxHealth());
    }

	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		//Triple damage from fire
		if (source == DamageSource.HOT_FLOOR || source == DamageSource.IN_FIRE || source == DamageSource.ON_FIRE || source == DamageSource.LAVA)
			amount *= 3;
		return super.attackEntityFrom(source, amount);
    }

	@Override
	public boolean getCanSpawnHere() {
		return ArrayUtils.contains(HarvestersNightConfig.dimList, world.provider.getDimension()) == HarvestersNightConfig.whiteList
				&& posY > 40
				&& rand.nextInt(HarvestersNightConfig.harvesterChance) == 0
				&& world.canSeeSky(new BlockPos(posX, posY + getEyeHeight(), posZ))
				&& super.getCanSpawnHere();
	}

	@Override
    @Nullable
	public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
		setEquipmentBasedOnDifficulty(difficulty);
		//setEnchantmentBasedOnDifficulty(difficulty);

        if (HarvestersNightConfig.lightning) world.addWeatherEffect(new EntityLightningBolt(world, posX, posY, posZ, true));
        if (HarvestersNightConfig.laugh) playSound(HarvestersNight.harvesterSpawn, 8, 1);

		return super.onInitialSpawn(difficulty, livingdata);
	}

	@Override
	protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
		setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(HarvestersNight.harvesterScythe));
		setDropChance(EntityEquipmentSlot.MAINHAND, 0);
	}

	@Override
	public void addTrackingPlayer(EntityPlayerMP player) {
		super.addTrackingPlayer(player);
		if (HarvestersNightConfig.healthBar) bossInfo.addPlayer(player);
	}

	@Override
	public void removeTrackingPlayer(EntityPlayerMP player) {
		super.removeTrackingPlayer(player);
		bossInfo.removePlayer(player);
	}

	@Override
	public boolean isNonBoss() {
		return !HarvestersNightConfig.isBoss;
	}

	@Override
	public void setCustomNameTag(String name) {
		super.setCustomNameTag(name);
		bossInfo.setName(getDisplayName());
	}

	@Override
    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

	@Override
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.UNDEAD;
	}

	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.BLOCK_FIRE_AMBIENT;
	}

	@Override
	protected SoundEvent getHurtSound(DamageSource source) {
		return HarvestersNight.harvesterHurt;
	}

	@Override
	protected SoundEvent getDeathSound() {
		return HarvestersNight.harvesterDie;
	}

	private void setHarvesterAnimation(int state){
		dataManager.set(ANIM_STATE, state);
	}

	private boolean getHarvesterAnimation(int state) {
		int i = dataManager.get(ANIM_STATE);
		if (i == state) return true;
		else return false;
	}

	private void setHarvesterState(int state) {
		dataManager.set(CURRENT_STATE, state);
	}

	private boolean getHarvesterState(int state) {
        int i = dataManager.get(CURRENT_STATE);
		if (i == state) return true;
		else return false;
    }

	private void setHarvesterPrevState(int state) {
		dataManager.set(PREV_STATE, state);
	}

	private boolean getHarvesterPrevState(int state) {
		int i = dataManager.get(PREV_STATE);
		if (i == state) return true;
		else return false;
	}

	//sets current state
	public void resetState(){
		setHarvesterState(-1);
	}
	public boolean isIdle(){
		return getHarvesterState(-1);
	}

    public boolean isCharging() {
        return getHarvesterState(1);
    }
    public void setCharging() {
        setHarvesterState(1);
    }

	public boolean isCasting() {
		return getHarvesterState(2);
	}
	public void setCasting() {
		setHarvesterState(2);
	}
    // sets previous state
	public boolean wasCharging() {
		return getHarvesterPrevState(1);
	}
	public void setWasCharging() {
		setHarvesterPrevState(1);
	}

	public boolean wasCasting() {
		return getHarvesterPrevState(2);
	}
	public void setWasCasting() {
		setHarvesterPrevState(2);
	}
	//sets animation state
	public void resetAnimation(){
		setHarvesterAnimation(-1);
	}

	public boolean isChargingAnimation() {
		return getHarvesterAnimation(1);
	}
	public void setChargingAnimation() {
		setHarvesterAnimation(1);
	}

	public boolean isCastingAnimation() {
		return getHarvesterAnimation(2);
	}
	public void setCastingAnimation() {
		setHarvesterAnimation(2);
	}

	public void teleportRandomRadius(double radius, double height){
		EntityLivingBase target = this.getAttackTarget();

		double angle = this.rand.nextInt(360) * this.rand.nextDouble();
		double x = radius * cos(angle);
		double z = radius * sin(angle);

		this.setPositionAndUpdate(target.posX + x, target.posY + height, target.posZ + z);
	}


	//Damn it Majong and your inner classes
	//Bunch of stuff copied from Vexes (and also from the Mourner)
	private static class AIMoveControl extends EntityMoveHelper
    {
        public AIMoveControl(EntityHarvester harvester) {
            super(harvester);
        }

        public void onUpdateMoveHelper() {
            if (action == EntityMoveHelper.Action.MOVE_TO)
            {
                double d0 = posX - entity.posX;
                double d1 = posY - entity.posY;
                double d2 = posZ - entity.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = MathHelper.sqrt(d3);

                if (d3 < entity.getEntityBoundingBox().getAverageEdgeLength())
                {
                    this.action = EntityMoveHelper.Action.WAIT;
                    entity.motionX *= 0.5D;
                    entity.motionY *= 0.5D;
                    entity.motionZ *= 0.5D;
                }
                else
                {
                    entity.motionX += d0 / d3 * 0.05D * speed;
                    entity.motionY += d1 / d3 * 0.05D * speed;
                    entity.motionZ += d2 / d3 * 0.05D * speed;

                    if (entity.getAttackTarget() == null)
                    {
                        entity.rotationYaw = -((float)MathHelper.atan2(entity.motionX, entity.motionZ)) * (180F / (float)Math.PI);
                        entity.renderYawOffset = entity.rotationYaw;
                    }
                    else
                    {
                        double d4 = entity.getAttackTarget().posX - entity.posX;
                        double d5 = entity.getAttackTarget().posZ - entity.posZ;
                        entity.rotationYaw = -((float)MathHelper.atan2(d4, d5)) * (180F / (float)Math.PI);
                        entity.renderYawOffset = entity.rotationYaw;
                    }
                }
            }
        }
    }

	private static class AIChargeAttack extends EntityAIBase
    {
		private int phase;
		private float time;
		private EntityHarvester harvester;

		public AIChargeAttack(EntityHarvester harvester) {
			setMutexBits(1);
			this.harvester = harvester;
			time = 0;
			phase = 0;
		}

		@Override
		public boolean shouldExecute() {
			if (harvester.getAttackTarget() != null && !harvester.wasCharging() && harvester.isIdle()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean shouldContinueExecuting() {
			return harvester.isCharging() && harvester.getAttackTarget() != null && harvester.getAttackTarget().isEntityAlive();
		}

		@Override
		public void startExecuting() {
			harvester.setCharging();
			harvester.teleportRandomRadius(12, 4);
			time = 20;
			phase = 0;
			harvester.playSound(HarvestersNight.harvesterCharge, 1.0F, 1.0F);
		}

		@Override
		public void resetTask() {
			harvester.resetState();
			harvester.resetAnimation();
			harvester.setWasCharging();
			time = 0;
			phase = 0;
		}

		@Override
		public void updateTask() {
			EntityLivingBase entitylivingbase = harvester.getAttackTarget();
			time--;
			if(phase == 0 && time == 0){
				Vec3d vec3d = entitylivingbase.getPositionEyes(1.0F);
				harvester.moveHelper.setMoveTo(vec3d.x, vec3d.y, vec3d.z, 1.5 * harvester.chargeMultiplier);
				harvester.setChargingAnimation();
				phase = 1;
			}
			if (harvester.getEntityBoundingBox().grow(0.8).intersects(entitylivingbase.getEntityBoundingBox()) && harvester.moveHelper.isUpdating()) {
				harvester.attackEntityAsMob(entitylivingbase);
				harvester.resetState();
			}
			if (!harvester.moveHelper.isUpdating()){
				harvester.resetAnimation();
			}
			if (time <= -100){
				harvester.resetState();
			}
		}
	}

	private static class AIClawAttack extends EntityAIBase
    {
		private EntityHarvester harvester;
		//Phase 0 = startup, 1 = attack, 2 = ending
		private int phase;
		private float time;

		public AIClawAttack(EntityHarvester harvester) {
			setMutexBits(1);
			this.harvester = harvester;
			time = 0;
			phase = 0;
		}

		@Override
		public boolean shouldExecute() {
			if (harvester.getAttackTarget() != null && !harvester.wasCasting() && harvester.isIdle()) {
				return true;
			} else {
				return false;
			}
		}

		@Override
		public boolean shouldContinueExecuting() {
			return time > 0 && harvester.isCasting() && harvester.getAttackTarget() != null && harvester.getAttackTarget().isEntityAlive();
		}

		@Override
		public void startExecuting() {
			harvester.teleportRandomRadius(harvester.rand.nextInt(3) + 4, 6);
			harvester.setMoveVertical(0);
			harvester.setMoveForward(0);
			harvester.setCasting();
			harvester.playSound(HarvestersNight.harvesterSpell, 1.0F, 1.0F);
			time = 40;
			phase = 0;
		}

		@Override
		public void resetTask() {
			harvester.resetState();
			harvester.setWasCasting();
			time = 0;
			phase = 0;
		}

		@Override
		public void updateTask() {
			EntityLivingBase target = harvester.getAttackTarget();
			time--;
			//Attack
			if (phase == 1 && time % (10 * harvester.fangSpeed) == 0) {
				if (target != null && target.isEntityAlive()) {
					double yMin = target.posY;
		            float f = (float)MathHelper.atan2(target.posZ - harvester.posZ, target.posX - harvester.posX);
					spawnFangs(target.posX, target.posZ, yMin, target.posY, f, 0);
				}
			}
			//Change phase
			if (time <= 0 && phase < 3) {
				if (phase == 0) {
					time = Math.round((60 + harvester.rand.nextInt(5)*10) * harvester.fangDuration);
					harvester.setCastingAnimation();
				}
				else if (phase == 1) {
					harvester.resetAnimation();
					time = 50;
				}
				phase++;
			}
			harvester.getLookHelper().setLookPositionWithEntity(target, 10, 10);
		}

		//Adapted from the Evoker
		private void spawnFangs(double x, double z, double yMin, double yStart, float yaw, int delayTick) {
            BlockPos blockpos = new BlockPos(x, yStart, z);
            boolean flag = false;
            double d0 = 0.0D;

            while (true)
            {
                if (!harvester.world.isBlockNormalCube(blockpos, true) && harvester.world.isBlockNormalCube(blockpos.down(), true))
                {
                    if (!harvester.world.isAirBlock(blockpos))
                    {
                        IBlockState iblockstate = harvester.world.getBlockState(blockpos);
                        AxisAlignedBB axisalignedbb = iblockstate.getCollisionBoundingBox(harvester.world, blockpos);

                        if (axisalignedbb != null)
                        {
                            d0 = axisalignedbb.maxY;
                        }
                    }

                    flag = true;
                    break;
                }

                blockpos = blockpos.down();
                if (blockpos.getY() < MathHelper.floor(yMin))
                {
                    break;
                }
            }


            EntityEvokerFangs entityevokerfangs = new EntityEvokerFangs(harvester.world, x, flag ? ((double)blockpos.getY() + d0) : yStart, z, yaw, delayTick, harvester);
            harvester.world.spawnEntity(entityevokerfangs);
        }
	}

}
