package tugas1_ai;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Arrays; 
import java.util.Scanner;
import java.util.*;

public class Tugas1_ai {
    static int[][] intitateRandPop(int ukPop, int panjangKromosom){
        Random rand = new Random();
        int[][] randPop = new int[ukPop][panjangKromosom];
        for (int i = 0; i < ukPop; i++) {
            for (int j = 0; j < panjangKromosom; j++) {
                randPop[i][j] = rand.nextInt(2);
            }
        }
        return randPop;
    }
    static void notAllZero(int[][] pop, int pjgDataTrain ){
        Random r = new Random();
        for(int[] abc : pop){
            for (int i = 1; i <= abc.length/pjgDataTrain; i++) {
                if(abc[0*i] == 0 && abc[1*i] == 0 && abc[2*i] == 0){
                    abc[r.nextInt(3)*i] = 1;
                }
                if(abc[3*i] == 0 && abc[4*i] == 0 && abc[5*i] == 0 && abc[6*i] == 0){
                    abc[(r.nextInt(4)+3)*i] = 1;
                }
                if(abc[7*i] == 0 && abc[8*i] == 0 && abc[9*i] == 0 && abc[10*i] == 0){
                    abc[(r.nextInt(4)+7)*i] = 1;
                }
                if(abc[11*i] == 0 && abc[12*i] == 0 && abc[13*i] == 0){
                    abc[(r.nextInt(4)+11)*i] = 1;
                }
            }
        }
    }
    static ArrayList<int[]> DecodeDataTrain(String file, int pjgDataTrain) throws FileNotFoundException{
        ArrayList<int[]> numbers = new ArrayList<int[]>();
        Scanner sc = new Scanner(new File(file));
        while(sc.hasNext()){
            int[] dataTrain = new int[pjgDataTrain];
            int n = 0;
            String[] abc = sc.nextLine().split(",");
            for (int i = 0; i < dataTrain.length; i++) {
                dataTrain[i] = 0;
            }
            for (int i = 0; i < 4; i++) {
                n = 0;
                if (i==1){
                    n = 3;
                }else if(i == 2){
                    n = 7;
                }else if(i == 3){
                    n = 11;
                }
                dataTrain[Integer.parseInt(abc[i]) + n] = 1;
            }
            if(abc[4].equals("0")){
                dataTrain[dataTrain.length-1] = 0;
            }else{
                 dataTrain[dataTrain.length-1] = 1;
            }
            numbers.add(dataTrain);
        }
        sc.close();  //closes the scanner  
        return numbers;
    }
    static int hitungFitness(int[] dataTrain, int[] rule, int n, int jmlRule){
        int count = 0;
        int benar = 0;
        for (int i = 0; i < 3; i++) {
            if(dataTrain[i] == rule[i] && dataTrain[i] == 1 && rule[i] == 1){
                count+=1;
            }
        }
        if(count > 0){
            benar+=1;
        }
        count = 0;
        for (int i = 3; i < 7; i++) {
            if(dataTrain[i] == rule[i] && dataTrain[i] == 1 && rule[i] == 1){
                count+=1;
            }
        }
        if(count > 0){
            benar+=1;
        }
        count = 0;
        for (int i = 7; i < 11; i++) {
            if(dataTrain[i] == rule[i] && dataTrain[i] == 1 && rule[i] == 1){
                count+=1;
            }
        }
        if(count > 0){
            benar+=1;
        }
        count = 0;
        for (int i = 11; i < 14; i++) {
            if(dataTrain[i] == rule[i] && dataTrain[i] == 1 && rule[i] == 1){
                count+=1;
            }
        }
        if(count > 0){
            benar+=1;
        }
        if(benar == 4){
            if(dataTrain[14] == rule[14]){
                return dataTrain[14];
            }else{
                return 13;
            }
        }else{
            if(n == jmlRule - 1){
              if(dataTrain[14] != rule[14]){
                  int a = rule[14];
                  a = (a+1)-2;
                  if(a<0){
                      a = a*(-1);
                  }
                  return a;
              }
            }
        }
        return 13;
    }
    static double[] hitungAllFitness(ArrayList<int[]> dataTrain, int[][] rules, int pjgDataTrain){
        double[] allFitness = new double[rules.length];
        ArrayList<Integer> dataBelum = new ArrayList<Integer>();
        ArrayList<Integer> dataBelum_copy = new ArrayList<Integer>();
        int countBenar = 0;
        int idx = 0;
        int testing = 0;
        int n = 0;
        for (int[] rule:rules) {
            countBenar = 0;
            int ruleinKrom = rule.length/pjgDataTrain;
            int[][] rule_rule = new int[ruleinKrom][pjgDataTrain];
            for (int i = 1; i <= ruleinKrom; i++) {
                rule_rule[i-1] = Arrays.copyOfRange(rule, (pjgDataTrain*i)-pjgDataTrain, (pjgDataTrain*i));
            }
            n = 0;
            for (int i = 0; i < ruleinKrom; i++) {
                if(i < 1){
                    for (int j = 0; j < dataTrain.size(); j++) {
                        testing = hitungFitness(dataTrain.get(j),rule_rule[i],n,ruleinKrom);
                        if(testing == dataTrain.get(j)[14]){
                            countBenar += 1;
                        }else{
                            dataBelum.add(j);
                        }
                    }  
                    n++;
                }else{
                    for (int j : dataBelum_copy) {
                        testing = hitungFitness(dataTrain.get(j),rule_rule[i],n,ruleinKrom);
                        if(testing == dataTrain.get(j)[14]){
                            countBenar += 1;
                        }else{
                            dataBelum.add(j);
                        }
                    }
                    n++;
                }
                allFitness[idx] = (double)countBenar/dataTrain.size();
                dataBelum_copy = (ArrayList)dataBelum.clone();
                dataBelum.clear();
            }
            idx++;
        }
        
        
        return allFitness;
    }
    static int[] Parent_Selection(double[] fitness, int ukPop){
        int[] parent = new int[2];
        double[] fitness_copy = fitness.clone();
        Random r = new Random();
        for (int z = 0; z < 2; z++) {
            int bagus = r.nextInt(fitness_copy.length);
            int ukuranTournament = r.nextInt(fitness_copy.length)+1;
            for (int i = 0; i < ukuranTournament; i++) {
                int next = r.nextInt(fitness_copy.length);
                if(fitness[bagus] < fitness[next]){
                        bagus = next;
                }
            }
            parent[z] =  bagus;
        }
        if(parent[0] == parent[1]){
            if(parent[0] != 0){
                parent[0] -= 1;
            }else{
                parent[0] +=1;
            }
        }
        return parent;
    }
    
    static int[][] crossover(int[][] populasi, double[] fitness, double Pc,int ukPop, int pjgKrom, int pjgDataTrain){
        Random rand = new Random();
        int [][] child = new int[2][];
        int [][] child2 = new int[2][];
        int[] parent = Parent_Selection(fitness,ukPop);
        
        int idxParent = parent[0];
        int idxParent2 = parent[1];
//        System.out.println(idxParent+" & "+idxParent2);
        child[0] = new int[populasi[idxParent].length];
        child[1] = new int[populasi[idxParent2].length];
        child[0] = populasi[idxParent].clone();
        child[1] = populasi[idxParent2].clone();
        if(child[0].length > child[1].length){
            int[] temp = child[0].clone();
            child[0] = child[1].clone();
            child[1] = temp.clone();
        }
        if(rand.nextDouble() < Pc){
            int crossO = rand.nextInt(child[0].length);
            int crossO_2 = rand.nextInt(child[0].length);
            int max = Math.max(crossO, crossO_2);
            if(crossO == max){
                int temp = crossO_2;
                crossO_2 = max;
                crossO = temp;
            }
            int crossP = crossO;
            int crossP_2 = crossO_2;
            int p1 = crossO_2 - crossO;
            int gap1 = p1 % pjgDataTrain;
            if(p1 > pjgDataTrain){
                int[][] p2 = new int[3][2];
                p2[0][0] = crossO;
                p2[0][1] = crossO_2;
                p2[1][0] = crossO;
                p2[1][1] = crossO + gap1;
                p2[2][0] = crossO_2 - gap1;
                p2[2][1] = crossO_2;
                int randomP2 = rand.nextInt(3);
                crossP = p2[randomP2][0];
                crossP_2 = p2[randomP2][1];
            }
            int p2 = crossP_2 - crossP;
            int[] potong1 = new int[p1];
            int[] potong2 = new int[p2];
            for (int i = crossO; i < crossO_2; i++) {
                potong1[i-crossO] = child[0][i];
            }
            for (int i = crossP; i < crossP_2; i++) {
                potong2[i-crossP] = child[1][i];
            }
            child2[0] = new int[(child[0].length - potong1.length) + potong2.length];
            child2[1] = new int[(child[1].length - potong2.length) + potong1.length];

            for (int i = 0; i < crossO; i++) {
                child2[0][i] = child[0][i];
            }
            for (int i = 0; i < potong2.length; i++) {
                child2[0][i+crossO] = potong2[i];
            }
            for (int i = 0; i < child[0].length - crossO_2; i++) {
                child2[0][i+crossO + potong2.length] = child[0][crossO_2 + i];
            }
            for (int i = 0; i < crossP; i++) {
                child2[1][i] = child[1][i];
            }
            for (int i = 0; i < potong1.length; i++) {
                child2[1][i+crossP] = potong1[i];
            }
            for (int i = 0; i < child[1].length - crossP_2; i++) {
                child2[1][i+crossP + potong1.length] = child[1][crossP_2 + i];
            }
            return child2;
        }
        return child;
    }
    
    static void mutasi(int[] child, double Pm, int pjgKrom){
        Random rand = new Random();
        if(rand.nextDouble()<Pm){
            child[rand.nextInt(child.length)] = rand.nextInt(2);
            child[rand.nextInt(child.length)] = rand.nextInt(2);
            child[rand.nextInt(child.length)] = rand.nextInt(2);
            child[rand.nextInt(child.length)] = rand.nextInt(2);
        }
    }
    
    static int getSurvivor(double[] fitness){
        int idxMax = 0;
        for (int i = 1; i < fitness.length; i++) {
            if(fitness[i] > fitness[idxMax]){
                idxMax = i;
            }
        }
        return idxMax;
    }
    static int[][] generalReplacement(int[][] pop_lama, int[][] pop_baru,double[] fitness, int ukuran_pop, int pjgKrom){
        int[][] newPop = new int[ukuran_pop][];
        newPop[0] = pop_lama[getSurvivor(fitness)].clone();
        for (int i = 1; i < pop_baru.length; i++) {
            newPop[i] = pop_baru[i].clone();
        }
        return newPop;
    }
    
    static void displayOneKrom(int[] kromosom){
        System.out.print("Rule Terbaik: ");
        for (int i = 0; i < kromosom.length; i++) {
            System.out.print(kromosom[i]+" ");
        }
        System.out.println("");
    }
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
    static int[] checkAllData(int[] krom,String file, int pjgDataTrain) throws FileNotFoundException{
        ArrayList<int[]> numbers = new ArrayList<int[]>();
        Scanner sc = new Scanner(new File(file));
        while(sc.hasNext()){
            int[] dataTrain = new int[pjgDataTrain];
            int n = 0;
            String[] abc = sc.nextLine().split(",");
            for (int i = 0; i < dataTrain.length; i++) {
                dataTrain[i] = 0;
            }
            for (int i = 0; i < 4; i++) {
                n = 0;
                if (i==1){
                    n = 3;
                }else if(i == 2){
                    n = 7;
                }else if(i == 3){
                    n = 11;
                }
                dataTrain[Integer.parseInt(abc[i]) + n] = 1;
            }
            numbers.add(dataTrain);
        }
        int[] aye = new int[numbers.size()];
        int jmlRule = krom.length/pjgDataTrain;
        int[][] rule_rule = new int[jmlRule][pjgDataTrain];
        for (int i = 1; i <= jmlRule; i++) {
            rule_rule[i-1] = Arrays.copyOfRange(krom, (pjgDataTrain*i)-pjgDataTrain, (pjgDataTrain*i));
            }
        int ac = 0;
        int soo = 123;
        for (int[] ea:numbers) {
            int i = 0;
            while(i < jmlRule){
                soo = checkData(rule_rule[i], ea, jmlRule, i);
                if (soo == 123){
                    i++;
                }else{
                    i = jmlRule;
                }
            }
            aye[ac] = soo;
            ac++;
        }
        return aye;
    }
    static String printText(String file, int[] answer) throws FileNotFoundException{
        String dapat = new String();
        Scanner sc = new Scanner(new File(file));
        int i = 0;
        while(sc.hasNext()){
            dapat+=sc.nextLine()+"	"+answer[i]+"\n";
            i++;
        }
        return dapat;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int ukuranPopulasi = 50;
        int pjgKrom = 15;
        int pjgDataTrain = 15;
        double Pm = 0.7;
        double Pc = 0.5;
        int G = 50;
        ArrayList<int[]> numbers = DecodeDataTrain("F:\\Kuliah\\Artificial intelligent\\data_ai\\data_latih_opsi_2.csv", pjgDataTrain);
        int[][] pop = intitateRandPop(ukuranPopulasi,pjgKrom);
        double[] fitness = new double[ukuranPopulasi];
        int[][] child;
        for (int j = 0; j < G; j++) {
            int[][] newPop = new int[ukuranPopulasi][];
            int i = 0;
            while(i < ukuranPopulasi){
                child = crossover(pop.clone(), fitness.clone(), Pc,ukuranPopulasi, pjgKrom, pjgDataTrain);
                mutasi(child[0],Pm, pjgKrom);
                mutasi(child[1],Pm, pjgKrom);
                newPop[i++] = child[0].clone();
                newPop[i++] = child[1].clone();
            }
            pop = generalReplacement(pop.clone(), newPop.clone(),fitness.clone(),ukuranPopulasi, pjgKrom);
            fitness = hitungAllFitness(numbers, pop, pjgDataTrain);
            notAllZero(pop,pjgDataTrain);
        }
        System.out.println("Max Fitness (Akurasi): "+fitness[getSurvivor(fitness)] * 100 + "% ");
        int[] bestKrom = pop[getSurvivor(fitness)];
        displayOneKrom(bestKrom);
        try (PrintWriter out = new PrintWriter("data_hasil.txt")) {
            out.println(printText("F:\\Kuliah\\Artificial intelligent\\data_ai\\data_uji_opsi_2.txt",checkAllData(bestKrom,"F:\\Kuliah\\Artificial intelligent\\data_ai\\data_uji_opsi_2.csv",pjgDataTrain)));
        }
        
    }
    
}
