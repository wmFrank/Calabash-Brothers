package Role;

import Forms.*;
import Space.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class DemonScorpion extends Evil implements FightLeader {
    public DemonScorpion() {
        this.setImageview(new ImageView(new Image("icons/Scorpion.png",50,50,true,true)));
        this.setName("蝎子精");
        this.setHp(600);
        this.setLife(600);
        this.setDamage(150);
        this.setDefence(50);
        this.setHitrate(9);
        this.setTired(12);
//		this.setQuality("坏");
    }

    public void SetLeaderground(Coordinate a, Formation form) {
        form.setLeader(a);
    }

    public void GiveOrder(String a, Formation form) {
        form.setLayout(a);
    }

    public void GotoBattleground(Battleground bg, Formation form) {
        if(bg.getSpot()[form.getLayout()[0].getX()][form.getLayout()[0].getY()].SpotEmpty() == true) {
            //this.setCoordinate(bg.getSpot()[form.getLayout()[0].getX()][form.getLayout()[0].getY()].getCoordinate());
            this.setCoordinate(new Coordinate(form.getLayout()[0].getX(),form.getLayout()[0].getY()));
            bg.getSpot()[form.getLayout()[0].getX()][form.getLayout()[0].getY()].setExistence(this);
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