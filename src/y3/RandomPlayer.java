/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package y3;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Tobias
 */
import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Tobias
 */
public class RandomPlayer implements BattleshipsPlayer {

    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;
    private Board myBoard;
    private ArrayList<Position> usedShipNumbers = new ArrayList<>();
    private ArrayList<Position> usedNumbers = new ArrayList<>();
    private int currentAShips = 5;
    private int hitAmount = 0;
    ArrayList<Position> huntNumbers = new ArrayList<>();
    private boolean hunt = false;
    private int e2Count = 0;
    private boolean e2Active = false;
    boolean used2 = false;
    private int[][] aL = new int[10][10];

    private ArrayList<Position> e4 = new ArrayList<>();
    private Position shot1 = new Position(0, 2);
    private Position shot2 = new Position(0, 6);
    private Position shot3 = new Position(1, 1);
    private Position shot4 = new Position(1, 5);
    private Position shot5 = new Position(1, 9);
    private Position shot6 = new Position(2, 0);
    private Position shot7 = new Position(2, 4);
    private Position shot8 = new Position(2, 8);
    private Position shot9 = new Position(3, 3);
    private Position shot10 = new Position(3, 7);
    private Position shot11 = new Position(4, 2);
    private Position shot12 = new Position(4, 6);
    private Position shot13 = new Position(5, 1);
    private Position shot14 = new Position(5, 5);
    private Position shot15 = new Position(5, 9);
    private Position shot16 = new Position(6, 0);
    private Position shot17 = new Position(6, 4);
    private Position shot18 = new Position(6, 8);
    private Position shot19 = new Position(7, 3);
    private Position shot20 = new Position(7, 7);
    private Position shot21 = new Position(8, 2);
    private Position shot22 = new Position(8, 6);
    private Position shot23 = new Position(9, 1);
    private Position shot24 = new Position(9, 5);
    private Position shot25 = new Position(9, 9);

    private ArrayList<Position> e2 = new ArrayList<>();
    private Position shot26 = new Position(0, 0);
    private Position shot27 = new Position(0, 4);
    private Position shot28 = new Position(0, 8);
    private Position shot29 = new Position(1, 3);
    private Position shot30 = new Position(1, 7);
    private Position shot31 = new Position(2, 2);
    private Position shot32 = new Position(2, 6);
    private Position shot33 = new Position(3, 1);
    private Position shot34 = new Position(3, 5);
    private Position shot35 = new Position(3, 9);
    private Position shot36 = new Position(4, 0);
    private Position shot37 = new Position(4, 4);
    private Position shot38 = new Position(4, 8);
    private Position shot39 = new Position(5, 3);
    private Position shot40 = new Position(5, 7);
    private Position shot41 = new Position(6, 2);
    private Position shot42 = new Position(6, 6);
    private Position shot43 = new Position(7, 1);
    private Position shot44 = new Position(7, 5);
    private Position shot45 = new Position(7, 9);
    private Position shot46 = new Position(8, 0);
    private Position shot47 = new Position(8, 4);
    private Position shot48 = new Position(8, 8);
    private Position shot49 = new Position(9, 3);
    private Position shot50 = new Position(9, 7);
    private boolean used3;

    public RandomPlayer() {
        e4.add(shot1);
        e4.add(shot2);
        e4.add(shot3);
        e4.add(shot4);
        e4.add(shot5);
        e4.add(shot6);
        e4.add(shot7);
        e4.add(shot8);
        e4.add(shot9);
        e4.add(shot10);
        e4.add(shot11);
        e4.add(shot12);
        e4.add(shot13);
        e4.add(shot14);
        e4.add(shot15);
        e4.add(shot16);
        e4.add(shot17);
        e4.add(shot18);
        e4.add(shot19);
        e4.add(shot20);
        e4.add(shot21);
        e4.add(shot22);
        e4.add(shot23);
        e4.add(shot24);
        e4.add(shot25);

        e2.add(shot26);
        e2.add(shot27);
        e2.add(shot28);
        e2.add(shot29);
        e2.add(shot30);
        e2.add(shot31);
        e2.add(shot32);
        e2.add(shot33);
        e2.add(shot34);
        e2.add(shot35);
        e2.add(shot36);
        e2.add(shot37);
        e2.add(shot38);
        e2.add(shot39);
        e2.add(shot40);
        e2.add(shot41);
        e2.add(shot42);
        e2.add(shot43);
        e2.add(shot44);
        e2.add(shot45);
        e2.add(shot46);
        e2.add(shot47);
        e2.add(shot48);
        e2.add(shot49);
        e2.add(shot50);
    }

    /**
     * The method called when its time for the AI to place ships on the board
     * (at the beginning of each round).
     *
     * The Ship object to be placed MUST be taken from the Fleet given (do not
     * create your own Ship objects!).
     *
     * A ship is placed by calling the board.placeShip(..., Ship ship, ...) for
     * each ship in the fleet (see board interface for details on placeShip()).
     *
     * A player is not required to place all the ships. Ships placed outside the
     * board or on top of each other are wrecked.
     *
     * @param fleet Fleet all the ships that a player should place.
     * @param board Board the board were the ships must be placed.
     */
    @Override
    public void placeShips(Fleet fleet, Board board) {
        myBoard = board;
        sizeX = board.sizeX();
        sizeY = board.sizeY();
        for (int i = 0; i < fleet.getNumberOfShips(); ++i) {
            Ship s = fleet.getShip(i);
            boolean vertical = rnd.nextBoolean();
            Position pos = null;
            boolean start = true;
            while (start) {
                if (vertical) {
                    int x = rnd.nextInt(sizeX);
                    int y = rnd.nextInt(sizeY - (s.size() - 1));
                    pos = new Position(x, y);
                } else {
                    int x = rnd.nextInt(sizeX - (s.size() - 1));
                    int y = rnd.nextInt(sizeY);
                    pos = new Position(x, y);
                }
                if (Check(pos, vertical, s)) {
                    Remember(pos, vertical, s);
                    board.placeShip(pos, s, vertical);
                    start = false;
                }
            }
        }
    }

    private void Remember(Position pos, boolean vert, Ship s) {
        //Alle pladser i arrayet er sat til 0 fra starten af.
        //Metoden skal placere skibene i vores array og gemme dem 
        //som 1 taller
        if (vert) {
            for (int k = 0; k < s.size(); k++) {
                aL[pos.y + k][pos.x] = 1;
            }
        } else {
            for (int k = 0; k < s.size(); k++) {
                aL[pos.y][pos.x + k] = 1;
            }
        }
    }

    private boolean Check(Position pos, boolean vert, Ship s) {
        //Kigger i vores 2d array. 
        //Hvis et af pladserne har værdien 1, så er pladsen optaget og
        //hele metoden skal stoppes og return false.
        //Ellers hvis alle pladserne, som loopet tjekker er 0 (dvs. der ikke er noget),
        //så skal den return true.
        int check = 0;
        if (vert) {
            for (int j = 0; j < s.size(); j++) {
                if (aL[pos.y + j][pos.x] > 0) {
                    check = 0;
                    break;
                } else if (aL[pos.y + j][pos.x] == 0) {
                    check = 1;
                }
            }
        } else {
            for (int j = 0; j < s.size(); j++) {
                if (aL[pos.y][pos.x + j] > 0) {
                    check = 0;
                    break;
                } else if (aL[pos.y][pos.x + j] == 0) {
                    check = 1;
                }
            }
        }
        if (check == 1) {
            return true;
        }
        return false;
    }

    /**
     * Called every time the enemy has fired a shot.
     *
     * The purpose of this method is to allow the AI to react to the enemy's
     * incoming fire and place his/her ships differently next round.
     *
     * @param pos Position of the enemy's shot
     */
    @Override
    public void incoming(Position pos) {
        //Do nothing
    }

    /**
     * Called by the Game application to get the Position of your shot.
     * hitFeedBack(...) is called right after this method.
     *
     * @param enemyShips Fleet the enemy's ships. Compare this to the Fleet
     * supplied in the hitFeedBack(...) method to see if you have sunk any
     * ships.
     *
     * @return Position of you next shot.
     */
    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        boolean used = true;
        Position xy = null;

        while (hunt == true && used == true) {

            xy = hunt();

            if (!usedNumbers.contains(xy)) {
                usedNumbers.add(xy);
                used = false;
            }
        }
        {

            while (used == true && hunt == false) {
//        int x = rnd.nextInt(sizeX);
//        int y = rnd.nextInt(sizeY);
//
//        xy = new Position(x, y);

                e2Count = 0;
                for (int i = 0; i < e4.size(); i++) {
                    if (usedNumbers.contains(e4.get(i))) {
                        e2Count++;
                    }
                }

                if (e2Count <= 24) {
                    xy = e4.get(rnd.nextInt(e4.size()));
                }

                if (e2Count > 24 || e2Active == true) {
                    e2Active = true;
                    xy = e2.get(rnd.nextInt(e2.size()));
                }

                if (!usedNumbers.contains(xy)) {
                    huntNumbers.clear();
                    huntNumbers.add(xy);
                    usedNumbers.add(xy);
                    used = false;
                }
            }
        }
        return xy;
    }

    public Position hunt() {
        Position startPoint = huntNumbers.get(0);

        Position north = new Position(startPoint.x, startPoint.y + 1);
        Position east = new Position(startPoint.x + 1, startPoint.y);
        Position south = new Position(startPoint.x, startPoint.y - 1);
        Position west = new Position(startPoint.x - 1, startPoint.y);

        if (!huntNumbers.contains(north) && startPoint.x <= 9 && startPoint.x >= 0 && startPoint.y <= 9 && startPoint.y >= 0) {
            huntNumbers.add(north);
            return north;
        }
        if (!huntNumbers.contains(east) && startPoint.x <= 9 && startPoint.x >= 0 && startPoint.y <= 9 && startPoint.y >= 0) {
            huntNumbers.add(east);
            return east;
        }
        if (!huntNumbers.contains(south) && startPoint.x <= 9 && startPoint.x >= 0 && startPoint.y <= 9 && startPoint.y >= 0) {
            huntNumbers.add(south);
            return south;
        }
        if (!huntNumbers.contains(west) && startPoint.x <= 9 && startPoint.x >= 0 && startPoint.y <= 9 && startPoint.y >= 0) {
            huntNumbers.add(west);
            return west;
        }

        huntNumbers.clear();
        hunt = false;
        return new Position(1, 1);
    }

    /**
     * Called right after getFireCoordinates(...) to let your AI know if you hit
     * something or not.
     *
     * Compare the number of ships in the enemyShips with that given in
     * getFireCoordinates in order to see if you sunk a ship.
     *
     * @param hit boolean is true if your last shot hit a ship. False otherwise.
     * @param enemyShips Fleet the enemy's ships.
     */
    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {

        if (hit == true) {
            //hitAmount++;
            hunt = true;
//      int newAShips = enemyShips.getNumberOfShips();
//      if (currentAShips > newAShips)
//      {
//        currentAShips = enemyShips.getNumberOfShips();
//        hunt = false;
//      }
        } else // Do nothing
        {

        }
    }

    /**
     * Called in the beginning of each match to inform about the number of
     * rounds being played.
     *
     * @param rounds int the number of rounds i a match
     */
    @Override
    public void startMatch(int rounds) {
    }

    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    @Override
    public void startRound(int round) {
        aL = new int[10][10];
        usedShipNumbers.clear();
        usedNumbers.clear();
        huntNumbers.clear();
    }

    /**
     * Called at the end of each round to let you know if you won or lost.
     * Compare your points with the enemy's to see who won.
     *
     * @param round int current round number.
     * @param points your points this round: 100 - number of shot used to sink
     * all of the enemy's ships.
     *
     * @param enemyPoints int enemy's points this round.
     */
    @Override
    public void endRound(int round, int points, int enemyPoints) {
        //Do nothing
    }

    /**
     * Called at the end of a match (that usually last 1000 rounds) to let you
     * know how many losses, victories and draws you scored.
     *
     * @param won int the number of victories in this match.
     * @param lost int the number of losses in this match.
     * @param draw int the number of draws in this match.
     */
    @Override
    public void endMatch(int won, int lost, int draw
    ) {
        //Do nothing
    }
}
