package Role;

import Forms.*;
import Space.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class CalabashBro extends Justice implements FightLeader{
    private String color;
    private int rank;

    public CalabashBro(int i) {
        switch(i) {
            case 1:
                this.setImageview(new ImageView(new Image("icons/Red.png",50,50,true,true)));
                this.setName("老大");
                this.setHp(500);
                this.setLife(500);
                this.setDamage(120);
                this.setDefence(30);
                this.setHitrate(7);
                this.setTired(15);
                color = "红色";
                rank = 1;
                break;
            case 2:
                this.setImageview(new ImageView(new Image("icons/Orange.png",50,50,true,true)));
                this.setName("老二");
                this.setHp(300);
                this.setLife(300);
                this.setDamage(70);
                this.setDefence(20);
                this.setHitrate(10);
                this.setTired(7);
                color = "橙色";
                rank = 2;
                break;
            case 3:
                this.setImageview(new ImageView(new Image("icons/Yellow.png",50,50,true,true)));
                this.setName("老三");
                this.setHp(300);
                this.setLife(300);
                this.setDamage(100);
                this.setDefence(60);
                this.setHitrate(8);
                this.setTired(8);
                color = "黄色";
                rank = 3;
                break;
            case 4:
                this.setImageview(new ImageView(new Image("icons/Green.png",50,50,true,true)));
                this.setName("老四");
                this.setHp(300);
                this.setLife(300);
                this.setDamage(100);
                this.setDefence(25);
                this.setHitrate(8);
                this.setTired(8);
                color = "绿色";
                rank = 4;
                break;
            case 5:
                this.setImageview(new ImageView(new Image("icons/Cyan.png",50,50,true,true)));
                this.setName("老五");
                this.setHp(300);
                this.setLife(300);
                this.setDamage(100);
                this.setDefence(25);
                this.setHitrate(8);
                this.setTired(8);
                color = "青色";
                rank = 5;
                break;
            case 6:
                this.setImageview(new ImageView(new Image("icons/Blue.png",50,50,true,true)));
                this.setName("老六");
                this.setHp(200);
                this.setLife(200);
                this.setDamage(200);
                this.setDefence(10);
                this.setHitrate(9);
                this.setTired(10);
                color = "蓝色";
                rank = 6;
                break;
            case 7:
                this.setImageview(new ImageView(new Image("icons/Purple.png",50,50,true,true)));
                this.setName("老七");
                this.setHp(300);
                this.setLife(300);
                this.setDamage(100);
                this.setDefence(25);
                this.setHitrate(8);
                this.setTired(8);
                color = "紫色";
                rank = 7;
                break;
        }
    }

    public String getColor() {
        return color;
    }

    public int getRank() {
        return rank;
    }

    public void SetLeaderground(Coordinate a, Formation form) {   //设置领导点，即阵型的关键点的位置（所谓关键点就是决定阵型位置的点）
        if(color == "红色") {
            form.setLeader(a);
//            System.out.println(form.getLeader().getX() + " " + form.getLeader().getY());
        }
        else {
            System.out.println("该葫芦娃没有权利决定领导点");
        }
    }

    public void GiveOrder(String a, Formation form) {             //发号施令，即决定摆出哪一种阵型
        if(color == "红色") {
            form.setLayout(a);
        }
        else {
            System.out.println("该葫芦娃没有权利决定阵型");
        }
    }

    public void GotoBattleground(Battleground bg, Formation form) {      //奔赴战场，根据阵型找到自己站立的位置
        for(int i = 0; i < form.getNumber(); i++) {
            if(bg.getSpot()[form.getLayout()[i].getX()][form.getLayout()[i].getY()].SpotEmpty() == true) {
                this.setCoordinate(new Coordinate(form.getLayout()[i].getX(),form.getLayout()[i].getY()));
                bg.getSpot()[form.getLayout()[i].getX()][form.getLayout()[i].getY()].setExistence(this);
                break;
            }
        }
    }

    public void LeaveBattleground(Battleground bg) {            //离开战场，直接撤退
        bg.getSpot()[this.getCoordinate().getX()][this.getCoordinate().getY()].removeExistence();
        this.removeCoordinate();
        this.setState(1);
        this.setHp(this.getLife());
        this.setProgressbar(1.0f);
    }
}
