package lab11.graphs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Stack;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private boolean foundCircle;
    private Maze m;
    public MazeCycles(Maze m) {
        super(m);
        this.m = m;
    }

    @Override
    public void solve() {
        // TODO: Your code here!
        edgeTo[0] =0;
        distTo[0]=0;
        dfs(0);

    }

    // Helper methods go here

    private boolean dfs(int v){
        Stack<Integer> st = new Stack<>();
        st.push(v);
        while(!st.isEmpty()){
            int target  = st.pop();
            if(marked[target]){
                continue;
            }
            marked[target] = true;
            announce();
            for(int w : maze.adj(target)){
                if(edgeTo[w]!= Integer.MAX_VALUE && edgeTo[target]!=w){
                    //found cycle
                    handleCycleFound(w,target);
                    announce();
                    return true;
                }
                if(!marked[w] ){
                    edgeTo[w] = target;
                    distTo[w] = distTo[target] + 1;
                    announce();
                    st.push(w);
                }
            }
        }

        return false;
    }
    private void handleCycleFound(int endPoint, int cur){
        //get involved node number
        System.out.println("Cycle Found");

         //maybe depth search, the mutual starting point should be edgeTo[endPoint]???
        int startPoint = edgeTo[endPoint];
        marked = new boolean[m.V()];
        edgeTo[startPoint] = endPoint;
        edgeTo[endPoint] = cur;
        marked[endPoint]=true;
        marked[startPoint]=true;
        int tmp = endPoint;
        while(tmp!=startPoint){
            marked[tmp]=true;
            tmp = edgeTo[tmp];
        }
        announce();
    }
}

