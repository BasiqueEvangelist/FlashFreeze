package me.basiqueevangelist.flashfreeze.item;

import eu.pb4.polymer.item.VirtualItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;

public class FakeItem extends Item implements VirtualItem {
    public FakeItem(Settings settings) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return new LiteralText("Unknown item " + stack.getOrCreateSubTag("OriginalData").getString("id"));
    }

    @Override
    public Item getVirtualItem() {
        return Items.NETHER_STAR;
    }
}
