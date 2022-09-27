package hk.ust.comp3021.game;

import hk.ust.comp3021.entities.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;

/**
 * The state of the Sokoban Game.
 * Each game state represents an ongoing game.
 * As the game goes, the game state changes while players are moving while the original game map stays the unmodified.
 * <b>The game state should not modify the original game map.</b>
 * <p>
 * GameState consists of things changing as the game goes, such as:
 * <li>Current locations of all crates.</li>
 * <li>A move history.</li>
 * <li>Current location of player.</li>
 * <li>Undo quota left.</li>
 */
public class GameState {

    private final int maxWidth;
    private final int maxHeight;
    private Set<Position> destinations = new HashSet<Position>();
    private Entity[][] entityMap;
    private final Entity[][] initialEntityMap;

    private final Set<Position> initialDestinations;
    private static Optional<Integer> undoQuotaLeft;

    private final Optional<Integer> initialUndoQuotaLeft;

    private List<GameState> checkPoints = new ArrayList<>();
    /**
     * Create a running game state from a game map.
     *
     * @param map the game map from which to create this game state.
     */
    public GameState(@NotNull GameMap map) {
        // TODO
        this.undoQuotaLeft = map.getUndoLimit();
        this.maxHeight = map.getMaxHeight();
        this.maxWidth = map.getMaxWidth();
        this.destinations = new HashSet<>(map.getDestinations());

        this.entityMap = new Entity[this.maxHeight][this.maxWidth];
        this.initialEntityMap = new Entity[this.maxHeight][this.maxWidth];
        for(int i = 0; i<this.maxHeight; i++) {
            for(int j = 0; j<this.maxWidth; j++) {
                Entity entity = map.getEntity(Position.of(j, i));
                if (entity == null) {
                    entityMap[i][j] = null;
                    initialEntityMap[i][j] = null;
                }else if (entity instanceof Wall) {
                    entityMap[i][j] = new Wall();
                    initialEntityMap[i][j] = new Wall();
                } else if (entity instanceof Player) {
                    entityMap[i][j] = new Player(((Player) entity).getId());
                    initialEntityMap[i][j] = new Player(((Player) entity).getId());
                } else if (entity instanceof Box) {
                    entityMap[i][j] = new Box(((Box) entity).getPlayerId());
                    initialEntityMap[i][j] = new Box(((Box) entity).getPlayerId());
                } else if (entity instanceof Empty) {
                    entityMap[i][j] = new Empty();
                    initialEntityMap[i][j] = new Empty();
                }
            }
        }


        this.initialDestinations = new HashSet<>(this.destinations);
        this.initialUndoQuotaLeft = this.undoQuotaLeft;

        // throw new NotImplementedException();
    }

    public GameState(GameState prevState) {
        this.maxWidth = prevState.maxWidth;
        this.maxHeight = prevState.maxHeight;
        this.entityMap = new Entity[this.maxHeight][this.maxWidth];
        this.initialEntityMap = new Entity[this.maxHeight][this.maxWidth];
        for(int i = 0; i<this.maxHeight; i++) {
            for(int j = 0; j<this.maxWidth; j++) {
                Entity entity = prevState.getEntity(Position.of(j, i));
                if (entity == null) {
                    entityMap[i][j] = null;
                    initialEntityMap[i][j] = null;
                }else if (entity instanceof Wall) {
                    entityMap[i][j] = new Wall();
                    initialEntityMap[i][j] = new Wall();
                } else if (entity instanceof Player) {
                    entityMap[i][j] = new Player(((Player) entity).getId());
                    initialEntityMap[i][j] = new Player(((Player) entity).getId());
                } else if (entity instanceof Box) {
                    entityMap[i][j] = new Box(((Box) entity).getPlayerId());
                    initialEntityMap[i][j] = new Box(((Box) entity).getPlayerId());
                } else if (entity instanceof Empty) {
                    entityMap[i][j] = new Empty();
                    initialEntityMap[i][j] = new Empty();
                }
            }
        }
        this.destinations = new HashSet<>(prevState.destinations);
        this.initialDestinations = new HashSet<>(prevState.initialDestinations);

        this.undoQuotaLeft = prevState.undoQuotaLeft;
        this.initialUndoQuotaLeft = prevState.initialUndoQuotaLeft;


        if (! prevState.checkPoints.isEmpty()) {
            for(GameState checkPoint : prevState.checkPoints) {
                this.checkPoints.add(new GameState(checkPoint));
            }
        }

    }

    /**
     * Get the current position of the player with the given id.
     *
     * @param id player id.
     * @return the current position of the player.
     */
    public @Nullable Position getPlayerPositionById(int id) {
        // TODO
        for (int i = 0; i<maxHeight; i++) {
            for (int j = 0; j<maxWidth; j++) {
                if (entityMap[i][j] instanceof Player) {
                    if (((Player) entityMap[i][j]).getId() == id) {
                        return Position.of(j, i);
                    }
                }
            }
        }
        return null;
        // throw new NotImplementedException();

    }

    /**
     * Get current positions of all players in the game map.
     *
     * @return a set of positions of all players.
     */
    public @NotNull Set<Position> getAllPlayerPositions() {
        // this.printEntityMap();
        Set<Position> allPlayerPositions = new HashSet<>();
        // TODO
        for (int i = 0; i<maxHeight; i++) {
            for (int j = 0; j<maxWidth; j++) {
                if (entityMap[i][j] instanceof Player) {
                    allPlayerPositions.add(Position.of(j, i));
                }
            }
        }
        return allPlayerPositions;
        // throw new NotImplementedException();
    }

    public Set<Integer> getAllPlayerIds() {
        Set<Integer> allPlayerIds = new HashSet<>();
        for (Entity[] row : entityMap) {
            for (Entity entity : row) {
                if (entity instanceof Player) {
                    allPlayerIds.add(((Player) entity).getId());
                }
            }
        }
        return allPlayerIds;
    }

    /**
     * Get the entity that is currently at the given position.
     *
     * @param position the position of the entity.
     * @return the entity object.
     */
    public @Nullable Entity getEntity(@NotNull Position position) {
        // TODO
        // throw new NotImplementedException();
        return entityMap[position.y()][position.x()];
    }

    /**
     * Get all box destination positions as a set in the game map.
     * This should be the same as that in {@link GameMap} class.
     *
     * @return a set of positions.
     */
    public @NotNull @Unmodifiable Set<Position> getDestinations() {
        // TODO
        return this.destinations;
        // throw new NotImplementedException();
    }

    /**
     * Get the undo quota currently left, i.e., the maximum number of undo actions that can be performed from now on.
     * If undo is unlimited,
     *
     * @return the undo quota left (using {@link Optional#of(Object)}) if the game has an undo limit;
     * {@link Optional#empty()} if the game has unlimited undo.
     */
    public Optional<Integer> getUndoQuota() {
        if (undoQuotaLeft.get() >= 0) {
            return Optional.of(undoQuotaLeft.get());
        } else {
            return Optional.empty();
        }
        // TODO
        // throw new NotImplementedException();
    }

    /**
     * Check whether the game wins or not.
     * The game wins only when all box destinations have been occupied by boxes.
     *
     * @return true is the game wins.
     */
    public boolean isWin() {
        // TODO
        for (Position position : this.destinations) {
            if (!((this.entityMap[position.y()][position.x()]) instanceof Box)) {
                return false;
            }
        }
        return true;
        // throw new NotImplementedException();
    }

    /**
     * Move the entity from one position to another.
     * This method assumes the validity of this move is ensured.
     * <b>The validity of the move of the entity in one position to another need not to check.</b>
     *
     * @param from The current position of the entity to move.
     * @param to   The position to move the entity to.
     */
    public void move(Position from, Position to) {
        // TODO
        this.entityMap[to.y()][to.x()] = this.entityMap[from.y()][from.x()];
        this.entityMap[from.y()][from.x()] = new Empty();
        // throw new NotImplementedException();
    }

    /**
     * Record a checkpoint of the game state, including:
     * <li>All current positions of entities in the game map.</li>
     * <li>Current undo quota</li>
     * <p>
     * Checkpoint is used in {@link GameState#undo()}.
     * Every undo actions reverts the game state to the last checkpoint.
     */
    public void checkpoint() {
        // TODO
        this.checkPoints.add(new GameState(this));
        // throw new NotImplementedException();
    }

    /**
     * Revert the game state to the last checkpoint in history.
     * This method assumes there is still undo quota left, and decreases the undo quota by one.
     * <p>
     * If there is no checkpoint recorded, i.e., before moving any box when the game starts,
     * revert to the initial game state.
     */
    public void undo() {
        // TODO
        if (this.getUndoQuota().isEmpty() || undoQuotaLeft.get() > 0) {
            if (checkPoints.isEmpty()) {
                // change everything to initial - no need to decrement undoquota
                for (int i = 0; i < this.maxHeight; i++) {
                    for (int j = 0; j < this.maxWidth; j++) {
                        var entity = this.initialEntityMap[i][j];
                        if (entity == null) {
                            entityMap[i][j] = null;
                        } else if (entity instanceof Wall) {
                            entityMap[i][j] = new Wall();
                        } else if (entity instanceof Empty) {
                            entityMap[i][j] = new Empty();
                        } else if (entity instanceof Player) {
                            entityMap[i][j] = new Player(((Player) entity).getId());
                        } else if (entity instanceof Box) {
                            entityMap[i][j] = new Box(((Box) entity).getPlayerId());
                        }
                    }
                }
                // this.destinations = new HashSet<>(destinations);
            } else if (checkPoints.size() == 1) {
                // change everything to initial; undoquotaleft-- , empty checkpoints
                for (int i = 0; i < this.maxHeight; i++) {
                    for (int j = 0; j < this.maxWidth; j++) {
                        var entity = this.initialEntityMap[i][j];
                        if (entity == null) {
                            entityMap[i][j] = null;
                        } else if (entity instanceof Wall) {
                            entityMap[i][j] = new Wall();
                        } else if (entity instanceof Empty) {
                            entityMap[i][j] = new Empty();
                        } else if (entity instanceof Player) {
                            entityMap[i][j] = new Player(((Player) entity).getId());
                        } else if (entity instanceof Box) {
                            entityMap[i][j] = new Box(((Box) entity).getPlayerId());
                        }
                    }
                }
                // this.destinations = new HashSet<>(destinations);
                this.checkPoints.clear();
                if (this.getUndoQuota().isPresent()) {
                    undoQuotaLeft = Optional.of(undoQuotaLeft.get() - 1);
                }
            } else {
                // Copy entrymap, destinations, checkpoints from checkpoint ; undoquotleft --
                GameState checkPoint = this.checkPoints.get(checkPoints.size() - 2);
                for (int i = 0; i < this.maxHeight; i++) {
                    for (int j = 0; j < this.maxWidth; j++) {
                        var entity = checkPoint.getEntity(Position.of(j, i));
                        if (entity == null) {
                            entityMap[i][j] = null;
                        } else if (entity instanceof Wall) {
                            entityMap[i][j] = new Wall();
                        } else if (entity instanceof Empty) {
                            entityMap[i][j] = new Empty();
                        } else if (entity instanceof Player) {
                            entityMap[i][j] = new Player(((Player) entity).getId());
                        } else if (entity instanceof Box) {
                            entityMap[i][j] = new Box(((Box) entity).getPlayerId());
                        }
                    }
                }
                this.destinations = new HashSet<>(checkPoint.getDestinations());
                this.checkPoints = null;
                for (GameState state : checkPoint.checkPoints) {
                    this.checkPoints.add(new GameState(state));
                }
                if (this.getUndoQuota().isPresent()) {
                    undoQuotaLeft = Optional.of(undoQuotaLeft.get() - 1);
                }

            }
        }
        /*
        if (checkPoints.isEmpty() || checkPoints.size() == 1) {
            this.entityMap = this.initialMap;
            this.undoQuotaLeft = this.initialUndoQuotaLeft;
            this.destinations = this.initialDestinations;
        } else if (checkPoints.size() == 1) {
            this.entityMap = this.initialMap;
            if (this.undoQuotaLeft.isPresent()) {
                this.undoQuotaLeft = Optional.of(this.undoQuotaLeft.get() - 1);
            }
            this.destinations = this.initialDestinations;
        } else {

            this.maxHeight = this.checkPoints.get(checkPoints.size() - 2).maxHeight;
            this.maxWidth = this.checkPoints.get(checkPoints.size() - 2).maxWidth;
            this.destinations = this.checkPoints.get(checkPoints.size() - 2).destinations;
        }

         */
        // throw new NotImplementedException();
    }

    /**
     * Get the maximum width of the game map.
     * This should be the same as that in {@link GameMap} class.
     *
     * @return maximum width.
     */
    public int getMapMaxWidth() {
        // TODO
        return this.maxWidth;
        // throw new NotImplementedException();
    }

    /**
     * Get the maximum height of the game map.
     * This should be the same as that in {@link GameMap} class.
     *
     * @return maximum height.
     */
    public int getMapMaxHeight() {
        // TODO
        return this.maxHeight;
        // throw new NotImplementedException();
    }


    public void printEntityMap() {
        System.out.println("---------------------");

        for(int i = 0; i<maxHeight; i++){
            for(int j = 0; j<maxWidth; j++) {
                var entity = entityMap[i][j];
                if (entity instanceof Box) {
                    System.out.print(String.valueOf((char)(((Box) entity).getPlayerId()+'a')));
                } else if (entity instanceof Player) {
                    System.out.print(String.valueOf((char)(((Player) entity).getId()+'A')));
                } else if (entity instanceof Empty) {
                    if(destinations.contains(Position.of(j, i))) {
                        System.out.print("@");
                    } else {
                        System.out.print(".");
                    }
                } else if (entity == null) {
                    System.out.print(" ");
                } else if (entity instanceof Wall) {
                    System.out.print("#");
                }
            }
            System.out.println();
        }
    }
}
