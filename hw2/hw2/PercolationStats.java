package hw2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;

public class PercolationStats {
    double[] thresholds;
    double mean;
    int times;
    double stddev;

    public PercolationStats(int N, int T,PercolationFactory pf){
        if(N<=0 || T<=0){
            throw new IllegalArgumentException();
        }
        thresholds = new double[T];
        double sum =0;
        for(int i=0;i<T;i++){
            Percolation p = pf.make(N);
            System.out.printf("id: %d, percolates: %s",i,p.percolates());
            while(!p.percolates()){

                int row = StdRandom.uniform(N);
                int col = StdRandom.uniform(N);
                p.open(row,col);

            }
            thresholds[i]=p.numberOfOpenSites();
            sum+=thresholds[i];
        }
        mean = sum/(double)T;
        times = T;
        stddev=0;
    }

    public static void main(String[] args) {
        PercolationStats ps = new PercolationStats(5,5,new PercolationFactory());
    }
    public double mean(){

        return mean;
    }
    public double stddev(){
        if(stddev!=0){
            return stddev;
        }
        int sum = 0;
        for(int i=0;i<times;i++){
            double diff = thresholds[i]-mean;
            sum += diff*diff;
        }
        stddev = sum/(double)(times-1);
        return stddev;
    }
    public double confidenceLow(){
        double rootDev = Math.sqrt(stddev);
        double time = Math.sqrt(times);

        return mean-1.96*rootDev/time;
    }
    public double confidenceHigh(){
        double rootDev = Math.sqrt(stddev);
        double time = Math.sqrt(times);
        return mean+1.96*rootDev/time;
    }
}
