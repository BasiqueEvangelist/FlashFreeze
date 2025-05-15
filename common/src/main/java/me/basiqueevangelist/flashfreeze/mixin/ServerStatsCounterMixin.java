package me.basiqueevangelist.flashfreeze.mixin;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.llamalad7.mixinextras.injector.ModifyReceiver;
import com.mojang.serialization.Dynamic;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.stats.ServerStatsCounter;
import net.minecraft.stats.StatType;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.HashSet;
import java.util.Set;

@Mixin(ServerStatsCounter.class)
public class ServerStatsCounterMixin {
    @Unique private @Nullable Table<String, String, Integer> flashfreeze$unknownStats = null;

    @ModifyArg(method = "parseLocal", at = @At(value = "INVOKE", target = "Lcom/mojang/serialization/Codec;parse(Lcom/mojang/serialization/Dynamic;)Lcom/mojang/serialization/DataResult;"))
    private Dynamic<JsonElement> stripUnknown(Dynamic<JsonElement> value) {
        JsonObject obj = (JsonObject) value.getValue();

        Set<String> statTypesToStrip = new HashSet<>();

        for (var statTypeId : obj.keySet()) {
            StatType<?> statType = BuiltInRegistries.STAT_TYPE.getValue(ResourceLocation.parse(statTypeId));

            if (statType == null) statTypesToStrip.add(statTypeId);

            Set<String> statsToStrip = new HashSet<>();
            JsonObject stats = obj.getAsJsonObject(statTypeId);

            for (var statId : stats.keySet()) {
                boolean perserve = statType == null || !statType.getRegistry().containsKey(ResourceLocation.parse(statId));

                if (perserve) {
                    statsToStrip.add(statId);

                    if (flashfreeze$unknownStats == null) flashfreeze$unknownStats = HashBasedTable.create();

                    flashfreeze$unknownStats.put(statTypeId, statId, stats.get(statId).getAsInt());
                }
            }

            for (String statId : statsToStrip) stats.remove(statId);
        }

        for (String statTypeId : statTypesToStrip) obj.remove(statTypeId);

        return value;
    }

    @ModifyReceiver(method = "toJson", at = @At(value = "INVOKE", target = "Lcom/google/gson/JsonObject;toString()Ljava/lang/String;"))
    private JsonObject addTheStats(JsonObject instance) {
        if (flashfreeze$unknownStats == null) return instance;

        JsonObject statsObj = instance.getAsJsonObject("stats");

        for (var cell : flashfreeze$unknownStats.cellSet()) {
            JsonObject row = statsObj.getAsJsonObject(cell.getRowKey().toString());

            if (row == null) {
                row = new JsonObject();
                statsObj.add(cell.getRowKey().toString(), row);
            }

            row.add(cell.getColumnKey().toString(), new JsonPrimitive(cell.getValue()));
        }

        return instance;
    }

}
