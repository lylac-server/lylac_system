package net.kamilereon.lylac_system.bossbar;

import java.util.HashSet;
import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.entity.PlayerWrapper;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.bossbar.BossBar.Color;
import net.kyori.adventure.bossbar.BossBar.Overlay;
import net.kyori.adventure.text.Component;

@Getter
@Setter
public class HealthFocusBossbar {
    private BossBar currentBar;

    public HealthFocusBossbar(Component name) {
        currentBar = BossBar.bossBar(name, 1, Color.RED, Overlay.NOTCHED_12, new HashSet<>());
    }

    public void update(float scale) {
        currentBar.progress(scale);
    }

    public void showBossbar(PlayerWrapper playerWrapper) {
        currentBar.addViewer(playerWrapper.getController());
    }

    public void removeViewer(PlayerWrapper playerWrapper) {
        currentBar.removeViewer(playerWrapper.getController());
    }
}
