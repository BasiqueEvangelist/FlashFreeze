package me.basiqueevangelist.flashfreeze.mixin;

import net.minecraft.item.ArmorStandItem;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ArmorStandItem.class)
public class ArmorStandItemMixin {
    // TODO: remake this.

//    @WrapOperation(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/nbt/NbtCompound;Ljava/util/function/Consumer;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/SpawnReason;ZZ)Lnet/minecraft/entity/Entity;"))
//    private Entity makeEntity(EntityType<Entity> instance, ServerWorld world, NbtCompound itemNbt, Consumer<Entity> afterConsumer, BlockPos pos, SpawnReason reason, boolean alignPosition, boolean invertY, Operation<Entity> original, @Local(argsOnly = true) ItemUsageContext context) {
//        if (context.getStack().hasNbt() && context.getStack().getNbt().contains("OriginalEntityData", NbtElement.COMPOUND_TYPE)) {
//            Entity e = new FakeArmorStandEntity(world, context.getStack().getNbt().getCompound("OriginalEntityData").copy());
//            e.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
//
//            if (afterConsumer != null)
//                afterConsumer.accept(e);
//
//            return e;
//        }
//
//        return original.call(instance, world, itemNbt, afterConsumer, pos, reason, alignPosition, invertY);
//    }

}
