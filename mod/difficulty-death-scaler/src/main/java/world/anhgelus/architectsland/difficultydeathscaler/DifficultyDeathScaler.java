package world.anhgelus.architectsland.difficultydeathscaler;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameRules;
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
            increaseDeath((ServerPlayerEntity) entity);
            return true;
        });
    }

    private void increaseDeath(ServerPlayerEntity player) {
        numberOfDeath++;
        updateDeath(player.getServerWorld().getServer());
    }

    private void decreaseDeath(ServerPlayerEntity player) {
        if (numberOfDeath > 7) {
            numberOfDeath = 5;
        } else if (numberOfDeath > 5) {
            numberOfDeath = 3;
        } else if (numberOfDeath > 3) {
            numberOfDeath = 1;
        } else if (numberOfDeath > 1) {
            numberOfDeath = 0;
        }
        updateDeath(player.getServerWorld().getServer());
    }

    private void updateDeath(MinecraftServer server) {
        new GameRules().setAllValues(server.getGameRules(), server);
        final var rules = server.getGameRules().copy();

        server.getGameRules().setAllValues(rules, server);
        switch (numberOfDeath) {
            case 0:
                server.setDifficulty(Difficulty.NORMAL, true);
                server.getGameRules().get(GameRules.PLAYERS_SLEEPING_PERCENTAGE).set(30, server);
            case 1:
                server.getGameRules().get(GameRules.PLAYERS_SLEEPING_PERCENTAGE).set(70, server);
            case 3:
                server.setDifficulty(Difficulty.HARD, true);
            case 5:
                server.getGameRules().get(GameRules.PLAYERS_SLEEPING_PERCENTAGE).set(100, server);
                server.getGameRules().get(GameRules.NATURAL_REGENERATION).set(true, server);
            case 7:
                server.getGameRules().get(GameRules.NATURAL_REGENERATION).set(false, server);
        }
    }
}
