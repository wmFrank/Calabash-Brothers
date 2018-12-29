package Role;

import Forms.*;
import Space.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DemonSnake extends Evil implements SideLooker{
    public DemonSnake() {
        this.setImageview(new ImageView(new Image("icons/Snake.png",50,50,true,true)));
        this.setName("蛇精");
        this.setHp(400);
        this.setLife(400);
        this.setDamage(120);
        this.setDefence(30);
        this.setHitrate(9);
        this.setTired(8);
//		this.setQuality("坏");
    }

    public void Lookatside(Battleground bg, Coordinate a) {
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