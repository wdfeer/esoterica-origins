package wdfeer.esoterica_origins

import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory

object EsotericaOrigins : ModInitializer {
    private val logger = LoggerFactory.getLogger("esoterica-origins")

	override fun onInitialize() {
		logger.info("Esoterica Origins loaded!")
	}
}
