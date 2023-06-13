package natan.inthemoon.service.abstraction;

import natan.inthemoon.pojos.MoonMap;

public interface AbstractMapGeneratorService {
    MoonMap generateMap(final int mapDimension) throws Exception;
}
