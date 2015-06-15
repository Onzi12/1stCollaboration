package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class ByteArray {

	/**
	 * convert file into array of bytes
	 * @param file
	 * @return byte[]
	 */
	public static byte[] convertFileToByteArray(File file) throws Exception {
		
        byte[] bFile = new byte[(int)file.length()];
		 
    	FileInputStream fileInputStream = new FileInputStream(file);
	    fileInputStream.read(bFile);
	    fileInputStream.close();
        
        return bFile;
        
	}
	
	/**
	 * convert array of bytes into file
	 * @param bFile
	 * @param path
	 * @throws Exception
	 */
	public static void writeByteArrayToFile(byte[] bFile, String path) throws Exception {
		
	    FileOutputStream fileOuputStream = new FileOutputStream(path); 
	    fileOuputStream.write(bFile);
	    fileOuputStream.close();
		
	}
	
	
}
