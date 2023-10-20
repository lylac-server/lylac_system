package net.kamilereon.lylac_system.mouse_combination;

import java.util.List;

public enum CombinationType {
    RRR,
    RLR,
    RLL,
    RRL,
    FRRR,
    FRLR,
    FRLL,
    FRRL;

    /**
     * 리스트 형태의 types를 enum 으로 변환
     * 
     * @param isFKeyPressed
     * @param types         반드시 사이즈가 3인 리스트
     * @return
     */
    public static CombinationType getCombinationType(boolean isFKeyPressed, List<Type> types) {
        if (isFKeyPressed) {
            if (types.get(0) == Type.R) {
                if (types.get(1) == Type.R) {
                    if (types.get(2) == Type.R) {
                        return CombinationType.FRRR;
                    } else {
                        return CombinationType.FRRL;
                    }
                } else {
                    if (types.get(2) == Type.R) {
                        return CombinationType.FRLR;
                    } else {
                        return CombinationType.FRLL;
                    }
                }
            }
        } else {
            if (types.get(0) == Type.R) {
                if (types.get(1) == Type.R) {
                    if (types.get(2) == Type.R) {
                        return CombinationType.RRR;
                    } else {
                        return CombinationType.RRL;
                    }
                } else {
                    if (types.get(2) == Type.R) {
                        return CombinationType.RLR;
                    } else {
                        return CombinationType.RLL;
                    }
                }
            }
        }
        return null; // 여기까지 도달하면 오류임!!
    }
}
