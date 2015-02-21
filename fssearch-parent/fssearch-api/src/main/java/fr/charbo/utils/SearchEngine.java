package fr.charbo.utils;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.query.QueryBuilder;

import fr.charbo.query.result.SearchResult;
import fr.charbo.server.River;

/**
 * The Interface SearchEngine.
 */
public interface SearchEngine {

  /**
   * Results count.
   *
   * @param queryBuilder the query builder
   * @param rivers the rivers
   * @return the long
   */
  long resultsCount(QueryBuilder queryBuilder, String... rivers);

  /**
   * Search.
   *
   * @param queryBuilder the query builder
   * @param rivers the rivers
   * @return the search result
   */
  SearchResult search(QueryBuilder queryBuilder, String... rivers);

  /**
   * Search.
   *
   * @param queryBuilder the query builder
   * @param begin the begin
   * @param pageSize the page size
   * @param rivers the rivers
   * @return the search result
   */
  SearchResult search(QueryBuilder queryBuilder, int begin, int pageSize, String... rivers);

  /**
   * Adds the river.
   *
   * @param river the river
   * @return the index response
   * @throws ElasticsearchException the elasticsearch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  IndexResponse addRiver(River river) throws ElasticsearchException, IOException;

  /**
   * Update river.
   *
   * @param river the river
   * @return the river
   * @throws InterruptedException the interrupted exception
   * @throws ExecutionException the execution exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  River updateRiver(River river) throws InterruptedException, ExecutionException, IOException;

}
