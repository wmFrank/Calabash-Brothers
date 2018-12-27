import org.junit.Test;
import FinalWar.*;
import java.io.File;
import static org.junit.Assert.assertEquals;

public class FileopTest {

    @Test
    public void CorrectTest() {
        String filepath = "testio.txt";
        int to_x = 100;
        int from_x = 0;

        Fileop.FileWrite(filepath,to_x);
        from_x = Fileop.FileRead(filepath);
        File f = new File("testio.txt");
        if(f.exists() && f.isFile())
            f.delete();
        assertEquals(to_x,from_x);
    }

    @Test(timeout = 2000)
    public void TimeTest(){
        for(int i = 0; i < 300; i++) {
            String filepath = "testio.txt";
            int to_x = 100;
            int from_x = 0;

            Fileop.FileWrite(filepath, to_x);
            from_x = Fileop.FileRead(filepath);
            File f = new File("testio.txt");
            if (f.exists() && f.isFile())
                f.delete();
        }
    }
}