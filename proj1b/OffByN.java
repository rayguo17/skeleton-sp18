public class OffByN implements CharacterComparator{
    int target;
    public OffByN(int x){
        target=5;
    }
    @Override
    public boolean equalChars(char x, char y) {
        int dif = x-y;
        return dif==target||dif==-target;
    }
}
