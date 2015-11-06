package fr.charbo.temp;

import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentBuilder;

import java.io.IOException;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Index {
	
	public static void main(String[] args) {

    Client client = new TransportClient()
            .addTransportAddress(new InetSocketTransportAddress("localhost", 9300));
		try {
			XContentBuilder source = jsonBuilder().startObject()
					.field("titre", "Titre 3")
					.field("auteur", "Charbonnier").endObject();

      System.out.println(source.string());

			client.prepareIndex("bibli", "livre", "2").setSource(source).execute().actionGet();
			client.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
