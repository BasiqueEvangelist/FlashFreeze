package me.basiqueevangelist.flashfreeze.item;

import eu.pb4.polymer.core.api.item.PolymerItem;
import eu.pb4.polymer.core.api.utils.PolymerClientDecoded;
import eu.pb4.polymer.core.api.utils.PolymerKeepModel;
import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Rarity;
import org.jetbrains.annotations.Nullable;

public class UnknownItemItem extends Item implements PolymerItem, PolymerKeepModel, PolymerClientDecoded {
    public UnknownItemItem() {
        super(new FabricItemSettings().rarity(Rarity.UNCOMMON));
    }

    @Override
    public Text getName(ItemStack stack) {
        if (stack.hasNbt()
         && stack.getNbt().contains("OriginalData", NbtElement.COMPOUND_TYPE)
         && stack.getOrCreateSubNbt("OriginalData").contains("id", NbtElement.STRING_TYPE)) {
            return Text.of(stack.getOrCreateSubNbt("OriginalData").getString("id"));
        }

        return super.getName(stack);
    }

    @Override
    public Item getPolymerItem(ItemStack itemStack, @Nullable ServerPlayerEntity player) {
        if (player != null && ServerPlayNetworking.canSend(player, FlashFreeze.MALDENHAGEN)) {
            return this;
        }

        return Items.NETHER_STAR;
    }

    @Override
    public ItemStack getPolymerItemStack(ItemStack itemStack, TooltipContext context, @Nullable ServerPlayerEntity player) {
        if (player != null && ServerPlayNetworking.canSend(player, FlashFreeze.MALDENHAGEN)) {
            return itemStack;
        }

        return PolymerItem.super.getPolymerItemStack(itemStack, context, player);
    }
}
