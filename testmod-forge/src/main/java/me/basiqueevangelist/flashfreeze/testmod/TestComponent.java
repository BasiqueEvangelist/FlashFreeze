package me.basiqueevangelist.flashfreeze.testmod;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.util.math.Direction;
import net.minecraftforge.common.capabilities.Capability;
import org.jetbrains.annotations.Nullable;

public interface TestComponent {
    int getValue();
    void setValue(int value);

    public class Storage implements Capability.IStorage<TestComponent> {
        @Nullable
        @Override
        public NbtElement writeNBT(Capability<TestComponent> capability, TestComponent object, Direction arg) {
            NbtCompound tag = new NbtCompound();
            tag.putInt("Value", object.getValue());
            return tag;
        }

        @Override
        public void readNBT(Capability<TestComponent> capability, TestComponent object, Direction arg, NbtElement arg2) {
            object.setValue(((NbtCompound)arg2).getInt("Value"));
        }
    }
}
