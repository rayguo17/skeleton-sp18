package lab11.graphs;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;

/**
 *  @author Josh Hug
 */
public class MazeAStarPath extends MazeExplorer {
    private int s;
    private int t;
    private boolean targetFound = false;
    private Maze maze;

    public MazeAStarPath(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        maze = m;
        s = maze.xyTo1D(sourceX, sourceY);
        t = maze.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;

    }

    /** Estimate of the distance from v to the target. */
    private int h(int v) {
        return -1;
    }

    /** Finds vertex estimated to be closest to target. */
    private int findMinimumUnmarked() {
        return -1;
        /* You do not have to use this method. */
    }
    private int manhattan(int v, int w){
        return Math.abs(maze.toX(v)-maze.toX(w))+Math.abs(maze.toY(v)-maze.toY(w));
    }
    /** Performs an A star search from vertex s. */
    private void astar(int s) {
        // TODO
        MinPQ<Integer> pq = new MinPQ<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
                return (distTo[integer]+manhattan(integer,t))-( distTo[t1]+manhattan(t1,t));
            }
        });
        pq.insert(s);
        while(!pq.isEmpty()){
            int target = pq.delMin();
            marked[target] = true;
            announce();
            if(target == t){
                return;
            }
            for(int w: maze.adj(target)){
                if(!marked[w]){
                    edgeTo[w] = target;
                    distTo[w] = distTo[target]+1;
                    pq.insert(w);
                    announce();
                }
            }
        }
    }

    @Override
    public void solve() {
        astar(s);
    }

}

