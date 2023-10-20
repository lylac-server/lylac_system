package net.kamilereon.lylac_system.mouse_combination;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.kamilereon.lylac_system.mouse_combination.CombinationResult.CombinationStatus;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;

public class MouseCombination {
    private boolean fKeyPressed = false;
    private List<Type> pressedKeyTypes = new ArrayList<>();

    public CombinationResult pressMouseKey(Type type) {
        CombinationResult combinationResult = new CombinationResult();

        if (isFKey(type)) {
            toggleFKey();
        } else {
            if (!isCompleted() && checkRClickWhenFirstCombination(type)) {
                pressedKeyTypes.add(type);
                combinationResult.setCombinationStatus(CombinationStatus.CHANGED);
            }
        }

        if (isCompleted()) {
            combinationResult.setCombinationStatus(CombinationStatus.COMPLETE);
            combinationResult.setCombinationType(getCombinationType());
        }
        return combinationResult;
    }

    public void initializeCombination() {
        fKeyPressed = false;
        pressedKeyTypes.clear();
    }

    public Component convertToTextComponent() {
        Component component = Component.text("", NamedTextColor.WHITE)
                .decorations(Map.of(TextDecoration.ITALIC, State.FALSE));
        int gapLineCount = 0;
        for (Type type : pressedKeyTypes) {
            component = component
                    .append(Component.text(type.toString(), NamedTextColor.GREEN, TextDecoration.UNDERLINED));
            if (gapLineCount < pressedKeyTypes.size()) {
                component = component.append(Component.text(" - ", NamedTextColor.WHITE));
                gapLineCount++;
            }
        }
        return component;
    }

    private boolean isFKey(Type type) {
        return type == Type.F;
    }

    private void toggleFKey() {
        fKeyPressed = !fKeyPressed;
    }

    /** 조합이 완성되었는지? */
    private boolean isCompleted() {
        return pressedKeyTypes.size() == 3;
    }

    /**
     * 첫번째 클릭
     * 조합시 우클릭인지 검사
     */
    private boolean checkRClickWhenFirstCombination(Type type) {
        return pressedKeyTypes.size() >= 1 || type == Type.R;
    }

    private CombinationType getCombinationType() {
        return CombinationType.getCombinationType(fKeyPressed, pressedKeyTypes);
    }
}
