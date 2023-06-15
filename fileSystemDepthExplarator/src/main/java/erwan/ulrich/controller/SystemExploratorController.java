package erwan.ulrich.controller;

import erwan.ulrich.abstraction.AbstractFileSystemExploratorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/system-explorator")
@AllArgsConstructor()
public class SystemExploratorController {

    private final AbstractFileSystemExploratorService fileSystemExploratorService;

    @GetMapping("/{file-system-as-string}")
    public int exploreFileSystemDepth(@PathVariable(value = "file-system-as-string") String fileSystemAsString) {
        return this.fileSystemExploratorService.exploreFileSystemDepth(fileSystemAsString);
    }
}
