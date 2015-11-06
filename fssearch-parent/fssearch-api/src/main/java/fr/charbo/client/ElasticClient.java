package fr.charbo.client;

import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.node.Node;

import fr.charbo.query.result.DocumentBuilder;
import fr.charbo.query.result.SearchResult;

/**
 * The Class ElasticClient.
 */
public class ElasticClient implements Client {

  /** The Constant DOC. */
  private static final String DOC = "doccument";

  /** The node. */
  private final Node node;

  /**
   * Instantiates a new elastic client.
   *
   * @param node the node
   */
  public ElasticClient(final Node node) {
    this.node = node;
  }

  /*
   * (non-Javadoc)
   *
   * @see fr.charbo.client.Client#resultsCount(org.elasticsearch.index.query.QueryBuilder,
   * java.lang.String[])
   */
  @Override
  public long resultsCount(final QueryBuilder queryBuilder, final String... rivers) {
    final CountResponse res = this.node.client().prepareCount(rivers).setTypes(DOC).setQuery(queryBuilder).execute().actionGet();
    return res.getCount();
  }

  /* (non-Javadoc)
   * @see fr.charbo.client.Client#search(org.elasticsearch.index.query.QueryBuilder, java.lang.String[])
   */
  @Override
  public SearchResult search(final QueryBuilder queryBuilder, final String... rivers) {
    return this.search(queryBuilder, 0, Integer.MAX_VALUE, rivers);
  }

  /* (non-Javadoc)
   * @see fr.charbo.client.Client#search(org.elasticsearch.index.query.QueryBuilder, int, int, java.lang.String[])
   */
  @Override
  public SearchResult search(final QueryBuilder queryBuilder, final int begin, final int pageSize, final String... rivers) {
    final SearchRequestBuilder searchRequestBuilder =
        this.node.client().prepareSearch(rivers).setSearchType(SearchType.QUERY_THEN_FETCH).setQuery(queryBuilder)
//        .addField("title")
//        .addField("content")
//        .addField("path")
//        .setFrom(begin).setSize(pageSize)
            ;
    final SearchResponse response = searchRequestBuilder.execute().actionGet();
    return new SearchResult(response.getHits().getTotalHits() ,DocumentBuilder.extractDocuments(response));
  }


}
