
public class repeatingxor {
	public static String decrypt (String text, String key)
    {
        String result = "";
        byte [] btext = text.getBytes();
        byte [] skey = key.getBytes();
        for (int i = 0; i<text.length();i++){
            result+= String.format("%X", 0xF &((byte) (btext[i]^skey[i % key.length()])));
        }
        return result;
    }
	
}
