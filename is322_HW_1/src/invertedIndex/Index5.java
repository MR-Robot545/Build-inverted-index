/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package invertedIndex;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.io.InputStreamReader;
import static java.lang.Math.log10;
import static java.lang.Math.sqrt;

import java.util.*;
import java.io.PrintWriter;
import java.util.stream.Collectors;

/**
 *
 * @author ehab
 * @author HagarRagea
 * @author YoussefKaram
 * @author Shimaa
 * @author AmalMohammed
 * @author ShahdKhaled
 */
public class Index5 {

    //--------------------------------------------
    int N = 0;
    public Map<Integer, SourceRecord> sources;  // store the doc_id and the file name.

    public HashMap<String, DictEntry> index; // THe inverted index
    //--------------------------------------------

    public Index5() {
        sources = new HashMap<Integer, SourceRecord>();
        index = new HashMap<String, DictEntry>();
    }

    public void setN(int n) {
        N = n;
    }


    //---------------------------------------------
    //  prints the document IDs stored in a posting list given as a parameter
    // prints all the documents in which the posting was mentioned
    public void printPostingList(Posting p) {
        // Iterator<Integer> it2 = hset.iterator();
        System.out.print("[");
        while (p != null) {
            System.out.print("" + p.docId);
            if (p.next != null)
            {
                System.out.print("," );
            }
            p = p.next;
        }
        System.out.println("]");
    }

    //---------------------------------------------
    // Iterates over each entry in index and prints them it also prints doc_freq, term_freq, and total number of terms

    public void printDictionary() {
        Iterator it = index.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            DictEntry dd = (DictEntry) pair.getValue();
            System.out.print("** [" + pair.getKey() + "," + dd.doc_freq+"," + dd.term_freq + "]       =--> ");
            printPostingList(dd.pList);
        }
        System.out.println("------------------------------------------------------");
        System.out.println("*** Number of terms = " + index.size());
    }

    //-----------------------------------------------

    // Build the inverted index used for
    public void buildIndex(String[] files) {
        int fid = 0;
        for (String fileName : files) {
            try (BufferedReader file = new BufferedReader(new FileReader(fileName))) {
                if (!sources.containsKey(fid)) {
                    sources.put(fid, new SourceRecord(fid, fileName, fileName, "notext"));
                }
                String ln;
                int flen = 0;
                while ((ln = file.readLine()) != null) {

                    flen +=  indexOneLine(ln, fid);
                }
                sources.get(fid).length = flen;

            } catch (IOException e) {
                System.out.println("File " + fileName + " not found. Skip it");
            }
            fid++;
        }
//        int fid = 0; // Initialize document ID counter
//
//        // Iterate over each file in the input array
//        for (String fileName : files) {
//            try (BufferedReader file = new BufferedReader(new FileReader(fileName))) {
//                // Check if the file is already in the sources map, if not, add it
//                if (!sources.containsKey(fileName)) {
//                    sources.put(fid, new SourceRecord(fid, fileName, fileName, "notext"));
//                }
//                String ln;
//                int flen = 0; // Initialize file length counter
//
//                // Read each line of the file
//                while ((ln = file.readLine()) != null) {
//                    String[] words = ln.split("\\W+"); // Split line into words
//                    int lineLength = 0; // Initialize line length counter

        // Process each word in the line
//                    for (String word : words) {
//                        word = word.toLowerCase(); // Convert word to lowercase
//
//                        // If the word is not in the index, add it
//                        if (!index.containsKey(word)) {
//                            index.put(word, new DictEntry());
//                        }
//                        DictEntry dictEntry = index.get(word);
//                        // If the word is not already in the posting list for this document, update frequencies
//                        if (!dictEntry.postingListContains(fid)) {
//                            dictEntry.doc_freq++; // Increment document frequency
//                            dictEntry.addPosting(fid);
//                        }
//                        dictEntry.term_freq++; // Increment term frequency
//
//                        lineLength += word.length(); // Increment line length by the length of the word
//                    }
//                    // Add additional characters for spaces between words
//                    lineLength += words.length - 1;

//                    flen += indexOneLine(ln,fid); // Update file length
//                }
//
//                sources.get(fid).length = flen; // Update file length in sources map
//            } catch (IOException e) {
//                System.out.println("File " + fileName + " not found. Skip it");
//            }
//            fid++; // Increment document ID
//        }

        // Sorting the index
//        HashMap<String, DictEntry> sortedIndex = index.entrySet().stream()
//                .sorted(Map.Entry.comparingByKey())
//                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
//                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
//        index = sortedIndex; // Update index with sorted index

        //   printDictionary(); // Print the sorted index
    }


    // Helper method to process each line of text

    //----------------------------------------------------------------------------  
    public int indexOneLine(String ln, int fid) {
        int flen = 0;

        String[] words = ln.split("\\W+");
        //   String[] words = ln.replaceAll("(?:[^a-zA-Z0-9 -]|(?<=\\w)-(?!\\S))", " ").toLowerCase().split("\\s+");
        flen += words.length;
        for (String word : words) {
            word = word.toLowerCase();
            if (stopWord(word)) {
                continue;
            }
            word = stemWord(word);
            // check to see if the word is not in the dictionary
            // if not add it
            if (!index.containsKey(word)) {
                index.put(word, new DictEntry());
            }
            // add document id to the posting list
            if (!index.get(word).postingListContains(fid)) {
                index.get(word).doc_freq += 1; //set doc freq to the number of doc that contain the term 
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
            //set the term_fteq in the collection
            index.get(word).term_freq += 1;
            if (word.equalsIgnoreCase("lattice")) {

                System.out.println("  <<" + index.get(word).getPosting(1) + ">> " + ln);
            }

        }
        return flen;
    }


//----------------------------------------------------------------------------
    // if word equal to one of these return true

    boolean stopWord(String word) {
        if (word.equals("the") || word.equals("to") || word.equals("be") || word.equals("for") || word.equals("from") || word.equals("in")
                || word.equals("a") || word.equals("into") || word.equals("by") || word.equals("or") || word.equals("and") || word.equals("that")) {
            return true;
        }
        if (word.length() < 2) {
            return true;
        }
        return false;

    }
//----------------------------------------------------------------------------  

    String stemWord(String word) { //skip for now
        return word;
//        Stemmer s = new Stemmer();
//        s.addString(word);
//        s.stem();
//        return s.toString();
    }

    //----------------------------------------------------------------------------
    /**
     * Finds the intersection of two posting lists.
     * Given two posting lists, pL1 and pL2, this function
     * returns a new posting list containing the document IDs
     * that appear in both pL1 and pL2.
     *
     * @param pL1 The first posting list.
     * @param pL2 The second posting list.
     * @return A new posting list containing the intersection
     *         of pL1 and pL2.
     */

    Posting intersect(Posting pL1, Posting pL2) {
        Posting answer = null;
        Posting last = null;

        // Loop until one of the posting lists reaches its end
        while (pL1 != null && pL2 != null)
        {
            // If the document IDs of the current postings are equal
            if (pL1.docId == pL2.docId)
            {
                // Add the document ID to the answer
                if (answer == null) {
                    answer = new Posting(pL1.docId);
                    last = answer;
                } else {
                    last.next = new Posting(pL1.docId);
                    last = last.next;
                }
                // Move both pointers to the next postings
                pL1 = pL1.next;
                pL2 = pL2.next;
            }
            else if (pL1.docId < pL2.docId)
            { // If the document ID of pL1 is less than pL2
                pL1 = pL1.next; // Move pL1 to the next posting
            }
            else
            { // If the document ID of pL2 is less than pL1
                pL2 = pL2.next; // Move pL2 to the next posting
            }
        }
        return answer;
    }

    // Searches for documents containing all terms in the input phrase

    public Pair2 find_24_01(String phrase) { // any mumber of terms non-optimized search
        String result = "";
        String[] words = phrase.split("\\W+");
        int len = words.length;

        //fix this if word is not in the hash table will crash...
        if(!index.containsKey(words[0].toLowerCase())){
            result="Not found";
            return new Pair2(result,null);
        }
        Posting posting = index.get(words[0].toLowerCase()).pList;
        int i = 1;
        while (i < len) {
            if(!index.containsKey(words[i].toLowerCase())){
                result="Not found";
                return  new Pair2(result,null);
            }
            posting = intersect(posting, index.get(words[i].toLowerCase()).pList);
            i++;
        }
        Posting temp=posting;
        while (posting != null) {
            //System.out.println("\t" + sources.get(num));
            result += "\t" + posting.docId + " - " + sources.get(posting.docId).title + " - " + sources.get(posting.docId).length + "\n";
            posting = posting.next;
        }
        if(result.isEmpty()){
            result="notfound";
        }
        return new Pair2(result,temp);
    }


    //---------------------------------
    // This function implements a bubble sort algorithm to sort an array of strings as the parameter @param words

    String[] sort(String[] words) {  //bubble sort
        boolean sorted = false;
        String sTmp;
        //-------------------------------------------------------
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < words.length - 1; i++) {
                int compare = words[i].compareTo(words[i + 1]);
                if (compare > 0) {
                    sTmp = words[i];
                    words[i] = words[i + 1];
                    words[i + 1] = sTmp;
                    sorted = false;
                }
            }
        }
        return words;
    }

    //---------------------------------

    // store file physically on the hard disk

    public void store(String storageName) {
        try {
            String pathToStorage = "tmp11\\rl"+storageName;
            Writer wr = new FileWriter(pathToStorage);
            for (Map.Entry<Integer, SourceRecord> entry : sources.entrySet()) {
                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().URL + ", Value = " + entry.getValue().title + ", Value = " + entry.getValue().text);
                wr.write(entry.getKey().toString() + ",");
                wr.write(entry.getValue().URL.toString() + ",");
                wr.write(entry.getValue().title.replace(',', '~') + ",");
                wr.write(entry.getValue().length + ","); //String formattedDouble = String.format("%.2f", fee );
                wr.write(String.format("%4.4f", entry.getValue().norm) + ",");
                wr.write(entry.getValue().text.toString().replace(',', '~') + "\n");
            }
            wr.write("section2" + "\n");

            Iterator it = index.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                DictEntry dd = (DictEntry) pair.getValue();
                //  System.out.print("** [" + pair.getKey() + "," + dd.doc_freq + "] <" + dd.term_freq + "> =--> ");
                wr.write(pair.getKey().toString() + "," + dd.doc_freq + "," + dd.term_freq + ";");
                Posting p = dd.pList;
                while (p != null) {
                    //    System.out.print( p.docId + "," + p.dtf + ":");
                    wr.write(p.docId + "," + p.dtf + ":");
                    p = p.next;
                }
                wr.write("\n");
            }
            wr.write("end" + "\n");
            wr.close();
            System.out.println("=============EBD STORE=============");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//=========================================
    // check the if the storage file exists



    public boolean storageFileExists(String storageName){
        java.io.File f = new java.io.File("tmp11\\rl"+storageName);
        if (f.exists() && !f.isDirectory())
            return true;
        return false;

    }
//----------------------------------------------------

// create file to store

    public void createStore(String storageName) {
        try {
            String pathToStorage = "tmp11\\rl"+storageName;
            Writer wr = new FileWriter(pathToStorage);
            wr.write("end" + "\n");
            wr.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //----------------------------------------------------
    //load index from hard disk into memory
    public HashMap<String, DictEntry> load(String storageName) {
        try {
            String pathToStorage = "tmp11\\rl"+storageName;
            sources = new HashMap<Integer, SourceRecord>();
            index = new HashMap<String, DictEntry>();
            BufferedReader file = new BufferedReader(new FileReader(pathToStorage));
            String ln = "";
            int flen = 0;
            while ((ln = file.readLine()) != null) {
                if (ln.equalsIgnoreCase("section2")) {
                    break;
                }
                String[] ss = ln.split(",");
                int fid = Integer.parseInt(ss[0]);
                try {
                    System.out.println("**>>" + fid + " " + ss[1] + " " + ss[2].replace('~', ',') + " " + ss[3] + " [" + ss[4] + "]   " + ss[5].replace('~', ','));

                    SourceRecord sr = new SourceRecord(fid, ss[1], ss[2].replace('~', ','), Integer.parseInt(ss[3]), Double.parseDouble(ss[4]), ss[5].replace('~', ','));
                    //   System.out.println("**>>"+fid+" "+ ss[1]+" "+ ss[2]+" "+ ss[3]+" ["+ Double.parseDouble(ss[4])+ "]  \n"+ ss[5]);
                    sources.put(fid, sr);
                } catch (Exception e) {

                    System.out.println(fid + "  ERROR  " + e.getMessage());
                    e.printStackTrace();
                }
            }
            while ((ln = file.readLine()) != null) {
                //     System.out.println(ln);
                if (ln.equalsIgnoreCase("end")) {
                    break;
                }
                String[] ss1 = ln.split(";");
                String[] ss1a = ss1[0].split(",");
                String[] ss1b = ss1[1].split(":");
                index.put(ss1a[0], new DictEntry(Integer.parseInt(ss1a[1]), Integer.parseInt(ss1a[2])));
                String[] ss1bx;   //posting
                for (int i = 0; i < ss1b.length; i++) {
                    ss1bx = ss1b[i].split(",");
                    if (index.get(ss1a[0]).pList == null) {
                        index.get(ss1a[0]).pList = new Posting(Integer.parseInt(ss1bx[0]), Integer.parseInt(ss1bx[1]));
                        index.get(ss1a[0]).last = index.get(ss1a[0]).pList;
                    } else {
                        index.get(ss1a[0]).last.next = new Posting(Integer.parseInt(ss1bx[0]), Integer.parseInt(ss1bx[1]));
                        index.get(ss1a[0]).last = index.get(ss1a[0]).last.next;
                    }
                }
            }
            System.out.println("============= END LOAD =============");
            //    printDictionary();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return index;
    }


//

}

//=====================================================================
