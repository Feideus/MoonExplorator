package natan.inthemoon.service.implentation;

import natan.inthemoon.enums.Command;
import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.PointDescription;
import natan.inthemoon.service.abstraction.AbstractCommandHistoryService;
import natan.inthemoon.service.abstraction.AbstractMapGeneratorService;
import natan.inthemoon.service.abstraction.AbstractMapMovementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MapMovementService implements AbstractMapMovementService {

    private final AbstractMapGeneratorService mapGeneratorService;
    private final AbstractCommandHistoryService commandHistoryService;
    private MoonMap moonMap;

    @Autowired
    public MapMovementService(AbstractMapGeneratorService mapGeneratorService, AbstractCommandHistoryService commandHistoryService) {
        this.mapGeneratorService = mapGeneratorService;
        this.commandHistoryService = commandHistoryService;
    }

    public void initializeMap(final int mapDimension) throws Exception {
        this.moonMap = this.mapGeneratorService.generateMap(mapDimension);
    }

    public int executeCommands(final String commands) throws Exception {
        if (commands == null || commands.isEmpty()) {
            throw new Exception("Commands should not be empty");
        }

        String[] parsedCommands = commands.split(",");
        verifyCommands(parsedCommands);

        for (String command : parsedCommands) {
            executeMovement(command);
            this.commandHistoryService.historizeCommand(command);
        }

        return parsedCommands.length;
    }

    private void executeMovement(final String command) {
        // Not yet Implemented
    }

    private void verifyCommands(final String[] parsedCommands) throws Exception {
        if (parsedCommands == null || parsedCommands.length == 0) {
            throw new Exception("Bad command");
        }
        for (String command : parsedCommands) {
            try {
                Command.valueOf(command);
            } catch (IllegalArgumentException e) {
                // Use custom Exception here
                throw new Exception("Unrecognized command");
            }
        }
    }

    public String drawMapRepresentation() {
        StringBuilder strBuilder = new StringBuilder();
        for (PointDescription pointDescription : this.moonMap.getPointList()) {
            if (pointDescription.getX() % this.moonMap.getDimension() == 0) {
                strBuilder.append(System.getProperty("line.separator"));
            }
            strBuilder.append(pointDescription.isObstacle() ? "O" : ".");
        }
        return strBuilder.toString();
    }
}
