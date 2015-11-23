package fr.charbo.index.bean;

import fr.charbo.index.FileSystemObservable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;

@Service
public class Indexer {

  private FileSystemObservable fileSystemObservable;

  @Autowired
  public Indexer(FileSystemObservable fileSystemObservable) {
    this.fileSystemObservable = fileSystemObservable;
  }

  public void launch(String path) {
    fileSystemObservable.subscribeActionIndex(Paths.get(new File(path).toURI()));
  }
}
