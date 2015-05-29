package fr.charbo.temp;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class Sample {

  public static void main(final String[] args) throws InterruptedException, ExecutionException {
    Node node = null;
    try {

      node = NodeBuilder
				.nodeBuilder()
				.settings(
						ImmutableSettings.builder()
								.put("path.data", "c:/tmp_es/").build())
				.node();

      final CountResponse res = node.client().prepareCount("main_fs").setTypes("doc").execute().actionGet();

      System.out.println("-----------------");
      System.out.println(res.getCount());


      // //"includes": "*.doc,*.pdf"
      // XContentBuilder riv = jsonBuilder().prettyPrint().startObject()
      // .field("type", "fs")
      // .startObject("fs")
      // .field("includes", "*.doc,*.pdf")
      // .endObject()
      // .endObject();
      // System.out.println(riv.string());
      // node.client().prepareIndex("_river", "main_fs",
      // "_meta").setSource(riv).execute().actionGet();

      //      final UpdateRequest updateRequest = new UpdateRequest();
      //      updateRequest.index("_river");
      //      updateRequest.type("main_fs");
      //      updateRequest.id("_meta");
      //      final XContentBuilder update =
      //          jsonBuilder().prettyPrint().startObject().field("type", "fs").startObject("fs").field("includes", "*.doc,*.pdf").endObject().endObject();
      //      updateRequest.doc(update);
      //      node.client().update(updateRequest).get();

      // Waiting for kill signal
//      System.out.print("press return when finished:");
//      System.in.read();
    }  finally {
      if (node != null) {
        node.close();
      }
    }

  }

}
