package fr.charbo;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;

public class Query {

  public static void main(final String[] args) {
    Node node = null;
    try {
      node = NodeBuilder.nodeBuilder().node();
      final Client client = node.client();

      QueryBuilders.boolQuery().must(QueryBuilders.termQuery("content", "zob"));

      final SearchResponse response = client.prepareSearch("name")
          .setTypes("doc")
          .setSearchType(SearchType.DEFAULT)
          .setQuery(QueryBuilders.termQuery("content", "zob"))
          //		        .setQuery(QueryBuilders.termQuery("author", "charbonnier"))             // Query
          //		        .setPostFilter(FilterBuilders.rangeFilter("age").from(12).to(18))   // Filter
          .addField("content")
          .addField("url")
          .setFrom(0).setSize(60).setExplain(true)
          .execute()
          .actionGet();

      System.out.println("-----------");
      //		System.out.println(response);

      for (final SearchHit hit : response.getHits()) {

        System.out.println(hit.getScore());
        System.out.println(hit.getFields().get("file.url").values());
        System.out.println(hit.getSourceAsString());
      }

    } finally {
      if (node != null) {
        node.close();
      }
    }
  }


}
