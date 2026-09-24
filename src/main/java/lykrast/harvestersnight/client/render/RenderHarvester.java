package lykrast.harvestersnight.client.render;

import lykrast.harvestersnight.client.layers.LayerHarvesterHeldItem;
import lykrast.harvestersnight.client.models.ModelHarvester;
import lykrast.harvestersnight.common.EntityHarvester;
import lykrast.harvestersnight.common.HarvestersNight;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderHarvester extends RenderLiving<EntityHarvester> {
	public static final ResourceLocation TEXTURES = new ResourceLocation(HarvestersNight.MODID, "textures/entity/harvester.png"),
			EYES = new ResourceLocation(HarvestersNight.MODID, "textures/entity/harvester_eyes.png");

	public RenderHarvester(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelHarvester(), 0.5F);
		this.addLayer(new LayerHarvesterHeldItem(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityHarvester entity) {
		return TEXTURES;
	}

}
