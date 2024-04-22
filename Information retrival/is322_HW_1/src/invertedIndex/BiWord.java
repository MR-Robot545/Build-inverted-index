package invertedIndex;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Deque;

public class BiWord{
    public Pair1 find_biword(String phrase,Index5 in) { // any mumber of terms non-optimized search
        String result = "";
        String[] words = phrase.split("\\W+");
        int len = words.length;
        if(len<2){
            return new Pair1("not found",null);
        }
        Deque<String> dq=new ArrayDeque<>();
        Posting posting=null;
        for(String word:words){
            word=word.toLowerCase();
            if(in.stopWord(word))continue;
            word=in.stemWord(word);
            if(dq.size()<2){
                dq.addLast(word);
            }
            if(dq.size()==2){
                String temp=dq.getFirst()+'_'+dq.getLast();
                if(in.index.containsKey(temp)){
                    if(posting==null){
                        posting=in.index.get(temp).pList;
                    }else{
                        posting=in.intersect(posting,in.index.get(temp).pList);
                    }
                }else{
                    return new Pair1("not found",null);
                }
                dq.removeFirst();
            }
        }
        Posting Finallist=posting;
        while (posting != null) {
            //System.out.println("\t" + sources.get(num));
            result += "\t" + posting.docId + " - " + in.sources.get(posting.docId).title + " - " + in.sources.get(posting.docId).length + "\n";
            posting = posting.next;
        }
        if(result.isEmpty()){
            result="not found";
        }
        return new Pair1(result,Finallist);
    }
}
