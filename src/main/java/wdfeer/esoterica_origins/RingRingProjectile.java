package wdfeer.esoterica_origins;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.block.AbstractBlock;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.DustParticleEffect;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.TypeFilter;
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
                    .fireImmune()
                    .build();

    public RingRingProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    private boolean isValidTarget(LivingEntity entity) {
        return !entity.isRemoved() && entity.isAlive() && entity instanceof Monster && entity.getHealth() < entity.getMaxHealth() && entity.distanceTo(this) < 50;
    }

    @Override
    public void tick() {
        var world = getWorld();
        if (world.isClient) {
            var color = new Vector3f(0.6f + random.nextFloat() * 0.2f, 0f, 0f);
            world.addParticle(new DustParticleEffect(color, 0.7f + random.nextFloat(0.3f)), this.getX(), this.getY(), this.getZ(), 0, 0, 0);
        } else if (world instanceof ServerWorld serverWorld) {
            if (this.getWorld().getStatesInBox(this.getBoundingBox()).noneMatch(AbstractBlock.AbstractBlockState::isAir)) {
                this.discard();
            }
            for (Entity e : serverWorld.getOtherEntities(this, this.getBoundingBox())) {
                if (e != getOwner() && e instanceof LivingEntity && e.isAlive() && e.canBeHitByProjectile() && ((LivingEntity) e).canTakeDamage()) {
                    var damageTypeEntry = getWorld().getRegistryManager().get(RegistryKeys.DAMAGE_TYPE).entryOf(DamageTypes.MAGIC);
                    e.damage(new DamageSource(damageTypeEntry, this, getOwner()), 4);
                    kill();
                    return;
                }
            }

            if (targetUUID == null) {
                var validTargets = serverWorld.getEntitiesByType(TypeFilter.instanceOf(LivingEntity.class), this::isValidTarget);
                var closest = validTargets.stream().min((e1, e2) -> (int) (e1.distanceTo(this) - e2.distanceTo(this)));
                if (closest.isEmpty()) {
                    kill();
                } else {
                    targetUUID = closest.get().getUuid();
                }
                return;
            }

            LivingEntity target = (LivingEntity) serverWorld.getEntity(targetUUID);
            if (target == null || !isValidTarget(target)) {
                kill();
                return;
            }

            var centerOfMass = target.getEyePos().add(target.getPos()).multiply(0.5);
            var direction = centerOfMass.subtract(getPos()).normalize();
            setVelocity(direction.multiply(0.04).add(getVelocity().multiply(0.8)));
            setPosition(getPos().add(getVelocity()));
        }

        super.tick();
    }

    @Nullable
    private UUID targetUUID = null;

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if (nbt.contains("target")) targetUUID = nbt.getUuid("target");
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putUuid("target", targetUUID);
    }
}
