package natan.inthemoon.service.implentation;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.service.abstraction.AbstractMapGeneratorService;
import natan.inthemoon.service.abstraction.AbstractMapMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapMovementService implements AbstractMapMovementService {

    private AbstractMapGeneratorService mapGeneratorService;

    private MoonMap moonMap;

    @Autowired
    public MapMovementService(AbstractMapGeneratorService mapGeneratorService) {
        this.mapGeneratorService = mapGeneratorService;
    }

    public void initializeMap(final int mapDimension) throws Exception {
        this.moonMap = this.mapGeneratorService.generateMap(mapDimension);
    }
}
