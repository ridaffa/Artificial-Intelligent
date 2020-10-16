package tugas1_ai;
import java.util.Random;
import java.util.Arrays; 

public class Tugas1_ai {
    static int[][] intitateRandPop(int ukPop, int panjangKromosom){
        Random rand = new Random();
        int[][] randPop = new int[ukPop][panjangKromosom];
        for (int i = 0; i < ukPop; i++) {
            for (int j = 0; j < panjangKromosom; j++) {
                randPop[i][j] = rand.nextInt(10);
            }
        }
        return randPop;
    }
    
    static double decodeGen(int[] kromosom, int batasBawah, int batasAtas){
        double pembilang = 0;
        double penyebut = 0;
        for (int i = 1; i <= kromosom.length; i++) {
            pembilang += kromosom[i-1]*Math.pow(10, -i);
            penyebut += Math.pow(10, -i);
            
        }
        double hitung = ((batasAtas-batasBawah)/(9*penyebut))*pembilang;
        double phenotype = batasBawah+hitung;

        return phenotype;
    }
    static double[][] Decode(int[][] populasi, int panjangKromosom){
        double[][] decodedAll = new double[populasi.length][2];
        int divKrom = (panjangKromosom/2);
        int[] firstKrom;
        int[] secondKrom;
        for (int i = 0; i < populasi.length; i++) {
            firstKrom = Arrays.copyOfRange(populasi[i].clone(), 0, divKrom);
            secondKrom = Arrays.copyOfRange(populasi[i].clone(), divKrom, panjangKromosom);
            decodedAll[i][0] = decodeGen(firstKrom, -3, 3);
            decodedAll[i][1] = decodeGen(secondKrom, -2, 2);
        }
        return decodedAll;
    }
    static double hitungFitness(double x1, double x2){
        double h = (4-(2.1*Math.pow(x1, 2))+(Math.pow(x1, 4)/3))*Math.pow(x1, 2)+(x1*x2)+(-4+(4*Math.pow(x2, 2)))*Math.pow(x2, 2);
        return -h;
    }
    static double[] hitungFitness(double[][] decKrom){
        double[] allFitness = new double[decKrom.length];
        for (int i = 0; i < decKrom.length; i++) {
            allFitness[i] = hitungFitness(decKrom[i][0],decKrom[i][1]);
        }
        return allFitness;
    }
    static int Parent_Selection(double[] fitness, int ukPop){
        Random r = new Random();
        int bagus = r.nextInt(ukPop);
        int ukuranTournament = r.nextInt(ukPop)+1;
        for (int i = 1; i <= ukuranTournament; i++) {
            int next = r.nextInt(ukPop);
            if(fitness[bagus] < fitness[next]){
                bagus = next;
            }
        }
        return bagus;
    }
    
    static int[][] crossover(int[][] populasi, double[] fitness, double Pc,int ukPop, int pjgKrom){
        Random rand = new Random();
        int n = 0;
        int [][] child = new int[2][pjgKrom];
        int idxParent = Parent_Selection(fitness,ukPop);
        int idxParent2 = Parent_Selection(fitness,ukPop);
        child[0] = populasi[idxParent].clone();
        child[1] = populasi[idxParent2].clone();
        if(rand.nextDouble()<Pc){
            int crossO = rand.nextInt(pjgKrom);
            for (int i = 0; i < crossO; i++) {
                child[0][i]= populasi[idxParent2][i];
                child[1][i]= populasi[idxParent][i];
            }
        }
        return child;
    }
    
    static void mutasi(int[] child, double Pm, int pjgKrom){
        Random rand = new Random();
        if(rand.nextDouble()<Pm){
            child[rand.nextInt(pjgKrom)] = rand.nextInt(10);
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
        int[][] newPop = new int[ukuran_pop][pjgKrom];
        newPop[0] = pop_lama[getSurvivor(fitness)].clone();
        newPop[1] = pop_lama[getSurvivor(fitness)].clone();
        for (int i = 2; i < pop_baru.length; i++) {
            newPop[i] = pop_baru[i].clone();
        }
        return newPop;
    }
    
    static void displayOneKrom(int[] kromosom){
        for (int i = 0; i < kromosom.length; i++) {
            System.out.print(kromosom[i]+" ");
        }
        System.out.println("");
    }
    
    public static void main(String[] args) {
        int ukuranPopulasi = 90;
        int pjgKrom = 10;
        double Pm = 0.6;
        double Pc = 0.5;
        int G = 20;
        int[][] pop = intitateRandPop(ukuranPopulasi,pjgKrom);
        displayOneKrom(pop[3]);
        double[] fitness = new double[ukuranPopulasi];
        double[][] decodedAllKrom = new double[ukuranPopulasi][];
        int[][] child;
        for (int j = 0; j < G; j++) {
            int[][] newPop = new int[ukuranPopulasi][pjgKrom];
            int i = 0;
            while(i < ukuranPopulasi){
                decodedAllKrom =  Decode(pop.clone(), pjgKrom);
                fitness = hitungFitness(decodedAllKrom.clone());
                child = crossover(pop.clone(), fitness.clone(), Pc,ukuranPopulasi, pjgKrom);
                mutasi(child[0],Pm, pjgKrom);
                mutasi(child[1],Pm, pjgKrom);
                newPop[i++] = child[0].clone();
                newPop[i++] = child[1].clone();
            }
            pop = generalReplacement(pop.clone(), newPop.clone(),fitness.clone(),ukuranPopulasi, pjgKrom);
        }
        System.out.print("Kromosom terbaik: ");
        displayOneKrom(pop[getSurvivor(fitness)]);
        System.out.println("Decode kromosom terbaik (x1,x2): ("+decodedAllKrom[getSurvivor(fitness)][0]+" , "+decodedAllKrom[getSurvivor(fitness)][1]+")");

        }
    
}
