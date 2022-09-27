package hk.ust.comp3021.tui;

import hk.ust.comp3021.actions.Exit;
import hk.ust.comp3021.actions.InvalidInput;
import hk.ust.comp3021.actions.Move;
import hk.ust.comp3021.actions.Undo;
import hk.ust.comp3021.utils.TestKind;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OwnTerminalInputEngineTest {

    @Tag(TestKind.PUBLIC)
    @Test
    void testExit() {
        String[] exits = {"Exit", "ExiT", "exit", "EXIT", "exiT"};
        for(String exit : exits) {
            var inputStream = fixValueStream(exit);
            var inputEngine = new TerminalInputEngine(inputStream);
            var action = inputEngine.fetchAction();
            assertTrue(action instanceof Exit);
        }
    }
    @Test
    void testUndo() {
        String[] undos = {"u", "U"};
        for(String undo : undos) {
            var inputStream = fixValueStream(undo);
            var inputEngine = new TerminalInputEngine(inputStream);
            var action = inputEngine.fetchAction();
            assertTrue(action instanceof Undo);
        }
    }

    @Test
    void testMove() {
        String[] player0Moves = {"a", "A", "s", "S", "d", "D", "w", "W"};
        String[] player1Moves = {"h", "H", "j", "J", "k", "K", "l", "L"};
        for (int i = 0; i<player0Moves.length; i++) {
            var player0Move = player0Moves[i];
            var inputStream = fixValueStream(player0Move);
            var inputEngine = new TerminalInputEngine(inputStream);
            var action = inputEngine.fetchAction();
            assertTrue(action instanceof Move);
            assertEquals(0, action.getInitiator());
            switch(i) {
                case 0, 1 -> {
                    assertTrue(action instanceof Move.Left);
                    break;
                }
                case 2, 3 -> {
                    assertTrue(action instanceof Move.Down);
                    break;
                }
                case 4, 5 -> {
                    assertTrue(action instanceof Move.Right);
                    break;
                }
                case 6, 7 -> {
                    assertTrue(action instanceof Move.Up);
                    break;
                }
            }
        }
        for (int i = 0; i<player1Moves.length; i++) {
            var player1Move = player1Moves[i];
            var inputStream = fixValueStream(player1Move);
            var inputEngine = new TerminalInputEngine(inputStream);
            var action = inputEngine.fetchAction();
            assertTrue(action instanceof Move);
            assertEquals(1, action.getInitiator());
            switch(i) {
                case 0, 1 -> {
                    assertTrue(action instanceof Move.Left);
                    break;
                }
                case 2, 3 -> {
                    assertTrue(action instanceof Move.Down);
                    break;
                }
                case 4, 5 -> {
                    assertTrue(action instanceof Move.Right);
                    break;
                }
                case 6, 7 -> {
                    assertTrue(action instanceof Move.Up);
                    break;
                }
            }
        }
    }

    @Test
    void testInvalidInput() {
        String[] invalidInputs = {" exit", " w", "ww", "s ", " ", "", "\n", "b", "1"};
        for (String invalidInput : invalidInputs) {
            var inputStream = fixValueStream(invalidInput);
            var inputEngine = new TerminalInputEngine(inputStream);
            var action = inputEngine.fetchAction();
            assertTrue(action instanceof InvalidInput);

        }
    }


    private InputStream fixValueStream(String content) {
        final var bytes = content.getBytes(StandardCharsets.UTF_8);
        return new ByteArrayInputStream(bytes);
    }
}
