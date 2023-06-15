package natan.inthemoon.service.utils;

import natan.inthemoon.enums.ExceptionLabel;
import natan.inthemoon.exception.NatanInTheMoonException;
import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.WeightedPointDescription;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import static java.lang.Math.max;

/**
 * Implementation of the A* algorithm (iteration of Djikstra's)
 *
 * @Autor Erwan Ulrich
 */

public class AstarUtils {

    /**
     * For indepth detail of the logic, see https://fr.wikipedia.org/wiki/Algorithme_A*
     * @param moonMap
     * @param start
     * @param target
     * @return
     * @throws Exception
     */
    public static List<WeightedPointDescription> aStarShortestRoute(final MoonMap moonMap, final WeightedPointDescription start, final WeightedPointDescription target) {
        if (moonMap == null || start == null || target == null) {
            throw new NatanInTheMoonException(HttpStatus.BAD_REQUEST, ExceptionLabel.INVALID_A_STAR_INPUT);
        }
        PriorityQueue<WeightedPointDescription> openList = new PriorityQueue<>(WeightedPointDescription::compareTo);
        List<WeightedPointDescription> closedList = new ArrayList<>();
        openList.add(start);

        WeightedPointDescription currentPoint;

        while (!openList.isEmpty()) {
            currentPoint = openList.poll();
            if (currentPoint.equals(target)) {
                return resolveRouteFrom(currentPoint);
            }
            for (WeightedPointDescription neighbor : computeNeighbors(moonMap, currentPoint)) {
                if (!(listContainsPoint(closedList, neighbor)
                        || (listContainsPoint(new ArrayList<>(openList), neighbor)
                        && getPointDescriptionInQueue(openList, neighbor).getCost() < currentPoint.getCost()))) {
                    neighbor.setPrevious(currentPoint);
                    neighbor.setCost(currentPoint.getCost() + 1);
                    neighbor.setHeuristic(neighbor.getCost() + computeHeuristics(neighbor, target));
                    openList.add(neighbor);
                }
            }
            closedList.add(currentPoint);
        }
        throw new NatanInTheMoonException(HttpStatus.FORBIDDEN, ExceptionLabel.NO_ROUTE_FOUND);
    }

    /**
     * Using the last position and going previous to previous until
     * first position is found and then reverses, to give ordered
     * positions of the route
     * @param weightedPointDescription
     * @return
     */
    private static List<WeightedPointDescription> resolveRouteFrom(final WeightedPointDescription weightedPointDescription) {
        List<WeightedPointDescription> route = new ArrayList<>();
        WeightedPointDescription current = weightedPointDescription;
        if (weightedPointDescription.getPrevious() == null) {
            return route;
        }

        do {
            route.add(current);
            current = current.getPrevious();
        } while (current.getPrevious() != null);

        Collections.reverse(route);
        return route;
    }

    /**
     * Resolves neighboring positions that are not obstacles
     * for any given position
     * @param moonMap
     * @param weightedPointDescription
     * @return
     */
    private static List<WeightedPointDescription> computeNeighbors(final MoonMap moonMap, final WeightedPointDescription weightedPointDescription) {
        final int dimension = moonMap.getDimension();

        return moonMap.getPointList()
                .stream()
                // Exclude current point
                .filter(p1 -> !(p1.getX() == weightedPointDescription.getX() && p1.getY() == weightedPointDescription.getY()))
                .filter(p2 -> (p2.getX() == weightedPointDescription.leftIndex(dimension) && p2.getY() == weightedPointDescription.getY())
                        || (p2.getX() == weightedPointDescription.rightIndex(dimension) && p2.getY() == weightedPointDescription.getY())
                        || (p2.getX() == weightedPointDescription.getX() && p2.getY() == weightedPointDescription.upIndex(dimension))
                        || (p2.getX() == weightedPointDescription.getX() && p2.getY() == weightedPointDescription.downIndex(dimension)))
                // obstacles are not valid neighbors
                .filter(p3 -> !p3.isObstacle())
                .collect(Collectors.toList());
    }

    /**
     * Computes a relative distance between current position and target position
     * for indepth detail see https://fr.wikipedia.org/wiki/Distance_de_Tchebychev
     * @param weightedPointDescription
     * @param target
     * @return
     */
    private static double computeHeuristics(final WeightedPointDescription weightedPointDescription, final WeightedPointDescription target) {
        // Chebyshev heuristics method of calculus
        return weightedPointDescription.getCost() +
                max(Math.abs(weightedPointDescription.getX() - target.getX()), Math.abs(weightedPointDescription.getY() - target.getY()));
    }

    // ------- UTILS

    /**
     * Determine if list of positions has given position
     * @param pointDescriptionList
     * @param target
     * @return
     */
    private static boolean listContainsPoint(final List<WeightedPointDescription> pointDescriptionList, final WeightedPointDescription target) {
        return pointDescriptionList
                .stream()
                .anyMatch(p1 -> p1.equals(target));
    }

    /**
     * retrieves given position from PriorityQueue
     * @param queue
     * @param weightedPointDescription
     * @return
     */
    private static WeightedPointDescription getPointDescriptionInQueue(final PriorityQueue<WeightedPointDescription> queue, final WeightedPointDescription weightedPointDescription) {
        return queue
                .stream()
                .filter(p1 -> p1.equals(weightedPointDescription))
                .findFirst()
                .orElseThrow(() -> new NatanInTheMoonException(HttpStatus.INTERNAL_SERVER_ERROR,ExceptionLabel.NO_MATCH_FOR_COORDINATES));
    }

}