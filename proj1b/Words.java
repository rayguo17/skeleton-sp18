import java.util.*;

public class Words {
    public List<String> getWords(String inputFileName){
        In in = new In(inputFileName);
        List<String> storage = new ArrayList<>();
        while (!in.isEmpty()){
            String str = in.readString();
            storage.add(str);
        }
        return storage;
    }

    public int countUniqueWords(List<String> lst){
        Set<String> set = new HashSet<>();
        for (String x: lst){
            set.add(x);
        }
        return set.size();
    }

    public static Map<String,Integer> collectWordCount(List<String> targets, List<String> words){
        Map<String,Integer> mCount = new HashMap<>();
        for (String x: targets
             ) {
            mCount.put(x,0);

        }
        for (String y :
                words) {
            if (mCount.containsKey(y)){
                mCount.put(y,mCount.get(y)+1);
            }
        }
        return mCount;
    }

}
