package dev.terminalmc.safevoid.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.terminalmc.safevoid.SafeVoid;
import dev.terminalmc.safevoid.config.Config;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.network.chat.MutableComponent;

import static net.minecraft.commands.Commands.literal;

@SuppressWarnings("unchecked")
public class Commands<S> extends CommandDispatcher<S> {
    public void register(CommandDispatcher<S> dispatcher, CommandBuildContext buildContext,
                         net.minecraft.commands.Commands.CommandSelection selection) {
        dispatcher.register((LiteralArgumentBuilder<S>)literal(SafeVoid.MOD_ID)
                .then(literal("enable")
                        .executes(ctx -> {
                            MutableComponent msg = SafeVoid.PREFIX.copy();
                            if (Config.get().options.enabled) {
                                msg.append("Already enabled");
                            } else {
                                Config.get().options.enabled = true;
                                Config.save();
                                msg.append("Enabled");
                            }
                            ctx.getSource().getPlayer().sendSystemMessage(msg);
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(literal("disable")
                        .executes(ctx -> {
                            MutableComponent msg = SafeVoid.PREFIX.copy();
                            if (!Config.get().options.enabled) {
                                msg.append("Already disabled");
                            } else {
                                Config.get().options.enabled = false;
                                Config.save();
                                msg.append("Disabled");
                            }
                            ctx.getSource().getPlayer().sendSystemMessage(msg);
                            return Command.SINGLE_SUCCESS;
                        })
                )
                .then(literal("reload")
                        .executes(ctx -> {
                            MutableComponent msg = SafeVoid.PREFIX.copy();
                            Config.loadAndSave();
                            msg.append("Config reloaded");
                            ctx.getSource().getPlayer().sendSystemMessage(msg);
                            return Command.SINGLE_SUCCESS;
                        })
                )
        );
    }
}
