package y3;

import battleship.interfaces.BattleshipsPlayer;
import tournament.player.PlayerFactory;

public class Y3 implements PlayerFactory<BattleshipsPlayer>
{

    public Y3(){}
    
    
    @Override
    public BattleshipsPlayer getNewInstance()
    {
        return new RandomPlayer();
    }

    @Override
    public String getID()
    {
        return "Y3";
    }

    @Override
    public String getName()
    {
        return "Moooooomaaaaaayyyy 2.0";
    }
    
}
