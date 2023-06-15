package natan.inthemoon.enums;

import lombok.Getter;

/**
 * Recognized commands for movement on the map
 *
 * @author Erwan Ulrich
 * */

@Getter
public enum Command {
    S("South"),
    N("North"),
    W("West"),
    E("East");
    public final String label;
    Command(String label) {
        this.label = label;
    }
}
