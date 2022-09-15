package hk.ust.comp3021.game;

import hk.ust.comp3021.entities.*;
import hk.ust.comp3021.utils.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.stream.IntStream;

/**
 * A Sokoban game board.
 * GameBoard consists of information loaded from map data, such as
 * <li>Width and height of the game map</li>
 * <li>Walls in the map</li>
 * <li>Box destinations</li>
 * <li>Initial locations of boxes and player</li>
 * <p/>
 * GameBoard is capable to create many GameState instances, each representing an ongoing game.
 */
public class GameMap {
    private static int maxWidth;
    private static int maxHeight;
    private static Set<Position> destinations = new HashSet<Position>();
    private static int undoLimit;

    private static List<String> initialMap = new ArrayList<String>();
    private static List<Integer> playerIds = new ArrayList<Integer>();

    private static int boxNum;

    private static List<Integer> boxIds = new ArrayList<Integer>();


    /**
     * Create a new GameMap with width, height, set of box destinations and undo limit.
     *
     * @param maxWidth     Width of the game map.
     * @param maxHeight    Height of the game map.
     * @param destinations Set of box destination positions.
     * @param undoLimit    Undo limit.
     *                     Positive numbers specify the maximum number of undo actions.
     *                     0 means undo is not allowed.
     *                     -1 means unlimited. Other negative numbers are not allowed.
     */
    public GameMap(int maxWidth, int maxHeight, Set<Position> destinations, int undoLimit) {
        // TODO
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.destinations = destinations;
        this.undoLimit = undoLimit;
        // throw new NotImplementedException();
    }

    /**
     * Parses the map from a string representation.
     * The first line is undo limit.
     * Starting from the second line, the game map is represented as follows,
     * <li># represents a {@link Wall}</li>
     * <li>@ represents a box destination.</li>
     * <li>Any upper-case letter represents a {@link Player}.</li>
     * <li>
     * Any lower-case letter represents a {@link Box} that is only movable by the player with the corresponding upper-case letter.
     * For instance, box "a" can only be moved by player "A" and not movable by player "B".
     * </li>
     * <li>. represents an {@link Empty} position in the map, meaning there is no player or box currently at this position.</li>
     * <p>
     * Notes:
     * <li>
     * There can be at most 26 players.
     * All implementations of classes in the hk.ust.comp3021.game package should support up to 26 players.
     * </li>
     * <li>
     * For simplicity, we assume the given map is bounded with a closed boundary.
     * There is no need to check this point.
     * </li>
     * <li>
     * Example maps can be found in "src/main/resources".
     * </li>
     *
     * @param mapText The string representation.
     * @return The parsed GameMap object.
     * @throws IllegalArgumentException if undo limit is negative but not -1.
     * @throws IllegalArgumentException if there are multiple same upper-case letters, i.e., one player can only exist at one position.
     * @throws IllegalArgumentException if there are no players in the map.
     * @throws IllegalArgumentException if the number of boxes is not equal to the number of box destinations.
     * @throws IllegalArgumentException if there are boxes whose {@link Box#getPlayerId()} do not match any player on the game board,
     *                                  or if there are players that have no corresponding boxes.
     */
    public static GameMap parse(String mapText) {
        // TODO
        String[] arr = mapText.split("\n");
        maxHeight = arr.length - 1;
        for(int i = 0; i<arr.length; i++) {
            if (i == 0) {
                undoLimit = Integer.parseInt(arr[i]);
                continue;
            }
            initialMap.add(arr[i]);
            if (i == 1) {
                maxWidth = arr[i].length();
            }
            for(int j = 0; j<arr[i].length(); j++) {
                char block = arr[i].charAt(j);
                switch(block) {
                    case '#':
                        break;
                    case '.':
                        break;
                    case '@':
                        destinations.add(new Position(j, i));
                        break;
                    default:
                        if (Character.isUpperCase(block)) {
                            if (!playerIds.contains(returnIdOfAlphabet(block))) {
                                playerIds.add(returnIdOfAlphabet(block));
                            }
                        } else {
                            if (!boxIds.contains(returnIdOfAlphabet(block))) {
                                boxIds.add(returnIdOfAlphabet(block));
                            }
                            boxNum++;
                        }
                }
            }
        }
        System.out.println("-----------------------------");
        System.out.printf("MaxHeight: %d \n", maxHeight);
        System.out.printf("MaxWidth: %d \n", maxWidth);
        System.out.printf("UndoLimit: %d \n", undoLimit);
        System.out.printf("boxNum: %d \n", boxNum);
        System.out.printf("PlayerIds: %s \n", playerIds.toString());
        System.out.printf("boxIds: %s \n", boxIds.toString());
        System.out.printf("bombPositions: %s \n", destinations.toString());
        for(int i = 0; i<maxHeight; i++) {
           System.out.println(initialMap.get(i));
        }
        return new GameMap(maxWidth, maxHeight, destinations, undoLimit);
        // throw new NotImplementedException();
    }

    /**
     * Get the entity object at the given position.
     *
     * @param position the position of the entity in the game map.
     * @return Entity object.
     */
    @Nullable
    public Entity getEntity(Position position) {
        if(this.maxWidth<position.x() || this.maxHeight< position.y()){
            return null;
        } else {
            char block = initialMap.get(position.y()).charAt(position.x());
            switch (block) {
                case '#':
                    return new Wall();
                case '.':
                    return new Empty();
                case '@':
                    return new Empty();
                default:
                    if (Character.isUpperCase(block)) {
                        return new Player(returnIdOfAlphabet(block));
                    } else {
                        return new Box(returnIdOfAlphabet(block));
                    }
            }
        }

        // TODO
        // throw new NotImplementedException();
    }

    /**
     * Put one entity at the given position in the game map.
     *
     * @param position the position in the game map to put the entity.
     * @param entity   the entity to put into game map.
     */
    public void putEntity(Position position, Entity entity) {
        // TODO
        throw new NotImplementedException();
    }

    /**
     * Get all box destination positions as a set in the game map.
     *
     * @return a set of positions.
     */
    public @NotNull @Unmodifiable Set<Position> getDestinations() {
        // TODO
        return this.destinations;
    }

    /**
     * Get the undo limit of the game map.
     *
     * @return undo limit.
     */
    public Optional<Integer> getUndoLimit() {
        // TODO
        Optional<Integer> optionalUndoLimit = Optional.of(this.undoLimit);
        return optionalUndoLimit;
    }

    /**
     * Get all players' id as a set.
     *
     * @return a set of player id.
     */
    public Set<Integer> getPlayerIds() {
        // TODO
        throw new NotImplementedException();
    }

    /**
     * Get the maximum width of the game map.
     *
     * @return maximum width.
     */
    public int getMaxWidth() {
        // TODO
        return this.maxWidth;
    }

    /**
     * Get the maximum height of the game map.
     *
     * @return maximum height.
     */
    public int getMaxHeight() {
        // TODO
        return this.maxHeight;

    }

    public static int returnIdOfAlphabet(char c) {
        return (Character.isUpperCase(c)) ? c - 'A' : c - 'a';
    }
}
