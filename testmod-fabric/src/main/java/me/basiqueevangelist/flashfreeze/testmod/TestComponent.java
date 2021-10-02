package me.basiqueevangelist.flashfreeze.testmod;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface TestComponent extends ComponentV3 {
    int getValue();
    void setValue(int value);
}
