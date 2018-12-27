package FinalWar;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;

public class Battleground {
    private Spot[][] spots;          //二维数组模拟二维战场
    private int num;

    public Battleground(int size) {
        num = size;
        spots = new Spot[num][num];
        for(int i = 0; i < num; i++)
            for(int j = 0; j < num; j++)
                spots[i][j] = new Spot(new Coordinate(i,j));
    }

    public Spot[][] getSpot() {
        return spots;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int a) {
        num = a;
    }

    public void show(Pane pane) {                 //以print的方式打印出战场的布局
        for(int i = 0; i < num; i++) {
            for(int j = 0; j < num; j++) {
                if(!spots[i][j].SpotEmpty()){
                    ImageView img = spots[i][j].getExistence().getImageview();
                    img.setLayoutX(j*50);
                    img.setLayoutY(i*50 + 40);
                    pane.getChildren().add(img);
                    ProgressBar pro = spots[i][j].getExistence().getProgressbar();
                    pro.setLayoutX(j*50);
                    pro.setLayoutY(i*50 + 75);
                    pane.getChildren().add(pro);
                }
//                if(i == 0 || i == num - 1 || j == 0 || j == num - 1)
//                    System.out.print("*" + "\t");
//                else if(spots[i][j].SpotEmpty() == false)
//                    System.out.print(spots[i][j].getExistence().getName() + "\t");
//                else
//                    System.out.print("\t");
            }
            // System.out.println();
        }
    }
}

class Spot {
    private Coordinate coordinate;
    private CalabashWorld existence;     //该位置上站立的生物（可以为空）
    private boolean flag;

    public Spot() {
        coordinate = null;
        existence = null;
        flag = false;
    }

    public Spot(Coordinate a) {
        coordinate = a;
        existence = null;
        flag = false;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate a) {
        coordinate = a;
    }

    public CalabashWorld getExistence() {
        return existence;
    }

    public boolean getFlag() {
        return flag;
    }

    public void setFlag(boolean a) {
        flag = a;
    }

    public boolean SpotEmpty() {             //判断该位置是否为空
        if(flag == false)
            return true;
        else
            return false;
    }

    public void setExistence(CalabashWorld a) {    //将某个生物放到该位置上
        if(SpotEmpty() == true) {
            existence = a;
            flag = true;
        }
        else
            System.out.println("摆放东西失败，该位置已被占领");
    }

    public void removeExistence() {           //将某个生物移出该位置
        if(SpotEmpty() == false) {
            existence = null;
            flag = false;
        }
        else
            System.out.println("移出东西失败，该位置没有东西");
    }
}