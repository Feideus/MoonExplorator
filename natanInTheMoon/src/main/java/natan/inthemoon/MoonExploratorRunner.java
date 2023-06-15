package natan.inthemoon;

import natan.inthemoon.pojos.WeightedPointDescription;
import natan.inthemoon.service.abstraction.AbstractMapMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author Erwan Ulrich
 */

@Component
public class MoonExploratorRunner implements ApplicationRunner {

    private final AbstractMapMovementService mapMovementService;

    @Value("${mapDimension}")
    private int mapDimension;

    @Autowired
    public MoonExploratorRunner(AbstractMapMovementService mapMovementService) {
        this.mapMovementService = mapMovementService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        WeightedPointDescription startPosition = new WeightedPointDescription(0, 0, false, 0, 0, null);
        this.mapMovementService.initializeMap(mapDimension, startPosition);
    }
}
