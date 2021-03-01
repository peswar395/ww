
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class dictionary {
	
	static String localDir = System.getProperty("user.dir");

	public static void main(String[] args) {
		String fileName = "dictionary.txt";
		boolean fileExists = doesFileExist(fileName);
		BufferedReader reader;
		try {
			if (fileExists) {
				System.out.println("File exists ");
				reader = new BufferedReader(new FileReader(localDir + "/" + fileName));
				String line = reader.readLine();
				while (line != null) {
					//read words and meanings into array
					String[] words_meanings = line.split("–");
					//print word first
					System.out.println(words_meanings[0]);
					//then print meanings
					String[] meanings = words_meanings[1].split(",");
					for(String val: meanings) {
					    System.out.println(val);
					}
					// read next line
					line = reader.readLine();
				}
				reader.close();
			} else {
				System.out.println(" File " + fileName + " does NOT exists in path "+ localDir);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean doesFileExist(String path) {
		boolean exists = false;
		try {
			File temp = new File(localDir + "/" + path);
			exists = temp.exists();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return exists;
	}

}
