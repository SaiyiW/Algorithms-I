/**
 * Author: Saiyi Wang
 * Date: Nov. 28th, 2017
 * This is the assignment of the first week of the course *Algorithms, Part I (Princeton University)*.
 * This client enables estimating the percolation threshold and gives some statistics like mean, standard deviation, etc.
 * The statistics calculation is from the algs4 library.
 */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] percolationThreshold;
    private int trialTimes;
    public PercolationStats(int n, int trials){    // perform trials independent experiments on an n-by-n grid
        if(n<1||trials<1){
            throw new java.lang.IllegalArgumentException();
        }
        percolationThreshold=new double[trials];
        Percolation[] percolationTrials=new Percolation[trials];
        trialTimes=trials;
        for(int i=0;i<trials;i++){
            percolationTrials[i]=new Percolation(n);
            while(percolationTrials[i].percolates()==false){
                int row=StdRandom.uniform(1,n+1);  //[1,n+1)
                int col=StdRandom.uniform(1,n+1);  //[1,n+1)
                if(percolationTrials[i].isOpen(row,col)==false){
                    percolationTrials[i].open(row,col);
                }
            }
            double doubleN=(double)n;
            percolationThreshold[i]=(double)percolationTrials[i].numberOfOpenSites()/(doubleN*doubleN);
        }
    }
    
    /**
     * mean()
     * @return sample mean of percolation threshold
     */
    public double mean(){                          
        double avgMean=StdStats.mean(percolationThreshold);
        return avgMean;
    }
    
    /**
     * stddev()
     * @return sample standard deviation of percolation threshold 
     */
    public double stddev(){                        
        double standardDev=0;
        if (trialTimes==1){
            standardDev=Double.NaN;
        }
        else
            standardDev=StdStats.stddev(percolationThreshold);
        return standardDev;
    }
    /**
     * confidenceLo()
     * @return low endpoint of 95% confidence interval 
     */
    public double confidenceLo(){                  
        double lowBound=mean()-(1.96*stddev()/Math.sqrt(trialTimes));
        return lowBound;
    }
    /**
     * confidenceHi()
     * @return high endpoint of 95% confidence interval 
     */
    public double confidenceHi(){                  // high endpoint of 95% confidence interval
        double highBound=mean()+(1.96*stddev()/Math.sqrt(trialTimes));
        return highBound;
    }

    /**
     * main()
     * @para n: side length of the grid, T: desired number of trials
     */
    public static void main(String[] args){        
        int n=Integer.parseInt(args[0]);
        int T=Integer.parseInt(args[1]);
        PercolationStats experiments=new PercolationStats(n,T);
        System.out.println("mean"+"                    ="+experiments.mean()+"\n");
        System.out.println("stddev"+"                  ="+experiments.stddev()+"\n");
        System.out.println("95% confidence interval"+" ="+"["+experiments.confidenceLo()+", "+experiments.confidenceHi()+"]");
    }
}