package fr.charbo.client;

import fr.charbo.query.result.DocumentBuilder;
import fr.charbo.query.result.SearchResult;
import org.elasticsearch.action.count.CountResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.Client;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * The Class ElasticClient.
 */
public class ElasticClient implements SearchClient {

  /** The Constant DOC. */
  private static final String DOC = "doccument";

  /** The node. */
  private final Client client;

  /**
   * Instantiates a new elastic client.
   *
   * @param client the client
   */
  public ElasticClient(final Client client) {
    this.client = client;
  }

  /*
   * (non-Javadoc)
   *
   * @see fr.charbo.client.SearchClient#resultsCount(org.elasticsearch.index.query.QueryBuilder,
   * java.lang.String[])
   */
  @Override
  public long resultsCount(final QueryBuilder queryBuilder, final String... rivers) {
    final CountResponse res = client.prepareCount(rivers).setTypes(DOC).setQuery(queryBuilder).execute().actionGet();
    return res.getCount();
  }

  /* (non-Javadoc)
   * @see fr.charbo.client.SearchClient#search(org.elasticsearch.index.query.QueryBuilder, java.lang.String[])
   */
  @Override
  public SearchResult search(final QueryBuilder queryBuilder, final String... rivers) {
    return this.search(queryBuilder, 0, Integer.MAX_VALUE, rivers);
  }

  /* (non-Javadoc)
   * @see fr.charbo.client.SearchClient#search(org.elasticsearch.index.query.QueryBuilder, int, int, java.lang.String[])
   */
  @Override
  public SearchResult search(final QueryBuilder queryBuilder, final int begin, final int pageSize, final String... rivers) {
    final SearchRequestBuilder searchRequestBuilder =
            client.prepareSearch("documents")
                    .setTypes("document")
                    .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                    .setQuery(queryBuilder)
                    .addField("content")
                    .addField("title")
                    .addField("path")
                    ;
    final SearchResponse response = searchRequestBuilder.execute().actionGet();
    return new SearchResult(response.getHits().getTotalHits(), DocumentBuilder.extractDocuments(response));
  }


}
