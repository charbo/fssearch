package fr.charbo.index;

import com.google.gson.Gson;
import fr.charbo.index.bean.Document;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.elasticsearch.client.Client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileIndexer {

  private Client client;

  public FileIndexer(Client client) {
    this.client = client;
  }


  public void deleteDocument(Document document) {
    client.prepareDelete("documents", "document", document.getTitle().hashCode() + "").execute();
  }

  public void updateDocument(Document document) {
    Tika tika = new Tika();
    try {
      InputStream fileReader = Files.newInputStream(Paths.get(document.getPath()));

      String content =  tika.parseToString(fileReader);

      document.setContent(content);
      Gson gson = new Gson();
      String json = gson.toJson(document);
      client.prepareIndex("documents", "document", document.getTitle().hashCode() + "").setSource(json).execute();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TikaException e) {
      e.printStackTrace();
    }
  }
}
