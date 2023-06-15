package erwan.ulrich.implementation;

import erwan.ulrich.abstraction.AbstractFileSystemExploratorService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class FileSystemExploratorService implements AbstractFileSystemExploratorService {

    @Override
    public List<String> parseInput(String inputAsString) {
        return Arrays.asList(inputAsString);
    }
}
