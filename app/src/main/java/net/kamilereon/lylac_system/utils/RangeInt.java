package net.kamilereon.lylac_system.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * 정의된 범위 사이의 값을 가져오는 클래스
 */
@Setter
@Getter
public class RangeInt {
    private int min = 0;
    private int max = 0;

    public RangeInt(int min, int max) {
        this.min = min;
        this.max = max;
    }

    public int getRandomNumber() {
        double rnd = Math.random();
        int diff = max - min;
        rnd *= (diff + 1);
        return (int) Math.floor(rnd + min);
    }
}
