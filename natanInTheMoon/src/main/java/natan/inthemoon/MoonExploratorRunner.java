package natan.inthemoon;

import lombok.AllArgsConstructor;
import natan.inthemoon.service.abstraction.AbstractMapMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class MoonExploratorRunner implements ApplicationRunner {

    private AbstractMapMovementService mapMovementService;

    @Value("${mapDimension}")
    private int mapDimension;

    @Autowired
    public MoonExploratorRunner(AbstractMapMovementService mapMovementService) {
        this.mapMovementService = mapMovementService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.mapMovementService.initializeMap(mapDimension);
    }
}
