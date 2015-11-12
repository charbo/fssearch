package fr.charbo.service;

import fr.charbo.query.ContentField;
import fr.charbo.query.TitleField;
import fr.charbo.query.result.Document;
import fr.charbo.server.SearchEngine;
import fr.charbo.server.impl.SearchEngineImpl;
import org.elasticsearch.ElasticsearchException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


/**
 * The Class SearchServiceImpl.
 */
@Service
public class SearchServiceImpl implements SearchService {

  /** The search engine. */
  private final SearchEngine searchEngine;


  /**
   * Instantiates a new search service impl.
   *
   * @throws ElasticsearchException the elasticsearch exception
   * @throws IOException Signals that an I/O exception has occurred.
   */
  @Autowired
  public SearchServiceImpl(@Value("${fssearch.elastisearch.server.url}") final String url) throws ElasticsearchException, IOException {
    this.searchEngine = new SearchEngineImpl(url);
  }

  /**
   * Search.
   *
   * @param expression the expression
   * @return the list
   */
  @Override
  public List<Document> search(final String expression) {
    final ContentField content = new ContentField(expression);
    final TitleField title = new TitleField(content, expression);
    return this.searchEngine.search(title.getFuzzyBuilder()).getDocuments();
  }






}
