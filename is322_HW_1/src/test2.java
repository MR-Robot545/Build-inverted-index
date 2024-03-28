import java.io.*;

public class test2 {
    public static void main(String[] args) throws IOException {
        String files = "C:\\Users\\asdan\\Desktop\\ir_Assignment\\tmp11\\tmp11\\rl\\collection\\p1";

        BufferedReader file = new BufferedReader(new FileReader(files));
        String ln;
        int flen = 0;
        while ((ln = file.readLine()) != null) {
            System.out.println(ln);
        }
    }
}
