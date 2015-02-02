package fr.charbo.query.result;

import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

/**
 * The Class SearchResult.
 */
public class SearchResult {

  /** The total count. */
  private final long totalCount;

  /** The documents. */
  private final List<Document> documents;

  /**
   * Instantiates a new search result.
   *
   * @param totalCount the total count
   * @param documents the documents
   */
  public SearchResult(final long totalCount, final List<Document> documents) {
    this.totalCount = totalCount;
    this.documents = documents;

  }


  /**
   * Gets the total count.
   *
   * @return the total count
   */
  public long getTotalCount() {
    return this.totalCount;
  }


  /**
   * Gets the documents.
   *
   * @return the documents
   */
  public List<Document> getDocuments() {
    return ObjectUtils.clone(this.documents);
  }



}
