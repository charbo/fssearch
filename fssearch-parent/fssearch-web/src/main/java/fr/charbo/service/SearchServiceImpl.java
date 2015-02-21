package fr.charbo.service;

import java.io.IOException;
import java.util.List;

import org.elasticsearch.ElasticsearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.charbo.query.ContentField;
import fr.charbo.query.result.Document;
import fr.charbo.utils.SearchEngine;
import fr.charbo.utils.SearchEngineInitializer;


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
    SearchEngineInitializer.init(indexPath, riverName, riverPath, updateRate*1000*60);
    this.searchEngine = SearchEngineInitializer.getSearchEngine();

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
