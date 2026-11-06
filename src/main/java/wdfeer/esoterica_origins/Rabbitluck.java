package wdfeer.esoterica_origins;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Formatting;

public class Rabbitluck extends StatusEffect {
    public static final Rabbitluck INSTANCE = new Rabbitluck();
    protected Rabbitluck() {
        // TODO: make unique color
        super(StatusEffectCategory.BENEFICIAL, Formatting.YELLOW.getColorValue());
    }
}
