package wdfeer.esoterica_origins.mixin.rabbitluck;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import wdfeer.esoterica_origins.Rabbitluck;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin {
    @Shadow
    public abstract void addCritParticles(Entity target);

    @ModifyArgs(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    void editAttackDamage(Args args) {
        if (Rabbitluck.currentTarget != null && !Rabbitluck.critting && ((LivingEntity) (Object) this).hasStatusEffect(Rabbitluck.INSTANCE)) {
            args.set(1, args.<Float>get(1) * 1.5f);
            Rabbitluck.currentTarget = null;
            addCritParticles(Rabbitluck.currentTarget);
        }

        Rabbitluck.currentTarget = null;
        Rabbitluck.critting = false;
    }
}
