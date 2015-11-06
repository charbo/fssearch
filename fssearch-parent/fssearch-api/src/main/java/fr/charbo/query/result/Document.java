package fr.charbo.query.result;

/**
 * The Class Document.
 */
public class Document {

  /** The title. */
  @Mapping(key = "title")
  private String title;

  /** The author. */
//  @Mapping(key = "meta.author")
  private String author;

  /** The content. */
  private String content;

  /** The type. */
  private String type;

  /** The path. */
  @Mapping(key = "path")
  private String path;

  /**
   * Gets the title.
   *
   * @return the title
   */
  public String getTitle() {
    return this.title;
  }

  /**
   * Sets the title.
   *
   * @param title the new title
   */
  public void setTitle(final String title) {
    this.title = title;
  }

  /**
   * Gets the author.
   *
   * @return the author
   */
  public String getAuthor() {
    return this.author;
  }

  /**
   * Sets the author.
   *
   * @param author the new author
   */
  public void setAuthor(final String author) {
    this.author = author;
  }

  /**
   * Gets the content.
   *
   * @return the content
   */
  public String getContent() {
    return this.content;
  }

  /**
   * Sets the content.
   *
   * @param content the new content
   */
  public void setContent(final String content) {
    this.content = content;
  }

  /**
   * Gets the type.
   *
   * @return the type
   */
  public String getType() {
    return this.type;
  }

  /**
   * Sets the type.
   *
   * @param type the new type
   */
  public void setType(final String type) {
    this.type = type;
  }

  /**
   * Gets the path.
   *
   * @return the path
   */
  public String getPath() {
    return this.path;
  }

  /**
   * Sets the path.
   *
   * @param path the new path
   */
  public void setPath(final String path) {
    this.path = path;
  }



}
