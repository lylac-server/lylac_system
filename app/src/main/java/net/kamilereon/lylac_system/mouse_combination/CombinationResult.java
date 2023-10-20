package net.kamilereon.lylac_system.mouse_combination;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CombinationResult {
    private CombinationType combinationType;
    private CombinationStatus combinationStatus = CombinationStatus.NOT_CHANGED;

    enum CombinationStatus {
        COMPLETE,
        CHANGED,
        NOT_CHANGED,
    }
}
