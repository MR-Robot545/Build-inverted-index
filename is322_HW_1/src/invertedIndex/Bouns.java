package invertedIndex;

import java.util.Vector;

public class Bouns{
    String make(Index5 single,BiwordIndex bi,String Line){
        String res="";
        boolean ok=false;
        Vector<Pair>words=new Vector<>();
        // word   type of search(single=1,biword=2)     vector of word (index type=1 : one word ,type =2 :vectors of word)
        // 1   automated  (single word)
        // 2   specific -> information (words>=2).....
        Vector<String>v=new Vector<>();
        String s="";
        for (int i=0;i<Line.length();i++){
            if(Line.charAt(i)=='"'&&!ok){
                ok=true;
                continue;
            }
//            specific  information
            if(Line.charAt(i)=='"'&&ok){
                ok=false;
                if(!s.isEmpty()){
                    s=s.toLowerCase();
                    v.add(s);
                }
                //""
                if(!v.isEmpty()){
                    words.add(new Pair(2,v));
                }
                v.clear();
                s="";
                continue;
            }
            if(Line.charAt(i)==' '){
                if(!s.isEmpty()){
                    s=s.toLowerCase();
                    v.add(s);
                }
                if(!ok){
                    if(!v.isEmpty()){
                        words.add(new Pair(1,v));
                        v.clear();
                    }
                }
                s="";
                continue;
            }
            s+=Line.charAt(i);
        }
        if(!s.isEmpty()){
            v.add(s);
            if(!v.isEmpty()){
                words.add(new Pair(1,v));
                v.clear();
            }
        }
        Posting post=null;
        for (int i=0;i<words.size();i++){
            if(words.get(i).num==1){
                if(post==null){
                    post=single.find_24_01(words.get(i).w.get(0)).post;
                }else{
                    post=single.intersect(post,single.find_24_01(words.get(i).w.get(0)).post);
                }
            }else{
                // specific information
                String line="";
                for(String word:words.get(i).w){
                    if(line.isEmpty()){
                        line+=word;
                    }else{
                        line+=" ";
                        line+=word;
                    }
                }
                if(post==null){
                    post=bi.find_biword(line).post;
                }else{
                    post=single.intersect(post,bi.find_biword(line).post);
                }
            }
        }
        while (post != null) {
            //System.out.println("\t" + sources.get(num));
            res += "\t" + post.docId + " - " + single.sources.get(post.docId).title + " - " + single.sources.get(post.docId).length + "\n";
            post = post.next;
        }
        if(res.isEmpty()){
            res="notfound";
        }
        return res;
    }
}
// "specific information" automated