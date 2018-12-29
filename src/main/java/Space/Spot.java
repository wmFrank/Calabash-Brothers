package Space;

import Forms.*;
import Role.*;

public class Spot {
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