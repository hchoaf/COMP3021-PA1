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

    private static Entity[][] entityMap;

    // private static List<List<Entity>> entityMap = new ArrayList<>();
    private static List<String> initialCharMap = new ArrayList<String>();
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

        List<String> arr = new ArrayList<String>(Arrays.asList(mapText.split("\n")));
        maxHeight = arr.size() - 1;
        undoLimit = Integer.parseInt(arr.get(0));
        arr.remove(0);

        for(int i = 0; i<arr.size(); i++){
            System.out.println(arr.get(i));
            maxWidth = Math.max(maxWidth, arr.get(i).length());
        }
        entityMap = new Entity[maxHeight][maxWidth];

        for(int i = 0; i<arr.size(); i++){
            for(int j = 0; j<maxWidth; j++){
                char block = (j >= arr.get(i).length()) ? ' ' : arr.get(i).charAt(j);
                switch(block) {
                    case '#':
                        entityMap[i][j] = new Wall();
                        break;
                    case '.':
                        entityMap[i][j] = new Empty();
                        break;
                    case ' ':
                        entityMap[i][j] = null;
                        break;
                    case '@':
                        entityMap[i][j] = new Empty();
                        destinations.add(new Position(j, i));
                        break;
                    default:
                        int playerId = returnIdOfAlphabet(block);
                        if (Character.isUpperCase(block)) {
                            if (!playerIds.contains(playerId)) {
                                entityMap[i][j] = new Player(playerId);
                                playerIds.add(playerId);
                            } else {
                                throw new IllegalArgumentException("There are multiple same upper-case letters. One player can only exist at one position.");
                            }
                        } else {
                            if(!boxIds.contains(playerId)) {
                                boxIds.add(playerId);
                            }
                            entityMap[i][j] = new Box(playerId);
                            boxNum++;
                        }

                }
            }
        }
        /*
        for(int i = 0; i<arr.size(); i++) {
            initialCharMap.add(arr.get(i));
            maxWidth = Math.max(maxWidth, arr.get(i).length());
            for(int j = 0; j<arr.get(i).length(); j++) {
                entityMap.add(new ArrayList<>());
                char block = arr.get(i).charAt(j);
                switch(block) {
                    case '#':
                        entityMap.get(i).add(new Wall());
                        break;
                    case '.':
                        entityMap.get(i).add(new Empty());
                        break;
                    case ' ':
                        entityMap.get(i).add(null);
                        break;
                    case '@':
                        entityMap.get(i).add(new Empty());
                        destinations.add(new Position(j, i));
                        break;
                    default:
                        if (Character.isUpperCase(block)) {
                            if (!playerIds.contains(returnIdOfAlphabet(block))) {
                                entityMap.get(i).add(new Player(returnIdOfAlphabet(block)));
                                playerIds.add(returnIdOfAlphabet(block));
                            } else {
                                throw new IllegalArgumentException("There are multiple same upper-case letters. One player can only exist at one position.");
                            }
                        } else {
                            if (!boxIds.contains(returnIdOfAlphabet(block))) {
                                boxIds.add(returnIdOfAlphabet(block));
                            }

                            entityMap.get(i).add(new Box(returnIdOfAlphabet(block)));
                            boxNum++;
                        }
                }
            }
        }
         */

        System.out.println("-----------------------------");
        System.out.printf("MaxHeight: %d \n", maxHeight);
        System.out.printf("MaxWidth: %d \n", maxWidth);
        System.out.printf("UndoLimit: %d \n", undoLimit);
        System.out.printf("boxNum: %d \n", boxNum);
        System.out.printf("PlayerIds: %s \n", playerIds.toString());
        System.out.printf("boxIds: %s \n", boxIds.toString());
        System.out.printf("bombPositions: %s \n", destinations.toString());

        System.out.println("=====================================");
        printEntityMap();
        if(playerIds.isEmpty()){
            throw new IllegalArgumentException("There is no player in the map.");
        }
        if(boxNum != destinations.size()){
            throw new IllegalArgumentException("The number of boxes is not equal to the number of destinations.");
        }
        if(!boxIds.containsAll(playerIds)) {
            throw new IllegalArgumentException("Either there is box with no player or player with no box.");
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
            throw new IllegalArgumentException("Position out of bound.");
        } else {
            return entityMap[position.y()][position.x()];
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
        entityMap[position.y()][position.x()] = entity;
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
        return new HashSet<Integer>(playerIds);
        // throw new NotImplementedException();
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

    private static int returnIdOfAlphabet(char c) {
        return (Character.isUpperCase(c)) ? c - 'A' : c - 'a';
    }

    private static void printEntityMap() {

        for(int i = 0; i<maxHeight; i++){
            for(int j = 0; j<maxWidth; j++) {
                var entity = entityMap[i][j];
                if (entity instanceof Box) {
                    System.out.print(String.valueOf((char)(((Box) entity).getPlayerId()+'a')));
                } else if (entity instanceof Player) {
                    System.out.print(String.valueOf((char)(((Player) entity).getId()+'A')));
                } else if (entity instanceof Empty) {
                    if(destinations.contains(new Position(j, i))) {
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
