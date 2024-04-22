/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package invertedIndex;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;



/**
 *
 * @author Hager Ragaee 20211108
 * @author Shimaa 20211056
 * @author Youssef Karam 20210480
 * @author Amal Mohamed 20211016
 * @author Shahd Khaled 20210182
 * TA: Lamiaa
 *
 */
public class Test {

    public static void main(String args[]) throws IOException {
        Index5 index = new Index5();
        BiwordIndex biwordIndex = new BiwordIndex();
        Bouns bouns=new Bouns();

        //|**  change it to your collection directory
        //|**  in windows "C:\\tmp11\\rl\\collection\\"
        String files = "tmp11\\rl\\test\\";

        File file = new File(files);
        //|** String[] 	list()
        //|**  Returns an array of strings naming the files and directories in the directory denoted by this abstract pathname.
        String[] fileList = file.list();

        fileList = index.sort(fileList);
        index.N = fileList.length;

        for (int i = 0; i < fileList.length; i++) {
            fileList[i] = files + fileList[i];
        }

//        String test3 = "data  should plain greatest comif"; // data  should plain greatest comif
//        System.out.println("Boo0lean Model result = \n" + index.find_24_01(test3));


        String choice;
        String phrase;

        do {
            System.out.println("Which index type do you want to use?\n (1) Inverted Index\n (2) Positional Index\n (3) Biword Index\n (4) Bouns\n (5) Quit: ");
            BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
            choice = in.readLine();
            index.buildIndex(fileList);
            index.store("Inverted_index");
            biwordIndex.buildIndex(fileList);
            biwordIndex.store("Biword_index");
            biwordIndex.printDictionary();
            index.printDictionary();
            switch (choice) {
                case "1": {
                    do {
                        System.out.println("Print search phrase: ");
                        phrase = in.readLine();
                        if (!phrase.isEmpty()) {

                            System.out.println("Boo0lean Model result = \n" + index.find_24_01(phrase));

                        }
                    } while (!phrase.isEmpty());
                    break;
                }
                case "2": {
                    do {
                        System.out.println("Print search phrase: ");
                        phrase = in.readLine();
                        if (!phrase.isEmpty()) {
//                            System.out.println("Positional Index result = \n" + index.find_24_01(phrase).res);
                        }
                    } while (!phrase.isEmpty());
                    break;
                }

                case "3": {

                    do {
                        biwordIndex.printDictionary();
                        System.out.println("Print search phrase: ");
                        phrase = in.readLine();
                        if (!phrase.isEmpty()) {
                            System.out.println("Biword Index result = \n" + biwordIndex.find_biword(phrase));
                        }
                    } while (!phrase.isEmpty());
                    break;
                }

                case "4":
                    do {
                        System.out.println("Print search phrase: ");
                        phrase = in.readLine();
                        if (!phrase.isEmpty()) {
                            System.out.println("Biword Index result = \n" + bouns.make(index,biwordIndex,phrase));
                        }
                    } while (!phrase.isEmpty());
                    break;
                case "5":
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid input. Please enter a valid option.");
                    break;
            }
        } while (!choice.equals("4"));
    }
}