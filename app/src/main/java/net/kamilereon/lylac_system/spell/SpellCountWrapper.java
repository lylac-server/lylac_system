package net.kamilereon.lylac_system.spell;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpellCountWrapper {
    int value = 0;

    /**
     * 
     * @return value 에서 1 증가한 값을 반환
     */
    public int increase() {
        return ++value;
    }

    /**
     * 
     * @return value 에서 1 감소한 값을 반환
     */
    public int decrease() {
        return --value;
    }

    public void initalize() {
        value = 0;
    }
}
