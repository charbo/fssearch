package fr.charbo.temp;

import java.io.IOException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Index {
	
	public static void main(String[] args) {
		Node node = NodeBuilder.nodeBuilder().build().start();
	
		try {
			XContentBuilder source = jsonBuilder().startObject()
					.field("titre", "Titre 2")
					.field("auteur", "Charbonnier").endObject();
			node.client().prepareIndex("bibli", "livre", "2").setSource(source).execute().actionGet();
			node.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}
}
