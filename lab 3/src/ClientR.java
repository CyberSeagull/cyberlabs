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

public class ClientR {
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
