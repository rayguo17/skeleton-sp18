package Object;

public class Dog {
    public int weightInPounds;
    public static String binomen = "Canis familiaris";

    public Dog(int w){
        weightInPounds=w;
    }

    public static Dog maxDog(Dog d1, Dog d2){
        if (d1.weightInPounds > d2.weightInPounds){
            return d1;
        }
        return d2;
    }

    public void bark(){
        if(weightInPounds < 10){
            System.out.println("yipyipyip");
        }else if(weightInPounds <30){
            System.out.println("bark. bark");
        }else{
            System.out.println("woof!");
        }
    }
}
