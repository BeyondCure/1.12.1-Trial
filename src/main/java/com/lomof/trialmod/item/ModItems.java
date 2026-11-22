package com.lomof.trialmod.item;

import com.lomof.trialmod.TrialMod;
import com.lomof.trialmod.item.custom.ChiselItem;
import com.lomof.trialmod.item.custom.GeigerCounter;
import com.lomof.trialmod.item.custom.RadiationResistantArmorItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TrialMod.MODID);

    public static final DeferredItem<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot",() -> new Item(new Item.Properties().stacksTo(16)) {
        @Override
        public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
            tooltipComponents.add(Component.translatable("tooltip.lomofstrialmod.uranium.line1"));
            tooltipComponents.add(Component.translatable("tooltip.lomofstrialmod.uranium.line2")
                    .withStyle(ChatFormatting.YELLOW)
                    .withStyle(ChatFormatting.BOLD));
            super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
        }
    });
    public static final DeferredItem<Item> RAW_URANIUM = ITEMS.register("raw_uranium",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> R_ICON = ITEMS.register("r_icon",() -> new Item(new Item.Properties()));
    public static final DeferredItem<Item> LEAD_INGOT = ITEMS.register("lead_ingot",() -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> LEAD_HELMET = ITEMS.register("lead_helmet",
            () -> new RadiationResistantArmorItem(ModArmorMaterials.LEAD_ARMOR_MATERIAL, ArmorItem.Type.HELMET,
                    0.10D,
                    new Item.Properties().stacksTo(1)
                            .durability(ArmorItem.Type.HELMET.getDurability(12))));

    public static final DeferredItem<Item> LEAD_CHESTPLATE = ITEMS.register("lead_chestplate",
            () -> new RadiationResistantArmorItem(ModArmorMaterials.LEAD_ARMOR_MATERIAL, ArmorItem.Type.CHESTPLATE,
                    0.20D,
                    new Item.Properties().stacksTo(1)
                            .durability(ArmorItem.Type.CHESTPLATE.getDurability(12))));

    public static final DeferredItem<Item> LEAD_LEGGINGS = ITEMS.register("lead_leggings",
            () -> new RadiationResistantArmorItem(ModArmorMaterials.LEAD_ARMOR_MATERIAL, ArmorItem.Type.LEGGINGS,
                    0.15D,
                    new Item.Properties().stacksTo(1)
                            .durability(ArmorItem.Type.LEGGINGS.getDurability(12))));

    public static final DeferredItem<Item> LEAD_BOOTS = ITEMS.register("lead_boots",
            () -> new RadiationResistantArmorItem(ModArmorMaterials.LEAD_ARMOR_MATERIAL, ArmorItem.Type.BOOTS,
                    0.10D,
                    new Item.Properties().stacksTo(1)
                            .durability(ArmorItem.Type.BOOTS.getDurability(12))));

    public static final DeferredItem<Item> CHISEL = ITEMS.register("chisel",
            () -> new ChiselItem(
                    new Item.Properties()
                            .durability(32)
            )
    );

    public static final DeferredItem<Item> RADISH = ITEMS.register("radish",
            () -> new Item(new Item.Properties().food(ModFoodProperties.RADISH)));

    public static final DeferredItem<Item> BAMBOO_CHARCOAL = ITEMS.register("bamboo_charcoal", () -> new Item(new Item.Properties()));

    public static final DeferredItem<Item> GEIGER_COUNTER = ITEMS.register("geiger_counter",
            () -> new GeigerCounter(new Item.Properties().stacksTo(1)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }


}
