package natan.inthemoon.service.abstraction;

import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.PointDescription;

import java.util.List;

public interface AbstractMapMovementService {

    void initializeMap(final int mapDimension, PointDescription pointDescription) throws Exception;

    int executeCommands(final String commands) throws Exception;

    String drawMapRepresentation();

}
