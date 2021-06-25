package me.basiqueevangelist.palettebypass.mixin;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtElement;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Ingredient.class)
public class IngredientMixin {
    @Inject(method = "test", at = @At("HEAD"), cancellable = true)
    private void blockCraftingWithFakeItems(ItemStack itemStack, CallbackInfoReturnable<Boolean> cir) {
        if (itemStack.hasTag() && itemStack.getTag().contains("OriginalData", NbtElement.COMPOUND_TYPE)) {
            cir.setReturnValue(false);
        }
    }
}
