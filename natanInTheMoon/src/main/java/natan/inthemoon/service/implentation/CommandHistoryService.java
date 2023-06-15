package natan.inthemoon.service.implentation;

import natan.inthemoon.enums.Command;
import natan.inthemoon.enums.ExceptionLabel;
import natan.inthemoon.exception.NatanInTheMoonException;
import natan.inthemoon.pojos.WeightedPointDescription;
import natan.inthemoon.service.abstraction.AbstractCommandHistoryService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommandHistoryService implements AbstractCommandHistoryService {

    private final List<Command> commandsHistory = new ArrayList<>();

    /**
     * Stores the moves performed to reach target, in order
     *
     * @param moveList
     * @param dimension
     */
    public void historizeMoveList(final List<WeightedPointDescription> moveList, int dimension) {
        if (moveList == null || CollectionUtils.isEmpty(moveList)) {
            throw new NatanInTheMoonException(HttpStatus.BAD_REQUEST, ExceptionLabel.BAD_COMMAND);
        }

        for (WeightedPointDescription weightedPointDescription : moveList) {
            // Ignore starting point
            if (weightedPointDescription.getPrevious() == null) {
                continue;
            }
            if (weightedPointDescription.getPrevious().getX() == (weightedPointDescription.leftIndex(dimension))) {
                this.commandsHistory.add(Command.E);
            } else if (weightedPointDescription.getPrevious().getX() == weightedPointDescription.rightIndex(dimension)) {
                this.commandsHistory.add(Command.W);
            } else if (weightedPointDescription.getPrevious().getY() == weightedPointDescription.upIndex(dimension)) {
                this.commandsHistory.add(Command.S);
            } else if (weightedPointDescription.getPrevious().getY() == weightedPointDescription.downIndex(dimension)) {
                this.commandsHistory.add(Command.N);
            } else {
                throw new NatanInTheMoonException(HttpStatus.BAD_REQUEST, ExceptionLabel.BAD_COMMAND);
            }
        }
    }

    public List<Command> getCommandsHistory() {
        return commandsHistory;
    }
}
