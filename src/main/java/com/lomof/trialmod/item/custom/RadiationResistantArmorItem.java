package com.lomof.trialmod.item.custom;

import com.lomof.trialmod.TrialMod;
import com.lomof.trialmod.attribute.ModAttributes;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.component.ItemAttributeModifiers;

public class RadiationResistantArmorItem extends ArmorItem {
    private final double radiationResistance;

    public RadiationResistantArmorItem(Holder<ArmorMaterial> material, Type type,
                                       double radiationResistance, Item.Properties properties) {
        super(material, type, properties);
        this.radiationResistance = radiationResistance;
    }

    @Override
    public ItemAttributeModifiers getDefaultAttributeModifiers() {
        String materialName = getMaterial().unwrapKey()
                .map(key -> key.location().getPath())
                .orElse("unknown");

        return super.getDefaultAttributeModifiers().withModifierAdded(
                ModAttributes.RADIATION_RESISTANCE,
                new AttributeModifier(
                        ResourceLocation.fromNamespaceAndPath(TrialMod.MODID,
                                materialName + "_" + getType().getName() + "_radiation_resistance"),
                        radiationResistance,
                        AttributeModifier.Operation.ADD_VALUE
                ),
                EquipmentSlotGroup.bySlot(getType().getSlot())
        );
    }
}
