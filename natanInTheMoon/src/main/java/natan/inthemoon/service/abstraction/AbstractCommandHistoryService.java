package natan.inthemoon.service.abstraction;

import natan.inthemoon.enums.Command;
import natan.inthemoon.pojos.PointDescription;
import natan.inthemoon.pojos.WeightedPointDescription;

import java.util.List;

/**
 * Service responsible for command history management
 *
 * @author Erwan Ulrich
 * */

public interface AbstractCommandHistoryService {

    void historizeMoveList(final List<WeightedPointDescription> pointDescriptionList, int dimension);
    List<Command> getCommandsHistory();
}
