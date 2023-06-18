package edges;
import utils.CommonTask;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class PrewittOperation {
    private BufferedImage originalImage;
    private int[][] H_Kernel = {{-1, -1, -1}, {0, 0, 0}, {1, 1, 1}}; //Horizontal Direction
    private int[][] V_Kernel = {{-1, 0, 1}, {-1, 0, 1},{-1, 0, 1}};  //Vertical Direction
    int kernelSize;


    public PrewittOperation(BufferedImage originalImage) {
        this.originalImage = originalImage;
        kernelSize = V_Kernel.length; //number of rows 3
    }
    public BufferedImage applyPrewittOperator(){
        BufferedImage outputImage = new BufferedImage(this.originalImage.getWidth(),this.originalImage.getHeight(),BufferedImage.TYPE_BYTE_GRAY);

        try{
            int Gx,Gy;
            int G;
            int kerX = this.kernelSize/2; // 0 1 2 --- 3/2 =1
            int kerY = this.kernelSize/2;

            for(int row=0;row<this.originalImage.getHeight();row++){
                for(int col=0;col<this.originalImage.getWidth();col++){
                    Gx=Gy=0;
                    G=0;
                    for(int kx = 0;kx<this.H_Kernel.length;kx++){  // 0th row, 1st, 2nd
                        int kernelXIndex = this.kernelSize - 1 - kx; // 3 - 1 - 0 = 2 (kernelSize = V_Kernel.length)

                        for(int ky = 0;ky<this.V_Kernel.length;ky++){
                            int kernelYIndex = this.kernelSize - 1 - ky;
                            int imageXBoundary = row + (kx - kerX); // 0 + (0 - 1) = -1,            0,           1
                            int imageYBoundary = col + (ky - kerY); // 0 + (0 or 1 or 2 - 1) = -1, 0, 1
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


