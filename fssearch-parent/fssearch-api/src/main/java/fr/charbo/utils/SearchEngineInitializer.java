package fr.charbo.utils;

import java.io.IOException;

import org.elasticsearch.ElasticsearchException;

/**
 * The Class SearchEngineInitializer.
 */
public class SearchEngineInitializer {

  /** The instance. */
  private static SearchEngine SEARCHENGINE;

  private static SearchEngineInitializer INSTANCE;

  /** The river name. */
  private static String riverName;

  /** The update rate. */
  private  static Integer updateRate;

  /** The index path. */
  private static String indexPath;

  /** The root path. */
  private  static String rootPath;


  /**
   * Instantiates a new search engine initializer.
   *
   * @param indexPath the index path
   * @param riverName the river name
   * @param rootPath the root path
   * @param updateRate the update rate
   */
  public static final SearchEngineInitializer init(final String indexPath, final String riverName, final String rootPath , final Integer updateRate) {
    if (INSTANCE == null) {
      SearchEngineInitializer.indexPath = indexPath;
      SearchEngineInitializer.riverName = riverName;
      SearchEngineInitializer.rootPath = rootPath;
      SearchEngineInitializer.updateRate = updateRate;
    }
    return INSTANCE;
  }

  /**
   * Gets the search engine.
   *
   * @return the search engine
   * @throws ElasticsearchException the elasticsearch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  public static final SearchEngine getSearchEngine() throws ElasticsearchException, IOException {
    if (SEARCHENGINE == null) {
      SEARCHENGINE = new SearchEngineImpl(indexPath, riverName, rootPath, updateRate);
    }
    return SEARCHENGINE;
  }

}
