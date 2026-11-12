package wdfeer.esoterica_origins;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.server.command.CommandOutput;

import java.awt.*;

public class Rabbitluck extends StatusEffect {
    public static final Rabbitluck INSTANCE = new Rabbitluck();

    protected Rabbitluck() {
        super(StatusEffectCategory.BENEFICIAL, Color.getHSBColor(20f / 360, 0.13f, 0.95f).getRGB());
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        var server = entity.getServer();
        if (server != null) {
            var command = "power grant @s esoterica-origins:tewi/rabbitluck_tiny_buffs esoterica-origins:rabbitluck";
            server.getCommandManager().executeWithPrefix(entity.getCommandSource().withOutput(CommandOutput.DUMMY), command);
        }
    }


    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }
}
