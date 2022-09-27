package hk.ust.comp3021.game;

import hk.ust.comp3021.actions.Action;
import hk.ust.comp3021.actions.ActionResult;
import hk.ust.comp3021.actions.Exit;
import hk.ust.comp3021.actions.Move;
import hk.ust.comp3021.utils.TestHelper;
import hk.ust.comp3021.utils.TestKind;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class OwnAbstractSokobanGameTest {

    @Tag(TestKind.PUBLIC)
    @Test
    void testMove() {
        String mapText = """
            233
            ######
            #A..@#
            #....#
            #a...#
            ######
            """;
        final var testMap = TestHelper.parseGameMap(mapText);
        final var gameState = spy(new GameState(testMap));

        final var game = new SokobanGameForTesting(gameState);
        final var result = game.feedActionForProcessing(new Move.Down(0));

        assertTrue(result instanceof ActionResult.Success);
        verify(gameState, times(1)).move(any(), any());
    }


    private static class SokobanGameForTesting extends AbstractSokobanGame {

        protected SokobanGameForTesting(GameState gameState) {
            super(gameState);
        }

        @Override
        public void run() {
        }

        public ActionResult feedActionForProcessing(Action action) {
            return processAction(action);
        }
    }
}
