package fr.charbo;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class Sample {
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
        Node node = null;
        try {

            node = NodeBuilder.nodeBuilder().settings(
                    ImmutableSettings.builder().put("path.data", "C:/temp_es").build()
            ).node();

            XContentBuilder river = jsonBuilder().prettyPrint().startObject()
                    .field("type", "fs")
                    .startObject("fs")
                    .field("url", "C:\\temp_es\\doc")
                    .field("update_rate", 10)
                    .endObject()
                    .startObject("index")
                    .field("bulk_size", 1)
                    .endObject()
                    .endObject();

            System.out.println("Startin river");
            System.out.println(river.string());
            IndexResponse response = node.client().prepareIndex("_river", "main_fs", "_meta").setSource(river).execute().actionGet();
            
//            //"includes": "*.doc,*.pdf"
//            XContentBuilder riv = jsonBuilder().prettyPrint().startObject()
//                    .field("type", "fs")
//                    .startObject("fs")
//                    .field("includes", "*.doc,*.pdf")
//                    .endObject()
//                    .endObject();
//            System.out.println(riv.string());
//            node.client().prepareIndex("_river", "main_fs", "_meta").setSource(riv).execute().actionGet();
            
            UpdateRequest updateRequest = new UpdateRequest();
            updateRequest.index("_river");
            updateRequest.type("main_fs");
            updateRequest.id("_meta");
            updateRequest.doc(jsonBuilder()
                    .startObject()
                        .field("includes", "*.doc,*.pdf")
                    .endObject());
            node.client().update(updateRequest).get();
            
            // Waiting for kill signal
            System.out.print("press return when finished:");
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (node != null) {
                node.close();
            }
        }
    
		
	}

}
