package net.kamilereon.lylac_system.attribute;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.damage.DamageRange;
import net.kamilereon.lylac_system.utils.RangeInt;

@Getter
@Setter
public class EntityAttribute {
    private int health = 20;
    private int maxHealth = 20;
    private DamageRange damageRange = new DamageRange(new RangeInt(1, 10));
}
