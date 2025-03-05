package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.UnknownEntityEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(EntityType.class)
public class EntityTypeMixin {
    @Inject(method = "create(Lnet/minecraft/nbt/CompoundTag;Lnet/minecraft/world/level/Level;)Ljava/util/Optional;", at = @At("HEAD"), cancellable = true)
    private static void makeFakeEntityIfNeeded(CompoundTag nbt, Level world, CallbackInfoReturnable<Optional<Entity>> cir) {
        if (nbt.contains("id", Tag.TAG_STRING) && !BuiltInRegistries.ENTITY_TYPE.containsKey(ResourceLocation.parse(nbt.getString("id")))) {
            cir.setReturnValue(Optional.of(new UnknownEntityEntity(world, nbt)));
        }
    }
}
