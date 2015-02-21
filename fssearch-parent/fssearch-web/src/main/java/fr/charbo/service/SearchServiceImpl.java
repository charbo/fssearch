package fr.charbo.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.ElasticsearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.charbo.query.ContentField;
import fr.charbo.query.result.Document;
import fr.charbo.server.SearchEngine;
import fr.charbo.server.impl.SearchEngineBuilder;


/**
 * The Class SearchServiceImpl.
 */
@Service
public class SearchServiceImpl implements SearchService {

  /** The search engine. */
  private final SearchEngine searchEngine;

  /** The river name. */
  private final String riverName;

  /**
   * Instantiates a new search service impl.
   *
   * @param indexPath the index path
   * @param riverName the river name
   * @param riverPath the river path
   * @param updateRate the update rate
   * @throws ElasticsearchException the elasticsearch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Autowired
  public SearchServiceImpl(@Value("${fssearch.elastisearch.server.path}") final String indexPath, final @Value("${fssearch.default.river.name}") String riverName,
      @Value("${fssearch.default.river.path}") final String riverPath , @Value("${fssearch.default.river.update.rate}") final Integer updateRate) throws ElasticsearchException, IOException {
    this.riverName = riverName;
    final SearchEngineBuilder builder = new SearchEngineBuilder(indexPath);
    builder.setName(riverName).setRootPath(riverPath).setUpdateRate(updateRate*1000*60);
    this.searchEngine = builder.build();

  }

  /**
   * Search.
   *
   * @param query the query
   * @return the list
   */
  @Override
  public List<Document> search(final String query) {
    //TODO
    final ContentField content = new ContentField(query);
    return this.searchEngine.search(content.getFuzzyBuilder(),this.riverName).getDocuments();
  }






}
