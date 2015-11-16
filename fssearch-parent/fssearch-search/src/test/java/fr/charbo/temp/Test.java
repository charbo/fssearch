package fr.charbo.temp;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;

public class Test {

	public static void main(String[] args) {
		Node node = null;
		try {
			node = NodeBuilder
					.nodeBuilder()
					.settings(
							ImmutableSettings.builder()
									.put("path.data", "C:/projet/elasticsearch/elasticsearch-1.4.4/data").build())
					.node();
			
			final SearchResponse res = node.client().prepareSearch("bibliotheque").setTypes("libre").setQuery(QueryBuilders.termQuery("titre", "action")).execute().actionGet();
//			client.prepareSearch().execute().actionGet();

		      System.out.println("-----------------");
		      System.out.println(res);

		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (node != null) {
				node.close();
			}
		}

	}

}
