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

    private int[][] aL = new int[10][10];
    private ArrayList storeShipCoords = new ArrayList();
    private int[][] storeShots = new int[10][10];
    boolean shipHit = false;
    int prevShotX = 0;
    int prevShotY = 0;
    private ArrayList<Position> huntTargetCoord = new ArrayList();

    public RandomPlayer() {
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
        {
            if (shipHit) {
                addNearPos(prevShotX, prevShotY);
                shipHit = false;
            }

            if (!huntTargetCoord.isEmpty()) {
                storeShotCoords(huntTargetCoord.get(huntTargetCoord.size() - 1).x, huntTargetCoord.get(huntTargetCoord.size() - 1).y);
                prevShotX = huntTargetCoord.get(huntTargetCoord.size() - 1).x;
                prevShotY = huntTargetCoord.get(huntTargetCoord.size() - 1).y;
                return huntTargetCoord.remove(huntTargetCoord.size() - 1);
            }

            int x = rnd.nextInt(sizeX);
            int y = rnd.nextInt(sizeY);
            prevShotX = x;
            prevShotY = y;

            if (x >= sizeX || y >= sizeY || storeShots[x][y] == 1) {
                return getFireCoordinates(enemyShips);
            } else {
                storeShotCoords(x, y);
                return new Position(x, y);
            }

        }
    }

    public void storeShotCoords(int x, int y) {
        storeShots[x][y] = 1;
    }

    public boolean checkFireCoord(int x, int y) {
        if (x >= sizeX || x < 0 || y < 0 || y >= sizeY || storeShots[x][y] > 0) {
            return false;
        }
        return true;
    }

    public void addNearPos(int x, int y) {
        if (checkFireCoord(x + 1, y)) {
            huntTargetCoord.add(new Position(x + 1, y));
        }
        if (checkFireCoord(x - 1, y)) {
            huntTargetCoord.add(new Position(x - 1, y));
        }
        if (checkFireCoord(x, y + 1)) {
            huntTargetCoord.add(new Position(x, y + 1));
        }
        if (checkFireCoord(x, y - 1)) {
            huntTargetCoord.add(new Position(x, y - 1));
        }
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
        if (hit) {
            shipHit = true;
        }
    }

    /**
     * Called in the beginning of each match to inform about the number of
     * rounds being played.
     *
     * @param rounds int the number of rounds i a match
     */
    @Override
    public void startMatch(int rounds
    ) {
    }

    /**
     * Called at the beginning of each round.
     *
     * @param round int the current round number.
     */
    @Override
    public void startRound(int round
    ) {
        aL = new int[10][10];
        ArrayList storeShipCoords = new ArrayList();
        storeShots = new int[10][10];
        shipHit = false;
        prevShotX = 0;
        prevShotY = 0;
        huntTargetCoord = new ArrayList();
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
    public void endRound(int round, int points, int enemyPoints
    ) {
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
