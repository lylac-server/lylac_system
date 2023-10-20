package net.kamilereon.lylac_system.spell;

import java.util.Map;

import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;

public abstract class Spell {
    public String spellName = "spell something";

    public Spell(String spellName) {
        this.spellName = spellName;
    }

    public abstract void execute(AbstractEntityWrapper entityWrapper);

    public abstract void cancel();

    public Component getSpellUsingComponent() {
        return Component.text(spellName)
                .decorations(Map.of(TextDecoration.ITALIC, State.FALSE, TextDecoration.BOLD, State.TRUE))
                .color(TextColor.color(178, 222, 87));
    }
}
