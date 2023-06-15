package erwan.ulrich.implementation;

import erwan.ulrich.abstraction.AbstractFileSystemExploratorService;
import erwan.ulrich.abstraction.AbstractInputManagerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FileSystemExploratorService implements AbstractFileSystemExploratorService {

    private final AbstractInputManagerService inputManagementService;

    @Override
    public int exploreFileSystemDepth(String inputAsString) {
        inputManagementService.validateInput(inputAsString);
        List<String> inputs = inputManagementService.parseInput(inputAsString);
        return computeDepth(inputs);
    }


    @Override
    public int computeDepth(List<String> parsedInput) {
        return 0;
    }

}
