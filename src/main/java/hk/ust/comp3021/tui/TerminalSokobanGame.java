package hk.ust.comp3021.tui;


import hk.ust.comp3021.actions.ActionResult;
import hk.ust.comp3021.game.AbstractSokobanGame;
import hk.ust.comp3021.game.GameState;
import hk.ust.comp3021.game.InputEngine;
import hk.ust.comp3021.game.RenderingEngine;
import hk.ust.comp3021.utils.NotImplementedException;

/**
 * A Sokoban game running in the terminal.
 */
public class TerminalSokobanGame extends AbstractSokobanGame {

    private final InputEngine inputEngine;

    private final RenderingEngine renderingEngine;

    /**
     * Create a new instance of TerminalSokobanGame.
     * Terminal-based game only support at most two players, although the hk.ust.comp3021.game package supports up to 26 players.
     * This is only because it is hard to control too many players in a terminal-based game.
     *
     * @param gameState       The game state.
     * @param inputEngine     the terminal input engin.
     * @param renderingEngine the terminal rendering engine.
     * @throws IllegalArgumentException when there are more than two players in the map.
     */
    public TerminalSokobanGame(GameState gameState, TerminalInputEngine inputEngine, TerminalRenderingEngine renderingEngine) {
        super(gameState);
        this.inputEngine = inputEngine;
        this.renderingEngine = renderingEngine;
        if (gameState.getAllPlayerIds().size() > 2) {
            System.out.println(gameState.getAllPlayerIds().toString());
            throw new IllegalArgumentException("There are more than two players in the map");
        }
        // TODO
        // Check the number of players
        // throw new NotImplementedException();
    }

    @Override
    public void run() {
        // TODO
        // throw new NotImplementedException();
        while (!super.shouldStop()) {
            System.out.println("wowoww");
            renderingEngine.render(this.state);
            var action = inputEngine.fetchAction();
            var actionResult = processAction(action);
            if (actionResult instanceof ActionResult.Failed) {
                renderingEngine.message(((ActionResult.Failed) actionResult).getReason());
            } else if (actionResult instanceof ActionResult.Success) {

            }
            // renderingEngine.
        }
    }
}
