package fr.charbo.server.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.index.query.QueryBuilder;

import fr.charbo.query.result.SearchResult;
import fr.charbo.server.River;
import fr.charbo.server.SearchEngine;
import fr.charbo.server.Server;

/**
 * The Class SearchEngineImpl.
 */
public class SearchEngineImpl implements SearchEngine {

  /** The rivers. */
  private final Set<River> rivers = new HashSet<River>();

  /** The server. */
  private final Server server;

  /** The default river. */
  private River defaultRiver;

  /**
   * Instantiates a new search engine impl.
   *
   * @param path the path
   * @param riverName the river name
   * @param rootPath the root path
   * @param updateRate the update rate
   * @throws IOException
   * @throws ElasticsearchException
   */
  protected SearchEngineImpl(final String indexPath) {
    this.server = new ElasticServer(indexPath);

  }


  /**
   * Initialize default river.
   *
   * @param riverName the river name
   * @param rootPath the root path
   * @param updateRate the update rate
   * @throws ElasticsearchException the elasticsearch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  protected River initializeDefaultRiver(final String riverName, final String rootPath)  {
    this.defaultRiver = new FsRiver(riverName, rootPath);
    return this.defaultRiver;
  }
  
  protected IndexResponse launchRiver() throws ElasticsearchException, IOException {
	  return this.addRiver(this.defaultRiver);
  }


  /* (non-Javadoc)
   * @see fr.charbo.utils.SearchEngine#resultsCount(org.elasticsearch.index.query.QueryBuilder, java.lang.String[])
   */
  @Override
  public long resultsCount(final QueryBuilder queryBuilder, final String... rivers) {
    return this.server.getClient().resultsCount(queryBuilder, rivers);
  }

  /* (non-Javadoc)
   * @see fr.charbo.utils.SearchEngine#search(org.elasticsearch.index.query.QueryBuilder, java.lang.String[])
   */
  @Override
  public SearchResult search(final QueryBuilder queryBuilder, final String... rivers) {
    return this.server.getClient().search(queryBuilder, rivers);
  }

  /* (non-Javadoc)
   * @see fr.charbo.utils.SearchEngine#search(org.elasticsearch.index.query.QueryBuilder, int, int, java.lang.String[])
   */
  @Override
  public SearchResult search(final QueryBuilder queryBuilder, final int begin, final int pageSize, final String... rivers) {
    return this.server.getClient().search(queryBuilder, begin, pageSize, rivers);
  }

  /* (non-Javadoc)
   * @see fr.charbo.utils.SearchEngine#addRiver(fr.charbo.server.River)
   */
  @Override
  public IndexResponse addRiver(final River river) throws ElasticsearchException, IOException {
    this.rivers.add(river);
    return this.server.addRiver(river);
  }

  /* (non-Javadoc)
   * @see fr.charbo.utils.SearchEngine#updateRiver(fr.charbo.server.River)
   */
  @Override
  public River updateRiver(final River river) throws InterruptedException, ExecutionException, IOException {
    if (this.rivers.contains(river)) {
      this.rivers.remove(river);
      this.rivers.add(river);
      this.server.updateRiver(river);
    }
    return river;
  }


}
