package natan.inthemoon.service.implentation;

import natan.inthemoon.enums.Command;
import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.PointDescription;
import natan.inthemoon.pojos.WeightedPointDescription;
import natan.inthemoon.service.abstraction.AbstractCommandHistoryService;
import natan.inthemoon.service.abstraction.AbstractMapGeneratorService;
import natan.inthemoon.service.abstraction.AbstractMapMovementService;
import natan.inthemoon.service.utils.AstarUtils;
import natan.inthemoon.service.utils.MoonMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MapMovementService implements AbstractMapMovementService {

    private final AbstractMapGeneratorService mapGeneratorService;
    private final AbstractCommandHistoryService commandHistoryService;

    // MoonMap and currentPosition are stored localy in the service
    // TODO: implement a better solution for data storage
    private MoonMap moonMap;
    private WeightedPointDescription currentPosition;

    @Autowired
    public MapMovementService(AbstractMapGeneratorService mapGeneratorService, AbstractCommandHistoryService commandHistoryService) {
        this.mapGeneratorService = mapGeneratorService;
        this.commandHistoryService = commandHistoryService;
    }

    public void initializeMap(final int mapDimension, WeightedPointDescription pointDescription) throws Exception {
        this.moonMap = mapGeneratorService.generateMap(mapDimension);
        this.currentPosition = pointDescription;
    }

    /**
     * Parse commands received at endpoint
     * TODO : make separator customisable
     *
     * @param commands
     * @return
     * @throws Exception
     */
    public int executeCommands(final String commands) throws Exception {
        if (commands == null || commands.isEmpty()) {
            throw new Exception("Commands should not be empty");
        }

        String[] parsedCommands = commands.split(",");
        verifyCommands(parsedCommands);
        return executeMovements(parsedCommands);
    }

    /**
     * Core logic to execute movements on the map
     * Rests on an implementation of the A* algorithm
     *
     * @param commands
     * @return
     * @throws Exception
     */
    private int executeMovements(final String[] commands) throws Exception {
        if (commands == null || commands.length == 0) {
            throw new Exception("Bad commands");
        }
        WeightedPointDescription target = resolveTarget(moonMap, commands);
        List<WeightedPointDescription> aStarMoveList = AstarUtils.aStarShortestRoute(moonMap, currentPosition, target);
        commandHistoryService.historizeMoveList(aStarMoveList, moonMap.getDimension());
        currentPosition = target;
        MoonMapUtils.resetMoonMapHeuristics(moonMap);

        return aStarMoveList.size();
    }

    /**
     * Validates all commands are recognized
     *
     * @param parsedCommands
     * @throws Exception
     */
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

    /**
     *
     * Draws a representation of the map with the current position
     * on it
     *
     * @return
     */
    public String drawMapRepresentation() {
        StringBuilder strBuilder = new StringBuilder();
        for (PointDescription pointDescription : moonMap.getPointList()) {
            if (pointDescription.getX() % moonMap.getDimension() == 0) {
                strBuilder.append(System.getProperty("line.separator"));
            }
            if(pointDescription.equals(currentPosition)){
                strBuilder.append("x");
                continue;
            }
            strBuilder.append(pointDescription.isObstacle() ? "O" : ".");
        }
        return strBuilder.toString();
    }

    /**
     * Resolve the final position from a series of move commands
     *
     * @param moonMap
     * @param commands
     * @return
     * @throws Exception
     */
    private WeightedPointDescription resolveTarget(final MoonMap moonMap, final String[] commands) throws Exception {
        int nextX = currentPosition.getX();
        int nextY = currentPosition.getY();
        for (String command : commands) {
            switch (Command.valueOf(command)) {
                case W:
                    nextX = (nextX - 1 + moonMap.getDimension()) % moonMap.getDimension();
                    break;
                case E:
                    nextX = (nextX + 1 ) % moonMap.getDimension();
                    break;
                case N:
                    nextY = (nextY - 1 + moonMap.getDimension()) % moonMap.getDimension();
                    break;
                case S:
                    nextY = (nextY + 1 ) % moonMap.getDimension();
                    break;
                default:
                    throw new Exception("Bad command");
            }
        }
        return MoonMapUtils.getPointDescriptionInMap(moonMap, nextX, nextY);
    }
}
