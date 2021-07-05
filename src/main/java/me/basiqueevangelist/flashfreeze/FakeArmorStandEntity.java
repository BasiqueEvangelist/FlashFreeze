package me.basiqueevangelist.flashfreeze;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.text.LiteralText;
import net.minecraft.world.World;

@SuppressWarnings("EntityConstructor")
public class FakeArmorStandEntity extends ArmorStandEntity {
    private NbtCompound originalData;

    public FakeArmorStandEntity(World world, NbtCompound originalData) {
        super(EntityType.ARMOR_STAND, world);
        this.originalData = originalData;

        setCustomName(new LiteralText("Unknown entity " + originalData.getString("id")));
        setCustomNameVisible(true);

        NbtList pos = originalData.getList("Pos", 6);
        this.setPos(pos.getDouble(0), pos.getDouble(1), pos.getDouble(2));
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        nbt.put("OriginalData", originalData);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        originalData = nbt.getCompound("OriginalData");
    }

    @Override
    public boolean hasNoGravity() {
        return true;
    }

    @Override
    public boolean saveNbt(NbtCompound nbt) {
        nbt.copyFrom(originalData);
        return true;
    }

    public NbtCompound getOriginalData() {
        return originalData;
    }
}
