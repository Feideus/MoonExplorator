package natan.inthemoon.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.fail;

@WebMvcTest(MoonExploratorController.class)
public class MoonExploratorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void shouldMoveInDirectionValidTest() throws Exception {
        fail("Not yet implemented");
    }

    @Test
    public void shouldMoveInDirectionFailTest() throws Exception {
        fail("Not yet implemented");
    }

    @Test
    public void shouldReturnHistoryValidTest() throws Exception {
        fail("Not yet implemented");
    }

    @Test
    public void shouldReturnHistoryFailTest() throws Exception {
        fail("Not yet implemented");
    }

    @Test
    public void shouldReturnMapValidTest() throws Exception {
        fail("Not yet implemented");
    }

    @Test
    public void shouldReturnMapFailTest() throws Exception {
        fail("Not yet implemented");
    }


}
