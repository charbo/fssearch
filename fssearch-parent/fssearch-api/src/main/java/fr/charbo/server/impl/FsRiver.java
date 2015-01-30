package fr.charbo.server.impl;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ObjectUtils;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import fr.charbo.server.River;

/**
 * The Class FsRiver.
 */
@Component
public class FsRiver implements River {

  /** The name. */
  private final String name;

  /** The root path. */
  private final String rootPath;

  /** The update rate. */
  private final Integer updateRate;

  /** The stored type. */
  private final Set<String> storedType = new HashSet<String>();

  /**
   * Instantiates a new fs river.
   *
   * @param name the name
   * @param rootPath the root path
   * @param updateRate the update rate
   */
  @Autowired
  public FsRiver(@Value("${fssearch.default.river.name}") final String name, @Value("${fssearch.default.river.path}") final String rootPath,
      @Value("${fssearch.default.river.update.rate}") final Integer updateRate) {
    this.name = name;
    this.rootPath = rootPath;
    this.updateRate = updateRate;
  }

  /*
   * (non-Javadoc)
   *
   * @see fr.charbo.server.River#getRootPath()
   */
  @Override
  public String getRootPath() {
    return this.rootPath;
  }

  /*
   * (non-Javadoc)
   *
   * @see fr.charbo.server.River#getUpdateRate()
   */
  @Override
  public Integer getUpdateRate() {
    return this.updateRate;
  }

  /*
   * (non-Javadoc)
   *
   * @see fr.charbo.server.River#getName()
   */
  @Override
  public String getName() {
    return this.name;
  }

  /*
   * (non-Javadoc)
   *
   * @see fr.charbo.server.River#getStoredtype()
   */
  @Override
  public Set<String> getStoredtype() {
    return ObjectUtils.clone(this.storedType);
  }

  /*
   * (non-Javadoc)
   *
   * @see fr.charbo.server.River#addDocType(java.lang.String)
   */
  @Override
  public Set<String> addDocType(final String docType) {
    this.storedType.add(docType);
    return this.getStoredtype();
  }

  /*
   * (non-Javadoc)
   *
   * @see fr.charbo.server.River#removeDocType(java.lang.String)
   */
  @Override
  public Set<String> removeDocType(final String docType) {
    this.storedType.remove(docType);
    return this.getStoredtype();
  }

  /*
   * (non-Javadoc)
   *
   * @see fr.charbo.server.River#getXContentBuilder()
   */
  @Override
  public XContentBuilder getXContentBuilder() throws IOException {
    final XContentBuilder river =
        jsonBuilder().prettyPrint().startObject().field("type", "fs").startObject("fs").field("url", this.rootPath)
        .field("update_rate", this.updateRate);
    if (!this.storedType.isEmpty()) {
      river.array("includes", this.storedType.toArray());
    }
    river.endObject().startObject("index").field("bulk_size", 1).field("index", this.name).endObject().endObject();


    return river;
  }

}
