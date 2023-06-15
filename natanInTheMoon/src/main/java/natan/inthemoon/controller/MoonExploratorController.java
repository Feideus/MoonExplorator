package natan.inthemoon.controller;

import lombok.AllArgsConstructor;
import natan.inthemoon.enums.Command;
import natan.inthemoon.pojos.PointDescription;
import natan.inthemoon.service.abstraction.AbstractCommandHistoryService;
import natan.inthemoon.service.abstraction.AbstractMapMovementService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**
* Controller handling moon exploration and map control
*
* @author Erwan Ulrich
* */

@RestController
@RequestMapping("/api/natan-in-the-moon")
@AllArgsConstructor()
class MoonExploratorController {

    private final AbstractMapMovementService mapMovementService;
    private final AbstractCommandHistoryService commandHistoryService;

    @PostMapping("/commands")
    public int performMovement(@RequestParam("commands") String commands) {
        try {
            return mapMovementService.executeCommands(commands.trim());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage(), e);
        }
    }

    @GetMapping("/history")
    public List<String> commandsHistory() {
        return commandHistoryService.getCommandsHistory()
                .stream()
                .map(Command::getLabel)
                .collect(Collectors.toList());
    }

    @GetMapping("/map")
    void mapInformations() {
        return;
    }

    @GetMapping("/map-representation")
    public String mapRepresentation() {
        return mapMovementService.drawMapRepresentation();
    }

}