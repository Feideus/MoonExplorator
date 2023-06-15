package natan.inthemoon.service.abstraction;

import natan.inthemoon.pojos.PointDescription;
import natan.inthemoon.pojos.WeightedPointDescription;

/**
 * Service responsible for movement on the map
 *
 * @author Erwan Ulrich
 * */

public interface AbstractMapMovementService {

    void initializeMap(final int mapDimension, WeightedPointDescription pointDescription) throws Exception;

    int executeCommands(final String commands) throws Exception;

    String drawMapRepresentation();

}
