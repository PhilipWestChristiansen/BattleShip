package y3;

import battleship.interfaces.BattleshipsPlayer;
import battleship.interfaces.Fleet;
import battleship.interfaces.Position;
import battleship.interfaces.Board;
import battleship.interfaces.Ship;
import java.util.ArrayList;
import java.util.Random;

public class RandomPlayer implements BattleshipsPlayer {

    private final static Random rnd = new Random();
    private int sizeX;
    private int sizeY;
    private Board myBoard;

    private int[][] aL = new int[10][10];
    private ArrayList storeShipCoords = new ArrayList();
    private int[][] saveShot = new int[10][10];
    boolean shipHit = false;
    int shotX = 0;
    int shotY = 0;
    private ArrayList<Position> huntCoord = new ArrayList();

    public RandomPlayer() {
    }

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

    @Override
    public void incoming(Position pos) {
    }

    @Override
    public Position getFireCoordinates(Fleet enemyShips) {
        {
            if (shipHit) {
                pos(shotX, shotY);
                shipHit = false;
            }

            if (!huntCoord.isEmpty()) {
                saveCoords(huntCoord.get(huntCoord.size() - 1).x, huntCoord.get(huntCoord.size() - 1).y);
                shotX = huntCoord.get(huntCoord.size() - 1).x;
                shotY = huntCoord.get(huntCoord.size() - 1).y;
                return huntCoord.remove(huntCoord.size() - 1);
            }

            int x = rnd.nextInt(sizeX);
            int y = rnd.nextInt(sizeY);
            shotX = x;
            shotY = y;

            if (x >= sizeX || y >= sizeY || saveShot[x][y] == 1) {
                return getFireCoordinates(enemyShips);
            } else {
                saveCoords(x, y);
                return new Position(x, y);
            }
        }
    }

    public void saveCoords(int x, int y) {
        saveShot[x][y] = 1;
    }

    public boolean checkFireCoord(int x, int y) {
        if (x >= sizeX || x < 0 || y < 0 || y >= sizeY || saveShot[x][y] > 0) {
            return false;
        }
        return true;
    }

    public void pos(int x, int y) {
        if (checkFireCoord(x + 1, y)) {
            huntCoord.add(new Position(x + 1, y));
        }
        if (checkFireCoord(x - 1, y)) {
            huntCoord.add(new Position(x - 1, y));
        }
        if (checkFireCoord(x, y + 1)) {
            huntCoord.add(new Position(x, y + 1));
        }
        if (checkFireCoord(x, y - 1)) {
            huntCoord.add(new Position(x, y - 1));
        }
    }

    @Override
    public void hitFeedBack(boolean hit, Fleet enemyShips) {
        if (hit) {
            shipHit = true;
        }
    }

    @Override
    public void startMatch(int rounds) {
    }

    @Override
    public void startRound(int round) {
        aL = new int[10][10];
        ArrayList storeShipCoords = new ArrayList();
        saveShot = new int[10][10];
        shipHit = false;
        shotX = 0;
        shotY = 0;
        huntCoord = new ArrayList();
    }

    @Override
    public void endRound(int round, int points, int enemyPoints) {
    }

    @Override
    public void endMatch(int won, int lost, int draw) {
    }
}
