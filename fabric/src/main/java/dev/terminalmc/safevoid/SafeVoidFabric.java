package dev.terminalmc.safevoid;

import dev.terminalmc.safevoid.command.Commands;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.commands.CommandSourceStack;

public class SafeVoidFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // Commands
        CommandRegistrationCallback.EVENT.register(((dispatcher, buildContext, commandSelection) ->
                new Commands<CommandSourceStack>().register(dispatcher, buildContext, commandSelection)));

        // Tick events
        ServerTickEvents.END_SERVER_TICK.register(SafeVoid::onEndTick);

        // Main initialization
        SafeVoid.init();
    }
}
