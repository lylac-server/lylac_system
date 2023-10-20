package net.kamilereon.lylac_system.entity.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;

@Getter
@Setter
@AllArgsConstructor
public abstract class LylacEvent {
    private AbstractEntityWrapper entityWrapper;
}
