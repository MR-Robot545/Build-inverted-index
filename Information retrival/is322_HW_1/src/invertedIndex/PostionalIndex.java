package invertedIndex;

import java.io.*;
import java.util.*;

public class PostionalIndex {
    public String findpostional(String line,Index5 index){

        String[] words=line.split("\\W+");
        Posting p=null;
        int y=0;
        while (y<words.length&&index.stopWord(words[y].toLowerCase()))y++;
        if(y<words.length){
            p=index.index.get(words[y].toLowerCase()).pList;
        }
        Vector<Integer>ans=new Vector<>();
        while (p!=null){
            boolean exist=false;
            for(Integer pos:p.postions){
                int x=pos;
                boolean ok=false;
                for (int i=y+1;i<words.length;i++){
                    if(index.stopWord(words[i].toLowerCase()))continue;
                    if(index.index.get(words[i].toLowerCase()).get_doc(p.docId)!=null){
                        if(!index.index.get(words[i].toLowerCase()).get_doc(p.docId).postions.contains(++x)){
                            ok=true;
                            break;
                        }
                    }else{
                        ok=true;
                        break;
                    }
                }
                if(!ok){
                    exist=true;
                    break;
                }
            }
            if(exist){
                ans.addLast(p.docId);
            }
            p=p.next;
        }
        String res="";
        for(Integer docs:ans) {
            //System.out.println("\t" + sources.get(num));
            res += "\t" + docs + " - " + index.sources.get(docs).title + " - " + index.sources.get(docs).length + "\n";
        }
        return (res.isEmpty()?"Not Found":res) ;
    }
}

