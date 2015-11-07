package fr.charbo.temp;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHitField;

import java.util.Map;

public class Query {

  public static void main(final String[] args) {
    Client client = new TransportClient().addTransportAddress(new InetSocketTransportAddress("127.0.0.1", 9300));
      SearchResponse response = client.prepareSearch("documents")
              .setTypes("document")
              .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
              .setQuery(QueryBuilders.termQuery("title", "gael.txt"))             // Query
              .addField("content")
              .addField("title")
              .addField("path")
              .execute()
              .actionGet();


      for (final SearchHit hit : response.getHits()) {
          System.out.println(hit.getScore());
          System.out.println("-----");
          for (Map.Entry<String, SearchHitField> entry : hit.getFields().entrySet()) {
              System.out.println(entry.getKey());
              System.out.println(entry.getValue().getValue() + "");
          }
          System.out.println("-----");
          System.out.println(hit.getSourceAsString());
      }
  }


}
