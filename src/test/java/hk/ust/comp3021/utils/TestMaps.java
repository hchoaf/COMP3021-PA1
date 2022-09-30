package hk.ust.comp3021.utils;

public class TestMaps {
    public static final String correctMapOne = """
        15
         #####
         #...#
        #...#
        #a@A#
        #####
            """;

    public static final String correctMapTwo = """
        15
        #####  ####
        #...###...#
        #.........#
        #a@A###...#
        ##### #####
            """;
    public static final String correctMapThree =  """
        233
        ######
        #A..@#
        #...@###
        #a....@##
        #.a.....#
        #..a.####
        ######
            """;
    public static final String lotsOfPlayersMap = """
        26
        ############################
        #ABCDEFGHIJKLMNOPQRSTUVWXYZ#
        #abcdefghijklmnopqrstuvwxyz#
        #@@@@@@@@@@@@@@@@@@@@@@@@@@#
        #..........................#
        ############################
            """;
    public static final String unlimitedUndoQuotaMap = """
        -1
        #####
        #...#
        #..A#
        #.a@#
        #####
            """;

    public static final String invalidUndoQuotaMapOne = """
        -2
        #####
        #...#
        #..A#
        #.a@#
        #####
            """;

    public static final String invalidUndoQuotaMapTwo = """
        ab
        #####
        #...#
        #..A#
        #.a@#
        #####
            """;
    public static final String multiplePlayersMap = """
        5
        #####
        #A..#
        #..A#
        #.a@#
        #####
            """;
    public static final String multiplePlayersMapTwo = """
        5
        #####
        #A.@#
        #.B@#
        #baB#
        #####
            """;
    public static final String noPlayerMap = """
        5
        #####
        #..@#
        #...#
        #a..#
        #####
            """;
    public static final String playerWithNoBoxMap = """
        5
        #####
        #A..#
        #..B#
        #@a.#
        #####
            """;
    public static final String boxWithNoPlayerMap = """
        5
        #####
        #A..#
        #.b@#
        #@a.#
        #####
            """;
    public static final String differentBoxAndDestinationMap = """
        5
        #####
        #A..#
        #..@#
        #@a.#
        #####
            """;
    public static final String differentBoxAndDestinationMapTwo = """
        5
        #####
        #A..#
        #..@#
        #aa.#
        #####
            """;
    public static final String noBoxMap = """
        5
        #####
        #A..#
        #..@#
        #.@.#
        #####
            """;
    public static final String noDestinationMap = """
        5
        #####
        #A..#
        #..a#
        #.a.#
        #####
            """;
}
