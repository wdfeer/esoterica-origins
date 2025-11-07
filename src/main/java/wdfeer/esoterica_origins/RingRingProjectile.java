package wdfeer.esoterica_origins;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.joml.Vector3f;
import java.util.Random;

public class RingRingProjectile extends ProjectileEntity {
    static Random random = new Random();

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
        var damageTypeEntry = getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DamageTypes.MAGIC);
        entityHitResult.getEntity().damage(new DamageSource(damageTypeEntry, this, getOwner()), 4);
    }

    @Override
    public void tick() {
        if (getWorld().isClient) {
            var color = new Vector3f(0.6f + random.nextFloat() * 0.2f, 0f, 0f);
            getWorld().addParticle(new DustParticleEffect(color, 2f), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
        super.tick();
    }
}
