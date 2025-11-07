package wdfeer.esoterica_origins.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wdfeer.esoterica_origins.Flawless;

@Mixin(LivingEntity.class)
public abstract class FlawlessMixin {
    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Inject(method = "modifyAppliedDamage", at = @At("RETURN"), cancellable = true)
    private void modifyAppliedDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (hasStatusEffect(Flawless.INSTANCE) && cir.getReturnValue() > 1) {
            cir.setReturnValue(1f);
        }
    }
}