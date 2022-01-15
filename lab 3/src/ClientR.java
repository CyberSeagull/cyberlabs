import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

class Craccount {
	   public int id;
	   public int money;
	   public String deletionTime;
	}
class Stplay {
	   public String message;
	   public Craccount account;
	   public int realNumber;
	   public long reallong;
	}

class StplayMT {
	   public String message;
	   public Craccount account;
	   public long realNumber;
	}

public class ClientR {
	public static int rnd(int max) // return range [0;max]
	{	return (int) (Math.random() * ++max);
	}

	public Craccount getplayerid(String pathuri, Gson g) {
        Craccount newacc=null;
        String str=null;
		try {
			do 
			{   
				str=get( pathuri+String.format("/createacc?id=%d", rnd(1000000)) );
				System.out.println( str );
				if ( !( (str.indexOf("error")>0) || (str==null)) ) {
					newacc = g.fromJson(str, Craccount.class);
				//	System.out.println( g.toJson(newacc) );
					System.out.println(newacc.id);
					break;
				}
			} while (true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return newacc;
	}
	public String get(String uri) throws Exception {
		HttpClient client = HttpClient.newHttpClient();
	    HttpRequest request = HttpRequest.newBuilder()
	          .uri(URI.create(uri))
	          .build();

	    HttpResponse<String> response =
	          client.send(request, BodyHandlers.ofString());
        String str=response.body(); 
	    return str;
	}
	

}
