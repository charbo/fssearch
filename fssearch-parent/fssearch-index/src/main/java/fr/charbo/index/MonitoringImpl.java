package fr.charbo.index;

import fr.charbo.index.bean.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class MonitoringImpl implements Monitoring {
  private static final Logger LOG = LoggerFactory.getLogger(MonitoringImpl.class);

  private FileIndexer fileIndexer;

  private String pathsDef;

  @Autowired
  public MonitoringImpl(FileIndexer fileIndexer, @Value("${fssearch.file.paths}") final String path) {
    this.fileIndexer = fileIndexer;
    this.pathsDef = path;
  }

  @PostConstruct
  public void onStartup() {
    launchIndexation();
  }


  @Override
  @Scheduled(cron="0 0 0 * * ?")
  public void launchIndexation() {
    LOG.info("launch indexation");
    String[] paths = pathsDef.split(";");
    
    for (String path : paths) {
	    Path root = Paths.get(new File(path).toURI());
	    if (Files.isDirectory(root)) {
	      try (DirectoryStream<Path> ds = Files.newDirectoryStream(root)) {
	          for (Path child : ds) {
              //TODO utiliser les même filtres que pour observable
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
}
