package me.basiqueevangelist.flashfreeze.mixin.fabric;

import me.basiqueevangelist.flashfreeze.fabric.capabilities.CapabilityHolder;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    @Unique private final CapabilityHolder capabilityHolder = new CapabilityHolder();

    @Inject(method = "fromNbt", at = @At("RETURN"))
    private static void readCapabilities(NbtCompound nbt, CallbackInfoReturnable<ItemStack> cir) {
        ((ItemStackMixin)(Object) cir.getReturnValue()).capabilityHolder.fromTag(nbt);
    }

    @Inject(method = "writeNbt", at = @At("RETURN"))
    private void writeCapabilities(NbtCompound nbt, CallbackInfoReturnable<NbtCompound> cir) {
        capabilityHolder.toTag(nbt);
    }

}
