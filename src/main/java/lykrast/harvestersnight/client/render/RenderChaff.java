package lykrast.harvestersnight.client.render;

import lykrast.harvestersnight.client.layers.LayerChaffHeldItem;
import lykrast.harvestersnight.client.models.ModelChaff;
import lykrast.harvestersnight.common.EntityChaff;
import lykrast.harvestersnight.common.HarvestersNight;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderChaff extends RenderLiving<EntityChaff> {
	public static final ResourceLocation TEXTURES = new ResourceLocation(HarvestersNight.MODID, "textures/entity/chaff.png");

	public RenderChaff(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelChaff(), 0.2F);
		this.addLayer(new LayerChaffHeldItem(this));
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityChaff entity) {
		return TEXTURES;
	}

}
