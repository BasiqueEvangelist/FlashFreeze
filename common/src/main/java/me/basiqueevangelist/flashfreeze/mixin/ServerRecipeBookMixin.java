package me.basiqueevangelist.flashfreeze.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.ServerRecipeBook;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerRecipeBook.class)
public class ServerRecipeBookMixin extends RecipeBook {
    @ModifyExpressionValue(method = "loadRecipes", at = @At(value = "INVOKE", target = "Ljava/util/function/Predicate;test(Ljava/lang/Object;)Z"))
    private boolean alwaysKnown(boolean original) {
        return true;
    }
}
