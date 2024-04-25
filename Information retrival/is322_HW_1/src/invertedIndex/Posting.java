/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package invertedIndex;

import java.util.HashMap;
import java.util.Vector;

/**
 *
 * @author ehab
 */
 
public class Posting {

    public Posting next = null;
    public Vector<Integer>postions;
    int docId;
    int dtf = 1;

    Posting(int id, int t) {
        docId = id;
        dtf=t;
        postions=new Vector<>();
    }
    
    Posting(int id) {
        docId = id;
        postions=new Vector<>();
    }
}