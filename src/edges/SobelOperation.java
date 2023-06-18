package edges;

import utils.CommonTask;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class SobelOperation {
    private BufferedImage originalImage;
    private int[][] H_Kernel = {{-1, -2, -1}, {0, 0, 0}, {1, 2, 1}};
    private int[][] V_Kernel = {{-1, 0, 1}, {-2, 0, 2},{-1, 0, 1}};
    int kernelSize;

    public SobelOperation(BufferedImage originalImage) {
        this.originalImage = originalImage;
        kernelSize = V_Kernel.length;
    }

    public BufferedImage applySobelOperator(){

        BufferedImage outputImage = new BufferedImage(this.originalImage.getWidth(),this.originalImage.getHeight(),BufferedImage.TYPE_BYTE_GRAY);
        try{
            int Gx,Gy;
            int G;
            int kerX = this.kernelSize/2; // 0 1 2 --- 2/2 =1
            int kerY = this.kernelSize/2;
            for(int row=0;row<this.originalImage.getHeight();row++){
                for(int col=0;col<this.originalImage.getWidth();col++){
                    Gx=Gy=0;
                    G=0;
                    for(int kx = 0;kx<this.H_Kernel.length;kx++){
                        int kernelXIndex = this.kernelSize - 1 - kx;
                        for(int ky = 0;ky<this.V_Kernel.length;ky++){
                            int kernelYIndex = this.kernelSize - 1 - ky;
                            int imageXBoundary = row + (kx - kerX);
                            int imageYBoundary = col + (ky - kerY);
                            if(imageXBoundary>=0 && imageXBoundary<this.originalImage.getHeight() && imageYBoundary>=0 && imageYBoundary<this.originalImage.getWidth()){
                                int rgb = this.originalImage.getRGB(imageYBoundary, imageXBoundary);
                                Gx += ((this.H_Kernel[kernelXIndex][kernelYIndex] * rgb));
                                Gy += ((this.V_Kernel[kernelXIndex][kernelYIndex] * rgb));
                            }
                        }
                    }
                    G = Math.abs(Gx)+Math.abs(Gy);
                    outputImage.setRGB(col,row,(G<<16)|(G<<8)|G);
                }
            }


        }catch(Exception ex){
            System.out.println("getEdgeDetection Problem" + " " + ex.getMessage());
        }
        return outputImage;
    }
}
