package fr.charbo.index;

import fr.charbo.index.bean.Document;

public interface FileIndexer {
  void deleteDocument(Document document);

  void indexDocument(Document document);
}
