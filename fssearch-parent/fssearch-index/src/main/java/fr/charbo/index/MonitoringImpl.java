package fr.charbo.index;

import fr.charbo.index.bean.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.File;

@Component
public class MonitoringImpl implements Monitoring {
  private static final Logger LOG = LoggerFactory.getLogger(MonitoringImpl.class);

  private FileIndexer fileIndexer;

  private Path root;

  @Autowired
  public MonitoringImpl(FileIndexer fileIndexer, @Value("${fssearch.file.path}") final String path) {
    this.fileIndexer = fileIndexer;
    root = Paths.get(new File(path).toURI());
  }


  @Override
  @Scheduled(fixedRate = 500000)
  public void launchIndexation() {
    LOG.info("launch indexation");
    if (Files.isDirectory(root)) {
      try (DirectoryStream<Path> ds = Files.newDirectoryStream(root)) {
          for (Path child : ds) {
            if (!Files.isDirectory(child)) {
              Document document = new Document();
              document.setTitle(child.getFileName().toString());
              document.setPath(child.toString());

              fileIndexer.indexDocument(document);
            }
          }
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      //TODO throw exception
    }

  }
}
