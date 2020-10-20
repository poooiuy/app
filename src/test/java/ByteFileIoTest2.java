import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class ByteFileIoTest {
	
	
	
	public static void main(String[] args) {
		
		FileInputStream fis = null;
		FileOutputStream fos = null;
		
		try {
			fis = new FileInputStream("src/test/java/ByteFileIoTest.java");
			fos = new FileOutputStream("src/test/java/ByteFileIoTest2.java");
			
			int readData = -1;
			while( (readData=fis.read()) != -1 ) {
				fos.write(readData);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fis.close();
				fos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}

}
