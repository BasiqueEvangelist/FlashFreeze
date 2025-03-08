package me.basiqueevangelist.flashfreeze.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.RecipeBook;
import net.minecraft.stats.ServerRecipeBook;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeManager;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Set;
import java.util.function.Consumer;

@Mixin(ServerRecipeBook.class)
public class ServerRecipeBookMixin extends RecipeBook {
    @Unique private @Nullable Set<ResourceLocation> flashfreeze$targetSet = null;

    @Redirect(method = "loadRecipes", at = @At(value = "INVOKE", target = "Lorg/slf4j/Logger;error(Ljava/lang/String;Ljava/lang/Object;)V", ordinal = 0))
    private void eatErrorAndStore(Logger instance, String s, Object o, @Local ResourceLocation recipeId) {
        if (flashfreeze$targetSet == null) return;

        flashfreeze$targetSet.add(recipeId);
    }

    @WrapOperation(method = "fromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/stats/ServerRecipeBook;loadRecipes(Lnet/minecraft/nbt/ListTag;Ljava/util/function/Consumer;Lnet/minecraft/world/item/crafting/RecipeManager;)V", ordinal = 0))
    private void targetSet1(ServerRecipeBook instance, ListTag list, Consumer<RecipeHolder<?>> recipeConsumer, RecipeManager recipeMgr, Operation<Void> original) {
        flashfreeze$targetSet = this.known;

        try {
            original.call(instance, list, recipeConsumer, recipeMgr);
        } finally {
            flashfreeze$targetSet = null;
        }
    }

    @WrapOperation(method = "fromNbt", at = @At(value = "INVOKE", target = "Lnet/minecraft/stats/ServerRecipeBook;loadRecipes(Lnet/minecraft/nbt/ListTag;Ljava/util/function/Consumer;Lnet/minecraft/world/item/crafting/RecipeManager;)V", ordinal = 1))
    private void targetSet2(ServerRecipeBook instance, ListTag list, Consumer<RecipeHolder<?>> recipeConsumer, RecipeManager recipeMgr, Operation<Void> original) {
        flashfreeze$targetSet = this.highlight;

        try {
            original.call(instance, list, recipeConsumer, recipeMgr);
        } finally {
            flashfreeze$targetSet = null;
        }
    }
}
