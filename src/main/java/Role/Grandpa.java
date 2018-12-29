package Role;

import Forms.*;
import Space.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Grandpa extends Justice implements SideLooker{
    public Grandpa() {
        this.setImageview(new ImageView(new Image("icons/Grandpa.png",50,50,true,true)));
        this.setName("老爷爷");
        this.setHp(200);
        this.setLife(200);
        this.setDamage(60);
        this.setDefence(40);
        this.setHitrate(6);
        this.setTired(8);
//		this.setQuality("好");
    }

    public void Lookatside(Battleground bg, Coordinate a) {        //旁观，即直接选取战场中无人的位置站立
        this.setCoordinate(a);
        bg.getSpot()[a.getX()][a.getY()].setExistence(this);
    }

    public void LeaveBattleground(Battleground bg) {
        bg.getSpot()[this.getCoordinate().getX()][this.getCoordinate().getY()].removeExistence();
        this.removeCoordinate();
        this.setState(1);
        this.setHp(this.getLife());
        this.setProgressbar(1.0f);
    }
}