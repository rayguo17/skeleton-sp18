public class UnionFind implements DisjoinSets{
    private int[] parent;
    public UnionFind(int n){
        parent = new int[n];
        for(int i=0;i<n;i++){
            parent[i] = -1;
        }

    }

    public void validate(int v1){
        if(v1<0 || v1>parent.length){
            throw new IndexOutOfBoundsException();
        }
    }
    public int sizeOf(int v1){
        validate(v1);
        return Math.abs(parent[find(v1)]);
    }
    public int parent(int v1){
        validate(v1);
        return parent[v1];
    }
    public int find(int v1){
        if(parent[v1]<0){
            return v1;
        }else{
            int val = find(parent[v1]);
            parent[v1] = val;
            return val;
        }

    }
    @Override
    public boolean connected(int v1, int v2) {
        return find(v1)==find(v2);
    }

    @Override
    public void union(int v1, int v2) {
        //change the size here? what is path compression?
        int p1 = find(v1);
        int p2 = find(v2);
        if(sizeOf(v1)>=sizeOf(v2)){
            parent[p1]+=parent[p2];
            parent[p2] = p1;
        }else{
            parent[p2] += parent[p1];
            parent[p1] = p2;
        }
    }
}
