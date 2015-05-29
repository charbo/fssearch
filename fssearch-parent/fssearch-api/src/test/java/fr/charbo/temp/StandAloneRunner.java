package fr.charbo.temp;

import org.elasticsearch.common.io.FileSystemUtils;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import java.io.File;
import java.io.IOException;
import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class StandAloneRunner {
	public static void main(String[] args) {
		Node node = null;
		try {
			// Remove old data
			FileSystemUtils.deleteRecursively(new File("c:/tmp_es/data"));
			node = NodeBuilder
					.nodeBuilder()
					.settings(
							ImmutableSettings.builder()
									.put("path.data", "c:/tmp_es/").build())
					.node();
			XContentBuilder river = jsonBuilder().prettyPrint().startObject()
					.field("type", "fs").startObject("fs")
					.field("url", "C:/tmp_es/data")
					.field("update_rate", 500)
					.field("excludes", "archives")
					.endObject().startObject("index").field("bulk_size", 1)
					.endObject().endObject();
			System.out.println("Startin river");
			System.out.println(river.string());
			node.client().prepareIndex("_river", "main_fs", "_meta")
					.setSource(river).execute().actionGet();
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
