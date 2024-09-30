package dev.terminalmc.safevoid;

import dev.terminalmc.safevoid.command.Commands;
import net.minecraft.commands.CommandSourceStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.server.ServerStoppedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

@Mod(value = SafeVoid.MOD_ID)
public class SafeVoidNeoForge {
    public SafeVoidNeoForge() {
        // Main initialization
        SafeVoid.init();
    }

    @EventBusSubscriber(modid = SafeVoid.MOD_ID)
    static class ServerEventHandler {
        // Commands
        @SubscribeEvent
        static void onRegisterCommands(RegisterCommandsEvent event) {
            new Commands<CommandSourceStack>().register(
                    event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
        }

        // Tick events
        @SubscribeEvent
        public static void onServerTick(ServerTickEvent.Post event) {
            SafeVoid.onEndTick(event.getServer());
        }
    }
}
