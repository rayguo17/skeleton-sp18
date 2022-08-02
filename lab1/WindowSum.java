public class WindowSum {
    public static void main(String[] args) {
        int[] a = new int[]{1,2,-3,4,5,4};
        int n = 3;
        windowPosSum(a, n);
        System.out.println(java.util.Arrays.toString(a));
    }
    public static void windowPosSum(int[] a, int n){
        for(int i=0;i<a.length;i++){
            if(a[i]>0){
                for(int j=i+1;j<a.length && j<=(i+n);j++){
                    a[i] += a[j];
                }
            }
        }
    }
}
