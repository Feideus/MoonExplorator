package natan.inthemoon.service.utils;

import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.WeightedPointDescription;

/**
 *
 * Utilitaries to manipulate the 2D map
 *
 * @Author Erwan Ulrich
 */
public class MoonMapUtils {

    /**
     * Retrives WeightedPointDescription at given coordinates
     * @param moonMap
     * @param x
     * @param y
     * @return
     */
    public static WeightedPointDescription getPointDescriptionInMap(final MoonMap moonMap, int x, int y) {
        return moonMap
                .getPointList()
                .stream()
                .filter(p1 -> p1.getX() == x && p1.getY() == y)
                .findFirst()
                .orElseThrow();
    }

    /**
     * Resets all positions heuristics and costs to neutral for next
     * A* execution
     * @param moonMap
     */
    public static void resetMoonMapHeuristics(final MoonMap moonMap) {
        for (WeightedPointDescription weightedPointDescription : moonMap.getPointList()) {
            weightedPointDescription.setHeuristic(0.0);
            weightedPointDescription.setCost(0.0);
            weightedPointDescription.setPrevious(null);
        }
    }



}
