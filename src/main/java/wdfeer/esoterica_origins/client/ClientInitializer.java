package wdfeer.esoterica_origins.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.render.entity.EmptyEntityRenderer;
import wdfeer.esoterica_origins.RingRingProjectile;

public class ClientInitializer implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(RingRingProjectile.ENTITY_TYPE, EmptyEntityRenderer::new);
    }
}
