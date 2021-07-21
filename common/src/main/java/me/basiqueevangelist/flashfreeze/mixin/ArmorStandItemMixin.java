package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.FakeArmorStandEntity;
import me.shedaniel.architectury.utils.NbtType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ArmorStandItem.class)
public class ArmorStandItemMixin {
    @Redirect(method = "useOnBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/EntityType;create(Lnet/minecraft/server/world/ServerWorld;Lnet/minecraft/nbt/NbtCompound;Lnet/minecraft/text/Text;Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/entity/SpawnReason;ZZ)Lnet/minecraft/entity/Entity;"))
    private Entity makeEntity(EntityType<?> entityType, ServerWorld world, NbtCompound itemNbt, Text name, PlayerEntity player, BlockPos pos, SpawnReason spawnReason, boolean alignPosition, boolean invertY, ItemUsageContext context) {
        if (context.getStack().hasTag() && context.getStack().getTag().contains("OriginalEntityData", NbtType.COMPOUND)) {
            Entity e = new FakeArmorStandEntity(world, context.getStack().getTag().getCompound("OriginalEntityData").copy());
            e.setPos(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
            return e;
        }

        return entityType.create(world, itemNbt, name, player, pos, spawnReason, alignPosition, invertY);
    }

}
