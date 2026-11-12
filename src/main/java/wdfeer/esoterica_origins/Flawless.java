package wdfeer.esoterica_origins;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

import java.awt.*;

public class Flawless extends StatusEffect {
    public static final Flawless INSTANCE = new Flawless();
    protected Flawless() {
        super(StatusEffectCategory.BENEFICIAL, Color.getHSBColor(0.6f, 0.61f, 0.98f).getRGB());
    }
}
