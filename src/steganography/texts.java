package steganography;

import java.io.IOException;

public class texts {
	private String para;

	public texts(String para){
		this.para = para;
	}

	public boolean encrypt(Image im, int key){
		String biKey = toBinaryStr(key);
		int keyLength = biKey.length();
		double ptg = Math.pow(keyLength,-1)*(keyLength-toBinaryStr(key).replace("1", "").length());
		if(para.length()<=((im.getHeight()*im.getWidth()*3)*ptg)){
			String biPara = toBinary();
			short[][][] pixelMatrix = im.getPixelMatrix();
			int row = 0; int col = 0; int i = 0; int k = 0;
			System.out.println(biKey);
			for(int index = 0; index < biPara.length(); index++){
				short b = Short.parseShort(biPara.substring(index,index+1));
				while (biKey.charAt(k) != '1'){
					i++;
					if (i == 3){
						i = 0;
						col ++;
					}
					if (col == im.getWidth()){
						col = 0;
						row ++;
					}
					if(k+1 == keyLength){
						k = 0;
					} else {
						k++;
					}
				}
				System.out.println("n"+pixelMatrix[row][col][i]+"b"+b);
				if (pixelMatrix[row][col][i] % 2 == 0){
					pixelMatrix[row][col][i] += b;
				} else {
					pixelMatrix[row][col][i] += (b - 1);
				}
				i++;
				if (i == 3){
					i = 0;
					col ++;
				}
				if (col == im.getWidth()){
					col = 0;
					row ++;
				}
				if(k+1 == keyLength){
					k = 0;
				} else {
					k++;
				}
			}
			try {
				im.setRGB(pixelMatrix);
				return true;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return false;
	}

	public boolean decrypt(Image im, Image or, int key){
		String biKey = toBinaryStr(key);
		
		return false;
	}

	private String toBinary(){
		StringBuilder builder = new StringBuilder();
		if (para != null && para.length()>0){
			for(int i = 0; i < para.length(); i++){
				builder.append(toBinaryStr(para.codePointAt(i)));
			}
		}
		return builder.toString();
	}

	private static String toBinaryStr(int para){
		String retVal = Integer.toBinaryString(para);
		while (retVal.length() < 8){
			retVal = "0" + retVal;
		}
		return retVal;
	}
}
