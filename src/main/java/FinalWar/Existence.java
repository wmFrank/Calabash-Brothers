package FinalWar;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.*;

public abstract class Existence {
    private String name;           //名字

    public Existence() {
        name = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String a) {
        name = a;
    }
}

abstract class CalabashWorld extends Existence {
    private String quality;        //性质，有“好”“坏”“中立”三种
    private Coordinate coordinate; //坐标，在战场上的位置
    private ImageView imageview;
    private ImageView imagedie;
    private ProgressBar progressbar;
    private int state; //1是活着 0是死了
    private int hp;
    private int life;
    private int damage;
    private int defence;
    private int hitrate;
    private int tired;

    public CalabashWorld() {
        quality = null;
        coordinate = null;
        imageview = null;
        imagedie = new ImageView(new Image("icons/Grave.jpg",50,50,true,true));
        progressbar = new ProgressBar(1.0F);
        progressbar.setMaxSize(50,15);
        progressbar.setMinSize(50,15);
        hp = 0;
        life = 0;
        damage = 0;
        defence = 0;
        hitrate = 0;
        tired = 0;
        state = 1;
    }

    public String getQuality() {
        return quality;
    }

    public ImageView getImageview(){
        if(state == 1)
            return imageview;
        else
            return imagedie;
    }

    public int getHp(){
        return hp;
    }

    public int getLife(){
        return life;
    }

    public int getDamage(){
        return damage;
    }

    public int getDefence(){
        return defence;
    }

    public int getHitrate(){
        return hitrate;
    }

    public int getTired(){
        return tired;
    }

    public void setHp(int a){
        hp = a;
    }

    public void setLife(int a){
        life = a;
    }

    public void setDamage(int a){
        damage = a;
    }

    public void setDefence(int a){
        defence = a;
    }

    public void setHitrate(int a){
        hitrate = a;
    }

    public void setTired(int a){
        tired = a;
    }

    public ProgressBar getProgressbar(){
        return progressbar;
    }

    public void setProgressbar(double a){
        progressbar.setProgress(a);
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public int getState() { return state; }

    public void setCoordinate(Coordinate a) {
        coordinate = a;
    }

    public void removeCoordinate() {
        coordinate = null;
    }

    public void setQuality(String a) {
        quality = a;
    }

    public void setImageview(ImageView a){ imageview = a; }

    public void setState(int a) { state = a; }
}

abstract class Justice extends CalabashWorld {
    public Justice() {
        this.setQuality("Good");
    }
}

abstract class Evil extends CalabashWorld {
    public Evil() {
        this.setQuality("Bad");
    }
}

interface WarMan{
    public void LeaveBattleground(Battleground bg);
}

interface Fighter extends WarMan{
    public void GotoBattleground(Battleground bg, Formation form);
}

interface SideLooker extends WarMan{
    public void Lookatside(Battleground bg, Coordinate a);
}

interface FightLeader extends Fighter{
    public void SetLeaderground(Coordinate a, Formation form);
    public void GiveOrder(String a, Formation form);
}

class CalabashBro extends Justice implements FightLeader{
    private String color;
    private int rank;

    public CalabashBro(int i) {
//		this.setQuality("好");
        switch(i) {
            case 1:
                this.setImageview(new ImageView(new Image("icons/Red.jpg",50,50,true,true)));
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
                this.setImageview(new ImageView(new Image("icons/Orange.jpg",50,50,true,true)));
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
                this.setImageview(new ImageView(new Image("icons/Yellow.jpg",50,50,true,true)));
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
                this.setImageview(new ImageView(new Image("icons/Green.jpg",50,50,true,true)));
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
                this.setImageview(new ImageView(new Image("icons/Cyan.jpg",50,50,true,true)));
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
                this.setImageview(new ImageView(new Image("icons/Blue.jpg",50,50,true,true)));
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
                this.setImageview(new ImageView(new Image("icons/Purple.jpg",50,50,true,true)));
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
//            for(int i = 0; i < 7; i++)
//                System.out.println(form.getLayout()[i].getX() + " " + form.getLayout()[i].getY());
        }
        else {
            System.out.println("该葫芦娃没有权利决定阵型");
        }
    }

    public void GotoBattleground(Battleground bg, Formation form) {      //奔赴战场，根据阵型找到自己站立的位置
        for(int i = 0; i < form.getNumber(); i++) {
            if(bg.getSpot()[form.getLayout()[i].getX()][form.getLayout()[i].getY()].SpotEmpty() == true) {
                this.setCoordinate(new Coordinate(form.getLayout()[i].getX(),form.getLayout()[i].getY()));
//                if(this.getName() == "老大")
//                    for(int t = 0; t < 20; t++)
//                    {
//                        for(int j = 0; j < 20; j++)
//                            System.out.print(bg.getSpot()[t][j].getCoordinate().getX() + " " + bg.getSpot()[t][j].getCoordinate().getY() + "     ");
//                        System.out.println();
//                    }
//                System.out.println("didid " + form.getLayout()[i].getX() + " " + form.getLayout()[i].getY());
//                System.out.println("lalal " + bg.getSpot()[form.getLayout()[i].getX()][form.getLayout()[i].getY()].getCoordinate().getX() + " " + bg.getSpot()[form.getLayout()[i].getX()][form.getLayout()[i].getY()].getCoordinate().getY());
//
//                System.out.println("laoda " + this.getCoordinate().getX() + " " + this.getCoordinate().getY());
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

class Grandpa extends Justice implements SideLooker{
    public Grandpa() {
        this.setImageview(new ImageView(new Image("icons/Grandpa.jpg",50,50,true,true)));
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

class DemonSnake extends Evil implements SideLooker{
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

class DemonScorpion extends Evil implements FightLeader {
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

class Lackey extends Evil implements Fighter{
    public Lackey() {
        this.setImageview(new ImageView(new Image("icons/Lackey.jpg",50,50,true,true)));
        this.setName("小喽啰");
        this.setHp(250);
        this.setLife(250);
        this.setDamage(90);
        this.setDefence(20);
        this.setHitrate(9);
        this.setTired(6);
//		this.setQuality("坏");
    }

    public void GotoBattleground(Battleground bg, Formation form) {
        for(int i = 0; i < form.getNumber(); i++) {
            if(bg.getSpot()[form.getLayout()[i].getX()][form.getLayout()[i].getY()].SpotEmpty() == true) {
                //this.setCoordinate(bg.getSpot()[form.getLayout()[i].getX()][form.getLayout()[i].getY()].getCoordinate());
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