package world.anhgelus.architectsland.difficultydeathscaler;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.network.ServerPlayerEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DifficultyDeathScaler implements ModInitializer {
    public static final String MOD_ID = "difficulty-death-scaler";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private static int numberOfDeath = 0;

    @Override
    public void onInitialize() {
        ServerLivingEntityEvents.ALLOW_DEATH.register((entity, damageSource, damageAmount) -> {
            if (!(entity instanceof ServerPlayerEntity)) {
                return true;
            }
            // final ServerPlayerEntity player = (ServerPlayerEntity) entity;
            numberOfDeath++;
            return true;
        });
    }
}
