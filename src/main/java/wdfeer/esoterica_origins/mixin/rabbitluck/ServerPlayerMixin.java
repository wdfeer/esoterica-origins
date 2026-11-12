package wdfeer.esoterica_origins.mixin.rabbitluck;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.RabbitEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import wdfeer.esoterica_origins.Rabbitluck;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerMixin {
    @Inject(method = "attack", at = @At("HEAD"))
    void preAttack(Entity target, CallbackInfo ci) {
        Rabbitluck.critting = false;
        Rabbitluck.currentTarget = target;
    }

    @Inject(method = "addCritParticles", at = @At("HEAD"))
    void preCritParticles(Entity target, CallbackInfo ci) {
        if (target == Rabbitluck.currentTarget)
            Rabbitluck.critting = true;
    }
}
