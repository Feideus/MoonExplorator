package erwan.ulrich.implementation;

import erwan.ulrich.abstraction.AbstractInputManagerService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class InputManagerService implements AbstractInputManagerService {


    @Override
    public List<String> parseInput(String inputAsString) {
        return Arrays.asList(inputAsString);
    }

    @Override
    public void validateInput(String inputAsString) {

    }
}
