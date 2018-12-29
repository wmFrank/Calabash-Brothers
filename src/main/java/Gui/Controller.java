package Gui;

import Forms.*;
import Space.*;
import Role.*;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.text.*;
import javafx.scene.effect.*;
import javafx.scene.paint.*;
import javafx.scene.Parent;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Collections;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.util.Duration;
import java.io.*;
import java.util.List;
import java.util.ArrayList;

public class Controller {
    private Lineup lineup;                     //葫芦娃队列
    private Grandpa grandpa;                   //老爷爷
    private DemonSnake demonsnake;             //蛇精
    private DemonScorpion demonscorpion;       //蝎子精
    private Lackey[] lackey;                   //小喽啰队列
    private Battleground battleground;         //战场
    private Formation formation1;              //阵型1（好人使用）
    private Formation formation2;              //阵型2（坏人使用）
    private Pane root;
    private List<CalabashWorld> good;
    private List<CalabashWorld> bad;
    private boolean task0_end;
    private boolean task1_end;
    private int steps;
    private int fight_num;
    private int turns;
    private Label lb;
    private boolean isappend;
    private TextArea texta;

    public Controller(){
        lineup = new Lineup();
        grandpa = new Grandpa();
        demonsnake = new DemonSnake();
        demonscorpion = new DemonScorpion();
        lackey = new Lackey[6];
        for(int i = 0; i < 6; i++)
            lackey[i] = new Lackey();
        for(int i = 0; i < 6; i++)
            lackey[i].setName("小喽啰" + (i + 1));
        battleground = new Battleground(20);
        good = Collections.synchronizedList(new ArrayList<CalabashWorld>());
        bad = Collections.synchronizedList(new ArrayList<CalabashWorld>());
        root = new Pane();
        formation1 = new Formation(7);
        formation2 = new Formation(7);
        task0_end = false;
        task1_end = false;
        steps = 0;
        fight_num = 0;
        turns = 0;
        lb = new Label();
        isappend = false;
        texta = new TextArea();
    }

    public void CalabashBroShowup(Coordinate c, String a) {
        lineup.Randomsort();
        lineup.Bubblesort();
        lineup.getLine()[0].SetLeaderground(c, formation1);
        lineup.getLine()[0].GiveOrder(a, formation1);
        for(int i = 0; i < 7; i++)
            lineup.getLine()[i].GotoBattleground(battleground, formation1);
    }

    public void GrandpaShowup(Coordinate c) {
        grandpa.Lookatside(battleground, c);
    }

    public void LackeyShowup(Coordinate c, String a) {
        demonscorpion.SetLeaderground(c, formation2);
        demonscorpion.GiveOrder(a, formation2);
        demonscorpion.GotoBattleground(battleground, formation2);
        for(int i = 0; i < 6; i++)
            lackey[i].GotoBattleground(battleground, formation2);
    }

    public void DemonSnakeShowup(Coordinate c) {
        demonsnake.Lookatside(battleground, c);
    }

    public void AllLeaveaway() {
        for(int i = 0; i < 7; i++)
            lineup.getLine()[i].LeaveBattleground(battleground);
        grandpa.LeaveBattleground(battleground);
        demonscorpion.LeaveBattleground(battleground);
        for(int i = 0; i < 6; i++)
            lackey[i].LeaveBattleground(battleground);
        demonsnake.LeaveBattleground(battleground);
    }

    public void tell() {
        CalabashBroShowup(new Coordinate(12,5), "长蛇");
        LackeyShowup(new Coordinate(12,12), "方门");
        GrandpaShowup(new Coordinate(9,2));
        DemonSnakeShowup(new Coordinate(9,17));
        battleground.show(root);
        good.clear();
        bad.clear();
        good.add(grandpa);
        for(int i = 0; i < 7; i++)
            good.add(lineup.getLine()[i]);
        bad.add(demonscorpion);
        bad.add(demonsnake);
        for(int i = 0; i < 6; i++)
            bad.add(lackey[i]);
    }

    public void ready(){
        try {
            Button button0 = new Button("开始战斗(空格)");
            button0.setOnAction((ActionEvent t) -> {
                turns = 0;
                task0_end = false;
                fight_num++;
                isappend = false;
                texta.clear();
                texta.appendText("战斗开始\n");
                StartGame();
            });

            Button button1 = new Button("结束战斗(Z)");
            button1.setOnAction((ActionEvent t) -> {
                EndGame();
            });

            Button button3 = new Button("开始回放(L)");
            button3.setOnAction((ActionEvent t) -> {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("选择记录文件进行回放");
                File selectedFile = fileChooser.showOpenDialog(null);
                if(selectedFile != null) {
                    steps = 0;
                    task1_end = false;
                    texta.clear();
                    texta.appendText("回放开始\n");
                    StartRepeat(selectedFile.getPath());
                }
            });

            Button button4 = new Button("结束回放Repeat(D)");
            button4.setOnAction((ActionEvent t) -> {
                EndRepeat();
            });

            Label label1 = new Label();
            label1.setText("回合数：");
            lb.setText(String.valueOf(turns));
            HBox hbox1 = new HBox();
            hbox1.setSpacing(5);
            hbox1.getChildren().addAll(label1,lb);

            HBox hbox = new HBox();
            hbox.setSpacing(20);
            hbox.getChildren().addAll(button0,button1,button3,button4,hbox1);
            hbox.setLayoutX(50);
            hbox.setLayoutY(5);
            root.getChildren().add(hbox);

            root.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent event) {
                    if(event.getCode() == KeyCode.SPACE){
                        turns = 0;
                        task0_end = false;
                        fight_num++;
                        isappend = false;
                        texta.clear();
                        texta.appendText("战斗开始\n");
                        StartGame();
                    }
                    else if(event.getCode() == KeyCode.Z){
                        EndGame();
                    }
//                    else if(event.getCode() == KeyCode.X){
//                        RecordShow();
//                    }
                    else if(event.getCode() == KeyCode.L){
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("选择记录文件进行回放");
                        File selectedFile = fileChooser.showOpenDialog(null);
                        if(selectedFile != null) {
                            steps = 0;
                            task1_end = false;
                            texta.clear();
                            texta.appendText("回放开始\n");
                            StartRepeat(selectedFile.getPath());
                        }
                    }
                    else if(event.getCode() == KeyCode.D){
                        EndRepeat();
                    }
                }
            });

            ImageView bg = new ImageView(new Image("icons/Battle2.jpg",1000,1000,true,true));
            bg.setLayoutX(0);
            bg.setLayoutY(40);
            root.getChildren().add(bg);

            List<ImageView> drum1;
            drum1 = Collections.synchronizedList(new ArrayList<ImageView>());
            for(int i = 0; i < 6; i++)
            {
                drum1.add(new ImageView(new Image("icons/Drum1.png",50,50,true,true)));
                drum1.get(i).setLayoutX(50);
                drum1.get(i).setLayoutY(50 * (4 + 2 * i) + 40);
                root.getChildren().add(drum1.get(i));
            }

            List<ImageView> drum2;
            drum2 = Collections.synchronizedList(new ArrayList<ImageView>());
            for(int i = 0; i < 6; i++)
            {
                drum2.add(new ImageView(new Image("icons/Drum2.png",50,50,true,true)));
                drum2.get(i).setLayoutX(50 * 18);
                drum2.get(i).setLayoutY(50 * (4 + 2 * i) + 40);
                root.getChildren().add(drum2.get(i));
            }

            texta.setLayoutX(1000);
            texta.setLayoutY(40);
            texta.setMaxSize(270,1000);
            texta.setMinSize(270,1000);
            root.getChildren().add(texta);

            Text text1 = new Text();
            text1.setText("【葫芦峡谷】风起云涌");
            text1.setFill(Color.RED);
            text1.setFont(Font.font(null, FontWeight.BOLD, 50));

            Reflection r = new Reflection();
            r.setFraction(0.7);

            text1.setEffect(r);
            text1.setLayoutX(200);
            text1.setLayoutY(120);
            root.getChildren().add(text1);

        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public Parent createContent() {
        ready();
        tell();
        Opening();
        return root;
    }

    public synchronized void ClearPane(Pane pane)
    {
        for(int i = 0; i < 8; i++) {
            pane.getChildren().remove(good.get(i).getProgressbar());
            pane.getChildren().remove(good.get(i).getImageview());
        }
        for(int i = 0; i < 8; i++) {
            pane.getChildren().remove(bad.get(i).getProgressbar());
            pane.getChildren().remove(bad.get(i).getImageview());
        }
    }

    public synchronized void Refresh(Pane pane)
    {
        for(int i = 0; i < 8; i++) {
            pane.getChildren().remove(good.get(i).getProgressbar());
            pane.getChildren().remove(good.get(i).getImageview());
        }
        for(int i = 0; i < 8; i++) {
            pane.getChildren().remove(bad.get(i).getProgressbar());
            pane.getChildren().remove(bad.get(i).getImageview());
        }
        for(int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                if (!battleground.getSpot()[i][j].SpotEmpty()) {
                    ImageView img = battleground.getSpot()[i][j].getExistence().getImageview();
                    img.setLayoutX(j * 50);
                    img.setLayoutY(i * 50 + 40);
                    pane.getChildren().add(img);
                    if(battleground.getSpot()[i][j].getExistence().getState() == 1) {
                        ProgressBar pro = battleground.getSpot()[i][j].getExistence().getProgressbar();
                        pro.setLayoutX(j * 50);
                        pro.setLayoutY(i * 50 + 75);
                        pane.getChildren().add(pro);
                    }
                }
            }
        }
    }

    public synchronized boolean Hitornot(int hitrate)
    {
        boolean ret = false;
        Random random = new Random();
        int number = random.nextInt(10);
        if(number < hitrate)
            ret = true;
        return ret;
    }

    public synchronized boolean Dieornot(CalabashWorld obj2)
    {
        if(obj2.getHp() <= 0)
            return true;
        else
            return false;
    }

    public synchronized void oneBattle(CalabashWorld obj1, CalabashWorld obj2)
    {
        texta.appendText(obj1.getName() + "[" + obj1.getCoordinate().getX() + "," + obj1.getCoordinate().getY() + "]" + " 攻击 " + obj2.getName() + "[" + obj2.getCoordinate().getX() + "," + obj2.getCoordinate().getY() + "]" + "\n");
        if(Hitornot(obj1.getHitrate()) == true) {
            int damage = obj1.getDamage() - obj2.getDefence();
            int oldhp = obj2.getHp();
            obj2.setHp(oldhp - damage);
            if(Dieornot(obj2) == true)
            {
                root.getChildren().remove(obj2.getImageview());
                obj2.setState(0);
                if(obj2.getQuality() == "Good")
                    RecordToFile(0,good.indexOf(obj2),99,99,99,99,obj2.getHp());
                else
                    RecordToFile(1,bad.indexOf(obj2),99,99,99,99,obj2.getHp());
                texta.appendText(obj2.getName() + " [" + obj2.getCoordinate().getX() + "," + obj2.getCoordinate().getY() + "]" + " 死亡\n");
            }
            else
            {
                double now = 1.0 * obj2.getHp() / obj2.getLife();
                obj2.setProgressbar(now);
                if(obj2.getQuality() == "Good")
                    RecordToFile(0,good.indexOf(obj2),0,0,0,0,obj2.getHp());
                else
                    RecordToFile(1,bad.indexOf(obj2),0,0,0,0,obj2.getHp());
                //texta.appendText(obj2.getName() + "的生命值变为" + obj2.getHp() + "\n");
            }
        }
    }

    public synchronized boolean Combat(CalabashWorld obj, String quality)
    {
        if(quality == "Good")
        {
            int fromx = obj.getCoordinate().getX();
            int fromy = obj.getCoordinate().getY();
            if(fromx - 1 >= 0 && !battleground.getSpot()[fromx - 1][fromy].SpotEmpty())
            {
                CalabashWorld tmp = battleground.getSpot()[fromx - 1][fromy].getExistence();
                if(tmp.getQuality() == "Bad" && tmp.getState() == 1)
                {
                    oneBattle(obj,tmp);
                    return true;
                }
            }
            if(fromx + 1 <= 19 && !battleground.getSpot()[fromx + 1][fromy].SpotEmpty())
            {
                CalabashWorld tmp = battleground.getSpot()[fromx + 1][fromy].getExistence();
                if(tmp.getQuality() == "Bad" && tmp.getState() == 1)
                {
                    oneBattle(obj,tmp);
                    return true;
                }
            }
            if(fromy - 1 >= 0 && !battleground.getSpot()[fromx][fromy - 1].SpotEmpty())
            {
                CalabashWorld tmp = battleground.getSpot()[fromx][fromy - 1].getExistence();
                if(tmp.getQuality() == "Bad" && tmp.getState() == 1)
                {
                    oneBattle(obj,tmp);
                    return true;
                }
            }
            if(fromy + 1 <= 19 && !battleground.getSpot()[fromx][fromy + 1].SpotEmpty())
            {
                CalabashWorld tmp = battleground.getSpot()[fromx][fromy + 1].getExistence();
                if(tmp.getQuality() == "Bad" && tmp.getState() == 1)
                {
                    oneBattle(obj,tmp);
                    return true;
                }
            }
        }
        else
        {
            int fromx = obj.getCoordinate().getX();
            int fromy = obj.getCoordinate().getY();
            if(fromx - 1 >= 0 && !battleground.getSpot()[fromx - 1][fromy].SpotEmpty())
            {
                CalabashWorld tmp = battleground.getSpot()[fromx - 1][fromy].getExistence();
                if(tmp.getQuality() == "Good" && tmp.getState() == 1)
                {
                    oneBattle(obj,tmp);
                    return true;
                }
            }
            if(fromx + 1 <= 19 && !battleground.getSpot()[fromx + 1][fromy].SpotEmpty())
            {
                CalabashWorld tmp = battleground.getSpot()[fromx + 1][fromy].getExistence();
                if(tmp.getQuality() == "Good" && tmp.getState() == 1)
                {
                    oneBattle(obj,tmp);
                    return true;
                }
            }
            if(fromy - 1 >= 0 && !battleground.getSpot()[fromx][fromy - 1].SpotEmpty())
            {
                CalabashWorld tmp = battleground.getSpot()[fromx][fromy - 1].getExistence();
                if(tmp.getQuality() == "Good" && tmp.getState() == 1)
                {
                    oneBattle(obj,tmp);
                    return true;
                }
            }
            if(fromy + 1 <= 19 && !battleground.getSpot()[fromx][fromy + 1].SpotEmpty())
            {
                CalabashWorld tmp = battleground.getSpot()[fromx][fromy + 1].getExistence();
                if(tmp.getQuality() == "Good" && tmp.getState() == 1)
                {
                    oneBattle(obj,tmp);
                    return true;
                }
            }
        }
        return false;
    }

    public synchronized boolean Debuff(CalabashWorld obj)
    {
        int oldhp = obj.getHp();
        obj.setHp(oldhp - obj.getTired());
        if(Dieornot(obj) == true)
        {
            root.getChildren().remove(obj.getImageview());
            obj.setState(0);
            if(obj.getQuality() == "Good")
                RecordToFile(0,good.indexOf(obj),99,99,99,99,obj.getHp());
            else
                RecordToFile(1,bad.indexOf(obj),99,99,99,99,obj.getHp());
            texta.appendText(obj.getName() + " [" + obj.getCoordinate().getX() + "," + obj.getCoordinate().getY() + "]" + " 死亡\n");
            return true;
        }
        else
        {
            double now = 1.0 * obj.getHp() / obj.getLife();
            obj.setProgressbar(now);
            if(obj.getQuality() == "Good")
                RecordToFile(0, good.indexOf(obj), 0, 0, 0, 0, obj.getHp());
            else
                RecordToFile(1,bad.indexOf(obj),0,0,0,0,obj.getHp());
            //texta.appendText(obj.getName() + "的生命值变为" + obj.getHp() + "\n");
            return false;
        }
    }

    public synchronized void MoveRandom(CalabashWorld obj)
    {
        int obj_x = obj.getCoordinate().getX();
        int obj_y = obj.getCoordinate().getY();
        Random ran = new Random();
        int times = 10;
        while(true)
        {
            int getran = ran.nextInt(4);
            if(getran == 0)
            {
                if(obj_x - 1 >= 0)
                {
                    if(battleground.getSpot()[obj_x - 1][obj_y].SpotEmpty())
                    {
                        battleground.getSpot()[obj_x][obj_y].removeExistence();
                        battleground.getSpot()[obj_x - 1][obj_y].setExistence(obj);
                        obj.getCoordinate().setX(obj_x - 1);
                        if(obj.getQuality() == "Good") {
                            RecordToFile(0, good.indexOf(obj), obj_x, obj_y, obj_x - 1, obj_y,obj.getHp());
                        }else {
                            RecordToFile(1, bad.indexOf(obj), obj_x, obj_y, obj_x - 1, obj_y,obj.getHp());
                        }
                        texta.appendText(obj.getName() + " [" + obj_x + "," + obj_y + "]" + " 移动到 " + "[" + (obj_x - 1) + "," + obj_y + "]" + "\n");
                        break;
                    }
                }
            }
            else if(getran == 1)
            {
                if(obj_x + 1 <= 19)
                {
                    if(battleground.getSpot()[obj_x + 1][obj_y].SpotEmpty())
                    {
                        battleground.getSpot()[obj_x][obj_y].removeExistence();
                        battleground.getSpot()[obj_x + 1][obj_y].setExistence(obj);
                        obj.getCoordinate().setX(obj_x + 1);
                        if(obj.getQuality() == "Good") {
                            RecordToFile(0, good.indexOf(obj), obj_x, obj_y, obj_x + 1, obj_y,obj.getHp());
                        }else {
                            RecordToFile(1, bad.indexOf(obj), obj_x, obj_y, obj_x + 1, obj_y,obj.getHp());
                        }
                        texta.appendText(obj.getName() + " [" + obj_x + "," + obj_y + "]" + " 移动到 " + "[" + (obj_x + 1) + "," + obj_y + "]" + "\n");
                        break;
                    }
                }
            }
            else if(getran == 2)
            {
                if(obj_y - 1 >= 0)
                {
                    if(battleground.getSpot()[obj_x][obj_y - 1].SpotEmpty())
                    {
                        battleground.getSpot()[obj_x][obj_y].removeExistence();
                        battleground.getSpot()[obj_x][obj_y - 1].setExistence(obj);
                        obj.getCoordinate().setY(obj_y - 1);
                        if(obj.getQuality() == "Good") {
                            RecordToFile(0, good.indexOf(obj), obj_x, obj_y, obj_x, obj_y - 1,obj.getHp());
                        }else {
                            RecordToFile(1, bad.indexOf(obj), obj_x, obj_y, obj_x, obj_y - 1,obj.getHp());
                        }
                        texta.appendText(obj.getName() + " [" + obj_x + "," + obj_y + "]" + " 移动到 " + "[" + obj_x + "," + (obj_y - 1) + "]" + "\n");
                        break;
                    }
                }
            }
            else
            {
                if(obj_y + 1 <= 19)
                {
                    if(battleground.getSpot()[obj_x][obj_y + 1].SpotEmpty())
                    {
                        battleground.getSpot()[obj_x][obj_y].removeExistence();
                        battleground.getSpot()[obj_x][obj_y + 1].setExistence(obj);
                        obj.getCoordinate().setY(obj_y + 1);
                        if(obj.getQuality() == "Good") {
                            RecordToFile(0, good.indexOf(obj), obj_x, obj_y, obj_x, obj_y + 1,obj.getHp());
                        }else {
                            RecordToFile(1, bad.indexOf(obj), obj_x, obj_y, obj_x, obj_y + 1,obj.getHp());
                        }
                        texta.appendText(obj.getName() + " [" + obj_x + "," + obj_y + "]" + " 移动到 " + "[" + obj_x + "," + (obj_y + 1) + "]" + "\n");
                        break;
                    }
                }
            }
            times--;
            if(times < 0)
                break;
        }
    }

    public synchronized boolean MoveOn(CalabashWorld obj)
    {
        boolean ret_flag = true;
        if(Debuff(obj) == true)
            return ret_flag;
        if(obj.getQuality() == "Good")
        {
            boolean ret = Combat(obj, "Good");
            if(ret == false) {
                Random ran = new Random();
                int len = bad.size();
                boolean conti = false;
                for(int i = 0; i < len; i++)
                {
                    if(bad.get(i).getState() == 1)
                    {
                        conti = true;
                        break;
                    }
                }
                if(conti == false)
                {
                    ret_flag = false;
                    return ret_flag;
                }
                int times = 20;
                int index = 0;
                while(true)
                {
                    index = ran.nextInt(len);
                    if(bad.get(index).getState() == 1)
                        break;
                    times--;
                    if(times < 0)
                        return ret_flag;
                }
                CalabashWorld badguy = bad.get(index);
                int fromx = obj.getCoordinate().getX();
                int fromy = obj.getCoordinate().getY();
                int tox = badguy.getCoordinate().getX();
                int toy = badguy.getCoordinate().getY();
                boolean do_flag = false;
                if (fromy < toy) {
                    if (battleground.getSpot()[fromx][fromy + 1].SpotEmpty()) {
                        battleground.getSpot()[fromx][fromy].removeExistence();
                        battleground.getSpot()[fromx][fromy + 1].setExistence(obj);
                        obj.getCoordinate().setY(fromy + 1);
                        RecordToFile(0,good.indexOf(obj),fromx,fromy,fromx,fromy+1,obj.getHp());
                        texta.appendText(obj.getName() + " [" + fromx + "," + fromy + "]" + " 移动到 " + "[" + fromx + "," + (fromy + 1) + "]" + "\n");
                        do_flag = true;
                    }
                } else if (fromy > toy) {
                    if (battleground.getSpot()[fromx][fromy - 1].SpotEmpty()) {
                        battleground.getSpot()[fromx][fromy].removeExistence();
                        battleground.getSpot()[fromx][fromy - 1].setExistence(obj);
                        obj.getCoordinate().setY(fromy - 1);
                        RecordToFile(0,good.indexOf(obj),fromx,fromy,fromx,fromy-1,obj.getHp());
                        texta.appendText(obj.getName() + " [" + fromx + "," + fromy + "]" + " 移动到 " + "[" + fromx + "," + (fromy - 1) + "]" + "\n");
                        do_flag = true;
                    }
                } else {
                    if (fromx < tox) {
                        if (battleground.getSpot()[fromx + 1][fromy].SpotEmpty()) {
                            battleground.getSpot()[fromx][fromy].removeExistence();
                            battleground.getSpot()[fromx + 1][fromy].setExistence(obj);
                            obj.getCoordinate().setX(fromx + 1);
                            RecordToFile(0,good.indexOf(obj),fromx,fromy,fromx+1,fromy,obj.getHp());
                            texta.appendText(obj.getName() + " [" + fromx + "," + fromy + "]" + " 移动到 " + "[" + (fromx + 1) + "," + fromy + "]" + "\n");
                            do_flag = true;
                        }
                    } else if (fromx > tox) {
                        if (battleground.getSpot()[fromx - 1][fromy].SpotEmpty()) {
                            battleground.getSpot()[fromx][fromy].removeExistence();
                            battleground.getSpot()[fromx - 1][fromy].setExistence(obj);
                            obj.getCoordinate().setX(fromx - 1);
                            RecordToFile(0,good.indexOf(obj),fromx,fromy,fromx-1,fromy,obj.getHp());
                            texta.appendText(obj.getName() + " [" + fromx + "," + fromy + "]" + " 移动到 " + "[" + (fromx - 1) + "," + fromy + "]" + "\n");
                            do_flag = true;
                        }
                    }
                }
                if(do_flag == false)
                {
                    MoveRandom(obj);
                    return  ret_flag;
                }
            }
            return ret_flag;
        }
        else
        {
            boolean ret = Combat(obj, "Bad");
            if(ret == false) {
                Random ran = new Random();
                int len = good.size();
                boolean conti = false;
                for(int i = 0; i < len; i++)
                {
                    if(good.get(i).getState() == 1)
                    {
                        conti = true;
                        break;
                    }
                }
                if(conti == false)
                {
                    ret_flag = false;
                    return ret_flag;
                }
                int times = 20;
                int index = 0;
                while(true)
                {
                    index = ran.nextInt(len);
                    if(good.get(index).getState() == 1)
                        break;
                    times--;
                    if(times < 0)
                        return ret_flag;
                }
                CalabashWorld goodguy = good.get(index);
                int fromx = obj.getCoordinate().getX();
                int fromy = obj.getCoordinate().getY();
                int tox = goodguy.getCoordinate().getX();
                int toy = goodguy.getCoordinate().getY();
                boolean do_flag = false;
                if (fromy < toy) {
                    if (battleground.getSpot()[fromx][fromy + 1].SpotEmpty()) {
                        battleground.getSpot()[fromx][fromy].removeExistence();
                        battleground.getSpot()[fromx][fromy + 1].setExistence(obj);
                        obj.getCoordinate().setY(fromy + 1);
                        RecordToFile(1,bad.indexOf(obj),fromx,fromy,fromx,fromy+1,obj.getHp());
                        texta.appendText(obj.getName() + " [" + fromx + "," + fromy + "]" + " 移动到 " + "[" + fromx + "," + (fromy + 1) + "]" + "\n");
                        do_flag = true;
                    }
                } else if (fromy > toy) {
                    if (battleground.getSpot()[fromx][fromy - 1].SpotEmpty()) {
                        battleground.getSpot()[fromx][fromy].removeExistence();
                        battleground.getSpot()[fromx][fromy - 1].setExistence(obj);
                        obj.getCoordinate().setY(fromy - 1);
                        RecordToFile(1,bad.indexOf(obj),fromx,fromy,fromx,fromy-1,obj.getHp());
                        texta.appendText(obj.getName() + " [" + fromx + "," + fromy + "]" + " 移动到 " + "[" + fromx + "," + (fromy - 1) + "]" + "\n");
                        do_flag = true;
                    }
                } else {
                    if (fromx < tox) {
                        if (battleground.getSpot()[fromx + 1][fromy].SpotEmpty()) {
                            battleground.getSpot()[fromx][fromy].removeExistence();
                            battleground.getSpot()[fromx + 1][fromy].setExistence(obj);
                            obj.getCoordinate().setX(fromx + 1);
                            RecordToFile(1,bad.indexOf(obj),fromx,fromy,fromx+1,fromy,obj.getHp());
                            texta.appendText(obj.getName() + " [" + fromx + "," + fromy + "]" + " 移动到 " + "[" + (fromx + 1) + "," + fromy + "]" + "\n");
                            do_flag = true;
                        }
                    } else if (fromx > tox) {
                        if (battleground.getSpot()[fromx - 1][fromy].SpotEmpty()) {
                            battleground.getSpot()[fromx][fromy].removeExistence();
                            battleground.getSpot()[fromx - 1][fromy].setExistence(obj);
                            obj.getCoordinate().setX(fromx - 1);
                            RecordToFile(1,bad.indexOf(obj),fromx,fromy,fromx-1,fromy,obj.getHp());
                            texta.appendText(obj.getName() + " [" + fromx + "," + fromy + "]" + " 移动到 " + "[" + (fromx - 1) + "," + fromy + "]" + "\n");
                            do_flag = true;
                        }
                    }
                }
                if(do_flag == false)
                {
                    MoveRandom(obj);
                    return  ret_flag;
                }
            }
            return ret_flag;
        }
    }

    public synchronized boolean MoveByStep(String a)
    {
        boolean ret_flag = true;
        try {
            FileReader fr = new FileReader(a);
            BufferedReader br = new BufferedReader(fr);
            String s = "";
            for(int i = 0; i < steps; i++)
                s = br.readLine();
            if((s = br.readLine()) == null)
            {
                ret_flag = false;
                return ret_flag;
            }
            String[] str = s.split(" ");
            int quality,index,old_x,old_y,new_x,new_y,hp;
            quality = Integer.valueOf(str[0]);
            index = Integer.valueOf(str[1]);
            old_x = Integer.valueOf(str[2]);
            old_y = Integer.valueOf(str[3]);
            new_x = Integer.valueOf(str[4]);
            new_y = Integer.valueOf(str[5]);
            hp = Integer.valueOf(str[6]);
            if(quality == 0)
            {
                if(old_x == 99 && old_y == 99 && new_x == 99 && new_y == 99) {
                    root.getChildren().remove(good.get(index).getImageview());
                    good.get(index).setState(0);
                    texta.appendText(good.get(index).getName() + " [" + good.get(index).getCoordinate().getX() + "," + good.get(index).getCoordinate().getY() + "]" + " 死亡\n");
                }
                else if(old_x == 0 && old_y == 0 && new_x == 0 && new_y == 0)
                {
                    double rate = 1.0 * hp / good.get(index).getLife();
                    good.get(index).setProgressbar(rate);
                }
                else
                {
                    double rate = 1.0 * hp / good.get(index).getLife();
                    good.get(index).setProgressbar(rate);
                    battleground.getSpot()[old_x][old_y].removeExistence();
                    battleground.getSpot()[new_x][new_y].setExistence(good.get(index));
                    good.get(index).getCoordinate().setX(new_x);
                    good.get(index).getCoordinate().setY(new_y);
                    texta.appendText(good.get(index).getName() + " [" + old_x + "," + old_y + "]" + " 移动到 " + "[" + new_x + "," + new_y + "]" + "\n");
                }
            }
            else
            {
                if(old_x == 99 && old_y == 99 && new_x == 99 && new_y == 99) {
                    root.getChildren().remove(bad.get(index).getImageview());
                    bad.get(index).setState(0);
                    texta.appendText(bad.get(index).getName() + " [" + bad.get(index).getCoordinate().getX() + "," + bad.get(index).getCoordinate().getY() + "]" + " 死亡\n");

                }
                else if(old_x == 0 && old_y == 0 && new_x == 0 && new_y == 0)
                {
                    double rate = 1.0 * hp / bad.get(index).getLife();
                    bad.get(index).setProgressbar(rate);
                }
                else
                {
                    double rate = 1.0 * hp / bad.get(index).getLife();
                    bad.get(index).setProgressbar(rate);
                    battleground.getSpot()[old_x][old_y].removeExistence();
                    battleground.getSpot()[new_x][new_y].setExistence(bad.get(index));
                    bad.get(index).getCoordinate().setX(new_x);
                    bad.get(index).getCoordinate().setY(new_y);
                    texta.appendText(bad.get(index).getName() + " [" + old_x + "," + old_y + "]" + " 移动到 " + "[" + new_x + "," + new_y + "]" + "\n");
                }
            }
            Refresh(root);
            steps++;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret_flag;
    }

    public synchronized void StartGame()
    {
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    ClearPane(root);
                    AllLeaveaway();
                    tell();
                    cancel();
                    System.gc();
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(task,1500);

        TimerTask task_count = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    boolean goodmen = false;
                    for(int i = 0; i < 8; i++)
                    {
                        if(good.get(i).getState() == 1)
                        {
                            goodmen = true;
                            break;
                        }
                    }
                    boolean badmen = false;
                    for(int i = 0; i < 8; i++)
                    {
                        if(bad.get(i).getState() == 1)
                        {
                            badmen = true;
                            break;
                        }
                    }
                    if(badmen == false || goodmen == false)
                    {
                        for(int i = 0; i < 8; i++)
                        {
                            if(good.get(i).getState() == 1)
                            {
                                Victory(good.get(i));
                            }
                        }
                        for(int i = 0; i < 8; i++)
                        {
                            if(bad.get(i).getState() == 1)
                            {
                                Victory(bad.get(i));
                            }
                        }
                        cancel();
                        System.gc();
                    }
                    turns++;
                    lb.setText(String.valueOf(turns));
                });
            }
        };
        Timer timer_count = new Timer();
        timer_count.scheduleAtFixedRate(task_count,2500,1000);

        TimerTask task0 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(good.get(0).getState() == 0) {

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(good.get(0));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer0 = new Timer();
        timer0.scheduleAtFixedRate(task0,2500,1000);

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(good.get(1).getState() == 0) {

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(good.get(1));
                        Refresh(root);
                        if(ret == false)
                        {
                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer1 = new Timer();
        timer1.scheduleAtFixedRate(task1,2500,1000);

        TimerTask task2 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(good.get(2).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(good.get(2));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer2 = new Timer();
        timer2.scheduleAtFixedRate(task2,2500,1000);

        TimerTask task3 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(good.get(3).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(good.get(3));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer3 = new Timer();
        timer3.scheduleAtFixedRate(task3,2500,1000);

        TimerTask task4 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(good.get(4).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(good.get(4));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer4 = new Timer();
        timer4.scheduleAtFixedRate(task4,2500,1000);

        TimerTask task5 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(good.get(5).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(good.get(5));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer5 = new Timer();
        timer5.scheduleAtFixedRate(task5,2500,1000);

        TimerTask task6 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(good.get(6).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(good.get(6));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer6 = new Timer();
        timer6.scheduleAtFixedRate(task6,2500,1000);

        TimerTask task7 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(good.get(7).getState() == 0){
                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(good.get(7));
                        Refresh(root);
                        if(ret == false)
                        {
                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer7 = new Timer();
        timer7.scheduleAtFixedRate(task7,2500,1000);

        TimerTask task00 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(bad.get(0).getState() == 0) {

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(bad.get(0));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer00 = new Timer();
        timer00.scheduleAtFixedRate(task00,2500,1000);

        TimerTask task11 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(bad.get(1).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(bad.get(1));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer11 = new Timer();
        timer11.scheduleAtFixedRate(task11,2500,1000);

        TimerTask task22 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(bad.get(2).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(bad.get(2));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer22 = new Timer();
        timer22.scheduleAtFixedRate(task22,2500,1000);

        TimerTask task33 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(bad.get(3).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(bad.get(3));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer33 = new Timer();
        timer33.scheduleAtFixedRate(task33,2500,1000);

        TimerTask task44 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(bad.get(4).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(bad.get(4));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer44 = new Timer();
        timer44.scheduleAtFixedRate(task44,2500,1000);

        TimerTask task55 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(bad.get(5).getState() == 0) {

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(bad.get(5));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer55 = new Timer();
        timer55.scheduleAtFixedRate(task55,2500,1000);

        TimerTask task66 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(bad.get(6).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(bad.get(6));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer66 = new Timer();
        timer66.scheduleAtFixedRate(task66,2500,1000);

        TimerTask task77 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    if(task0_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    if(bad.get(7).getState() == 0){

                        cancel();
                        System.gc();
                    }
                    else {
                        boolean ret = MoveOn(bad.get(7));
                        Refresh(root);
                        if(ret == false)
                        {

                            cancel();
                            System.gc();
                        }
                    }
                });
            }
        };
        Timer timer77 = new Timer();
        timer77.scheduleAtFixedRate(task77,2500,1000);
    }

    public synchronized void EndGame()
    {
        task0_end = true;
    }

    public synchronized void StartRepeat(String a)
    {
        TimerTask task0 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    ClearPane(root);
                    AllLeaveaway();
                    tell();
                    cancel();
                    System.gc();
                });
            }
        };
        Timer timer0 = new Timer();
        timer0.schedule(task0,1500);

        TimerTask task1 = new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    //move according to the file
                    if(task1_end == true)
                    {
                        cancel();
                        System.gc();
                    }
                    boolean ret = MoveByStep(a);
                    if(ret == false)
                    {
                        cancel();
                        System.gc();
                    }
                });
            }
        };
        Timer timer1 = new Timer();
        timer1.scheduleAtFixedRate(task1,2500,300);

    }

    public synchronized void EndRepeat()
    {
        task1_end = true;
    }

    //保存的文件格式为 Good/Bad(0 for Good / 1 for Bad) index old_x old_y new_x new_y hp
    public synchronized void RecordToFile(int quality, int index, int old_x, int old_y, int new_x, int new_y, int hp)
    {
        try {
            FileWriter fw = new FileWriter("src/main/resources/records/"+ fight_num + ".txt",isappend);
            isappend = true;
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(quality));bw.append(" ");
            bw.write(String.valueOf(index));bw.append(" ");
            bw.write(String.valueOf(old_x));bw.append(" ");
            bw.write(String.valueOf(old_y));bw.append(" ");
            bw.write(String.valueOf(new_x));bw.append(" ");
            bw.write(String.valueOf(new_y));bw.append(" ");
            bw.write(String.valueOf(hp));bw.newLine();
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void RecordShow()
    {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择记录文件进行查看");
            File selectedFile = fileChooser.showOpenDialog(null);
            String path;
            if(selectedFile != null) {
                path = selectedFile.getPath();
            }
            else
            {
                System.out.println("无记录文件被选中");
                return;
            }
            FileReader fr = new FileReader(path);
            BufferedReader br = new BufferedReader(fr);
            String s = "";
            while((s = br.readLine()) != null)
            {
                String[] str = s.split(" ");
                int quality,index,old_x,old_y,new_x,new_y,hp;
                quality = Integer.valueOf(str[0]);
                index = Integer.valueOf(str[1]);
                old_x = Integer.valueOf(str[2]);
                old_y = Integer.valueOf(str[3]);
                new_x = Integer.valueOf(str[4]);
                new_y = Integer.valueOf(str[5]);
                hp = Integer.valueOf(str[6]);
                System.out.println(" quality = " + quality + " index = " + index + " old_x = " + old_x + " old_y = " + old_y + " new_x = " + new_x + " new_y = " + new_y + " hp = " + hp);
            }
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void Opening()
    {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(8);
        timeline.setAutoReverse(true);
        KeyValue keyValue1 = new KeyValue(good.get(1).getImageview().translateXProperty(), 50, Interpolator.LINEAR);
        KeyFrame keyFrame1 = new KeyFrame(Duration.millis(600), keyValue1);
        KeyValue keyValue2 = new KeyValue(good.get(2).getImageview().translateXProperty(), 50, Interpolator.EASE_BOTH);
        KeyFrame keyFrame2 = new KeyFrame(Duration.millis(600), keyValue2);
        KeyValue keyValue3 = new KeyValue(good.get(3).getImageview().translateXProperty(), 50, Interpolator.EASE_IN);
        KeyFrame keyFrame3 = new KeyFrame(Duration.millis(600), keyValue3);
        KeyValue keyValue4 = new KeyValue(good.get(4).getImageview().translateXProperty(), 50, Interpolator.EASE_OUT);
        KeyFrame keyFrame4 = new KeyFrame(Duration.millis(600), keyValue4);
        KeyValue keyValue5 = new KeyValue(good.get(5).getImageview().translateXProperty(), 50, Interpolator.EASE_IN);
        KeyFrame keyFrame5 = new KeyFrame(Duration.millis(600), keyValue5);
        KeyValue keyValue6 = new KeyValue(good.get(6).getImageview().translateXProperty(), 50, Interpolator.EASE_BOTH);
        KeyFrame keyFrame6 = new KeyFrame(Duration.millis(600), keyValue6);
        KeyValue keyValue7 = new KeyValue(good.get(7).getImageview().translateXProperty(), 50, Interpolator.LINEAR);
        KeyFrame keyFrame7 = new KeyFrame(Duration.millis(600), keyValue7);
        KeyValue keyValue11 = new KeyValue(good.get(1).getProgressbar().translateXProperty(), 50, Interpolator.LINEAR);
        KeyFrame keyFrame11 = new KeyFrame(Duration.millis(600), keyValue11);
        KeyValue keyValue22 = new KeyValue(good.get(2).getProgressbar().translateXProperty(), 50, Interpolator.EASE_BOTH);
        KeyFrame keyFrame22 = new KeyFrame(Duration.millis(600), keyValue22);
        KeyValue keyValue33 = new KeyValue(good.get(3).getProgressbar().translateXProperty(), 50, Interpolator.EASE_IN);
        KeyFrame keyFrame33 = new KeyFrame(Duration.millis(600), keyValue33);
        KeyValue keyValue44 = new KeyValue(good.get(4).getProgressbar().translateXProperty(), 50, Interpolator.EASE_OUT);
        KeyFrame keyFrame44 = new KeyFrame(Duration.millis(600), keyValue44);
        KeyValue keyValue55 = new KeyValue(good.get(5).getProgressbar().translateXProperty(), 50, Interpolator.EASE_IN);
        KeyFrame keyFrame55 = new KeyFrame(Duration.millis(600), keyValue55);
        KeyValue keyValue66 = new KeyValue(good.get(6).getProgressbar().translateXProperty(), 50, Interpolator.EASE_BOTH);
        KeyFrame keyFrame66 = new KeyFrame(Duration.millis(600), keyValue66);
        KeyValue keyValue77 = new KeyValue(good.get(7).getProgressbar().translateXProperty(), 50, Interpolator.LINEAR);
        KeyFrame keyFrame77 = new KeyFrame(Duration.millis(600), keyValue77);
        timeline.getKeyFrames().addAll(keyFrame1,keyFrame2,keyFrame3,keyFrame4,keyFrame5,keyFrame6,keyFrame7,keyFrame11,keyFrame22,keyFrame33,keyFrame44,keyFrame55,keyFrame66,keyFrame77);
        timeline.play();
    }

    public synchronized void Victory(CalabashWorld obj)
    {
        RotateTransition rotate = new RotateTransition(Duration.seconds(1), obj.getImageview());
        rotate.setFromAngle(0);
        rotate.setToAngle(720);
        rotate.setCycleCount(5);
        rotate.setAutoReverse(false);
        rotate.play();
    }
}
