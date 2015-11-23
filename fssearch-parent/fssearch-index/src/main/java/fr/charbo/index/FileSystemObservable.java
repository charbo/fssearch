package fr.charbo.index;

import java.nio.file.Path;

public interface FileSystemObservable {

  void subscribeActionIndex(Path file);
}
