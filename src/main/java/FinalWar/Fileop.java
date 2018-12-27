package FinalWar;

import java.io.*;
import java.util.*;

public class Fileop {
    public static void FileWrite(String filepath, int x)
    {
        try {
            FileWriter fw = new FileWriter(filepath);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(String.valueOf(x));
            bw.newLine();
            bw.flush();
            bw.close();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int FileRead(String filepath)
    {
        int x = 0;
        try {
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String s = br.readLine();
            x = Integer.valueOf(s);
            br.close();
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return x;
    }
}
