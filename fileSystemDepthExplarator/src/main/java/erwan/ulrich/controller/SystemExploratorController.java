package erwan.ulrich.controller;

import erwan.ulrich.abstraction.AbstractFileSystemExploratorService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;

@RestController
@RequestMapping("/api/system-explorator")
@AllArgsConstructor()
public class SystemExploratorController {

    private final AbstractFileSystemExploratorService fileSystemExploratorService;

    @PostMapping("/exploreFileSystemDepth")
    public int exploreFileSystemDepth(@RequestParam(value = "inputAsString") String fileSystemAsString) throws Exception {
        return this.fileSystemExploratorService.exploreFileSystemDepth(fileSystemAsString);
    }
}
