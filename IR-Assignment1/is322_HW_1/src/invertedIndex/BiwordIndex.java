package invertedIndex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.FileWriter;
import java.util.Vector;


public class BiwordIndex extends Index5 {

    @Override
    public int indexOneLine(String ln, int fid) {
        int flen = 0;

        String[] words = ln.split("\\W+");
        flen += words.length;

        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if (stopWord(word)) {
                continue;
            }
            word = stemWord(word);

            // Process single word
            if (!index.containsKey(word)) {
                index.put(word, new DictEntry());
            }
            if (!index.get(word).postingListContains(fid)) {
                index.get(word).doc_freq += 1;
                if (index.get(word).pList == null) {
                    index.get(word).pList = new Posting(fid);
                    index.get(word).last = index.get(word).pList;
                } else {
                    index.get(word).last.next = new Posting(fid);
                    index.get(word).last = index.get(word).last.next;
                }
            } else {
                index.get(word).last.dtf += 1;
            }
            index.get(word).term_freq += 1;

            if (word.equalsIgnoreCase("lattice")) {
                System.out.println("  <<" + index.get(word).getPosting(1) + ">> " + ln);
            }

            // Process biword
            if (i < words.length - 1) {
                String nextWord = words[i + 1].toLowerCase();
                if (!stopWord(nextWord)) {
                    String biword = word + "_" + nextWord;
                    if (!index.containsKey(biword)) {
                        index.put(biword, new DictEntry());
                    }
                    if (!index.get(biword).postingListContains(fid)) {
                        index.get(biword).doc_freq += 1;
                        if (index.get(biword).pList == null) {
                            index.get(biword).pList = new Posting(fid);
                            index.get(biword).last = index.get(biword).pList;
                        } else {
                            index.get(biword).last.next = new Posting(fid);
                            index.get(biword).last = index.get(biword).last.next;
                        }
                    } else {
                        index.get(biword).last.dtf += 1;
                    }
                    index.get(biword).term_freq += 1;
                }
            }
        }
        return flen;
    }


//    public String find_biword(String phrase) {
//        StringBuilder result = new StringBuilder();
//        String[] words = phrase.split("\\s+"); // Split by whitespace
//        int len = words.length;
//
//        if (len < 2) {
//            return "Biword search requires at least two words.";
//        }
//
//        try (FileWriter writer = new FileWriter("biword_search_results.txt")) {
//            // Process each pair of consecutive words as a biword
//            for (int i = 0; i < len - 1; i++) {
//                String biword = words[i].toLowerCase() + "_" + words[i + 1].toLowerCase();
//                if (index.containsKey(biword)) {
//                    Posting posting = index.get(biword).pList;
//                    while (posting != null) {
//                        result.append("\t").append(posting.docId).append(" - ").append(sources.get(posting.docId).title).append(" - ").append(sources.get(posting.docId).length).append("\n");
//                        posting = posting.next;
//                    }
//                }
//            }
//            if (result.length() == 0) {
//                writer.write("No documents found for the given biword(s).");
//            } else {
//                writer.write(result.toString());
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        if (result.length() == 0) {
//            return "No documents found for the given biword(s).";
//        }
//        return "Biword search results stored in 'biword_search_results.txt'.";
//    }


    public Pair2 find_biword(String phrase) {
        String result = "";
        String[] temp = phrase.split("\\W+");
        int len = temp.length;

        if (len < 2) {
            return new Pair2("Biword search requires at least two words.",null);
        }
        Vector<String> words=new Vector<String>();
        for(String word:temp){
            if(!stopWord(word)){
                words.add(word);
            }
        }
        // Process each pair of consecutive words as a biword
        Posting ans=null;
        for (int i = 0; i < words.size() - 1; i++) {
            String biword = words.get(i).toLowerCase() + "_" + words.get(i + 1).toLowerCase();
            if (index.containsKey(biword)) {
                if(ans==null){
                    ans=index.get(biword).pList;
                }else{
                    ans = intersect(ans, index.get(words.get(i).toLowerCase()).pList);
                }
            }
        }
        Posting t=ans;
        while (ans != null) {
            //System.out.println("\t" + sources.get(num));
            result += "\t" + ans.docId + " - " + sources.get(ans.docId).title + " - " + sources.get(ans.docId).length + "\n";
            ans = ans.next;
        }
        if (result.isEmpty()) {
            result = "No documents found.";
        }
        return new Pair2(result,t);
    }

}
// intro to deep
// intro deep


// automated specific
// automated "specific information" word "words word"
// "specific information"

// line inverted index
// biword
// mix