package wdfeer.flawless;

import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class FlawlessMod implements ModInitializer {
    public static final String MOD_ID = "flawless";

    public static final Flawless FLAWLESS = new Flawless();

    @Override
    public void onInitialize() {
        Registry.register(Registries.STATUS_EFFECT, Identifier.of(MOD_ID, "flawless"), FLAWLESS);
    }
}