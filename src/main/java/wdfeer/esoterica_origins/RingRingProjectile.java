package wdfeer.esoterica_origins;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import java.util.Random;
import java.util.UUID;

public class RingRingProjectile extends ProjectileEntity {
    static Random random = new Random();

    @Override
    protected void initDataTracker() {
    }

    public static final EntityType<RingRingProjectile> ENTITY_TYPE =
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, RingRingProjectile::new)
                    .dimensions(EntityDimensions.fixed(0.4f, 0.4f))
                    .build();

    public RingRingProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        var damageTypeEntry = getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DamageTypes.MAGIC);
        entityHitResult.getEntity().damage(new DamageSource(damageTypeEntry, this, getOwner()), 4);
        kill();
    }

    @Override
    protected void onBlockCollision(BlockState state) {
        super.onBlockCollision(state);
        for (int i = 0; i < 6; i++) {
            var color = new Vector3f(0.6f + random.nextFloat() * 0.2f, 0f, 0f);
            getWorld().addParticle(new DustParticleEffect(color, 1.2f), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        }
        if (!getWorld().isClient) {
            kill();
        }
    }

    @Override
    public void tick() {
        var world = getWorld();
        if (world.isClient) {
            var color = new Vector3f(0.6f + random.nextFloat() * 0.2f, 0f, 0f);
            world.addParticle(new DustParticleEffect(color, 2f), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        } else if (world instanceof ServerWorld serverWorld) {
            if (targetUUID == null) {
                for (Entity e : serverWorld.iterateEntities()) {
                   if (e instanceof Monster) {
                      targetUUID = e.getUuid();
                   }
                }
                return;
            }

            Entity target = serverWorld.getEntity(targetUUID);
            if (target == null || target.isRemoved() || !target.isAlive()) {
                kill();
                return;
            }

            var direction = target.getPos().subtract(getPos()).normalize();
            addVelocity(direction.multiply(0.01));
        }

        super.tick();
    }

    @Nullable
    private UUID targetUUID = null;

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        targetUUID = nbt.getUuid("target");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putUuid("target", targetUUID);
    }
}
