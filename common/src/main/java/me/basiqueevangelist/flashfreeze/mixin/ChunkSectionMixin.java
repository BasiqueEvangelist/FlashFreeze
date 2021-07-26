package me.basiqueevangelist.flashfreeze.mixin;

import me.basiqueevangelist.flashfreeze.UnknownBlockState;
import me.basiqueevangelist.flashfreeze.util.NbtType;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.chunk.ChunkSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.function.Function;

@Mixin(ChunkSection.class)
public class ChunkSectionMixin {
    @ModifyArg(method = "<init>(ISSS)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/PalettedContainer;<init>(Lnet/minecraft/world/chunk/Palette;Lnet/minecraft/util/collection/IdList;Ljava/util/function/Function;Ljava/util/function/Function;Ljava/lang/Object;)V"), index = 2)
    private Function<NbtCompound, Object> switchDeserializer(Function<NbtCompound, BlockState> old) {
        return tag -> {
            if (tag.contains("Name", NbtType.STRING)) {
                if (!Registry.BLOCK.containsId(new Identifier(tag.getString("Name")))) {
                    return new UnknownBlockState(tag);
                }
            }

            return old.apply(tag);
        };
    }

    @ModifyArg(method = "<init>(ISSS)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/PalettedContainer;<init>(Lnet/minecraft/world/chunk/Palette;Lnet/minecraft/util/collection/IdList;Ljava/util/function/Function;Ljava/util/function/Function;Ljava/lang/Object;)V"), index = 3)
    private Function<Object, NbtCompound> switchSerializer(Function<BlockState, NbtCompound> old) {
        return obj -> {
            if (obj instanceof UnknownBlockState)
                return ((UnknownBlockState) obj).toTag();

            return old.apply((BlockState) obj);
        };
    }
}
