package me.basiqueevangelist.flashfreeze.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerClientDecoded;
import eu.pb4.polymer.core.api.utils.PolymerKeepModel;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.tooltip.TooltipType;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;

public class UnknownItemItem extends Item implements PolymerItem, PolymerKeepModel, PolymerClientDecoded {
    public UnknownItemItem() {
        super(new Item.Settings().rarity(Rarity.UNCOMMON));
    }

    @Override
    public Text getName(ItemStack stack) {
        if (stack.contains(FlashFreezeDataComponents.ORIGINAL_ITEM_ID)) {
            return Text.of(stack.get(FlashFreezeDataComponents.ORIGINAL_ITEM_ID));
        }

        return Text.translatable("item.flashfreeze.unknown_item.broken");
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        if (player != null && FlashFreeze.hasAtLeast(player, 1)) {
            return this;
        }

        return Items.NETHER_STAR;
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipType tooltipType, RegistryWrapper.WrapperLookup lookup, @Nullable ServerPlayerEntity player) {
        if (player != null && FlashFreeze.hasAtLeast(player, 1)) {
            return itemStack;
        }

        return PolymerItem.super.getPolymerItemStack(itemStack, tooltipType, lookup, player);
    }
}
