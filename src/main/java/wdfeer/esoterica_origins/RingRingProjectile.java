package wdfeer.esoterica_origins;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;

public class RingRingProjectile extends ProjectileEntity {
    @Override
    protected void initDataTracker() {
    }

    public static final EntityType<RingRingProjectile> ENTITY_TYPE =
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, RingRingProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.25f, 0.25f))
                    .build();

    public RingRingProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        // TODO: deal damage
    }

    @Override
    public void tick() {
        // TODO: spawn particles on client
        super.tick();
    }
}
