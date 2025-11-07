package wdfeer.esoterica_origins;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Formatting;

public class Flawless extends StatusEffect {
    public static final Flawless INSTANCE = new Flawless();
    protected Flawless() {
        super(StatusEffectCategory.BENEFICIAL, Formatting.YELLOW.getColorValue());
    }
}
