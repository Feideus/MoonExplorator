package natan.inthemoon.enums;

import lombok.Getter;

@Getter
public enum ExceptionLabel {
    UNRECOGNIZED_COMMAND("Unrecognized command"),
    INVALID_COMMAND("Invalid command"),
    INVALID_A_STAR_INPUT("Wrong input for A* pathfinding"),
    NO_ROUTE_FOUND("No route found"),
    NO_MATCH_FOR_COORDINATES("No match for this WeightedPointDescription"),
    NEGATIVE_OR_NULL_MAP_DIMENSION("Map dimension cannot be <= 0"),
    BAD_COMMAND("Bad command");

    public final String label;

    ExceptionLabel(String label) {
        this.label = label;
    }
}
