package wdfeer.esoterica_origins;

import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Initializer implements ModInitializer {
    public static final String MOD_ID = "esoterica-origins";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "flawless"), Flawless.INSTANCE);
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "graze"), Graze.INSTANCE);
        LOGGER.info("Esoterica Origins loaded!");
    }
}
