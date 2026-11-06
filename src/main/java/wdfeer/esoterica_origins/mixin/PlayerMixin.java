package wdfeer.esoterica_origins.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;
import wdfeer.esoterica_origins.Rabbitluck;

@Mixin(PlayerEntity.class)
public abstract class PlayerMixin {
    @Shadow
    public abstract void addCritParticles(Entity target);

    @Unique
    @Nullable
    Entity currentTarget;
    @Unique
    boolean critted;

    @Inject(method = "attack", at = @At("HEAD"))
    void preAttack(Entity target, CallbackInfo ci) {
        critted = false;
        currentTarget = target;
    }

    @Inject(method = "addCritParticles", at = @At("HEAD"))
    void preCritParticles(Entity target, CallbackInfo ci) {
        critted = true;
    }

    @ModifyArgs(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"))
    void postAttack(Args args) {
        if (currentTarget != null && !critted && ((LivingEntity) (Object) this).hasStatusEffect(Rabbitluck.INSTANCE)) {
            args.set(1, args.<Float>get(1) * 1.5f);
            addCritParticles(currentTarget);
        }

        currentTarget = null;
        critted = false;
    }
}
