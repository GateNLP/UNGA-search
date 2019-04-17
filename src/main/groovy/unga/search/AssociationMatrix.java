package unga.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gate.mimir.tool.WebUtils;
import tnt.NominalSlot;
import tnt.QueryExplosion;
import tnt.Shrapnel;

public class AssociationMatrix {



  private static Pattern regexp = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"");

  private List<String> rowStrings, columnStrings;

  private List<Entry> links;

  public static AssociationMatrix build(String keywords1, String keywords2) {
    return new AssociationMatrix(keywords1, keywords2);
  }

  public void populate(String indexURL, WebUtils webUtils) throws Exception {
    NominalSlot word1 = new NominalSlot("word1", rowStrings.toArray(new String[rowStrings.size()]));
    NominalSlot word2 = new NominalSlot("word2", columnStrings.toArray(new String[columnStrings.size()]));

    links = Collections.synchronizedList(new ArrayList<Entry>());

    Shrapnel query = new Shrapnel("({Sentence} OVER (\"${word1}\" AND \"${word2}\"))", new ArrayList<String>());;

    List<Shrapnel> exploded = QueryExplosion.explode(query,word2,word1);

    //for (Shrapnel expanded : exploded) {
    exploded.parallelStream().forEach((expanded) -> {
      try {
        expanded.execute(indexURL, webUtils, false);

        if (expanded.getCount() > 0) {
          System.out.println(expanded.getFillers());

          links.add(new Entry(rowStrings.indexOf(expanded.getFillers().get(0)),columnStrings.indexOf(expanded.getFillers().get(1)),expanded.getCount()));
        }
      }
      catch (Exception e) {
        e.printStackTrace();
      }

    });
    //}
  }


  private AssociationMatrix(String keywords1, String keywords2) {
    //no idea why this is necessary but without I get a class cast exception
    System.out.println(keywords1.getClass());
    System.out.println(keywords2.getClass());

    this.rowStrings = splitKeywords(keywords1);
    this.columnStrings = splitKeywords(keywords2);
  }

  public List<Node> getRow_nodes() {
    List<Node> nodes = new ArrayList<Node>();

    for (int i = 0 ; i < rowStrings.size() ; ++i) {
      nodes.add(new Node(rowStrings.get(i),i));
    }

    return nodes;
  }

  public List<Node> getCol_nodes() {
    List<Node> nodes = new ArrayList<Node>();

    for (int i = 0 ; i < columnStrings.size() ; ++i) {
      nodes.add(new Node(columnStrings.get(i),i));
    }

    return nodes;
  }

  public List<Entry> getLinks() {
    return links;
  }

  private static List<String> splitKeywords(String input) {
    List<String> output = new ArrayList<String>();

    int sort = 0;

    Matcher matcher = regexp.matcher(input);

    while (matcher.find()) {
      String term = "";

      if (matcher.group(1) != null) {
        term = matcher.group(1);
      }
      else {
        term = matcher.group();
      }

      term = term.trim();

      if (term.length() == 0) continue;

      output.add(term);

      ++sort;
    }

    return output;
  }

  public static class Entry {
    int word1, word2;
    long association;

    public Entry(int word1, int word2, long association) {
      this.word1 = word1;
      this.word2 = word2;
      this.association = association;
    }

    public int getSource() {
      return word1;
    }

    public int getTarget() {
      return word2;
    }

    public long getValue() {
      return association;
    }

  }

  public static class Node {
    int sort;
    String name;

    public Node(String name, int sort) {
      this.name = name;
      this.sort = sort;
    }

    public int getSort() {
      return sort;
    }

    public String getName() {
      return name;
    }

    public int hashCode() {
      return name.hashCode();
    }

    public boolean equals(Object obj) {
      return name.equals(obj);
    }
  }
}
