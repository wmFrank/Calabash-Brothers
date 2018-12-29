package Role;

import Forms.*;
import Space.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Lackey extends Evil implements Fighter{
    public Lackey() {
        this.setImageview(new ImageView(new Image("icons/Lackey2.png",50,50,true,true)));
        this.setName("小喽啰");
        this.setHp(250);
        this.setLife(250);
        this.setDamage(90);
        this.setDefence(20);
        this.setHitrate(9);
        this.setTired(7);
    }

    public void GotoBattleground(Battleground bg, Formation form) {
        for(int i = 0; i < form.getNumber(); i++) {
            if(bg.getSpot()[form.getLayout()[i].getX()][form.getLayout()[i].getY()].SpotEmpty() == true) {
                this.setCoordinate(new Coordinate(form.getLayout()[i].getX(),form.getLayout()[i].getY()));
                bg.getSpot()[form.getLayout()[i].getX()][form.getLayout()[i].getY()].setExistence(this);
                break;
            }
        }
    }

    public void LeaveBattleground(Battleground bg) {
        bg.getSpot()[this.getCoordinate().getX()][this.getCoordinate().getY()].removeExistence();
        this.removeCoordinate();
        this.setState(1);
        this.setHp(this.getLife());
        this.setProgressbar(1.0f);
    }
}