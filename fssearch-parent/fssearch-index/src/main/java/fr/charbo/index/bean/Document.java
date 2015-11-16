package fr.charbo.index.bean;

import java.io.Serializable;
import java.util.Date;

public class Document implements Serializable {
  private String title;
  private String content;
  private String path;
  private Date date;

//  public Document(FileSystemObservable.MonEvent monEvent) {
//    this.title = monEvent.getName();
//    this.path = monEvent.getPath().toString();
//    this.date = new Date();
//  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getPath() {
    return path;
  }

  public void setPath(String path) {
    this.path = path;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

}
