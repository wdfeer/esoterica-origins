package wdfeer.esoterica_origins.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wdfeer.esoterica_origins.Flawless;
import wdfeer.esoterica_origins.Graze;

import java.util.Map;

@Mixin(LivingEntity.class)
public abstract class AppliedDamageMixin {
    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    @Final
    private Map<StatusEffect, StatusEffectInstance> activeStatusEffects;

    @Inject(method = "modifyAppliedDamage", at = @At("RETURN"), cancellable = true)
    private void modifyAppliedDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (hasStatusEffect(Graze.INSTANCE) && cir.getReturnValue() > 0 && !source.isIn(DamageTypeTags.BYPASSES_EFFECTS)) {
            var instance = activeStatusEffects.get(Graze.INSTANCE);
            if (instance.getAmplifier() > 1) {
                activeStatusEffects.replace(Graze.INSTANCE, new StatusEffectInstance(Graze.INSTANCE,
                        instance.getDuration(),
                        instance.getAmplifier() - 1));
            } else {
                activeStatusEffects.remove(Graze.INSTANCE);
            }

            cir.setReturnValue(0f);
        } else if (hasStatusEffect(Flawless.INSTANCE) && cir.getReturnValue() > 1) {
            cir.setReturnValue(1f);
        }
    }
}