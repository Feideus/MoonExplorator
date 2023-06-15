package natan.inthemoon.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Description of a position on the map
 *
 * @author Erwan Ulrich
 */

@Getter
@Setter
public final class WeightedPointDescription extends PointDescription implements Comparable<WeightedPointDescription> {

    private double heuristic;
    private double cost;
    private WeightedPointDescription previous;

    public WeightedPointDescription(int x, int y, boolean obstacle, double heuristic, double cost, WeightedPointDescription previous) {
        super(x, y, obstacle);
        this.heuristic = heuristic;
        this.cost = cost;
        this.previous = previous;
    }

    /**
     * Implemetation of heuristics comparision between positions
     *
     * @param pointDescription the object to be compared.
     * @return
     */
    @Override
    public int compareTo(WeightedPointDescription pointDescription) {
        return Double.compare(this.heuristic, pointDescription.heuristic);
    }

    /**
     * Equal override for PointDescription
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        WeightedPointDescription p = (WeightedPointDescription) o;
        return super.getX() == p.getX() && super.getY() == p.getY();
    }
}
