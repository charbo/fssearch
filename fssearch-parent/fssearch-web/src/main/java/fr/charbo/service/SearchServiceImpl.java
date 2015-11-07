package fr.charbo.service;

import java.io.IOException;
import java.util.List;

import fr.charbo.server.impl.SearchEngineImpl;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import fr.charbo.query.ContentField;
import fr.charbo.query.result.Document;
import fr.charbo.server.SearchEngine;


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
   * @param query the query
   * @return the list
   */
  @Override
  public List<Document> search(final String query) {
    //TODO
    final ContentField content = new ContentField(query);
    
    return this.searchEngine.search(QueryBuilders.fuzzyLikeThisQuery().likeText(query)).getDocuments();
  }






}
