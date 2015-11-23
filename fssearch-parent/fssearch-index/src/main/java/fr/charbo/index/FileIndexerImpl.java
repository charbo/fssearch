package fr.charbo.index;

import com.google.gson.Gson;
import fr.charbo.index.bean.Document;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
public class FileIndexerImpl implements FileIndexer {

  private Client client;

  @Autowired
  public FileIndexerImpl(@Value("${fssearch.server.url}") final String url) {
    this.client = new TransportClient().addTransportAddress(new InetSocketTransportAddress(url, 9300));;
  }

  @Override
  public void deleteDocument(Document document) {
    client.prepareDelete("documents", "document", document.getTitle().hashCode() + "").execute();
  }

  @Override
  public void indexDocument(Document document) {
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
