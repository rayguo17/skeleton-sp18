package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

public class Solver {
//    public static void main(String[] args) {
//        String start = "cube";
//        String goal = "tubes";
//
//        Word startState = new Word(start, goal);
//        for(WorldState neighbor: startState.neighbors()){
//            System.out.println(neighbor);
//        }
//    }
    public List<WorldState> paths;
    public int moves;
    public class SearchNode{
        public WorldState state;
        public int moves;
        public SearchNode previous;
        public SearchNode(WorldState state, int moves, SearchNode previous){
            this.state = state;
            this.moves = moves;
            this.previous = previous;
        }

        @Override
        public String toString() {
            if(previous==null){
                return "state: "+state.toString()+", moves: "+moves+", estimated: "+state.estimatedDistanceToGoal();
            }
            return "state: "+state.toString()+", moves: "+moves+", estimated: "+state.estimatedDistanceToGoal()+", previous: "+previous.state.toString();
        }
    }
    public Solver(WorldState initial){
        paths = new LinkedList<>();
        MinPQ<SearchNode> mq = new MinPQ<>(new Comparator<SearchNode>() {
            @Override
            public int compare(SearchNode searchNode, SearchNode t1) {
                return searchNode.moves+searchNode.state.estimatedDistanceToGoal()-t1.moves-t1.state.estimatedDistanceToGoal();
            }
        });
        mq.insert(new SearchNode(initial,0,null));
        while(!mq.isEmpty()){
            //System.out.println("next!");
            SearchNode target = mq.delMin();
            //System.out.println(target.toString());
            if(target.state.estimatedDistanceToGoal()==0){
                SearchNode tmp = target;
                while(tmp!=null){
                    paths.add(tmp.state);
                    tmp = tmp.previous;
                }
                moves = target.moves;
                break;
            }
            //System.out.println("mq");
            for (WorldState neighbor: target.state.neighbors()){
                if(checkGrandParentExist(neighbor,target)){
                    continue;
                }
                mq.insert(new SearchNode(neighbor, target.moves+1,target ));
            }

        }

    }
    private boolean checkGrandParentExist(WorldState neighbor, SearchNode target){
        if(target.previous==null){
            return false;
        }
        return target.previous.state.equals(neighbor);
    }
//    private void printMQ(MinPQ<SearchNode> mq){
//        for(SearchNode node: mq){
//            System.out.println(node);
//        }
//    }
    public int moves(){
        return moves;
    }
    public Iterable<WorldState> solution(){
        List<WorldState> solutions = new LinkedList<>();
        int size = paths.size();
        for(int i=size-1;i>=0;i--){
            solutions.add(paths.get(i));
        }
        return solutions;
    }
}
