package invertedIndex;

import java.util.Vector;

public class Pair2 {
    int type;
    Vector<String>Words;
    Pair2(int t,Vector<String>word){
        type=t;
        Words=new Vector<>();
        for(String w:word){
            Words.addLast(w);
        }
    }
}
