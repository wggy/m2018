package com.wggy.prune.info1113;

import lombok.extern.slf4j.Slf4j;
import processing.core.PApplet;
import processing.core.PImage;
import processing.event.MouseEvent;
import java.util.List;

@Slf4j
public class AtominationGUI extends PApplet {

    private static final int IMAGE_WIDTH = 64;
    private static final int IMAGE_HEIGHT = 64;
    private static final int LINE_X = 15;
    private static final int LINE_Y = 10;
    private static Grid[][] GRIDS = null;
    private static int STEPS = 0;
    private static final int PLAYER_NUMS = 2;
    private static final String[] colors = new String[]{"red", "blue"};
    private static boolean TURN_RED = true;
    private static Player RED_PLAYER = null;
    private static Player BLUE_PLAYER = null;
    private static PImage RED1 = null;
    private static PImage RED2 = null;
    private static PImage BLUE1 = null;
    private static PImage BLUE2 = null;
    private static PImage EMPTY = null;


    public AtominationGUI() {

    }

    @Override
    public void mouseClicked(MouseEvent event) {
        redraw();
    }

    @Override
    public void setup() {
        background(60, 82, 132);
        int w = this.width;
        int h = this.height;
        stroke(245, 255, 250);
        for (int m=0; m<=w;) {
            line(m+1, 1, m+1, h);
            m = m+IMAGE_WIDTH;
        }
        for (int m=0; m<h;) {
            line(1, m+1, w, m+1);
            m = m+IMAGE_HEIGHT;
        }

        initGrids();
        initPlayers();
        blendMode(REPLACE);

        noLoop();
    }

    @Override
    public void settings() {
        /// DO NOT MODIFY SETTINGS
        size(IMAGE_WIDTH * LINE_X + 2, IMAGE_HEIGHT * LINE_Y + 2);
        RED1 = loadImage("E:\\workspace\\m2018\\m2018\\prune\\src\\main\\resources\\assets\\red1.png");
        RED2 = loadImage("E:\\workspace\\m2018\\m2018\\prune\\src\\main\\resources\\assets\\red2.png");
        BLUE1 = loadImage("E:\\workspace\\m2018\\m2018\\prune\\src\\main\\resources\\assets\\blue1.png");
        BLUE2 = loadImage("E:\\workspace\\m2018\\m2018\\prune\\src\\main\\resources\\assets\\blue2.png");
        EMPTY = loadImage("E:\\workspace\\m2018\\m2018\\prune\\src\\main\\resources\\assets\\empty.png");
    }

    @Override
    public void draw() {
        // PImage p = new PImage(Atomination.width, Atomination.height);
        // image(PImage image, int x, int y, int width, int height);
        int x = this.mouseX;
        int y = this.mouseY;
        int grid_x = x / IMAGE_WIDTH;
        int grid_y = y / IMAGE_HEIGHT;
        int locate_x = grid_x * IMAGE_WIDTH;
        int locate_y = grid_y * IMAGE_HEIGHT;

        Grid grid = GRIDS[grid_x][grid_y];
        Player player = grid.getOwner();

        setGrid1(player, grid, locate_x, locate_y, grid_x, grid_y);
    }

    public static void go() {
        AtominationGUI.main("AtominationGUI");
    }

    public static void main(String[] args) {
        AtominationGUI.main(AtominationGUI.class);
    }



    ////////////////////////////////////////////////////////////////////////////////////////////////////////

    void initGrids() {
        GRIDS = new Grid[LINE_Y][];
        for (int i = 0; i < LINE_Y; i++) {
            GRIDS[i] = new Grid[LINE_X];
        }
        //create grid objects
        for (int i = 0; i < LINE_Y; i++) {
            for (int j = 0; j < LINE_X; j++) {
                GRIDS[i][j] = new Grid(null, 0);
                image(EMPTY, i * IMAGE_HEIGHT, j * IMAGE_WIDTH);
            }
        }
    }

    void initPlayers() {
        RED_PLAYER = new Player(colors[0], 0);
        BLUE_PLAYER = new Player(colors[1], 0);
    }

    void setGrid(Grid grid, int locate_x, int locate_y, int grid_x, int grid_y) {
        grid.inc();
        int gridNum = grid.getAtomCount();
        if (gridNum == 1) {
            PImage newImage = TURN_RED ? RED1 : BLUE1;
            image(newImage, locate_x, locate_y);
        } else if (gridNum == 2) {
            PImage newImage = TURN_RED ? RED2 : BLUE2;
            PImage oldImage = TURN_RED ? RED1 : BLUE1;
            oldImage = null;
            image(newImage, locate_x, locate_y);
        } else {
            log.info("该格子【{}】【{}】将达到3个点，需要爆炸", grid_x, grid_y);
            image(EMPTY, locate_x, locate_y);
            grid.revert();
        }
    }

    void setGrid1(Player player, Grid grid, int locate_x, int locate_y, int grid_x, int grid_y) {
        // 该格子未被占据
        if (player == null) {
            if (TURN_RED) {
                grid.setOwner(RED_PLAYER);
                // 计算红方的棋子个数
                RED_PLAYER.inc();
            } else {
                grid.setOwner(BLUE_PLAYER);
                // 计算红方的棋子个数
                BLUE_PLAYER.inc();
            }
            // 计算格子的棋子个数
            setGrid(grid, locate_x, locate_y, grid_x, grid_y);
        }
        // 该格子被红方占有
        else if (colors[0].equalsIgnoreCase(player.getColour())) {
            if (TURN_RED) {
                setGrid(grid, locate_x, locate_y, grid_x, grid_y);
            } else {
                log.error("蓝方：该格子【{}】【{}】是红方的，你不能设点", grid_x, grid_y);
            }
        }
        // 该格子被蓝方占有
        else if (colors[1].equalsIgnoreCase(player.getColour())) {
            if (TURN_RED) {
                log.error("红方：该格子【{}】【{}】是蓝方的，你不能设点", grid_x, grid_y);
            } else {
                setGrid(grid, locate_x, locate_y, grid_x, grid_y);
            }
        }
        // 该格子即没有被任何一方占有，也不属于红蓝双方，则异常
        else {
            log.error("该格子【{}】【{}】异常", grid_x, grid_y);
        }
        TURN_RED = !TURN_RED;
    }

//    PImage getImage(int idx) {
//        if (idx == -1) {
//            return loadImage("E:\\workspace\\m2018\\m2018\\prune\\src\\main\\resources\\assets\\green1.png");
//        }
//        if (TURN_RED) {
//            return loadImage("E:\\workspace\\m2018\\m2018\\prune\\src\\main\\resources\\assets\\red" + idx + ".png");
//        } else {
//            return loadImage("E:\\workspace\\m2018\\m2018\\prune\\src\\main\\resources\\assets\\blue" + idx + ".png");
//        }
//    }
}
