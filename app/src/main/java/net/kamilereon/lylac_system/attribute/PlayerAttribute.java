package net.kamilereon.lylac_system.attribute;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.attribute.stat.Stat;

@Getter
@Setter
public class PlayerAttribute extends EntityAttribute {
    private int mana = 10;
    private int maxMana = 10;
    private int level = 1;
    private int xp = 0;
    private Stat stat = new Stat();
}
