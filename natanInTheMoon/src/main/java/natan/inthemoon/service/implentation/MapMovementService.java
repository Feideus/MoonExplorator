package natan.inthemoon.service.implentation;

import natan.inthemoon.enums.Command;
import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.PointDescription;
import natan.inthemoon.service.abstraction.AbstractCommandHistoryService;
import natan.inthemoon.service.abstraction.AbstractMapGeneratorService;
import natan.inthemoon.service.abstraction.AbstractMapMovementService;
import natan.inthemoon.service.utils.AstarUtils;
import natan.inthemoon.service.utils.MoonMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.abs;


@Service
public class MapMovementService implements AbstractMapMovementService {

    private final AbstractMapGeneratorService mapGeneratorService;
    private final AbstractCommandHistoryService commandHistoryService;
    private MoonMap moonMap;
    private PointDescription currentPosition;

    @Autowired
    public MapMovementService(AbstractMapGeneratorService mapGeneratorService, AbstractCommandHistoryService commandHistoryService) {
        this.mapGeneratorService = mapGeneratorService;
        this.commandHistoryService = commandHistoryService;
    }

    public void initializeMap(final int mapDimension, PointDescription pointDescription) throws Exception {
        this.moonMap = mapGeneratorService.generateMap(mapDimension);
        this.currentPosition = pointDescription;
    }

    public int executeCommands(final String commands) throws Exception {
        if (commands == null || commands.isEmpty()) {
            throw new Exception("Commands should not be empty");
        }

        String[] parsedCommands = commands.split(",");
        verifyCommands(parsedCommands);
        return executeMovements(parsedCommands);
    }

    private int executeMovements(final String[] commands) throws Exception {
        if (commands == null || commands.length == 0) {
            throw new Exception("Bad commands");
        }
        PointDescription target = resolveTarget(moonMap, commands);
        List<PointDescription> aStarMoveList = AstarUtils.aStarShortestRoute(moonMap, currentPosition, target);

        commandHistoryService.historizeMoveList(aStarMoveList, moonMap.getDimension());

        MoonMapUtils.resetMoonMapHeuristics(moonMap);

        return aStarMoveList.size();
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
        for (PointDescription pointDescription : moonMap.getPointList()) {
            if (pointDescription.getX() % moonMap.getDimension() == 0) {
                strBuilder.append(System.getProperty("line.separator"));
            }
            strBuilder.append(pointDescription.isObstacle() ? "O" : ".");
        }
        return strBuilder.toString();
    }

    private PointDescription resolveTarget(final MoonMap moonMap, final String[] commands) throws Exception {
        int nextX = currentPosition.getX();
        int nextY = currentPosition.getY();
        for (String command : commands) {
            switch (Command.valueOf(command)) {
                case N:
                    nextX = (nextX - 1 + moonMap.getDimension()) % moonMap.getDimension();
                    break;
                case S:
                    nextX = (nextX + 1 ) % moonMap.getDimension();
                    break;
                case E:
                    nextY = (nextY - 1 + moonMap.getDimension()) % moonMap.getDimension();
                    break;
                case W:
                    nextY = (nextY + 1 ) % moonMap.getDimension();
                    break;
                default:
                    throw new Exception("Bad command");
            }
        }
        return MoonMapUtils.getPointDescriptionInMap(moonMap, nextX, nextY);
    }
}
