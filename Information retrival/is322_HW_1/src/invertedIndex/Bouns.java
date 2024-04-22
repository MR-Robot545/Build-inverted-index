package invertedIndex;

import java.util.Vector;

public class Bouns {
    public String find_Mixed(Index5 si,BiWord bi,String Phrase){
        Vector<Pair2>Word=new Vector<>();
        Vector<String>temp=new Vector<>();
        boolean ok=false;
        String ww="";
        for (int i=0;i<Phrase.length();i++){
            if(Phrase.charAt(i)=='"'&&!ok){
                ok=true;
                continue;
            }
//            specific  information
            if(Phrase.charAt(i)=='"'&&ok){
                ok=false;
                if(!ww.isEmpty()){
                    ww=ww.toLowerCase();
                    temp.add(ww);
                }
                //""
                if(!temp.isEmpty()){
                    Word.add(new Pair2(2,temp));
                }
                temp.clear();
                ww="";
                continue;
            }
            if(Phrase.charAt(i)==' '){
                if(!ww.isEmpty()){
                    ww=ww.toLowerCase();
                    temp.add(ww);
                }
                if(!ok){
                    if(!temp.isEmpty()){
                        Word.add(new Pair2(1,temp));
                        temp.clear();
                    }
                }
                ww="";
                continue;
            }
            ww+=Phrase.charAt(i);
        }
        if(!ww.isEmpty()){
            temp.add(ww);
            if(!temp.isEmpty()){
                Word.add(new Pair2(1,temp));
                temp.clear();
            }
        }
        String res="";
        Posting finallist=null;
        for (int i=0;i<Word.size();i++){
            if(Word.get(i).type==1){
                if(finallist==null){
                    finallist=si.find_24_01(Word.get(i).Words.get(0)).post;
                }else{
                    finallist=si.intersect(finallist,si.find_24_01(Word.get(i).Words.get(0)).post);
                }
            }else{
                // specific information
                String line="";
                for(String word:Word.get(i).Words){
                    if(line.isEmpty()){
                        line+=word;
                    }else{
                        line+=" ";
                        line+=word;
                    }
                }
                if(finallist==null){
                    finallist=bi.find_biword(line,si).post;
                }else{
                    finallist=si.intersect(finallist,bi.find_biword(line,si).post);
                }
            }
        }
        while (finallist != null) {
            //System.out.println("\t" + sources.get(num));
            res += "\t" + finallist.docId + " - " + si.sources.get(finallist.docId).title + " - " + si.sources.get(finallist.docId).length + "\n";
            finallist = finallist.next;
        }
        if(res.isEmpty()){
            res="notfound";
        }
        return res;
    }
}
