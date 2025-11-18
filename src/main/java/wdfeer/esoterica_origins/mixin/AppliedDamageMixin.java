package wdfeer.esoterica_origins.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.tag.DamageTypeTags;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import wdfeer.esoterica_origins.Flawless;
import wdfeer.esoterica_origins.Graze;

@Mixin(LivingEntity.class)
public abstract class AppliedDamageMixin {
    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    @Shadow
    public abstract boolean removeStatusEffect(StatusEffect type);

    @Shadow
    public abstract boolean addStatusEffect(StatusEffectInstance effect);

    @Shadow
    @Nullable
    public abstract StatusEffectInstance getStatusEffect(StatusEffect effect);

    @Inject(method = "modifyAppliedDamage", at = @At("RETURN"), cancellable = true)
    private void modifyAppliedDamage(DamageSource source, float amount, CallbackInfoReturnable<Float> cir) {
        if (hasStatusEffect(Graze.INSTANCE) && cir.getReturnValue() > 0 && !source.isIn(DamageTypeTags.BYPASSES_ARMOR)) {
            var instance = getStatusEffect(Graze.INSTANCE);
            assert instance != null;
            if (instance.getAmplifier() > 1) {
                removeStatusEffect(Graze.INSTANCE);
                addStatusEffect(new StatusEffectInstance(Graze.INSTANCE,
                        instance.getDuration(),
                        instance.getAmplifier() - 1));
            } else {
                removeStatusEffect(Graze.INSTANCE);
            }

            cir.setReturnValue(0f);
        } else if (hasStatusEffect(Flawless.INSTANCE) && cir.getReturnValue() > 1) {
            cir.setReturnValue(1f);
        }
    }
}