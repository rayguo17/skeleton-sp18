package lab11.graphs;

import java.util.LinkedList;
import java.util.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    private Maze maze;
    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        maze = m;
        s= maze.xyTo1D(sourceX,sourceY);
        t = maze.xyTo1D(targetX,targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        // TODO: Your code here. Don't forget to update distTo, edgeTo, and marked, as well as call announce()
        //queue
        Queue<Integer> queue = new LinkedList<>();
        //
        queue.add(s);
        while(!queue.isEmpty()){
            int target = queue.remove();
            marked[target] = true;
            announce();
            if(target ==t){
                return;
            }
            for(int w : maze.adj(target)){
                if(!marked[w]){
                    edgeTo[w] = target;
                    announce();
                    distTo[w] = distTo[target] + 1;
                    queue.add(w);
                }
            }

        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

