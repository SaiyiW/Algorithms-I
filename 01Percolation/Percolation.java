/* 
 * Author: Saiyi Wang
 * Date: Nov. 28th, 2017
 * This is the assignment of the first week of the course *Algorithms, Part I (Princeton University)*.
 * A percolation model is implemented using the weighted quick union algorithm from algs4 library provided by the teaching group. 
 * The main() method is for simple tests at the development stage. An integer n is required from command line.
 * For percolation threshold, one can use the PercolationStats.java client.
 */
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{
    private int sideLength;
    private int numOfOpenSites;
    private int[] siteStatus;
    private int[] siteConnectToBottom;
    private WeightedQuickUnionUF weightedQU;
    public Percolation(int n){
        if(n<=0) {
            throw new java.lang.IllegalArgumentException();
        }
        sideLength=n;
        numOfOpenSites=0;
        siteStatus= new int[n*n+1];  //record whether the site is open; [0,n*n-1]
        siteConnectToBottom=new int[n*n+1];//same indices as weightedQU
        weightedQU=new WeightedQuickUnionUF(n*n+2);
        //add virtual top and bottom site: site0 and site(n*n+1)); so the indices of real sites are [1,n*n]
        
        //initialize the sites to be blocked
        siteStatus[0]=1;
        siteConnectToBottom[0]=0;
        for(int i=1;i<n*n+1;i++){
            siteStatus[i]=0;  //entry=0 the site blocked, entry=1 open
            if(i<=n*(n-1))
                siteConnectToBottom[i]=0;
            else
                siteConnectToBottom[i]=1;
        }

        //union the top row and bottom row to the corresponding virtual site
        for(int j=1;j<=n;j++){
            weightedQU.union(0,j);  //virtual top site
        }
    }
    /**
     * open(int row, int col)
     * @para row: the row index of the site to be open; col: the index of the column of the site to be open
     */
    public void open(int row, int col){
        if((row<1||row>sideLength)||(col<1||col>sideLength)){
            throw new java.lang.IllegalArgumentException();
        }
        int index1D=convertIndexTo1D(row,col);
        if(!isOpen(row,col))
            numOfOpenSites++;
        siteStatus[index1D]=1;
        
        int thisOldConnectBottom=siteConnectToBottom[weightedQU.find(index1D)];
        int topConnectBottom=0;
        int bottomConnectBottom=0;
        int leftConnectBottom=0;
        int rightConnectBottom=0;
        if((sideLength==1)&&(index1D==1)){
            thisOldConnectBottom=1;
        }
        if((index1D-sideLength>0)&&(siteStatus[index1D-sideLength])==1){
            topConnectBottom=siteConnectToBottom[weightedQU.find(index1D-sideLength)];
            weightedQU.union(index1D,index1D-sideLength);
        }
        if((index1D+sideLength<=sideLength*sideLength)&&(siteStatus[index1D+sideLength])==1){
            bottomConnectBottom=siteConnectToBottom[weightedQU.find(index1D+sideLength)];
            weightedQU.union(index1D,index1D+sideLength);
        }
        if((index1D-1>0)&&(siteStatus[index1D-1]==1)&&((index1D-1)%sideLength!=0)){
              leftConnectBottom=siteConnectToBottom[weightedQU.find(index1D-1)];
              weightedQU.union(index1D,index1D-1);
        }
        if((index1D+1<=sideLength*sideLength)&&(siteStatus[index1D+1]==1)&&((index1D)%sideLength!=0)){
             rightConnectBottom=siteConnectToBottom[weightedQU.find(index1D+1)];
             weightedQU.union(index1D,index1D+1);
        }
        int newRoot=weightedQU.find(index1D);
        int thisNewConnectBottom=thisOldConnectBottom+topConnectBottom+bottomConnectBottom+leftConnectBottom+rightConnectBottom;
        if(thisNewConnectBottom>500)
            thisNewConnectBottom=1; //prevent overflow
        siteConnectToBottom[newRoot]=thisNewConnectBottom;
    }
    /**
     * isOpen(int row, int col)
     * @para  row:the row index of the site; col: the index of the column of the site
     * @return true if the site is open; false otherwise
     */
    public boolean isOpen(int row, int col){
        if((row<1||row>sideLength)||(col<1||col>sideLength)){
            throw new java.lang.IllegalArgumentException();
       }
        return siteStatus[convertIndexTo1D(row,col)]==1;
    }
   /**
     * isFull(int row, int col)
     * @para  row:the row index of the site; col: the index of the column of the site
     * @return true if the site is full; false otherwise
     */
    public boolean isFull(int row, int col){
        if((row<1||row>sideLength)||(col<1||col>sideLength)){
            throw new java.lang.IllegalArgumentException();
        }
        int index1D=convertIndexTo1D(row,col);
        return (isOpen(row,col))&&(weightedQU.connected(0,index1D));
    }
     /**
     * numberOfOpenSites()
     * @return the number of open sites
     */
    public int numberOfOpenSites(){
        return numOfOpenSites;
    }
    /**
     * percolates()
     * @return true if the grid percolates; false otherwise
     */
    public boolean percolates(){
        int rootOfVirtualTop=weightedQU.find(0);
        return (siteConnectToBottom[rootOfVirtualTop]!=0)&&(siteStatus[rootOfVirtualTop]==1);
    }
     /**
     * convertIndexTo1D(int row, int col)
     * @para row, col: the dimensions that is required in this assignment: left-upper corner is (1,1)
     * @return the corresponding one-dimension index in an array
     */
     private int convertIndexTo1D(int row, int col){
        int newIndex=(row-1)*sideLength+col;
        return newIndex;
    }
      /**
     * main()
     * @para n the side length of the grid; input from command line
     */
     public static void main(String[] args){
        Percolation test=new Percolation(Integer.parseInt(args[0]));
        System.out.println("Percolates before open?"+" "+test.percolates());
        test.open(1,1);
        System.out.println("The site is open?"+" "+test.isOpen(1,1));
//        test.open(2,3);
//        test.open(2,1);
//        int x=test.convertIndexToQU(2,3);
//        int y=test.convertIndexToQU(2,1);
        System.out.println("Percolates after open?"+" "+test.percolates());
//        System.out.println("The later two sites are connected?"+" "+test.weightedQU.connected(x,y));
//        System.out.println("The third site is full?"+" "+test.isFull(2,1));
     }
    
}