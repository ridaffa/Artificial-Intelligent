package tugas_ai_rev;
import java.util.Random;

public class Tugas1_ai_rev {
    static int[][] initPop(int ukPop, int panjangKromosom){
        
        int[][] populasi = new int[ukPop][panjangKromosom];
        for (int i = 0; i < ukPop; i++) {
            for (int j = 0; j < panjangKromosom; j++) {
                Random rand = new Random();
                populasi[i][j] = rand.nextInt(10);
            }
        }
        return populasi;
    }
    static double[] decodeKromosom(int[] kromosom, int panjangKromosom){
        double[] decoded = new double[2];
        int pembagi = panjangKromosom/2;
        double pembilang = 0;
        double pembilang2 = 0;
        double penyebut = 0;
        for (int j = 0; j < pembagi; j++) {
            pembilang += kromosom[j]*Math.pow(10, -j);
            pembilang2 += kromosom[j+pembagi]*Math.pow(10, -j);
            penyebut += Math.pow(10, -j);
        }
        double hitung = -3+(((3-(-3))/(9*penyebut))*pembilang);
        decoded[0] = hitung;
        hitung = -2+(((2-(-2))/(9*penyebut))*pembilang2);
        decoded[1] = hitung;
        return decoded;
    }
    static double[] hitungFitness(int[][] populasi){
        double[] fitness = new double[populasi.length];
        for (int i = 0; i < populasi.length; i++) {
            double x1 = decodeKromosom(populasi[i],populasi[i].length)[0];
            double x2 = decodeKromosom(populasi[i],populasi[i].length)[1];
            double h = (4-(2.1*Math.pow(x1, 2))+(Math.pow(x1, 4)/3))*Math.pow(x1, 2)+(x1*x2)+(-4+(4*Math.pow(x2, 2)))*Math.pow(x2, 2);
            fitness[i] = -h;
        }
        return fitness;
    }
    static int tournamentSelection(double[] fitness){
        Random r = new Random();
        int best = r.nextInt(fitness.length);
        int tourSize = r.nextInt(fitness.length)+1;
        for (int i = 1; i <= tourSize; i++) {
            int next = r.nextInt(fitness.length);
            if(fitness[best] < fitness[next]){
                best = next;
            }
        }
        return best;
    }
    static int[][] parentSelection(double[] fitness, int[][] populasi, int panjangKromosom){
        int[][] parent = new int[2][panjangKromosom];
        for (int i = 0; i < 2; i++) {
            int tourSelect = tournamentSelection(fitness);
            parent[i] = populasi[tourSelect].clone();
        }
        return parent;
        
    }
    
    static int[][] crossOver(int[][] populasi, double[] fitness, double Pc,int ukPop, int panjangKromosom){
        Random rand = new Random();
        int [][] parent = parentSelection(fitness, populasi, panjangKromosom);
        int [][] child = parent;
        if(rand.nextDouble()<Pc){
            int crossO = rand.nextInt(panjangKromosom);
            for (int i = crossO; i < panjangKromosom; i++) {
                child[0][i]= parent[1][i];
                child[1][i]= parent[0][i];
            }
        }
        return child;
    }
    
    static void mutation(int[] child, double Pm, int panjangKromosom){
        Random rand = new Random();
        if(rand.nextDouble()<Pm){
            child[rand.nextInt(panjangKromosom)] = rand.nextInt(10);
        }
    }
    
    static int idxSurvivor(double[] fitness){
        int idxMax = 0;
        for (int i = 1; i < fitness.length; i++) {
            if(fitness[i] > fitness[idxMax]){
                idxMax = i;
            }
        }
        return idxMax;
    }
    
    static int[][] generalReplacement(int[][] populasiLama, int[][] populasiBaru,double[] fitness, int ukPop, int panjangKromosom){
        int[][] newPop = new int[ukPop][panjangKromosom];
        newPop[0] = populasiLama[idxSurvivor(fitness)].clone();
        newPop[1] = populasiLama[idxSurvivor(fitness)].clone();
        for (int i = 2; i < populasiBaru.length; i++) {
            newPop[i] = populasiBaru[i].clone();
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
        int ukPop = 100; //bisa bebas diisi ukPop>=2
        int panjangKromosom = 10; //bisa bebas diisi panjangKromosom >=2
        double Pm = 0.8; //bisa bebas diisi
        double Pc = 0.7; //bisa bebas diisi
        int G = 20; //bisa bebas diisi
        int[][] populasi = initPop(ukPop,panjangKromosom);
        int[][] newPop = new int[ukPop][panjangKromosom];
        double[] allFitness = new double[ukPop];
        double[][] decodedAllKrom = new double[ukPop][];
        int[][] child;
        newPop = new int[ukPop][panjangKromosom];
        for (int j = 0; j < G; j++) {
            int i = 0;
            while(i < ukPop){
                allFitness = hitungFitness(populasi.clone());
                child = crossOver(populasi.clone(), allFitness.clone(), Pc,ukPop, panjangKromosom);
                mutation(child[0],Pm, panjangKromosom);
                mutation(child[1],Pm, panjangKromosom);
                newPop[i++] = child[0].clone();
                if(i < ukPop){
                    newPop[i++] = child[1].clone();
                }
            }
            populasi = generalReplacement(populasi.clone(),newPop.clone(),allFitness,ukPop,panjangKromosom);
        }
            System.out.print("Kromosom Terbaik : ");
            displayOneKrom(populasi[idxSurvivor(allFitness)]);
            System.out.println("Nilai : "+ -allFitness[idxSurvivor(allFitness)]);
            System.out.println("Decode Kromosom terbaik (x1,x2) : "+decodeKromosom(populasi[idxSurvivor(allFitness)].clone(),panjangKromosom)[0]+" , "+decodeKromosom(populasi[idxSurvivor(allFitness)].clone(),panjangKromosom)[1]);
        

    
    }
    
}
