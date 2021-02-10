package com.liner;

import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class RPGGame {
    private HashMap<Integer, String> mapIcons;
    private int[][] map;

    public RPGGame(int mapSize) {
        mapIcons = new HashMap<>();
        mapIcons.put(0, "⬜"); //Ground
        mapIcons.put(1, "⬛"); // Wall
        mapIcons.put(2, "\uD83D\uDFE6"); //Water
        mapIcons.put(3, "\uD83C\uDF33"); //Tree
        mapIcons.put(4, "\uD83D\uDCCD"); //Player
//        map = new int[][]{
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
//                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
//        };
        createMap(mapSize);
        drawMap();
        System.out.println(drawMap());
        System.out.println(aroundHaveTile(4, 4, 2, 3));
    }

    public String drawMap() {
        StringBuilder stringBuilder = new StringBuilder();
        iterateMap(new MapIterator() {
            @Override
            public void onLocation(int x, int y) {
                stringBuilder.append(mapIcons.get(map[x][y]));
                if (y == map.length - 1)
                    stringBuilder.append("\n");
            }
        });
        return stringBuilder.toString();
    }

    private void createMap(int size) {
        map = new int[size][size];
        iterateMap((x, y) -> {
            if ((x == 0 || x == map.length - 1) || y == 0 || y == map.length - 1) {
                map[x][y] = 1;
            } else {
                createTile(x, y);
            }
        });
    }

    private void createTile(int x, int y) {
        int existsTile = map[x][y];
        int newTile = new Random().nextInt(5);
        if (existsTile != 1 && newTile != 1 && newTile != 4) {
            map[x][y] = newTile;
        }
        if (!containPlayer())
            if (newTile == 4) {
                if (map[x][y] == 0) {
                    map[x][y] = 4;
                }
            }
    }

    private boolean containPlayer() {
        AtomicBoolean result = new AtomicBoolean(false);
        iterateMap((x, y) -> {
            result.set(map[x][y] == 4);
        });
        return result.get();
    }

    private boolean aroundHaveTile(int x, int y, int tile, int count) {
        int countTiles = 0;
        if ((x > 1 && x < map.length - 1) && (y > 1 && y < map.length - 1)) {
            for (int xO = x - 1; xO < x + 2; xO++) {
                for (int yO = y - 1; yO < x + 2; yO++) {
                    int tileValue = map[xO][yO];
                    if(tileValue == tile)
                        countTiles++;
                }
            }
            return countTiles == count;
        } else {
            return false;
        }
    }

    private void iterateMap(MapIterator callback) {
        for (int x = 0; x < map.length; x++) {
            for (int y = 0; y < map.length; y++) {
                callback.onLocation(x, y);
            }
        }
    }

    private interface MapIterator {
        void onLocation(int x, int y);
    }
}
