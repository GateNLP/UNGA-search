package unga.search;

import java.net.URL;
import java.util.*;

public class DocumentResult {

  public DocumentResult(String title, String url) {
    this.title = title;
    this.url = url;
    this.sentences = new ArrayList<String>();
  }

  String title;
  String url;

  List<String> sentences;

  public String getTitle() {
    return title;
  }

  public String getURL() {
    return url;
  }

  public List<String> getSentences() {
    return sentences;
  }

}
