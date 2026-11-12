package wdfeer.esoterica_origins;

import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.Nullable;

import java.awt.*;

public class Rabbitluck extends StatusEffect {
    public static final Rabbitluck INSTANCE = new Rabbitluck();
    protected Rabbitluck() {
        super(StatusEffectCategory.BENEFICIAL, Color.getHSBColor(20f / 360, 0.13f, 0.95f).getRGB());
    }

    // Vars below used in mixins for implementation

    @Nullable
    public static Entity currentTarget = null;
    public static boolean critting = false;
}
