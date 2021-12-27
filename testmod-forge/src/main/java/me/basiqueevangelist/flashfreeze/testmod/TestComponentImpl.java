package me.basiqueevangelist.flashfreeze.testmod;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;

public class TestComponentImpl implements ICapabilitySerializable<NbtCompound>, TestComponent {
    private int value = 0;

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> LazyOptional<T> getCapability(Capability<T> capability, Direction arg) {
        if (capability == FlashFreezeTestMod.TEST_COMPONENT) {
            return (LazyOptional<T>) LazyOptional.of(() -> this);
        }

        return LazyOptional.empty();
    }

    @Override
    public NbtCompound serializeNBT() {
        NbtCompound tag = new NbtCompound();
        tag.putInt("Value", value);
        return tag;
    }

    @Override
    public void deserializeNBT(NbtCompound arg) {
        value = arg.getInt("Value");
    }
}
