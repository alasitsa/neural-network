package neuralnetwork.controller;

import java.awt.image.BufferedImage;
import java.awt.image.Raster;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import javax.imageio.ImageIO;

import neuralnetwork.constants.Constants;

public class ImageParseController {
	public double[] selectImage(Scanner sc) throws IOException {
		System.out.println("Type filename: ");
		String filename = sc.next();
		File file = new File("images/" + filename + ".jpg");
		BufferedImage image = ImageIO.read(file);
		Raster raster = image.getRaster();
		double[][] area = parseToAreas(raster);
		return matrixToVector(area);
	}
	public double[] selectImage(String filename) throws IOException {
		File file = new File("images//" + filename + ".jpg");
		BufferedImage image = ImageIO.read(file);
		Raster raster = image.getRaster();
		double[][] area = parseToAreas(raster);
		return matrixToVector(area);
	}
	public double[] getImageValues(File file) throws IOException {
		BufferedImage image = ImageIO.read(file);
		Raster raster = image.getRaster();
		double[][] area = parseToAreas(raster);
		return matrixToVector(area);
	}
	public double[][] parseToAreas(Raster raster) {
		int width = raster.getWidth();
		int height = raster.getHeight();
		double[][] pixel = new double[height][width];
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				int[] localPixel = raster.getPixel(i,  j,  new int[3]);
				pixel[i][j] = (localPixel[0] + localPixel[1] + localPixel[2]) / 3.0;
			}
		}
		int count = Constants.COUNT_AREAS;
		double[][] area = new double[count][count];
		int aWidth = width / count;
		int aHeight = height / count;
		for(int i = 0; i < count; i++) {
			for(int j = 0; j < count; j++) {
				int yPos = aHeight * i;
				int xPos = aWidth * j;
				double localArea = 0;
				for(int h = 0; h < aHeight; h++) {
					for(int g = 0; g < aWidth; g++) {
						localArea += pixel[h + yPos][g + xPos];
					}
				}
				localArea /= (aHeight * aWidth);
				area[i][j] = localArea;
			}
		}
		
		return area;
	}
	public double[] matrixToVector(double[][] matrix) {
		int height = matrix.length;
		int width = matrix[0].length;
		
		double[] vector = new double[height * width];
		int h = 0;
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < width; j++) {
				vector[h] = matrix[i][j];
				h++;
			}
		}
		return vector;
	}
	/*public void writeImage(double[][] area) throws IOException  {
		File file = new File("image.jpg");
		if(!file.exists()) {
			file.createNewFile();
		}
		BufferedImage image = ImageIO.read(file);
		WritableRaster raster = image.getRaster();
		int count = Constants.COUNT_AREAS;
		for(int i = 0; i < count; i++) {
			for(int j = 0; j < count; j++) {
				int[] pixel = new int[4];
				for(int h = 0; h < 3; h++) {
					pixel[h] = (int)Math.round(area[i][j]);
				}
				pixel[3] = 0;
				raster.setPixel(i, j, pixel);
			}
		}
		image.setData(raster);
		ImageIO.write(image, "jpg", file);
		System.out.println("finished");
	}*/
}
