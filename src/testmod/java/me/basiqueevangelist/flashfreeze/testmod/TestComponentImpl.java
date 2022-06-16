package me.basiqueevangelist.flashfreeze.testmod;

import net.minecraft.nbt.NbtCompound;

public class TestComponentImpl implements TestComponent {
    private int value = 0;

    @Override
    public void readFromNbt(NbtCompound tag) {
        value = tag.getInt("Value");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putInt("Value", value);
    }

    @Override
    public int getValue() {
        return value;
    }

    @Override
    public void setValue(int value) {
        this.value = value;
    }
}
