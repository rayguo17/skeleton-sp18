package hw4.puzzle;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static hw4.puzzle.TestSolver.readBoard;
import static org.junit.Assert.assertEquals;

public class Board implements WorldState{
    public static void main(String[] args) {
        for (int i = 0; i <= 30; i += 1) {
            String pnum = String.format("%02d", i);
            String puzzleName = "input/puzzle4x4-" + pnum + ".txt";
            System.out.println(puzzleName);
            Board b = readBoard(puzzleName);
            int numMoves = i;
            Solver s = new Solver(b);
            System.out.println("moves:"+ s.moves());
        }



    }
    public int[][] tiles;
    public int hammingDistance;
    public int manhattanDistance;
    public Board(int[][] tiles){
        this.tiles = Arrays.stream(tiles).map(int[]::clone).toArray(int[][]::new);
        hammingDistance=-1;
        manhattanDistance=-1;
    }
    public int tileAt(int row, int col){
        return this.tiles[row][col];
    }
    public int size(){
        return tiles.length;
    }
    public int hamming(){
        //number of tiles in the wrong position;
        if(hammingDistance==-1){
            int tmpCnt = 0;
            int cnt=1;
            for(int i=0;i<tiles.length;i++){
                for(int j=0;j<tiles[i].length;j++){
                    if(tiles[i][j]==0){
                        cnt++;
                        continue;
                    }
                    if(tiles[i][j]!=cnt){
                        tmpCnt++;
                    }
                    cnt++;
                }
            }
            hammingDistance = tmpCnt;
        }
        return hammingDistance;
    }
    public int manhattan(){
        int size = size();
        if(manhattanDistance==-1){
            int cnt=0;
            for(int i=0;i<tiles.length;i++){
                for(int j=0;j<tiles[i].length;j++){
                    if(tiles[i][j]==0){
                        continue;
                    }
                    int num = tiles[i][j];
                    int row = (num-1)/size;
                    int col = (num-1)%size;
                    int dis = Math.abs(row-i)+Math.abs(col-j);
                    cnt += dis;
                }
            }
            manhattanDistance = cnt;
        }
        return manhattanDistance;
    }

    @Override
    public int estimatedDistanceToGoal() {
        return hamming();
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Set<WorldState> neighbors = new HashSet<>();
        int i0=0;
        int j0=0;
        int size = size();
        for(int i=0;i<size;i++){
            for(int j=0;j<tiles[i].length;j++){
                if(tiles[i][j]==0){
                    i0=i;
                    j0=j;
                    break;
                }
            }

        }
        int[][] dirArr = {
                {1,0},{-1,0},{0,1},{0,-1}
        };
        for(int i=0;i<dirArr.length;i++){
            int tarI=i0+dirArr[i][0];
            int tarJ=j0+dirArr[i][1];
            if(tarI>=0&& tarI<size && tarJ>=0 && tarJ<size){
                swap(tiles,tarI,tarJ,i0,j0);
                neighbors.add(new Board(tiles));
                swap(tiles,tarI,tarJ,i0,j0);
            }
        }
        return neighbors;
    }
    private void swap(int[][] tarTiles ,int tarI, int tarJ, int i0, int j0){
        int tmp = tarTiles[tarI][tarJ];
        tarTiles[tarI][tarJ] = tarTiles[i0][j0];
        tarTiles[i0][j0]=tmp;

    }
    public boolean equals(Object o){
        if(this== o){
            return true;
        }
        if(o==null || getClass() != o.getClass()){
            return false;
        }
        Board board1 = (Board) o;
        //compare tiles
        return tiles==null? board1.tiles==null:Arrays.deepEquals(tiles,board1.tiles);
    }

    /** Returns the string representation of the board. 
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
