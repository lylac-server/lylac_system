package net.kamilereon.lylac_system.display;

import org.bukkit.Location;
import org.bukkit.entity.Display.Billboard;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.TextDisplay;

import lombok.AllArgsConstructor;
import net.kamilereon.lylac_system.SchedulerWrapper;
import net.kamilereon.lylac_system.damage.DamageDTO;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;

@AllArgsConstructor
public class DamageDisplay {
    private Location location;
    private DamageDTO damage;

    public void dispose() {
        TextDisplay textDisplay = (TextDisplay) location.getWorld().spawnEntity(location, EntityType.TEXT_DISPLAY);
        textDisplay.text(Component.text("- ♥" + damage.getDamage()).color(TextColor.color(255, 0, 0))
                .decorate(TextDecoration.BOLD));
        textDisplay.setBillboard(Billboard.CENTER);
        SchedulerWrapper schedulerWrapper = new SchedulerWrapper();
        schedulerWrapper.runLater(30, () -> {
            textDisplay.remove();
        });
    }
}
