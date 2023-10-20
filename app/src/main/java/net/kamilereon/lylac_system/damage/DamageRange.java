package net.kamilereon.lylac_system.damage;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.utils.RangeInt;

/**
 * 데미지 범위 클래스
 */
@Setter
@Getter
public class DamageRange {
    private RangeInt damageRange = new RangeInt(1, 10);

    public DamageRange() {
    }

    public DamageRange(RangeInt damageRange) {
        this.damageRange = damageRange;
    }
}
