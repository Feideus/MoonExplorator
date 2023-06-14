package natan.inthemoon.pojos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;


@Builder
@Getter
@Setter
public final class PointDescription implements Comparable<PointDescription> {
    private final int x;
    private final int y;
    private final boolean obstacle;
    private double heuristic;
    private double cost;
    private PointDescription previous;

    @Override
    public int compareTo(PointDescription pointDescription) {
        return Double.compare(this.heuristic, pointDescription.heuristic);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PointDescription p = (PointDescription) o;
        return x == p.x && y == p.y;
    }
}
