package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF grid;
    private boolean[][] openGrid;
    private int size ;
    private int openSites;
    private int[][] dirArr = {
            {1,0},{-1,0},{0,1},{0,-1}
    };
    public Percolation(int N){
        if(N<=0){
            throw new IllegalArgumentException();
        }
        grid  = new WeightedQuickUnionUF(N*N);
        size = N;
        openGrid=new boolean[N][N];
        openSites=0;
    }
    public void open(int row, int col){
        if(openGrid[row][col]){
            return;
        }
        openSites++;
        //
        openGrid[row][col]= true;
        //check four direction of row and col
        for(int i=0;i<dirArr.length;i++){
            int tarRow=row+dirArr[i][0];
            int tarCol=col+dirArr[i][1];
            if(tarRow>=0&&tarRow<size &&tarCol>=0&&tarCol<size){
                if(openGrid[tarRow][tarCol]){
                    grid.union(row*size+col,tarRow*size+tarCol);
                }
            }
        }
    }
    public boolean isOpen(int row, int col){
        return openGrid[row][col];
    }
    public boolean isFull(int row,int col){
        if(!isOpen(row,col)){
            return false;
        }
        //
        for(int i=0;i<size;i++){
            if(!isOpen(0,i)){
                continue;
            }
            if(grid.find(row*size+col)==grid.find(i)){
                return true;
            }
        }

        return false;
    }
    public int numberOfOpenSites(){
        return openSites;
    }
    public boolean percolates(){
        for(int i=0;i<size;i++){
            if(!isOpen(size-1,i)){
                continue;
            }
            for(int j=0;j<size;j++){
                if(!isOpen(0,j)){
                    continue;
                }
                if(grid.find((size-1)*size+i)==grid.find(j)){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Percolation p1 = new Percolation(5);
        p1.open(0,0);
        p1.open(0,1);
        System.out.println(p1.isFull(0,0));
        p1.open(1,2);
        System.out.println(p1.isOpen(1,2));
        System.out.println(p1.isFull(1,2));
        System.out.println(p1.isFull(1,1));
        p1.open(0,2);
        System.out.println(p1.isFull(1,2));
        System.out.println(p1.isFull(1,1));
        p1.open(2,2);
        System.out.println(p1.percolates());
    }
}
