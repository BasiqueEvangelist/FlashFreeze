package me.basiqueevangelist.flashfreeze.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(EntityType.class)
public interface EntityTypeAccessor {
    @Invoker
    static double callGetOriginY(WorldView world, BlockPos pos, boolean invertY, Box boundingBox) {
        throw new RuntimeException("Mixin wasn't applied!");
    }
}
