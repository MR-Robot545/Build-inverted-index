package invertedIndex;

import java.util.Vector;

public class Pair {
    int num;
    Vector<String>w;
    Pair(int num,Vector<String> w){
        this.w=new Vector<>();
        this.num=num;
        for (String u:w){
            this.w.add(u);
        }
    }
}
