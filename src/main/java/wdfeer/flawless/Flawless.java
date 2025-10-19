package wdfeer.flawless;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Formatting;

public class Flawless extends StatusEffect {
    protected Flawless() {
        super(StatusEffectCategory.BENEFICIAL, Formatting.YELLOW.getColorValue());
    }
}
