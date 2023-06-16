package erwan.ulrich;

import erwan.ulrich.implementation.InputManagerService;
import erwan.ulrich.pojos.Node;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.fail;

@SpringBootTest
public class InputManagerServiceTest {

    @InjectMocks
    private InputManagerService inputManagerService;

    @Test
    void validateInputShouldSucceed() {
        fail("Not yet implemented");
    }

    @Test
    void validateInputShouldFailTest() {
        fail("Not yet implemented");
    }

    @Test
    void parseInputShouldfail() {
        fail("Not yet implemented");
    }

    @Test
    void parseInputShouldSucceed() {
        fail("Not yet implemented");
    }

    @Test
    void splitInputOnTabShouldSucceed() {
        fail("Not yet implemented");
    }
}
