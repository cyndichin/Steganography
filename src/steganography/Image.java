package steganography;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class Image {
	private BufferedImage image;
	private String address;
	private short[][][] pixelMatrix;
	private int width;
	private int height;
	private boolean hasAlphaChannel;

	public Image(String address) throws IOException{
		this.address = address;
		image = ImageIO.read(new File(address));
		width = image.getWidth();
		height = image.getHeight();
		hasAlphaChannel = image.getAlphaRaster() != null;
		processRGB();
	}

	public short[][][] getPixelMatrix(){
		return this.pixelMatrix;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public void setRGB(short[][][] pm) throws IOException {
		for (int row = 0, col = 0; row < height;){
			int rgb = (pm[row][col][3] << 24) | (pm[row][col][0] << 16) | (pm[row][col][1] << 8) | pm[row][col][2];
			image.setRGB(col, row, rgb);
			col++;
			if (col == width) {
				col = 0;
				row++;
			}
		}
		ImageIO.write(image, "PNG", new File(address + ".encrypted.png"));
	}

	private void processRGB() throws IOException {
		pixelMatrix = new short[height][width][4];
		final byte[] pixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
		if (hasAlphaChannel) {
			for (int pixel = 1, row = 0, col = 0; pixel < pixels.length;){
				pixelMatrix[row][col][3] = (short) (pixels[pixel] & 0xff); //alpha
				pixelMatrix[row][col][2] = (short) (pixels[pixel++] & 0xff); //blue
				pixelMatrix[row][col][1] = (short) (pixels[pixel++] & 0xff); //green
				pixelMatrix[row][col][0] = (short) (pixels[pixel++] & 0xff); //red
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		} else {
			for (int pixel = 0, row = 0, col = 0; pixel < pixels.length;){
				pixelMatrix[row][col][2] = (short) (pixels[pixel++] & 0xff); //blue
				pixelMatrix[row][col][1] = (short) (pixels[pixel++] & 0xff); //green
				pixelMatrix[row][col][0] = (short) (pixels[pixel++] & 0xff); //red
				pixelMatrix[row][col][3] = 0; //alpha
				col++;
				if (col == width) {
					col = 0;
					row++;
				}
			}
		}
	}
}
