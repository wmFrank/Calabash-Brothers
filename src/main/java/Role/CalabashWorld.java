package Role;

import Forms.*;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class CalabashWorld extends Existence {
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
        imagedie = new ImageView(new Image("icons/Grave3.png",50,50,true,true));
        progressbar = new ProgressBar(1.0F);
        progressbar.setMaxSize(50,12);
        progressbar.setMinSize(50,12);
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