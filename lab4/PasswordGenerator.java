import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;


public class PasswordGenerator {

	
    private static void SaveToCsv(List<String> records, String fName) {
	    String currentDir = System.getProperty("user.dir");
	    String fnPath  = currentDir+'/'+fName;
    	try	    
    	{      
    		BufferedWriter writter = new BufferedWriter(new FileWriter(fnPath));
            for(String str: records) {       
		     	 writter.write(str+  "\n");
        	}
            writter.close();
		   }
		   catch (FileNotFoundException e) {
		     e.printStackTrace();
		   }
		   catch (IOException e) {
		     e.printStackTrace();
		   }	   
	   }	

	public static void main(String[] args) throws NoSuchAlgorithmException {
	    int passwordCount = 100000;

	    PasswordManager ps = new PasswordManager();
	    List<String> passwords = ps.GeneratePasswords(passwordCount);
	    List<String>  desHash =  HashTool.HashDes(passwords);
        SaveToCsv(desHash, "Weak.csv");

	    List<String>  bcryptHash =  HashTool.HashBcrypt(passwords);
        SaveToCsv(bcryptHash, "Strong.csv");

	}
	
}
