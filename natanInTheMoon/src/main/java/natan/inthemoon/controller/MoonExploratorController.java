package natan.inthemoon.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/natan-in-the-moon")
@AllArgsConstructor()
class MoonExploratorController {

    @PostMapping("/commands")
    void performMovement(@RequestParam("commands") String commands) {
        return;
    }

    @GetMapping("/history")
    void movementHistory() {
        return;
    }
    @GetMapping("/map")
    void mapInformations() {
        return;
    }

}