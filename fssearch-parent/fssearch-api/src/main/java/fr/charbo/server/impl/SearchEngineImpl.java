package fr.charbo.server.impl;

import org.elasticsearch.index.query.QueryBuilder;

import fr.charbo.query.result.SearchResult;
import fr.charbo.server.SearchEngine;
import fr.charbo.server.Server;

/**
 * The Class SearchEngineImpl.
 */
public class SearchEngineImpl implements SearchEngine {

  /** The server. */
  private final Server server;


  public SearchEngineImpl(final String url) {
    this.server = new ElasticServer(url);

  }

  /* (non-Javadoc)
   * @see fr.charbo.utils.SearchEngine#resultsCount(org.elasticsearch.index.query.QueryBuilder, java.lang.String[])
   */
  @Override
  public long resultsCount(final QueryBuilder queryBuilder) {
    return this.server.getSearchClient().resultsCount(queryBuilder);
  }

  /* (non-Javadoc)
   * @see fr.charbo.utils.SearchEngine#search(org.elasticsearch.index.query.QueryBuilder, java.lang.String[])
   */
  @Override
  public SearchResult search(final QueryBuilder queryBuilder) {
    return this.server.getSearchClient().search(queryBuilder);
  }

  /* (non-Javadoc)
   * @see fr.charbo.utils.SearchEngine#search(org.elasticsearch.index.query.QueryBuilder, int, int, java.lang.String[])
   */
  @Override
  public SearchResult search(final QueryBuilder queryBuilder, final int begin, final int pageSize) {
    return this.server.getSearchClient().search(queryBuilder, begin, pageSize);
  }



}
