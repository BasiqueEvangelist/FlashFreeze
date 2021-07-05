package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.FlashFreeze;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin {
    @Shadow private NbtCompound tag;

    @Shadow @Nullable public abstract NbtCompound getSubTag(String key);

    @Inject(method = "fromNbt", at = @At("HEAD"), cancellable = true)
    private static void makeUnknownIfNeeded(NbtCompound tag, CallbackInfoReturnable<ItemStack> cir) {
        if (tag.contains("id", NbtElement.STRING_TYPE)
        && !Registry.ITEM.containsId(new Identifier(tag.getString("id")))) {
            cir.setReturnValue(FlashFreeze.makeFakeStack(tag));
        }
    }

    @Inject(method = "writeNbt", at = @At("HEAD"), cancellable = true)
    private void writeUnknownNbt(NbtCompound tag, CallbackInfoReturnable<NbtCompound> cir) {
        if (this.tag != null && this.tag.contains("OriginalData", NbtElement.COMPOUND_TYPE)) {
            tag.copyFrom(getSubTag("OriginalData"));
            cir.setReturnValue(tag);
        }
    }
}
