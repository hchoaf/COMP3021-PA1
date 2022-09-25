package hk.ust.comp3021.game;

import hk.ust.comp3021.utils.TestHelper;
import hk.ust.comp3021.utils.TestKind;
import hk.ust.comp3021.utils.TestMaps;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OwnGameMapTest {


    @Tag(TestKind.PUBLIC)
    @Test
    void testMultiplePlayers() {
        TestHelper testHelper = new TestHelper();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> testHelper.parseGameMap(TestMaps.multiplePlayersMap));
        assertTrue(thrown.getMessage().contains("There are multiple same upper-case letters. One player can only exist at one position."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testMultiplePlayersTwo() {
        TestHelper testHelper = new TestHelper();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> testHelper.parseGameMap(TestMaps.multiplePlayersMapTwo));
        assertTrue(thrown.getMessage().contains("There are multiple same upper-case letters. One player can only exist at one position."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testPlayerWithNoBox() {
        TestHelper testHelper = new TestHelper();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> testHelper.parseGameMap(TestMaps.playerWithNoBoxMap));
        assertTrue(thrown.getMessage().contains("Either there is box with no player or player with no box."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testBoxWithNoPlayer() {
        TestHelper testHelper = new TestHelper();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> testHelper.parseGameMap(TestMaps.boxWithNoPlayerMap));
        assertTrue(thrown.getMessage().contains("Either there is box with no player or player with no box."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testDifferentBoxAndDestination() {
        TestHelper testHelper = new TestHelper();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> testHelper.parseGameMap(TestMaps.differentBoxAndDestinationMap));
        assertTrue(thrown.getMessage().contains("The number of boxes is not equal to the number of destinations."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testDifferentBoxAndDestinationTwo() {
        TestHelper testHelper = new TestHelper();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> testHelper.parseGameMap(TestMaps.differentBoxAndDestinationMapTwo));
        assertTrue(thrown.getMessage().contains("The number of boxes is not equal to the number of destinations."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testNoBox() {
        TestHelper testHelper = new TestHelper();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> testHelper.parseGameMap(TestMaps.noBoxMap));
        assertTrue(thrown.getMessage().contains("The number of boxes is not equal to the number of destinations."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testNoDestination() {
        TestHelper testHelper = new TestHelper();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> testHelper.parseGameMap(TestMaps.noDestinationMap));
        assertTrue(thrown.getMessage().contains("The number of boxes is not equal to the number of destinations."));
    }

    @Tag(TestKind.PUBLIC)
    @Test
    void testNoPlayer() {
        TestHelper testHelper = new TestHelper();
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () -> testHelper.parseGameMap(TestMaps.noPlayerMap));
        assertTrue(thrown.getMessage().contains("There is no player in the map."));
    }
}
