package Space;

import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.image.ImageView;
import Forms.*;

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
            }
        }
    }
}

