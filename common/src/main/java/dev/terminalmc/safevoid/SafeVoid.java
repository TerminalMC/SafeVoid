package dev.terminalmc.safevoid;

import dev.terminalmc.safevoid.config.Config;
import dev.terminalmc.safevoid.util.ModLogger;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;

import static dev.terminalmc.safevoid.util.Localization.translationKey;

public class SafeVoid {
    public static final String MOD_ID = "safevoid";
    public static final String MOD_NAME = "SafeVoid";
    public static final ModLogger LOG = new ModLogger(MOD_NAME);
    public static final Component PREFIX = Component.empty()
            .append(Component.literal("[").withStyle(ChatFormatting.DARK_GRAY))
            .append(Component.literal(MOD_NAME).withStyle(ChatFormatting.GOLD))
            .append(Component.literal("] ").withStyle(ChatFormatting.DARK_GRAY))
            .withStyle(ChatFormatting.GRAY);

    public static void init() {
        Config.getAndSave();
    }

    public static void onEndTick(MinecraftServer mc) {

    }

    public static void onConfigSaved(Config config) {
        // If you are maintaining caches based on config values, update them here.
    }
}
