public class UnionFindTester {
    public static void main(String[] args) {
        UnionFind u1 = new UnionFind(9);
        u1.union(3,5);
        u1.union(3,4);
        u1.union(0,2);
        u1.union(0,1);
        u1.union(0,3);

        u1.union(6,7);
        u1.union(6,8);
        u1.union(0,6);
    }
}
