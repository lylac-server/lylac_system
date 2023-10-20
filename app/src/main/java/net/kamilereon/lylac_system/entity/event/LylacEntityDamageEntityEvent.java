package net.kamilereon.lylac_system.entity.event;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.damage.DamageDTO;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;

@Getter
@Setter
public class LylacEntityDamageEntityEvent extends LylacEvent {
    private AbstractEntityWrapper target;
    private DamageDTO damageDTO;

    public LylacEntityDamageEntityEvent(AbstractEntityWrapper entityWrapper) {
        super(entityWrapper);
    }
}
