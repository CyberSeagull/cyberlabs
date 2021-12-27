import java.util.UUID;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

class CharUUID {
	public char cs;
	UUID uuid1;
	public CharUUID (char c) {
		this.cs=c;
		uuid1=UUID.randomUUID();
	    }
}

class SortbyUUID implements Comparator<CharUUID> { 
    public int compare(CharUUID  a, CharUUID  b) { 
        return  a.uuid1.compareTo(b.uuid1); 
    }
} 

public class PasswordManager {
	private static String[] generalPasswords = { "123456", "123456789", "password", "12345678", "111111", "123123", "12345", "1234567890", "1234567", "qwerty123",
            "000000", "1q2w3e", "aa12345678", "abc123", "password1", "1234", "qwertyuiop", "123321", "password123", "1q2w3e4r5t", "iloveyou", "654321", "666666", "987654321",
            "123", "123456a", "qwe123", "1q2w3e4r", "7777777", "1qaz2wsx", "123qwe", "zxcvbnm", "121212", "11111111", "asdasd", "a123456", "555555", "dragon", "112233",
            "123123123", "monkey", "asdfghjkl", "222222", "1234qwer", "asdfgh" };
	private  static String[] mostUsedWords = { "password", "qwerty", "dragon", "pussy", "baseball","football", "letmein", "monkey", "mustang", "michael", "shadow",
            "master", "jennifer", "jordan", "superman", "harley", "hunter", "fuckme", "ranger", "buster",  "thomas", "tigger", "robert", "soccer", "batman", "test", "pass",
            "killer", "hockey", "george", "charlie", "andrew", "michelle", "love", "sunshine",  "jessica", "pepper",  "daniel", "access", "joshua", "maggie", "starwars", "silver",
            "yankees", "hello", "freedom" };
    
	private static String allowed = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz!@#$%^&*()_-+=[{]};:>|./?";

    private static Random dice = new Random();

    public  List <String> GeneratePasswords(int count) {
    	if (count <= 0)
    		throw new IllegalArgumentException("COUNT<=0");
    	List<String> result=new ArrayList<String>(count);
    	
   
        for (int i = 0; i < count; i++)
        {
            switch (dice.nextInt(20))
            {
                case 1:
                	result.add(generalPasswords[dice.nextInt(generalPasswords.length)]);
                    break;
                case 2:
        
                	char[] c = allowed.toCharArray();        
                	
                    List<CharUUID> sch = new ArrayList<>();
                	for (int j = 0; j < allowed.length(); j++)
                    	sch.add( new CharUUID(c[j]));
                    
                	Collections.sort(sch, new SortbyUUID());
                    
                	String s="";
                    int passwlength=dice.nextInt(11)+10;
                    for(CharUUID go:sch) {
                    	if (s.length()>=passwlength) break;
                      	s+=go.cs;
                      	}
                	System.out.println(s);    
                	result.add(s);

                    break;

                default:
                	char[] word = mostUsedWords[dice.nextInt(mostUsedWords.length)].toCharArray();

                    for (int j = 0; j < word.length; j++)
                    {
                        if (Character.isLetter(word[j]) &&  Character.isLowerCase(word[j]) && dice.nextInt(100) > 80)
                            word[j] = Character.toUpperCase(word[j]);
                    }
                    result.add(new String(word));
                    break;
                	
            }
        }

    	
		return result;
    }
}
