/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package invertedIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.Buffer;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author ehab
 */
public class Test {

    public static void main(String args[]) throws IOException {
        Index5 index = new Index5();
        BiWord biword = new BiWord();
        Bouns Bo = new Bouns();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        //|**  change it to your collection directory
        //|**  in windows "C:\\tmp11\\rl\\collection\\"       
        String files = "tmp11\\tmp11\\rl\\test\\";

        File file = new File(files);
        //|** String[] 	list()
        //|**  Returns an array of strings naming the files and directories in the directory denoted by this abstract pathname.
        String[] fileList = file.list();

        fileList = index.sort(fileList);
        index.N = fileList.length;

        for (int i = 0; i < fileList.length; i++) {
            fileList[i] = files + fileList[i];
        }
        index.buildIndex(fileList);
        index.store("index");
        index.printDictionary();

//        String test3 = "data  should plain greatest comif"; // data  should plain greatest comif
//        System.out.println("Boo0lean Model result = \n" + index.find_24_01(test3));

        System.out.println("1- Inverted index");
        System.out.println("2- Biword index");
        System.out.println("3- Positional index");
        System.out.println("4- Bouns");
        System.out.println("5- Exit");
        System.out.println("enter Your Choice: ");
        String choice=in.readLine();
        while (!Objects.equals(choice, "5")){
            if(Objects.equals(choice, "1")){
                String phrase;
                do {
                    System.out.println("Print search phrase: ");
                    phrase = in.readLine();
                    if(phrase.isEmpty()) {
                        System.out.println("the program is end");
                        break;
                    }
                    System.out.println("Boo0lean Model result = \n" + index.find_24_01(phrase).res);

                } while (!phrase.isEmpty());
            }else if(Objects.equals(choice, "2")){
                String phrase = "";
                do {
                    System.out.println("Print search phrase: ");
                    phrase = in.readLine();
                    if(phrase.isEmpty()) {
                        System.out.println("the program is end");
                        break;
                    }
                    System.out.println("Boo0lean Model result = \n" + biword.find_biword(phrase,index).res);

                } while (!phrase.isEmpty());
            }else if(Objects.equals(choice, "3")){

            }else if(Objects.equals(choice, "4")){
                String phrase = "";
                do {
                    System.out.println("Print search phrase: ");
                    phrase = in.readLine();
                    if(phrase.isEmpty()) {
                        System.out.println("the program is end");
                        break;
                    }
                    System.out.println("Boo0lean Model result = \n" +Bo.find_Mixed(index,biword,phrase));

                } while (!phrase.isEmpty());
            }
            System.out.println("enter Your Choice: ");
            choice=in.readLine();
        }

    }
}
/**
 * automated "specific information"
 */