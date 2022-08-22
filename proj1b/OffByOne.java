public class OffByOne implements CharacterComparator {

    @Override
    public boolean equalChars(char x, char y) {
        int a = x-y;
        return a==1 || a==-1;
    }
}
