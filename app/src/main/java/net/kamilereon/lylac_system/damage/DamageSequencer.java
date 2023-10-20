package net.kamilereon.lylac_system.damage;

import net.kamilereon.lylac_system.display.DamageDisplay;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;
import net.kamilereon.lylac_system.entity.event.LylacEntityDamageEntityEvent;
import net.kamilereon.lylac_system.utils.WorldUtil;

/**
 * 엔티티 데미지 절차
 */
public class DamageSequencer {
    public static void damageTo(AbstractEntityWrapper from, AbstractEntityWrapper to, DamageRange damageRange) {
        DamageDTO determined = determineDamage(damageRange);
        sendLylacEntityDamageEntityEvent(from, to, determined);
        to.damage(determined, from);
        summonDamageDisplay(to, determined);
    }

    private static DamageDTO determineDamage(DamageRange damageRange) {
        DamageDTO dto = new DamageDTO();
        int damage = damageRange.getDamageRange().getRandomNumber();
        dto.setDamage(damage);
        return dto;
    }

    private static void sendLylacEntityDamageEntityEvent(AbstractEntityWrapper from, AbstractEntityWrapper to,
            DamageDTO dto) {
        LylacEntityDamageEntityEvent event = new LylacEntityDamageEntityEvent(from);
        event.setTarget(to);
        event.setDamageDTO(dto);
        from.onDamagedToEntity(event);
    }

    private static void summonDamageDisplay(AbstractEntityWrapper target, DamageDTO determinedDamage) {
        DamageDisplay damageDisplay = new DamageDisplay(
                WorldUtil.getRandomPositionInRadius(target.getController().getLocation().clone().add(0,
                        1, 0), 0.5),
                determinedDamage);
        damageDisplay.dispose();
    }
}
