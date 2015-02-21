package fr.charbo.server.impl;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.ElasticsearchException;
import org.junit.runners.model.InitializationError;

import fr.charbo.server.SearchEngine;

/**
 * The Class SearchEngineBuilder.
 */
public class SearchEngineBuilder {

  /** The search engine. */
  private static SearchEngineImpl SEARCH_ENGINE;

  /** The name. */
  private String name;

  /** The root path. */
  private String rootPath;

  /** The update rate. */
  private Integer updateRate = 60000;

  /**
   * Instantiates a new search engine builder.
   *
   * @param indexPath the index path
   */
  public SearchEngineBuilder(final String indexPath) {
    if (SEARCH_ENGINE == null) {
      SEARCH_ENGINE = new SearchEngineImpl(indexPath);
    }
  }


  /**
   * Sets the name.
   *
   * @param name the new name
   */
  public SearchEngineBuilder setName(final String name) {
    this.name = name;
    return this;
  }

  /**
   * Sets the root path.
   *
   * @param rootPath the new root path
   */
  public SearchEngineBuilder setRootPath(final String rootPath) {
    this.rootPath = rootPath;
    return this;
  }

  /**
   * Sets the update rate.
   *
   * @param updateRate the new update rate
   */
  public SearchEngineBuilder setUpdateRate(final Integer updateRate) {
    this.updateRate = updateRate;
    return this;
  }

  /**
   * Builds the.
   *
   * @return the search engine
   * @throws InitializationError
   * @throws IOException
   * @throws ElasticsearchException
   */
  public SearchEngine build() throws ElasticsearchException, IOException {
    if (StringUtils.isBlank(this.rootPath) || StringUtils.isBlank(this.name)) {
      throw new InstantiationError("rootPath and name must be initialiszed");
    }
    //TODO use response
    SEARCH_ENGINE.initializeDefaultRiver(this.name, this.rootPath, this.updateRate);
    return SEARCH_ENGINE;
  }
}
