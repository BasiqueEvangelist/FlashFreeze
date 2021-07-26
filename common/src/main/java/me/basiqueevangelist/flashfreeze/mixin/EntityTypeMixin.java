package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.FakeArmorStandEntity;
import me.basiqueevangelist.flashfreeze.util.NbtType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(EntityType.class)
public class EntityTypeMixin {
    @Inject(method = "getEntityFromNbt", at = @At("HEAD"), cancellable = true)
    private static void makeFakeEntityIfNeeded(NbtCompound nbt, World world, CallbackInfoReturnable<Optional<Entity>> cir) {
        if (nbt.contains("id", NbtType.STRING) && !Registry.ENTITY_TYPE.containsId(new Identifier(nbt.getString("id")))) {
            cir.setReturnValue(Optional.of(new FakeArmorStandEntity(world, nbt)));
        }
    }
}
