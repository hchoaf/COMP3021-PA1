package hk.ust.comp3021.game;

import hk.ust.comp3021.actions.*;
import hk.ust.comp3021.entities.Box;
import hk.ust.comp3021.entities.Empty;
import org.jetbrains.annotations.NotNull;

import static hk.ust.comp3021.utils.StringResources.*;

/**
 * A base implementation of Sokoban Game.
 */
public abstract class AbstractSokobanGame implements SokobanGame {
    @NotNull
    protected final GameState state;

    protected AbstractSokobanGame(@NotNull GameState gameState) {
        this.state = gameState;
    }

    /**
     * @return True is the game should stop running.
     * For example when the user specified to exit the game or the user won the game.
     */
    protected boolean shouldStop() {
        // if (state.isWin() || )
        // TODO
        if (state.isWin()) {
            return true;
        }
        return false;
    }

    /**
     * @param action The action received from the user.
     * @return The result of the action.
     *  <li>If nextPosition is empty, move</li>
     *  <li>If nextPosition is box</li>
     *  <li>- If boxID is different from playerID, fail</li>
     *  <li>- If nextPosition of box is empty, move</li>
     *  <li>- If nextPosition of box is either Player or Wall, fail</li>
     *  <li>- If boxID is different from playerID, fail</li>
     *  <li>else (if nextPosition is not empty or box = player or wall), fail</li>
     */
    protected ActionResult processAction(@NotNull Action action) {
        if (action instanceof Exit) {
            return new ActionResult.Success(action);
        } else if (action instanceof Move) {
            var playerId = action.getInitiator();
            var playerPosition = state.getPlayerPositionById(action.getInitiator());
            var targetPosition = ((Move) action).nextPosition(playerPosition);
            var targetEntity = state.getEntity(targetPosition);

            if (! state.getAllPlayerIds().contains(playerId)) {
                return new ActionResult.Failed(action, PLAYER_NOT_FOUND);
            }

            if (targetEntity instanceof Empty) {
                state.move(playerPosition, targetPosition);
                return new ActionResult.Success(action);
            } else if (targetEntity instanceof Box) {
                if (playerId != ((Box) targetEntity).getPlayerId()) {
                    // Can't move a box which does not belong to the player
                    return new ActionResult.Failed(action, "Failed to push the box.");
                } else {
                    if (state.getEntity(((Move) action).nextPosition(targetPosition)) instanceof Empty) {
                        // Move box then player
                        state.move(targetPosition, ((Move) action).nextPosition(targetPosition));
                        state.move(playerPosition, targetPosition);
                        state.checkpoint();
                        return new ActionResult.Success(action);
                    } else {
                        // Can't move the box
                        return new ActionResult.Failed(action, "Failed to push the box");
                    }
                }
            } else {
                return new ActionResult.Failed(action, "You hit a wall.");
            }

        } else if (action instanceof Undo) {
            if (state.getUndoQuota().isPresent() && state.getUndoQuota().get() <= 0) {
                return new ActionResult.Failed(action, UNDO_QUOTA_RUN_OUT);
            }
            state.undo();
            return new ActionResult.Success(action);
        } else if (action instanceof InvalidInput) {
            return new ActionResult.Failed(action, INVALID_INPUT_MESSAGE);
        }
        return null;
        // TODO
        // throw new NotImplementedException();
    }
}
