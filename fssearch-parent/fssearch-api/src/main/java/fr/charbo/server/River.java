package fr.charbo.server;

import java.io.IOException;
import java.util.Set;

import org.elasticsearch.common.xcontent.XContentBuilder;


/**
 * The Interface FsRiver.
 */
public interface River {

  /**
   * Gets the root path.
   * Used to set "url" in FsRiver définition.
   * @return the root path
   */
  String getRootPath();

  /**
   * Gets the update rate.
   *
   * @return the update rate
   */
  Integer getUpdateRate();

  /**
   * Gets the name.
   *
   * @return the name
   */
  String getName();

  /**
   * Gets the storedtype.
   *
   * @return the current Stored Types
   */
  Set<String> getStoredtype();

  /**
   * Adds doc type in storedtype.
   *
   * @param docType the doc type
   * @return the current Stored Types
   */
  Set<String> addDocType(String docType);

  /**
   * Removes the doc type.
   *
   * @param docType the doc type
   * @return the current Stored Types
   */
  Set<String> removeDocType(String docType);

  /**
   * Gets the x content builder.
   *
   * @return the x content builder
   * @throws IOException
   */
  XContentBuilder getXContentBuilder() throws IOException;




}
