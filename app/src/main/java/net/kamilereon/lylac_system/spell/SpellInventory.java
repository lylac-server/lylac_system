package net.kamilereon.lylac_system.spell;

import java.util.ArrayList;
import java.util.List;

import net.kamilereon.lylac_system.mouse_combination.CombinationType;

public class SpellInventory {
    private final List<Spell> spells = new ArrayList<>();

    public SpellInventory() {
        for (int i = 0; i < 8; i++) {
            spells.add(null);
        }
    }

    public void setSpell(int index, Spell spell) {
        spells.set(index, spell);
    }

    public void removeSpell(int index) {
        spells.set(index, null);
    }

    public Spell getSpell(CombinationType type) {
        return getSpell(type.ordinal());
    }

    public Spell getSpell(int index) {
        if (index >= 8)
            return null;
        return spells.get(index);
    }
}
