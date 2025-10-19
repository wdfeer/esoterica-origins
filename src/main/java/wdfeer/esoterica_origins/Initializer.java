package wdfeer.esoterica_origins;

import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.logging.Logger;

public class Initializer implements ModInitializer {
    public static final String MOD_ID = "esoterica-origins";

    public static final Logger LOGGER = Logger.getLogger(MOD_ID);

    public static final Flawless FLAWLESS = new Flawless();

    @Override
    public void onInitialize() {
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "flawless"), FLAWLESS);
        LOGGER.info("Esoterica Origins loaded!");
    }
}