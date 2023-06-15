package natan.inthemoon.service.implentation;

import natan.inthemoon.enums.Command;
import natan.inthemoon.enums.ExceptionLabel;
import natan.inthemoon.exception.NatanInTheMoonException;
import natan.inthemoon.pojos.MoonMap;
import natan.inthemoon.pojos.PointDescription;
import natan.inthemoon.pojos.WeightedPointDescription;
import natan.inthemoon.service.abstraction.AbstractCommandHistoryService;
import natan.inthemoon.service.abstraction.AbstractMapGeneratorService;
import natan.inthemoon.service.abstraction.AbstractMapMovementService;
import natan.inthemoon.service.utils.AstarUtils;
import natan.inthemoon.service.utils.MoonMapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    private final String LINE_SEPARATOR = System.getProperty("line.separator");
    private final String CURRENT_POSITION_MARKER = "x";
    private final String FREE_POSITION_MARKER = ".";
    private final String OBSTACLE_POSITION_MARKER = "O";

    @Autowired
    public MapMovementService(AbstractMapGeneratorService mapGeneratorService, AbstractCommandHistoryService commandHistoryService) {
        this.mapGeneratorService = mapGeneratorService;
        this.commandHistoryService = commandHistoryService;
    }

    public void initializeMap(final int mapDimension, WeightedPointDescription pointDescription) {
        this.moonMap = mapGeneratorService.generateMap(mapDimension);
        this.currentPosition = pointDescription;
    }

    /**
     * Parse commands received at endpoint
     * TODO : make separator customisable
     *
     * @param commands
     * @return
     */
    public int executeCommands(final String commands) {
        if (commands == null || commands.isEmpty()) {
            throw new NatanInTheMoonException(HttpStatus.BAD_REQUEST, ExceptionLabel.INVALID_COMMAND);
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
     */
    private int executeMovements(final String[] commands) {
        if (commands == null || commands.length == 0) {
            throw new NatanInTheMoonException(HttpStatus.BAD_REQUEST, ExceptionLabel.BAD_COMMAND);
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
     */
    private void verifyCommands(final String[] parsedCommands) {
        if (parsedCommands == null || parsedCommands.length == 0) {
            throw new NatanInTheMoonException(HttpStatus.BAD_REQUEST, ExceptionLabel.BAD_COMMAND);
        }
        for (String command : parsedCommands) {
            try {
                Command.valueOf(command);
            } catch (IllegalArgumentException e) {
                // Use custom Exception here
                throw new NatanInTheMoonException(HttpStatus.BAD_REQUEST, ExceptionLabel.UNRECOGNIZED_COMMAND);
            }
        }
    }

    /**
     * Draws a representation of the map with the current position
     * on it
     *
     * @return
     */
    public String drawMapRepresentation() {
        StringBuilder strBuilder = new StringBuilder();
        for (PointDescription pointDescription : moonMap.getPointList()) {
            if (pointDescription.getX() % moonMap.getDimension() == 0) {
                strBuilder.append(LINE_SEPARATOR);
            }
            if (pointDescription.equals(currentPosition)) {
                strBuilder.append(CURRENT_POSITION_MARKER);
                continue;
            }
            strBuilder.append(pointDescription.isObstacle() ? OBSTACLE_POSITION_MARKER : FREE_POSITION_MARKER);
        }
        return strBuilder.toString();
    }

    /**
     * Resolve the final position from a series of move commands
     *
     * @param moonMap
     * @param commands
     * @return
     */
    private WeightedPointDescription resolveTarget(final MoonMap moonMap, final String[] commands) {
        WeightedPointDescription target = currentPosition;
        for (String command : commands) {
            switch (Command.valueOf(command)) {
                case W:
                    target = MoonMapUtils.getPointDescriptionInMap(moonMap, target.leftIndex(moonMap.getDimension()), target.getY());
                    break;
                case E:
                    target = MoonMapUtils.getPointDescriptionInMap(moonMap, target.rightIndex(moonMap.getDimension()), target.getY());
                    break;
                case N:
                    target = MoonMapUtils.getPointDescriptionInMap(moonMap, target.getX(), target.upIndex(moonMap.getDimension()));
                    break;
                case S:
                    target = MoonMapUtils.getPointDescriptionInMap(moonMap, target.getX(), target.downIndex(moonMap.getDimension()));
                    break;
                default:
                    throw new NatanInTheMoonException(HttpStatus.BAD_REQUEST, ExceptionLabel.BAD_COMMAND);
            }
        }
        return target;
    }
}
