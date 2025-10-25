package wdfeer.esoterica_origins;

import net.fabricmc.api.ModInitializer;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Initializer implements ModInitializer {
    public static final String MOD_ID = "esoterica-origins";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Flawless FLAWLESS = new Flawless();
    public static RegistryEntry<StatusEffect> flawlessEntry;

    @Override
    public void onInitialize() {
        flawlessEntry = Registry.registerReference(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "flawless"), FLAWLESS);
        LOGGER.info("Esoterica Origins loaded!");
    }
}
