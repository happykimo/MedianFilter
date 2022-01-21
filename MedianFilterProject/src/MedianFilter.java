import java.util.ArrayList;
import java.util.Collections;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.BufferedReader;
import java.io.StringWriter;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.util.Collections;
import java.util.Scanner;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class MedianFilter {
	private BufferedImage filteredImage;
	private BufferedImage[] images;
	
	public MedianFilter(String[] imageInputFilenames) {
		// constructor with array of names of the noisy images
		images = new BufferedImage[imageInputFilenames.length];
		for(int i = 0; i < imageInputFilenames.length; i++) {
			File one = new File(imageInputFilenames[i]);
			images[i] = readImage(one);
		}
		filteredImage = images[0];
	}
	
	public BufferedImage readImage(File imageFile) {
		BufferedImage bi = null;
		try {
			bi = ImageIO.read(imageFile);
		}
		catch(IOException e) {
			System.out.println("File can't be read");
		}
		return bi;
	}
	
	public BufferedImage removeNoise() {
		for(int i = 0; i < getHeight(); i++) {
			for(int j = 0; j < getWidth(); j++) {
				ArrayList<Integer> rgbValues = new ArrayList<Integer>();
				for(BufferedImage e: images) {
				rgbValues.add(e.getRGB(j,i));
				}
				int medianVal = getMedianValue(rgbValues);
				filteredImage.setRGB(j,i, getMedianValue(rgbValues));
			}
		}
		return filteredImage;
	}
	
	public int getMedianValue(ArrayList<Integer> pixels) {
		Collections.sort(pixels);
		return pixels.get(4);
	}
	
	public int writeImage(String outputFilename) {
		try {
			File filtered = new File(outputFilename);
			ImageIO.write(filteredImage, "jpg", filtered);
			return 0;
		}
		catch(IOException e) {
			System.out.println("Unable to write to file");
			return-1;
		}
	}
	
	public int getHeight() {
		// returns height (y-dimension) of filteredImage
		return filteredImage.getHeight();
	}
	public int getWidth() {
		// returns width (x-dimension) of filteredImage
		return filteredImage.getWidth();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//make array of strings of veg1.jpg to 8
		String[] veggies = {"veg1.jpg","veg2.jpg","veg3.jpg","veg4.jpg","veg5.jpg","veg6.jpg","veg7.jpg","veg8.jpg"};
		MedianFilter filter = new MedianFilter(veggies);
		filter.removeNoise();
		filter.writeImage("noMoreLlama.jpg");//write desired name of output file as the argument of this method
		//file will be output in folder where this program file is located
		}
		
	}

