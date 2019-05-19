package com.wggy.prune.info1113;

import java.util.*;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;

public class Atomination {

    //initialize some variables
    public static final String[] colours = {"Red", "Green", "Purple", "Blue"};
    public static List<Player> players = new ArrayList<>();
    public static List<ArrayList<Byte>> move = new ArrayList<>();
    public static List<Grid[][]> state = new ArrayList<>();
    public static int index = 0;
    public static int pointer = -1;
    public static byte width = -1;
    public static byte height = -1;
    public static boolean startSuccess = false;
    public static boolean placeSuccess = false;
    public static Grid[][] grids = null;
    public static boolean gameOver = false;
    public static boolean allTurn = false;
    public static boolean unDo = false;


    //displays this help message
    public static void help() {
        System.out.println("HELP\tdisplays this help message\n" +
                "QUIT\tquits the current game\n" + "\n" +
                "DISPLAY\tdraws the game board in terminal\n" +
                "START\t<number of players> <width> <height> starts the game\n" +
                "PLACE\t<x> <y> places an atom in a grid space\n" +
                "UNDO\tundoes the last move made\n" +
                "STAT\tdisplays game statistics\n" +
                "SAVE\t<filename> saves the state of the game\n" +
                "LOAD\t<filename> loads a save file\n");
    }

    //initialize the game property
    public static String start(String[] array) {
        if (array.length < 3) {
            return "Missing Argument\n";
        } else if (array.length > 3) {
            return "Too Many Arguments\n";
        } else {
            for (int i = 0; i < array.length; i++) {
                try {
                    int arg = Integer.parseInt(array[i]);
                    if (arg < 0) {
                        return "Invalid command arguments\n";
                    }
                } catch (Exception e) {
                    return "Invalid command arguments\n";
                }
            }
            startSuccess = true;
            width = (byte) Integer.parseInt(array[1]);
            height = (byte) Integer.parseInt(array[2]);

            //create Player objects
            for (int i = 0; i < (byte) Integer.parseInt(array[0]); i++) {
                players.add(new Player(colours[i], 0));
            }
            //create null grids
            Grid[][] doublearray = new Grid[height][];
            for (int i = 0; i < height; i++) {
                doublearray[i] = new Grid[width];
            }
            //create grid objects
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    doublearray[i][j] = new Grid(null, 0);
                }
            }
            grids = doublearray;
            pointer = index % players.size();
            String s = "Game Ready\n" + players.get(pointer).getColour() + "'s Turn\n";
            return s;
        }
    }

    // quits the current game
    public static void quit() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grids[i][j].setOwner(null);
                grids[i][j].setAtomCount(0);
            }
        }
        System.out.println("Bye!");
    }

    //Displays the current state of the game
    public static void stat() {
        statL();
        for (Player player : players) {
            System.out.println("Player " + player.getColour() + ":");
            if (player.getGridsOwned() == 0 && allTurn && !unDo) {
                System.out.println("Lost\n");
                unDo = false;
            } else {
                System.out.println("Grid Count: " + player.getGridsOwned() + "\n");
            }

        }
    }

    private static void statL() {
        int red = 0, green = 0, purple = 0, bule = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grids[i][j].getOwner() == null || grids[i][j].getAtomCount() == 0) {

                } else {
                    if ("Red".endsWith(grids[i][j].getOwner().getColour())) {
                        // red = red + grids[i][j].getAtomCount();
                        red++;
                    } else if ("Green".endsWith(grids[i][j].getOwner().getColour())) {
                        // green = green + grids[i][j].getAtomCount();
                        green++;
                    } else if ("Purple".endsWith(grids[i][j].getOwner().getColour())) {
                        // purple = purple + grids[i][j].getAtomCount();
                        purple++;
                    } else if ("Blue".endsWith(grids[i][j].getOwner().getColour())) {
                        // bule = bule + grids[i][j].getAtomCount();
                        bule++;
                    }

                }
            }
        }
        for (Player player : players) {
            if (player.getColour().endsWith("Red")) {
                player.setGridsOwned(red);
            } else if (player.getColour().endsWith("Green")) {
                player.setGridsOwned(green);
            } else if (player.getColour().endsWith("Purple")) {
                player.setGridsOwned(purple);
            } else if (player.getColour().endsWith("Blue")) {
                player.setGridsOwned(bule);
            }
        }
    }

    public static void undo() {
        if (state.size() <= 1) {
            System.out.println("Cannot Undo");
        } else {
            state.remove(state.size() - 1);
            Grid[][] tem = state.get(state.size() - 1);
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    grids[i][j] = tem[i][j];
                }
            }
            index--;
            pointer = (pointer + players.size() - 1) % players.size();
            System.out.println(players.get(pointer).getColour() + "'s Turn\n");
            unDo = true;
        }
        // display();
    }


    //Places an atom at an x, y position on the game board that is associated with the player's turn
    public static void place(int x, int y, boolean isFirst) {
        int tmpAtomCount = 0;
        if (isFirst) {
            if (x < 0 || x >= width || y < 0 || y >= height) {
                System.out.println("Invalid Coordinates\n");
                placeSuccess = false;
                return;
            }
            if (grids[y][x].getOwner() != null && !players.get(pointer).getColour().equals(grids[y][x].getOwner().getColour())) {
                System.out.println("Cannot Place Atom Here\n");
                placeSuccess = false;
                return;
            }
        }
        if (x < 0 || x >= width || y < 0 || y >= height) {
        } else {
            tmpAtomCount = grids[y][x].getAtomCount();
            // place in the corners
            if ((x == 0 && y == 0) || (x == 0 && y == height - 1)
                    || (y == 0 && x == width - 1)
                    || (x == width - 1 && y == height - 1)) {
                if (tmpAtomCount == 0) {
                    grids[y][x].setOwner(players.get(pointer));
                    grids[y][x].setAtomCount(1);
                } else {
                    grids[y][x].setAtomCount(0);
                    grids[y][x].setOwner(null);
                    place(x - 1, y, false);
                    place(x + 1, y, false);
                    place(x, y + 1, false);
                    place(x, y - 1, false);
                }
            }
            // place in the edges
            else if (x == 0 || y == 0 || x == width - 1 || y == height - 1) {
                if (tmpAtomCount <= 1) {
                    grids[y][x].setOwner(players.get(pointer));
                    grids[y][x].setAtomCount(++tmpAtomCount);
                } else {
                    grids[y][x].setAtomCount(0);
                    grids[y][x].setOwner(null);
                    place(x - 1, y, false);
                    place(x + 1, y, false);
                    place(x, y + 1, false);
                    place(x, y - 1, false);
                }

            } else {
                if (tmpAtomCount <= 2) {
                    grids[y][x].setOwner(players.get(pointer));
                    grids[y][x].setAtomCount(++tmpAtomCount);
                } else {
                    grids[y][x].setAtomCount(0);
                    grids[y][x].setOwner(null);
                    place(x - 1, y, false);
                    place(x + 1, y, false);
                    place(x, y + 1, false);
                    place(x, y - 1, false);

                }
            }
            if (isFirst) {
                placeSuccess = true;
                direct(x, y);
            }
        }

    }


    public static void direct(int x, int y) {
        int temPointer = pointer;
        statL();
        if (allTurn) {
            pointer = index % players.size();
            for (int i = pointer; i < players.size(); i++) {
                if (players.get(i).getGridsOwned() == 0) {
                    index++;
                }
            }
        }
        pointer = index % players.size();
        if (temPointer == pointer) {
            gameOver = true;
            System.out.println(players.get(pointer).getColour() + " Wins!");
        } else {
            System.out.println(players.get(pointer).getColour() + "'s Turn\n");
        }
        index++;
        if (pointer == 0) {
            allTurn = true;
        }
        // display();
    }


    //Displays the gameboard using + to denote the corner and two 鈥�to denote the colour and the number of atoms located.
    public static void display() {
        System.out.println();
        System.out.print("+");
        for (int i = 0; i < width * 2 + width - 1; i++) {
            System.out.print("-");
        }
        System.out.println("+");

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grids[i][j].getOwner() == null || grids[i][j].getAtomCount() == 0) {
                    System.out.print("|" + "  ");
                } else {
                    System.out.print("|" + grids[i][j].getOwner().getColour().charAt(0) + grids[i][j].getAtomCount());
                }
            }
            System.out.println("|");
        }

        System.out.print("+");
        for (int i = 0; i < width * 2 + width - 1; i++) {
            System.out.print("-");
        }
        System.out.println("+\n");
    }


    public static void save(String fileName) {
        File f = new File(fileName);
        try (FileOutputStream out = new FileOutputStream(f)) {
            out.write(width);
            out.write(height);
            byte numberOfPlayers = (byte) players.size();
            out.write(numberOfPlayers);
            byte zero = 0;
            for (int i = 0; i < move.size(); i++) {
                out.write(move.get(i).get(0));
                out.write(move.get(i).get(1));
                out.write(zero);
                out.write(zero);
            }
            out.close();
            System.out.println("Game Saved\n");
        } catch (FileAlreadyExistsException e) {
            System.out.println("File Already Exists\n");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static byte[] convert1(int v) {
        byte[] b = new byte[4];
        b[0] = (byte) (v >> 24);
        b[1] = (byte) (v >> 16);
        b[2] = (byte) (v >> 8);
        b[3] = (byte) v;
        return b;

    }

    public static void load(String fileName) {
        File f = new File(fileName);
        FileInputStream in = null;
        try {
            in = new FileInputStream(f);
            byte[] b = new byte[(int) f.length()];
            // int a = in.read(b);
            int w = in.read();
            int h = in.read();
            int p = in.read();
            System.out.println(w);
            System.out.println(h);
            System.out.println(p);
            System.out.println("Game Loaded\n");
        } catch (FileNotFoundException e) {
            System.out.println("Cannot Load Save");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int convert2(byte[] b) {
        return (b[3] & 0xFF) |
                ((b[2] & 0xFF) << 8) |
                ((b[1] & 0xFF) << 16) |
                ((b[0] & 0xFF) << 24);
    }

    public static void main(String[] args) {
        //Your game starts here!
        Scanner scan = new Scanner(System.in);
        List<String[]> commandlist = new ArrayList<String[]>();

        while (true) {
            // try{
            // System.out.println(commandlist);
            if (gameOver) {
                break;
            }
            String keyboard = scan.nextLine();

            String[] command = keyboard.split("\\s+");
            // System.out.println(commandlist);
            if (command[0].equals("HELP")) {
                help();
                commandlist.add(command);
            } else if (command[0].equals("QUIT")) {
                commandlist.add(command);
                quit();
                break;
            } else if (command[0].equals("START")) {
                String[] array = Arrays.copyOfRange(command, 1, command.length);
                if (!(commandlist.contains(command))) {
                    System.out.println(start(array));
                    index++;
                    if (startSuccess) {
                        commandlist.add(command);
                    }
                } else {
                    System.out.println("Invalid command arguments\n");
                }
            } else if (command[0].equals("STAT")) {
                if (startSuccess) {
                    commandlist.add(command);
                    stat();
                } else {
                    System.out.println("Game Not In Progress");
                }
            } else if (command[0].equals("PLACE")) {
                byte x = (byte) Integer.parseInt(command[1]);
                byte y = (byte) Integer.parseInt(command[2]);
                place(x, y, true);
                if (placeSuccess) {
                    ArrayList<Byte> m = new ArrayList<Byte>();
                    m.add(x);
                    m.add(y);
                    move.add(m);
                    commandlist.add(command);
                    Grid[][] tem = new Grid[height][width];
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            tem[i][j] = new Grid(null, 0);
                        }
                    }
                    for (int i = 0; i < height; i++) {
                        for (int j = 0; j < width; j++) {
                            if (grids[i][j].getOwner() != null) {
                                tem[i][j].setAtomCount(grids[i][j].getAtomCount());
                                tem[i][j].setOwner(grids[i][j].getOwner());
                            } else {
                                tem[i][j].setAtomCount(0);
                                tem[i][j].setOwner(null);
                            }

                        }
                    }
                    state.add(tem);
                }
                // System.out.println(move);


            } else if (command[0].equals("DISPLAY")) {
                commandlist.add(command);
                display();
            } else if (command[0].equals("SAVE")) {
                save(command[1]);
            } else if (command[0].equals("LOAD")) {
                load(command[1]);
            } else if (command[0].equals("UNDO")) {
                undo();
            } else {
                System.out.println("Invalid command arguments\n");
            }
            // }catch(Exception e){
            // 	break;
            // }
        }
    }
}

