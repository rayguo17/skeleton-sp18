public class triangle {
    public static void main(String[] args) {
        drawTriangle(10);
    }
    public static void drawTriangle(int n){
        //why static why not?
        //with static we do not need to instantiate as new!
        for (int i=1;i<=n;i++){
            for(int j=0;j<i;j++){
                System.out.print('*');
            }
            System.out.print('\n');
        }
    }
}
