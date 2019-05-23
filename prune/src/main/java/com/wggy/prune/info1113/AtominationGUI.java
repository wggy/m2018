package com.wggy.prune.info1113;

import lombok.extern.slf4j.Slf4j;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;

@Slf4j
public class AtominationGUI extends PApplet {

    private static final int IMAGE_WIDTH = 64;
    private static final int IMAGE_HEIGHT = 64;
    private static final int GRID_X = 10;
    private static final int GRID_Y = 7;
    private static final int STOKE_WEIGHT = 1;
    private static Grid[][] GRIDS = null;
    private static int STEPS = -1;
    private static int RED_NUMS = 0;
    private static int BLUE_NUMS = 0;
    private static final String[] colors = new String[]{"red", "blue"};
    private static boolean TURN_RED = true;
    private static Player RED_PLAYER = null;
    private static Player BLUE_PLAYER = null;
    private static PImage RED1 = null;
    private static PImage RED2 = null;
    private static PImage RED3 = null;
    private static PImage BLUE1 = null;
    private static PImage BLUE2 = null;
    private static PImage BLUE3 = null;
    private static PImage EMPTY = null;
    private static final String IMAGE_PREFIX = AtominationGUI.class.getClassLoader().getResource("").getPath() + "assets/";

    private static class Padding {
        static int top = 10;
        static int left = 10;
        static int right = 10;
        static int bottom = 10;
    }

    public AtominationGUI() {

    }

    @Override
    public void mouseClicked(MouseEvent event) {
        redraw();
    }

    @Override
    public void setup() {
        background(60, 82, 132);
        stroke(245, 255, 250);
        strokeWeight(STOKE_WEIGHT);
        for (int m = Padding.left, n = 0; m <= this.width && n <= GRID_X; n++) {
            line(m, Padding.top, m, this.height - STOKE_WEIGHT - Padding.bottom);
            m = m + IMAGE_WIDTH + STOKE_WEIGHT;
        }
        for (int m = Padding.top, n = 0; m < this.height && n <= GRID_Y; n++) {
            line(Padding.left, m, this.width - STOKE_WEIGHT - Padding.right, m);
            m = m + IMAGE_HEIGHT + STOKE_WEIGHT;
        }

        initGrids();
        initPlayers();
        noLoop();
    }

    @Override
    public void settings() {
        /// DO NOT MODIFY SETTINGS
        int frameWidth = Padding.left + IMAGE_WIDTH * GRID_X + STOKE_WEIGHT * (GRID_X + 1) + Padding.right;
        int frameHeight = Padding.top + IMAGE_HEIGHT * GRID_Y + STOKE_WEIGHT * (GRID_Y + 1) + Padding.bottom;
        size(frameWidth, frameHeight);
        RED1 = loadImage(IMAGE_PREFIX + "red1.png");
        RED2 = loadImage(IMAGE_PREFIX + "red2.png");
        RED3 = loadImage(IMAGE_PREFIX + "red3.png");
        BLUE1 = loadImage(IMAGE_PREFIX + "blue1.png");
        BLUE2 = loadImage(IMAGE_PREFIX + "blue2.png");
        BLUE3 = loadImage(IMAGE_PREFIX + "blue3.png");
        EMPTY = loadImage(IMAGE_PREFIX + "empty.png");
    }

    @Override
    public void draw() {
        // PImage p = new PImage(Atomination.width, Atomination.height);
        // image(PImage image, int x, int y, int width, int height);

        if (STEPS++ == -1) {
            return;
        }
        int x = this.mouseX;
        int y = this.mouseY;
        if (Padding.left > this.mouseX || this.mouseX > (this.width - Padding.right)) {
            log.error("横坐标不在表格区域");
            STEPS--;
            return;
        }
        if (Padding.top > this.mouseY || this.mouseY > (this.height - Padding.bottom)) {
            log.error("竖坐标不在表格区域");
            STEPS--;
            return;
        }
        int grid_x = (x - Padding.left) / (IMAGE_WIDTH + STOKE_WEIGHT);
        int grid_y = (y - Padding.top) / (IMAGE_HEIGHT + STOKE_WEIGHT);
        int locate_x = grid_x * IMAGE_WIDTH + STOKE_WEIGHT * (grid_x + 1) + Padding.left;
        int locate_y = grid_y * IMAGE_HEIGHT + STOKE_WEIGHT * (grid_y + 1) + Padding.top;

        Grid grid = GRIDS[grid_x][grid_y];
        Player player = grid.getOwner();
        petGrid(player, grid, locate_x, locate_y, grid_x, grid_y);
        log.info("Steps: {}", STEPS);
        stat();
        log.info("Red: {}, Blue: {}", RED_NUMS, BLUE_NUMS);
        if (STEPS == RED_NUMS) {
            log.info("Red wins !!!");
        } else if (STEPS == BLUE_NUMS) {
            log.info("Blue wins !!!");
        }
    }

    public static void go() {
        AtominationGUI.main("AtominationGUI");
    }

    public static void main(String[] args) {
        AtominationGUI.main(AtominationGUI.class);
    }


    //////////////////////////////////////////以下自定义方法///////////////////////////////////////////////////////////

    void initGrids() {
        GRIDS = new Grid[GRID_X][];
        for (int i = 0; i < GRID_X; i++) {
            GRIDS[i] = new Grid[GRID_Y];
        }
        //create grid objects
        for (int i = 0; i < GRID_X; i++) {
            for (int j = 0; j < GRID_Y; j++) {
                GRIDS[i][j] = new Grid(null, 0);
                int locateX = Padding.left + (i + 1) * STOKE_WEIGHT + i * IMAGE_WIDTH;
                int locateY = Padding.top + (j + 1) * STOKE_WEIGHT + j * IMAGE_HEIGHT;
                image(EMPTY, locateX, locateY);
            }
        }
    }

    void initPlayers() {
        RED_PLAYER = new Player(colors[0], 0);
        BLUE_PLAYER = new Player(colors[1], 0);
    }

    void petGrid(Player player, Grid grid, int locate_x, int locate_y, int grid_x, int grid_y) {
        // 该格子未被占据
        if (player == null) {
            place(grid_x, grid_y);
        }
        // 该格子被红方占有
        else if (colors[0].equalsIgnoreCase(player.getColour())) {
            if (TURN_RED) {
                place(grid_x, grid_y);
            } else {
                STEPS--;
                log.error("蓝方：该格子【{}】【{}】是红方的，你不能设点", grid_x, grid_y);
                return;
            }
        }
        // 该格子被蓝方占有
        else if (colors[1].equalsIgnoreCase(player.getColour())) {
            if (TURN_RED) {
                STEPS--;
                log.error("红方：该格子【{}】【{}】是蓝方的，你不能设点", grid_x, grid_y);
                return;
            } else {
                place(grid_x, grid_y);
            }
        }
        // 该格子即没有被任何一方占有，也不属于红蓝双方，则异常
        else {
            STEPS--;
            log.error("该格子【{}】【{}】异常", grid_x, grid_y);
            return;
        }
        TURN_RED = !TURN_RED;
    }

    void place(int grid_x, int grid_y) {
        // 递推退出条件
        if (grid_x < 0 || grid_x >= GRID_X || grid_y < 0 || grid_y >= GRID_Y) {
            return;
        }
        Grid grid = GRIDS[grid_x][grid_y];
        grid.setOwner(TURN_RED ? RED_PLAYER : BLUE_PLAYER);
        int locate_x = grid_x * IMAGE_WIDTH + STOKE_WEIGHT * (grid_x + 1) + Padding.left;
        int locate_y = grid_y * IMAGE_HEIGHT + STOKE_WEIGHT * (grid_y + 1) + Padding.top;

        // 放在棋盘的四个角
        if ((grid_x == 0 && grid_y == 0)
                || (grid_x == GRID_X - 1 && grid_y == 0)
                || (grid_x == 0 && grid_y == GRID_Y - 1)
                || (grid_x == GRID_X - 1 && grid_y == GRID_Y - 1)) {
            if (grid.getAtomCount() == 0) {
                PImage newImage = TURN_RED ? RED1 : BLUE1;
                image(newImage, locate_x, locate_y);
                grid.setAtomCount(1);
            } else {
                revertToBg(locate_x, locate_y);
                boom(grid, grid_x, grid_y);
            }
        }
        // 放在棋盘的四条边
        else if (grid_x == 0 || grid_y == 0 || grid_x == GRID_X - 1 || grid_y == GRID_Y - 1) {
            revertToBg(locate_x, locate_y);
            if (grid.getAtomCount() == 0) {
                PImage newImage = TURN_RED ? RED1 : BLUE1;
                image(newImage, locate_x, locate_y);
                grid.inc();
            } else if (grid.getAtomCount() == 1) {
                PImage newImage = TURN_RED ? RED2 : BLUE2;
                image(newImage, locate_x, locate_y);
                grid.inc();
            } else {
                revertToBg(locate_x, locate_y);
                boom(grid, grid_x, grid_y);
            }
        }
        // 放在棋盘中间位置
        else {
            revertToBg(locate_x, locate_y);
            if (grid.getAtomCount() == 0) {
                PImage newImage = TURN_RED ? RED1 : BLUE1;
                image(newImage, locate_x, locate_y);
                grid.inc();
            } else if (grid.getAtomCount() == 1) {
                PImage newImage = TURN_RED ? RED2 : BLUE2;
                image(newImage, locate_x, locate_y);
                grid.inc();
            } else if (grid.getAtomCount() == 2) {
                PImage newImage = TURN_RED ? RED3 : BLUE3;
                image(newImage, locate_x, locate_y);
                grid.inc();
            } else {
                revertToBg(locate_x, locate_y);
                boom(grid, grid_x, grid_y);
            }
        }
    }

    void boom(Grid grid, int grid_x, int grid_y) {
        grid.revert();
        place(grid_x - 1, grid_y);
        place(grid_x, grid_y - 1);
        place(grid_x + 1, grid_y);
        place(grid_x, grid_y + 1);
    }

    void revertToBg(int locate_x, int locate_y) {
        PImage oldImage = get(locate_x, locate_y, IMAGE_WIDTH, IMAGE_HEIGHT);
        for (int i = 0; i < oldImage.pixels.length; i++) {
            oldImage.pixels[i] = color(60, 82, 132, 255);
        }
        image(oldImage, locate_x, locate_y);
    }

    void stat() {
        RED_NUMS = 0;
        BLUE_NUMS = 0;
        for (int i = 0; i < GRID_X; i++) {
            for (int j = 0; j < GRID_Y; j++) {
                Grid grid = GRIDS[i][j];
                if (grid.getOwner() == null) {

                } else {
                    if (grid.getOwner().getColour().equalsIgnoreCase(colors[0])) {
                        RED_NUMS = RED_NUMS + grid.getAtomCount();
                    } else {
                        BLUE_NUMS = BLUE_NUMS + grid.getAtomCount();
                    }
                }
            }
        }
    }
}
