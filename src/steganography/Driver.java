package steganography;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Driver {
	public static void main(String[] args) throws IOException{
		Scanner input = new Scanner(new File("originalFile.txt"));
		StringBuilder builder = new StringBuilder();
		if(input.hasNextLine()){
			builder.append(input.nextLine());
		}
		input.close();
		texts t = new texts(builder.toString());
		Image im = new Image("trees.jpg");
		writeRGB(im);
		t.encrypt(im, 23);
	}
	
	public static void writeRGB (Image im){
		try {
			PrintWriter writer = new PrintWriter("ImageRGB.txt");
			for (int i = 0; i < im.getPixelMatrix().length; i++){
				writer.print("[");
				for (int j = 0; j < im.getPixelMatrix()[i].length; j++){
					writer.print("[");
					for(int k = 0; k < 4; k++){
						writer.print(im.getPixelMatrix()[i][j][k] + ",");
					}
					writer.print("],");
				}
				writer.println("]");
			}
			writer.close();
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
