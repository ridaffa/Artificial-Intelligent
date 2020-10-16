/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tugas1_ai;

import static com.sun.javafx.fxml.expression.Expression.and;
import static com.sun.javafx.fxml.expression.Expression.and;
import static com.sun.javafx.fxml.expression.Expression.and;
import static com.sun.javafx.fxml.expression.Expression.and;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import static javafx.beans.binding.Bindings.and;
import static javax.management.Query.and;

/**
 *
 * @author USER
 */
public class NewClass {
    static int checkData(int[] krom, int[] data, int jmlRule, int n){
        int count = 0;
        for (int i = 0; i < 3; i++) {
            if(data[i] == krom[i] && data[i] == 1 && krom[i] == 1){
                count+=1;
            }
        }
        for (int i = 3; i < 7; i++) {
            if(data[i] == krom[i] && data[i] == 1 && krom[i] == 1){
                count+=1;
            }
        }
        for (int i = 7; i < 11; i++) {
            if(data[i] == krom[i] && data[i] == 1 && krom[i] == 1){
                count+=1;
            }
        }
       for (int i = 11; i < 14; i++) {
            if(data[i] == krom[i] && data[i] == 1 && krom[i] == 1){
                count+=1;
            }
        }
       if(count == 4){
           return krom[14];
       }else{
           if(n == jmlRule-1){
               int a = krom[14];
               a = (a + 1) - 2;
               if(a < 0){
                   a = a * (-1);
               }
               return a;
           }
           return 123;
       }  
    }
    static void checkAllData(int[] krom, int pjgDataTrain, int[] data) throws FileNotFoundException{
        int jmlRule = krom.length/pjgDataTrain;
        int[][] rule_rule = new int[jmlRule][pjgDataTrain];
        for (int i = 1; i <= jmlRule; i++) {
            rule_rule[i-1] = Arrays.copyOfRange(krom, (pjgDataTrain*i)-pjgDataTrain, (pjgDataTrain*i));
            }
        int ac = 0;
        int soo = 123;
        int i = 0;
        while(i < jmlRule){
            soo = checkData(rule_rule[i], data, jmlRule, i);
            System.out.print(soo);
            if (soo == 123){
                i++;
            }else{
                i = jmlRule;
            }
        }
        System.out.println("");
        
    }
    public static void main(String[] args) throws FileNotFoundException {
        int[] krom = {1,0,1,0,1,1,1,0,1,1,1,1,0,1,1,1,0,1,0,1,1,1,0,1,1,1,1,0,1,0};
        int[] data = {0,1,0,1,0,0,0,1,0,0,0,0,1,0,0};
        checkAllData(krom, 15, data);
//        Random rand = new Random();
//        int[][] child = new int[2][];
//        int pjgDataTrain = 9;
//        int [][] child2 = new int[2][];
//        child[0] = new int[]{1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 0, 0, 0, 1};
//        child[1] = new int[]{1,0,0,1, 0, 0, 1, 1, 1};
//        if(child[0].length > child[1].length){
//            int[] temp = child[0].clone();
//            child[0] = child[1].clone();
//            child[1] = temp.clone();
//        }
//            int crossO = rand.nextInt(child[0].length);
//            int crossO_2 = rand.nextInt(child[0].length);
//            int max = Math.max(crossO, crossO_2);
//            if(crossO == max){
//                int temp = crossO_2;
//                crossO_2 = max;
//                crossO = temp;
//            }
//            int crossP = crossO;
//            int crossP_2 = crossO_2;
//            int p1 = crossO_2 - crossO;
//            int gap1 = p1 % pjgDataTrain;
//            System.out.println(gap1);
//            if(p1 > pjgDataTrain){
//                int[][] p2 = new int[3][2];
//                p2[0][0] = crossO;
//                p2[0][1] = crossO_2;
//                p2[1][0] = crossO;
//                p2[1][1] = crossO + gap1;
//                p2[2][0] = crossO_2 - gap1;
//                p2[2][1] = crossO_2;
//                int randomP2 = rand.nextInt(3);
//                crossP = p2[randomP2][0];
//                crossP_2 = p2[randomP2][1];
//            }
//            int p2 = crossP_2 - crossP;
//            int[] potong1 = new int[p1];
//            int[] potong2 = new int[p2];
//            for (int i = crossO; i < crossO_2; i++) {
//                potong1[i-crossO] = child[0][i];
//            }
//            for (int i = crossP; i < crossP_2; i++) {
//                potong2[i-crossP] = child[1][i];
//            }
//            child2[0] = new int[(child[0].length - potong1.length) + potong2.length];
//            child2[1] = new int[(child[1].length - potong2.length) + potong1.length];
//
//            for (int i = 0; i < crossO; i++) {
//                child2[0][i] = child[0][i];
//            }
//            for (int i = 0; i < potong2.length; i++) {
//                child2[0][i+crossO] = potong2[i];
//            }
//            for (int i = 0; i < child[0].length - crossO_2; i++) {
//                child2[0][i+crossO + potong2.length] = child[0][crossO_2 + i];
//            }
//            for (int i = 0; i < crossP; i++) {
//                child2[1][i] = child[1][i];
//            }
//            for (int i = 0; i < potong1.length; i++) {
//                child2[1][i+crossP] = potong1[i];
//            }
//            for (int i = 0; i < child[1].length - crossP_2; i++) {
//                child2[1][i+crossP + potong1.length] = child[1][crossP_2 + i];
//            }
//            for (int i = 0; i < child2[0].length; i++) {
//                System.out.print(child2[0][i]+" ");
//          }
//            System.out.println("");
//            for (int i = 0; i < child2[1].length; i++) {
//                System.out.print(child2[1][i]+" ");
//          }
//            System.out.println("");
//          System.out.println(crossO + ","+ crossO_2);
//          System.out.println(crossP + ","+ crossP_2);
    }
}
