package me.basiqueevangelist.flashfreeze.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import me.basiqueevangelist.flashfreeze.FakeArmorStandEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.function.Consumer;

@Mixin(ArmorStandItem.class)
public class ArmorStandItemMixin {
    @WrapOperation(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/nbt/NbtCompound;Ljava/util/function/Consumer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/SpawnReason;ZZ)Lnet/minecraft/entity/Entity;"))
    private Entity makeEntity(EntityType<Entity> instance, ServerWorld world, NbtCompound itemNbt, Consumer<Entity> afterConsumer, BlockPos pos, SpawnReason reason, boolean alignPosition, boolean invertY, Operation<Entity> original, @Local(argsOnly = true) ItemUsageContext context) {
        if (context.getStack().hasNbt() && context.getStack().getNbt().contains("OriginalEntityData", NbtElement.COMPOUND_TYPE)) {
            Entity e = new FakeArmorStandEntity(world, context.getStack().getNbt().getCompound("OriginalEntityData").copy());
            e.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);

            if (afterConsumer != null)
                afterConsumer.accept(e);

            return e;
        }

        return original.call(instance, world, itemNbt, afterConsumer, pos, reason, alignPosition, invertY);
    }

}
