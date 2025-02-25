package me.basiqueevangelist.flashfreeze.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;

public class UnknownItemItem extends Item {
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
}
