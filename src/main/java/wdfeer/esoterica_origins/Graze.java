package wdfeer.esoterica_origins;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Formatting;

public class Graze extends StatusEffect {
    public static final Graze INSTANCE = new Graze();
    protected Graze() {
        super(StatusEffectCategory.BENEFICIAL, Formatting.RED.getColorValue());
    }
}
