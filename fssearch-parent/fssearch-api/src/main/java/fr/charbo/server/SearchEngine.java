package fr.charbo.server;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.query.QueryBuilder;

import fr.charbo.query.result.SearchResult;

/**
 * The Interface SearchEngine.
 */
public interface SearchEngine {

  /**
   * Results count.
   *
   * @param queryBuilder the query builder
   * @return the long
   */
  long resultsCount(QueryBuilder queryBuilder);

  /**
   * Search.
   *
   * @param queryBuilder the query builder
   * @return the search result
   */
  SearchResult search(QueryBuilder queryBuilder);

  /**
   * Search.
   *
   * @param queryBuilder the query builder
   * @param begin the begin
   * @param pageSize the page size
   * @return the search result
   */
  SearchResult search(QueryBuilder queryBuilder, int begin, int pageSize);


}
