package edges;
import utils.CommonTask;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class LaplacianOperation {
    private BufferedImage originalImage;
    private int[][] Kernel = {{0, -1, 0}, {-1, 4, -1}, {0, -1, 0}}; //Negative
    int kernelSize;


    public LaplacianOperation(BufferedImage originalImage) {
        this.originalImage = originalImage;
        kernelSize = Kernel.length; //number of rows 3
    }
    public BufferedImage applyLaplacianOperator(){
        BufferedImage outputImage = new BufferedImage(this.originalImage.getWidth(),this.originalImage.getHeight(),BufferedImage.TYPE_BYTE_GRAY);

        try{
            int Gx;
            int Gy;
            int G;
            int kerX = this.kernelSize/2; // 0 1 2 --- 3/2 =1
            int kerY = this.kernelSize/2;

            for(int row=0;row<this.originalImage.getHeight();row++){
                for(int col=0;col<this.originalImage.getWidth();col++){
                   // Gx=Gy=0;
                    Gx = 0;
                    G=0;
                    for(int kx = 0;kx<this.Kernel.length;kx++){  // 0th row, 1st, 2nd
                        int kernelXIndex = this.kernelSize - 1 - kx; // 3 - 1 - 0 = 2 (kernelSize = Kernel.length)

                        for(int ky = 0;ky<this.Kernel.length;ky++){
                            int kernelYIndex = this.kernelSize - 1 - ky;
                            int imageXBoundary = row + (kx - kerX); // 0 + (0 - 1) = -1,            0,           1
                            int imageYBoundary = col + (ky - kerY); // 0 + (0 or 1 or 2 - 1) = -1, 0, 1
                            if(imageXBoundary>=0 && imageXBoundary<this.originalImage.getHeight() && imageYBoundary>=0 && imageYBoundary<this.originalImage.getWidth()){
                                int rgb = this.originalImage.getRGB(imageYBoundary, imageXBoundary);
                                Gx += ((this.Kernel[kernelXIndex][kernelYIndex] * rgb));
                               // Gy += ((this.Kernel[kernelXIndex][kernelYIndex] * rgb));
                            }
                        }
                    }
                    G = Math.abs(Gx); // + Math.abs(Gy);
                                                                                                 // double sigma = -0.9;
                                                                                                 // double n = Math.exp(-(Gx * Gx + Gy * Gy)/ 2* Math.pow(sigma,2.0));
                                                                                                //  G = (int) (1/(3.1416 * Math.pow(sigma,4.0))* (1-(Gx*Gx + Gy*Gy)/2 * Math.pow(sigma,2.0))* n);

                    outputImage.setRGB(col,row,(G<<16)|(G<<8)|G);
                }
            }


        }catch(Exception ex){
            System.out.println("getEdgeDetection Problem" + " " + ex.getMessage());
        }
        return outputImage;
    }
}
