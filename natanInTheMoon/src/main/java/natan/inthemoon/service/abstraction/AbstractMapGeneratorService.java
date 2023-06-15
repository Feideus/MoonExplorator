package natan.inthemoon.service.abstraction;

import natan.inthemoon.pojos.MoonMap;

/**
 * Service responsible for map generation
 *
 * @author Erwan Ulrich
 * */

public interface AbstractMapGeneratorService {
    MoonMap generateMap(final int mapDimension) throws Exception;

}
