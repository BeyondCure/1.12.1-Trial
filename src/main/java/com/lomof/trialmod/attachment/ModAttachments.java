package com.lomof.trialmod.attachment;

import com.lomof.trialmod.TrialMod;
import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class ModAttachments {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TrialMod.MODID);

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> RADIATION_DOSE =
            ATTACHMENT_TYPES.register("radiation_dose", () -> AttachmentType.builder(() -> 0.0D)
                    .serialize(Codec.DOUBLE)
                    .build());

    public static final DeferredHolder<AttachmentType<?>, AttachmentType<Double>> RADIATION_RATE =
            ATTACHMENT_TYPES.register("radiation_rate", () -> AttachmentType.builder(() -> 0.0D)
                    .build());

    private ModAttachments() {
    }

    public static void register(IEventBus modBus) {
        ATTACHMENT_TYPES.register(modBus);
    }
}
