package hw3.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        double[] bucketList = new double[M];
        for (Oomage o :
                oomages) {
            int bucketNum = (o.hashCode() & 0x7FFFFFFF) % M;
            bucketList[bucketNum]+=1;
        }
        double nums = oomages.size();
        double lowerBound = nums/50;
        double upperBound = nums/2.5;

        for(int i=0;i<M;i++){
            if(bucketList[i]>upperBound || bucketList[i]<lowerBound){
                return false;
            }
        }
        return true;
    }
}
