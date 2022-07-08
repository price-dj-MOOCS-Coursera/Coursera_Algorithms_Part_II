import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Picture;

/**
 * 
 */

/**
 * @author David Price
 *
 */
public class SeamCarver {
    private Picture inputPicture;
    private double[][] energyMatrix;
    
    // create a seam carver object based on the given picture
    public SeamCarver(Picture picture) {
        this.inputPicture = picture;
        
    }
    
    // current picture
    public Picture picture() {
        return this.inputPicture;
        
    }
    
    // width of current picture
    public int width() {
        int w = this.inputPicture.width();
        return w;
    }

    // height of current picture
    public int height() {
        int h = this.inputPicture.height();
        return h;
    }
    
    // energy of pixel at column x and row y
    public double energy(int x, int y) {
        if ((x < 0 || x >= this.width()) || (y < 0 || y >= this.height())) {
            throw new IndexOutOfBoundsException("Invalid range for x or y to energy function");
        }
        
        if ((x == 0 || x == this.width() - 1) || (y == 0 || y == this.height() - 1)) {
            return 1000;
        }
        
        double deltaSqX = deltaSquaredX(x, y);
        double deltaSqY = deltaSquaredY(x, y);
        
        double energy = Math.sqrt(deltaSqX + deltaSqY);
        
        return energy;
    }

    // sequence of indices for horizontal seam
    public int[] findHorizontalSeam() {
        
        this.transposePicture();
        
        int[] horizontalSeam = this.findVerticalSeam();
        
        this.transposePicture();
        
        return horizontalSeam;
    }

    // sequence of indices for vertical seam
    public int[] findVerticalSeam() {
        int w = this.width();
        int h = this.height();
        
        this.calcEnergymatrix();
        
        
        
        
       /* // Array List for seam
        List<Integer> seamList = new ArrayList<Integer>();
        
        // the sum energy matrix for a dynamic programming solution
        double[][] energySumMatrix = new double[w][h];
        
        // initialise first row of eSumMatrix
        for (int col = 0; col < w; col++) {
            energySumMatrix[col][0] = this.energyMatrix[col][0];
        }
        
        for (int col = 0; col < w; col++) {
            for (int row = 1; row < h; row++) {
                if (col == 0) {
                    energySumMatrix[col][row] = this.energyMatrix[col][row] + Math.min(energySumMatrix[col][row - 1]
                            , energySumMatrix[col + 1][row - 1]);
                }
                
                if (col == w - 1) {
                    energySumMatrix[col][row] = this.energyMatrix[col][row] + Math.min(energySumMatrix[col][row - 1]
                            , energySumMatrix[col - 1][row - 1]);
                }
                
                else if (col > 0 && col < w - 1){
                    
                    double lowestE = energySumMatrix[col][row - 1];
                    double b = energySumMatrix[col - 1][row - 1];
                    double c = energySumMatrix[col + 1][row - 1];
                    
                    if (lowestE > b) lowestE = b;
                    if (lowestE > c) lowestE = c;
                    
                    
                    energySumMatrix[col][row] = this.energyMatrix[col][row] + lowestE;
                }
            }
        }*/
        
        /*// find min at bottom row of energySum and which x value that corresponds to
        double minEnergyTotal = Double.MAX_VALUE;
        int xValue = 0;
        
        for (int col = 0; col < w; col++) {
            if (energySumMatrix[col][h - 1] < minEnergyTotal) {
                minEnergyTotal = energySumMatrix[col][h - 1];
                xValue = col;
            }
        }
        
        // add first xValue to seam list
        seamList.add(0, xValue);
        
        // Backtrack
        IndexMinPQ<Double> pq = new IndexMinPQ<Double>(w);
        
        
        for (int row = h - 1; row > 0; row--) {
            
            if (xValue == 0) {
                pq.insert(xValue, energySumMatrix[xValue][row - 1]);
                pq.insert(xValue + 1, energySumMatrix[xValue + 1][row - 1]);
            }
            
            if (xValue == w - 1) {
                pq.insert(xValue, energySumMatrix[xValue][row - 1]);
                pq.insert(xValue - 1, energySumMatrix[xValue - 1][row - 1]);
            }
            
            else if (xValue > 0 && xValue < w - 1){
                pq.insert(xValue, energySumMatrix[xValue][row - 1]);
                pq.insert(xValue + 1, energySumMatrix[xValue + 1][row - 1]);
                pq.insert(xValue - 1, energySumMatrix[xValue - 1][row - 1]);
            }
            
         // get min Energy x value and add to ArrayList
            xValue = pq.delMin();
            seamList.add(0, xValue);
            
            // Empty minPQ for next round
            while (!pq.isEmpty()) {
                pq.delMin();
            }
            
        }*/
        
        
        /*IndexMinPQ<Double> pq1 = new IndexMinPQ<Double>(w);
        
        // initialise height variable y = 0
        int y = 0;
        
        y++;    // iterate height to 1 
        
        for (int x = 0; x < w; x++) {
            
            // Insert all energy matrix values in y = 1 row
            // using x value for index
            
            pq1.insert(x, energySumMatrix[x][y]);
        }
        
     // get lowest Energy value
        int seamStart = pq1.delMin();
        
        // add two x values for y = 0 and y = 1 to entries
        seamList.add(seamStart);
        
        seamList.add(seamStart);
        
        // The 2nd min PQ for interating through the image
        IndexMinPQ<Double> pq2 = new IndexMinPQ<Double>(w);
        
        y++;
        //y++;
        
        // start the seam at x = seamStart
        int next = seamStart;
        
        for (int q = y; q < h; q++) {
            
            // boundary conditions if between the edges
            if (next > 0 && next < w - 1) {
                pq2.insert(next-1, energySumMatrix[next - 1][q]);
                pq2.insert(next, energySumMatrix[next][q]);
                pq2.insert(next+1, energySumMatrix[next + 1][q]);
                
            }
            
            // if at left edge
            else if (next == 0) {
                pq2.insert(next, energySumMatrix[next][q]);
                pq2.insert(next+1, energySumMatrix[next + 1][q]);
            }
            // if at right edge
            else if (next == w - 1) {
                pq2.insert(next-1, energySumMatrix[next - 1][q]);
                pq2.insert(next, energySumMatrix[next][q]);
            }
            
            // get min Energy x value and add to ArrayList
            next = pq2.delMin();
            seamList.add(next);
            
            // Empty minPQ for next round
            while (!pq2.isEmpty()) {
                pq2.delMin();
            }
            
        }
        
        
        // Convert to array and return
        int[] seamArr = new int[seamList.size()];
        
        for (int i = 0; i < seamList.size(); i++) {
            seamArr[i] = seamList.get(i);
        }
        
        return seamArr;*/
    }
    
    // remove horizontal seam from current picture
    public void removeHorizontalSeam(int[] seam) {
        
        int w = this.width();
        
        int h = this.height();
        
        if (h <= 1) {
            throw new java.lang.IllegalArgumentException();
        }
        
        // transpose image
        this.transposePicture();
        
        
        // call removeVerticalSeam
        this.removeVerticalSeam(seam);
        
        // transpose image back
        this.transposePicture();
        
    }
    
    
    // remove vertical seam from current picture
    public void removeVerticalSeam(int[] seam) {
        
        int w = this.width();
        
        if (w <= 1) {
            throw new java.lang.IllegalArgumentException();
        }
        
        int h = this.height();
        
        Picture copyPic = new Picture(w - 1, h);
        
        for (int row = 0; row < h; row++) {
            // just copy pixel by pixel up until seam
            for (int col = 0; col < seam[row] - 1; col++) {
                copyPic.set(col, row, this.inputPicture.get(col, row));
            }
            // after seam copy pixel from one to the right
            for (int col = seam[row]; col < w - 1; col++) {
                copyPic.set(col, row, this.inputPicture.get(col + 1, row));
            }
        }
        
        this.inputPicture = copyPic;
        
    }
    
    // Private method to calculate the deltax-squared
    private double deltaSquaredX(int x, int y) {
        
        // Where: 0 < x < this.width() - 1
        
        Color color1 = this.inputPicture.get(x + 1, y);
        Color color2 = this.inputPicture.get(x - 1, y);
        
        int redX = color1.getRed() - color2.getRed();
        int blueX = color1.getBlue() - color2.getBlue();
        int greenX = color1.getGreen() - color2.getGreen();
        
        double deltaSqX = Math.pow(redX, 2) + Math.pow(blueX, 2) + Math.pow(greenX, 2);
        
        return deltaSqX;
    }
    
    // Private method to calculate the deltay-squared
    private double deltaSquaredY(int x, int y) {
        
        //Where: 0 < y < this.height() - 1
        
        Color color1 = this.inputPicture.get(x, y + 1);
        Color color2 = this.inputPicture.get(x, y - 1);
        
        int redY = color1.getRed() - color2.getRed();
        int blueY = color1.getBlue() - color2.getBlue();
        int greenY = color1.getGreen() - color2.getGreen();
        
        double deltaSqY = Math.pow(redY, 2) + Math.pow(blueY, 2) + Math.pow(greenY, 2);
        
        return deltaSqY;
    }
    
    // Private method to calculate Energy Matrix in constructor
    private void calcEnergymatrix() {
        int w = width();
        int h = height();
        this.energyMatrix = new double[w][h];
        
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                this.energyMatrix[x][y] = energy(x, y);
            }
        }
        
    }
    
    // private method to transpose Energy Matrix
    private void transposeEnergyMatrix() {
        //transpose energy matrix
        int w = this.width();
        int h = this.height();
        
        for (int i = 0; i < h; i++) {
            for (int j = i+1; j < w; j++) {
                double temp = this.energyMatrix[i][j];
                this.energyMatrix[i][j] = this.energyMatrix[j][i];
                this.energyMatrix[j][i] = temp;
            }
        }
        
    }
    
   
    
    // Helper to transpose pic
    private void transposePicture() {
        
        int w = this.inputPicture.width();
        int h = this.inputPicture.height();
        
        
       Picture transPic = new Picture(h, w);
       
       for (int col = 0; col < w; col++) {
           for (int row = 0; row < h; row++) {
               transPic.set(row, col, this.inputPicture.get(col, row));
           }
       }
       
       this.inputPicture = transPic;
       
        
    }
    
    
    
}
