package fr.charbo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.StringUtils;

import fit.ActionFixture;
import fr.charbo.query.ContentField;
import fr.charbo.server.River;
import fr.charbo.server.Server;
import fr.charbo.server.impl.ElasticServer;
import fr.charbo.server.impl.FsRiver;

public class WorkFlowFssearchTest extends ActionFixture {
  private static final Integer updateRate = 15;

  private Server server;

  private String path;

  private String[] types;

  private String word;

  public int sample() {
    return 2;
  }

  public void repertoireAIndexer(final String path) {
    this.path = StringUtils.replace(path, "\\", File.separator);
  }

  public void typeDeDocumentAIndexer(final String toIndex) {
    this.types = toIndex.split(",");
  }


  public void indexer() throws IOException {
    this.server = new ElasticServer("C:\\temp_es\\elasticsearch");
    final River river = new FsRiver("name", this.path, updateRate);
    for (final String type : this.types) {
      river.addDocType(type);
    }
    this.server.addRiver(river);

  }

  public void motRecherche(final String word) {
    this.word = word;
  }

  public Long rechercher() throws InterruptedException {
    Thread.sleep(updateRate * 1000);

    final ContentField search = new ContentField(this.word);
    return this.server.getClient().resultsCount(search.getFuzzyBuilder(), "name");
  }


}
