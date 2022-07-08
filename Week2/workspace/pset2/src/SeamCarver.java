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
        
        // Array List for seam
        List<Integer> entries = new ArrayList<Integer>();
        
        // Initial min PQ for finding first min energy pixel 
        // in first level down
        IndexMinPQ<Double> E1 = new IndexMinPQ<Double>(w + 1);
        
        // initialise height variable y = 0
        int y = 0;
        
        y++;    // iterate height to 1 
        
        for (int x = 0; x < w; x++) {
            
            // Insert all energy matrix values in y = 1 row
            // using x value for index
            
            E1.insert(x, energyMatrix[x][y]);
        }
        
        // get lowest Energy value
        int seamStart = E1.delMin();
        
        // add two x values for y = 0 and y = 1 to entries
        entries.add(seamStart);
        
        entries.add(seamStart);
        
        // The 2nd min PQ for interating through the image
        IndexMinPQ<Double> E2 = new IndexMinPQ<Double>(w);
        
        y++;
        //y++;
        
        // start the seam at x = seamStart
        int next = seamStart;
        
        for (int q = y; q < h; q++) {
            
            // boundary conditions if between the edges
            if (next > 0 && next < w - 1) {
                E2.insert(next-1, energyMatrix[next - 1][q]);
                E2.insert(next, energyMatrix[next][q]);
                E2.insert(next+1, energyMatrix[next + 1][q]);
                
            }
            
            // if at left edge
            else if (next == 0) {
                E2.insert(next, energyMatrix[next][q]);
                E2.insert(next+1, energyMatrix[next + 1][q]);
            }
            // if at right edge
            else if (next == w - 1) {
                E2.insert(next-1, energyMatrix[next - 1][q]);
                E2.insert(next, energyMatrix[next][q]);
            }
            
            // get min Energy x value and add to ArrayList
            next = E2.delMin();
            entries.add(next);
            
            // Empty minPQ for next round
            while (!E2.isEmpty()) {
                E2.delMin();
            }
            
        }
        
        // Convert to array and return
        int[] returnArr = new int[entries.size()];
        
        for (int i = 0; i < entries.size(); i++) {
            returnArr[i] = entries.get(i);
        }
        
        return returnArr;
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
        energyMatrix = new double[width()][height()];
        int w = width();
        int h = height();
        
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                energyMatrix[x][y] = energy(x, y);
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
                double temp = energyMatrix[i][j];
                energyMatrix[i][j] = energyMatrix[j][i];
                energyMatrix[j][i] = temp;
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
