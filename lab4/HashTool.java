import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.nio.charset.StandardCharsets;
import java.math.BigInteger;
/*
class SaltHash {
	String salt;
	String hash;
	public SaltHash (String s, String h) {
		salt=s;
		hash=h;
	    }
}
*/

public class HashTool {

        public static List<String> HashDes(List<String> passwords) throws NoSuchAlgorithmException
        {
        	List<String> result=new ArrayList<String>(passwords.size());
            MessageDigest md = MessageDigest.getInstance("SHA-256");
       
            for(String psw:passwords) {       
        		md.update(psw.getBytes(StandardCharsets.UTF_8));
                byte[] digest = md.digest();
                String hash = String.format("%064x", new BigInteger(1, digest));
                result.add(hash);
                System.out.println("Converted=> " +psw+" to=> "+hash);
        	}
            return result;
        }
        
        
        public static List<String> HashBcrypt(List<String> passwords)
        {
        	List<String> result=new ArrayList<String>(passwords.size());
            long start = System.currentTimeMillis();

        	int i=0;
        	for(String psw:passwords) {
        		String salt = BCrypt.gensalt(5);
        		String hash = BCrypt.hashpw(psw, salt);
                result.add(new String(salt+","+hash) );
        		System.out.println((i++)+"  SALT=> " +salt+" to=> "+hash);
        	 }
            long timeWorkCode = System.currentTimeMillis() - start;
     	    System.out.println(" TIME "+ timeWorkCode + " ms");
            return result;        	
        }
    	

 /*       
        public static List<SaltHash> HashBcrypt(List<String> passwords)
        {
        	List<SaltHash> result=new ArrayList<SaltHash>(passwords.size());
            long start = System.currentTimeMillis();

        	int i=0;
        	for(String psw:passwords) {
        		String salt = BCrypt.gensalt();
        		String hash = BCrypt.hashpw(psw, salt);
                result.add(new SaltHash(salt+","+hash,hash) );
        		System.out.println((i++)+"  SALT=> " +salt+" to=> "+hash);
        	 }
            long timeWorkCode = System.currentTimeMillis() - start;
     	    System.out.println(" TIME "+ timeWorkCode + " ms");
            return result;        	
        }
    	
*/	
}
