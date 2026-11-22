package com.lomof.trialmod.command;

import com.lomof.trialmod.TrialMod;
import com.lomof.trialmod.attachment.ModAttachments;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Locale;

@EventBusSubscriber(modid = TrialMod.MODID)
public final class ModCommands {
    private ModCommands() {
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {

        // JUST FOR DEBUG
        event.getDispatcher().register(
                Commands.literal("check_rad")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            double dose = player.getData(ModAttachments.RADIATION_DOSE);
                            context.getSource().sendSuccess(
                                    () -> Component.literal(
                                            "当前辐照值: " + String.format(Locale.ROOT, "%.2f", dose)),
                                    false
                            );
                            return 1;
                        })
        );

        // JUST FOR DEBUG
        event.getDispatcher().register(
                Commands.literal("clear_rad")
                        .executes(context -> {
                            ServerPlayer player = context.getSource().getPlayerOrException();
                            player.setData(ModAttachments.RADIATION_DOSE, 0.0D);
                            context.getSource().sendSuccess(
                                    () -> Component.literal("已清空累计辐照值"),
                                    false
                            );
                            return 1;
                        })
        );
    }
}
