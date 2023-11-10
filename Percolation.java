import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private WeightedQuickUnionUF grid;
    private int[] g1;
    private int len;
    private int count = 0;
    public Percolation(int n){
        if(n<=0){
            throw new java.lang.IllegalArgumentException("Row or col is outside its prescribed range");
        }
        else {
            grid = new WeightedQuickUnionUF(n *  n +1);
            g1 = new int[n * n + 1];
            int leng = g1.length;
            for (int i = 0; i < leng; i++) {
                if (i <= n) {
                    grid.union(0, xyTo1D(0, i));
                }
                if(i>=leng-n){
                    grid.union(n*n,xyTo1D(n,i));
                }

                g1[i] = 0;
            }
            len = n;
        }
    }
    public void open(int row, int col) {
            if (row > len || row <= 0 || col > len || col <= 0) {
                throw new java.lang.IllegalArgumentException("Row or col is outside its prescribed range");
            } else {
                int p = xyTo1D(row, col);

                int t;
                if (g1[p] != 1) {
                    g1[p] = 1;
                    count++;
                    if(row==len) {
                        g1[len*len] = 1;
                    }
                    int[][] adj = adj(row, col);
                    int l = adj.length;
                    for (int i = 0; i < l; i++) {
                        int y = adj[i][0];
                        int x = adj[i][1];
                        t = xyTo1D(y, x);
                        if (g1[t] == 1) {
                                grid.union(t, p);
                                
                            if(y==len) {
                                g1[len*len] = 1;
                            }
                        }
                    }
                }
            }
        }



    private int xyTo1D(int y, int x){
        if(y!=0){
            y-=1;
        }
        if(x!=0){
            x-=1;
        }

        return (y*len)+x;
    }
    private int[][] adj(int y, int x){
        int n = 0;
        int [][] adj = new int[4][2];
        if(y-1>0){
            int [] surr = new int[2];
           surr[0] = y-1;
           surr[1] = x;
           adj[n++] = surr;
        }
        if(y+1<=this.len){
            int [] surr1 = new int[2];
            surr1[0] = y+1;
            surr1[1] = x;
            adj[n++] = surr1;
        }
        if(x-1>0){
            int [] surr2 = new int[2];
            surr2[0] = y;
            surr2[1] = x-1;
            adj[n++] = surr2;
        }
        if(x+1<=this.len) {
            int[] surr3 = new int[2];
            surr3[0] = y;
            surr3[1] = x + 1;
            adj[n++] = surr3;
        }
        int [][] adj1 = new int[n][2];
        for(int i=0;i<n;i++){
            adj1[i] = adj[i];
        }
        return adj1;
    }
    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if(row>len||row<=0||col>len||col<=0){
            throw new java.lang.IllegalArgumentException("Row or col is outside its prescribed range");
        }
        else {
            if (g1[xyTo1D(row, col)] == 1) {
                return true;
            }
            return false;
        }
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col)  {
        if(row>len||row<=0||col>len||col<=0){
            throw new java.lang.IllegalArgumentException("Row or col is outside its prescribed range");
        }
        else {
            int p = xyTo1D(row, col);
            if (g1[p] == 1) {
                int t = grid.find(p);
                if (t == grid.find(0) || t == 0) {
                    return true;
                }
            }
            return false;
        }

    }

    // returns the number of open sites
    public int numberOfOpenSites(){
        return count;
    }

    // does the system percolate?
    public boolean percolates(){
        if(len!=1) {
            int leng = g1.length;
                if(g1[len*len] ==1) {
                    if (grid.find(len * len) == grid.find(0))
                        return true;

                }

            return false;
        }
        else{
            if(g1[0] == 1){
                return true;
            }
            return false;
        }
    }

    // test client (optional)
    public static void main(String[] args){

    }
}
