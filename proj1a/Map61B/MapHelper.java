package Map61B;

import java.util.List;

public class MapHelper {
    public static <X,Zerp> Zerp get(ArrayMap<X,Zerp> am, X key){
        if(am.containsKey(key)){
            return am.get(key);
        }else{
            return null;
        }
    }
    //type upper bound, "extends" here is not the same as inheritance's "extends",only to state K need to implement Comparable
    public static <K extends Comparable<K>,V> K maxKey(ArrayMap<K,V> am){
        List<K> list = am.keys();
        if(list.size()>0 && list.get(0) != null){
            K largest = list.get(0);
            for(int i=0;i<list.size();i++){
                if(largest.compareTo(list.get(i))<0){
                    largest = list.get(i);
                }
            }
            return largest;
        }
        return null;
    }
}
