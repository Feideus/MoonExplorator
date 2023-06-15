package natan.inthemoon.service;

import natan.inthemoon.enums.Command;
import natan.inthemoon.service.implentation.CommandHistoryService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
public class CommandHistoryServiceTest {

    @InjectMocks
    private CommandHistoryService commandHistoryService;

    @Test
    void commandHistoryValidTest() throws Exception {
        // GIVEN
        String command = "E";

        // TODO: update
        // WHEN
//        commandHistoryService.historizeMoveList(command);
//
//        // THEN
//        assertTrue(commandHistoryService.getCommandsHistory()
//                .stream()
//                .filter(commandstr -> Objects.equals(commandstr, Command.valueOf(command)))
//                .count() == 1);
    }

    @Test
    void commandHistoryUnrecognizedCommandFailTest() {
        // GIVEN
        // Unrecognized command
        String command = "T";

        // TODO: update
        // WHEN
//        var exception = Assertions.assertThrows(Exception.class, () ->
//                this.commandHistoryService.historizeMoveList(command));
//
//        // THEN
//        assertEquals(exception.getClass(), IllegalArgumentException.class);
    }
}
