package me.basiqueevangelist.flashfreeze.mixin;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.StatType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(ServerStatsCounter.class)
public class ServerStatsCounterMixin {
    @Unique private @Nullable Table<ResourceLocation, ResourceLocation, Integer> flashfreeze$unknownStats = null;

    // TODO!!!

//    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
//    @WrapWithCondition(method = "parseLocal", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;ifElse(Ljava/util/Optional;Ljava/util/function/Consumer;Ljava/lang/Runnable;)Ljava/util/Optional;"))
//    private boolean eatErrorAndStore(Optional<StatType<?>> opt, Consumer<StatType<?>> consumer, Runnable orElse,
//                                     @Local(ordinal = 1) CompoundTag statsTag, @Local(ordinal = 1) String key) {
//        if (opt.isPresent()) return true;
//
//        if (flashfreeze$unknownStats == null) flashfreeze$unknownStats = HashBasedTable.create();
//
//        ResourceLocation keyId = ResourceLocation.parse(key);
//        CompoundTag subTag = statsTag.getCompound(key);
//
//        for (String subKey : subTag.getAllKeys()) {
//            flashfreeze$unknownStats.put(keyId, ResourceLocation.parse(subKey), subTag.getInt(subKey));
//        }
//
//        return false;
//    }
//
//    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
//    @WrapWithCondition(method = {"lambda$parseLocal$2", "method_17991"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;ifElse(Ljava/util/Optional;Ljava/util/function/Consumer;Ljava/lang/Runnable;)Ljava/util/Optional;"))
//    private boolean eatErrorAndStore(Optional<Object> opt, Consumer<Object> consumer, Runnable orElse, @Local(argsOnly = true) StatType<?> statType, @Local(ordinal = 1) String subKey, @Local(ordinal = 1) CompoundTag subTag) {
//        if (opt.isPresent()) return true;
//
//        ResourceLocation typeId = BuiltInRegistries.STAT_TYPE.getKey(statType);
//        ResourceLocation subId = ResourceLocation.parse(subKey);
//
//        if (flashfreeze$unknownStats == null) flashfreeze$unknownStats = HashBasedTable.create();
//
//        flashfreeze$unknownStats.put(typeId, subId, subTag.getInt(subKey));
//        return false;
//    }
//
//    @ModifyReceiver(method = "toJson", at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonObject;toString()Ljava/lang/String;"))
//    private JsonObject addTheStats(JsonObject instance) {
//        if (flashfreeze$unknownStats == null) return instance;
//
//        JsonObject statsObj = instance.getAsJsonObject("stats");
//
//        for (var cell : flashfreeze$unknownStats.cellSet()) {
//            JsonObject row = statsObj.getAsJsonObject(cell.getRowKey().toString());
//
//            if (row == null) {
//                row = new JsonObject();
//                statsObj.add(cell.getRowKey().toString(), row);
//            }
//
//            row.add(cell.getColumnKey().toString(), new JsonPrimitive(cell.getValue()));
//        }
//
//        return instance;
//    }

}
