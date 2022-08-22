public class WordUtils {
    public static String longest(List61B<String> list){
        int maxIndex=0;
        int maxLength=0;
        int size = list.size();
        for (int i = 0; i < size; i++) {
            int tmpLength = list.get(i).length();
            if (tmpLength>maxLength){
                maxLength=tmpLength;
                maxIndex=i;
            }
        }
        return list.get(maxIndex);

    }
}
