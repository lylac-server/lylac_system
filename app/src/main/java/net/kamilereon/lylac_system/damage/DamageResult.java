package net.kamilereon.lylac_system.damage;

import lombok.Getter;
import lombok.Setter;

/**
 * 데미지 결과
 */
@Getter
@Setter
public class DamageResult {
    private DamageDTO originDamage;
    private DamageDTO finalDamage;
}
