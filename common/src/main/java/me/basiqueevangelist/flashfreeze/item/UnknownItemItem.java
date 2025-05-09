package me.basiqueevangelist.flashfreeze.item;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;

public class UnknownItemItem extends Item {
    public UnknownItemItem(ResourceKey<Item> id) {
        super(new Item.Properties().rarity(Rarity.UNCOMMON).setId(id));
    }

    @Override
    public Component getName(ItemStack stack) {
        if (stack.has(FlashFreezeDataComponents.ORIGINAL_ITEM_ID)) {
            return Component.translationArg(stack.get(FlashFreezeDataComponents.ORIGINAL_ITEM_ID));
        }

        return Component.translatable("item.flashfreeze.unknown_item.broken");
    }
}
