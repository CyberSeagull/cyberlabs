
public class repeatingxor {
	public static byte[] decrypt (String text, String key)
    {
        byte [] res = new byte [text.length()];
        byte [] btext = text.getBytes();
        byte [] skey = key.getBytes();
        for (int i = 0; i<text.length();i++){
            res[i] = (byte) (btext[i]^skey[i % key.length()]);
        }
        return (res);
    }
	


public static void main(String[] args) {
	System.out.println(decrypt("1207554F07181D45041C1A1E43534212131D4E134445541F0D4900014F140A444D120D48014E104F011255190C011C00", "7468276f7070726573736f7227732077726f6e67"));

}
}
