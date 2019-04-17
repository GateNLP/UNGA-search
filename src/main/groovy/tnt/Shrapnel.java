package tnt;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import gate.mimir.index.IndexException;
import gate.mimir.search.RemoteQueryRunner;
import gate.mimir.search.query.Binding;
import gate.mimir.tool.WebUtils;
import unga.search.DocumentResult;

public class Shrapnel {
  List<String> fillers;
  String query;
  long count = -1;
  Map<String, Long> aggregates = null;
  Map<String, Map<Serializable, Long>> metadataAggregates = null;
  List<Long> docs = null;

  List<DocumentResult> documents;

  public Shrapnel(String query, List<String> fillers) {
    this.query = query;
    this.fillers = fillers;
  }

  public List<String> getFillers() {
    return fillers;
  }

  public String getQuery() {
    return query;
  }

  public long getCount() {
    return count;
  }

  public String toString() {
    String content = "";

    for (String f : fillers) {
      content += f + "\t";
    }

    return content + query;
  }

  public Map<String, Long> getAggregateCounts() {
    return aggregates;
  }

  public Map<String, Map<Serializable, Long>> getMetadataAggregateCounts() {
    return metadataAggregates;
  }

  public List<Long> getDocIDs() {
    return docs;
  }

  public List<DocumentResult> getResults() {
    return documents;
  }

  public void execute(String indexURL, WebUtils webUtils, boolean collectResults)
          throws IOException, InterruptedException, IndexOutOfBoundsException, IndexException {

    System.out.println(query);
    RemoteQueryRunner mimir = new RemoteQueryRunner(indexURL, query, null, webUtils);

    while (mimir.getDocumentsCount() == -1) {
      Thread.sleep(500);
    }

    count = 0;

    long numDocs = mimir.getDocumentsCount();

    docs = new ArrayList<Long>();

    documents = Collections.synchronizedList(new ArrayList<DocumentResult>());

    //for (int rank = 0; rank < count; rank++) {
    IntStream.range(0, (int)numDocs).parallel().forEach((rank) -> {
      try {
        docs.add(mimir.getDocumentID(rank));

        List<Binding> bindings = mimir.getDocumentHits(rank);

        if (bindings != null) {
          count += bindings.size();

          if (collectResults) {


            DocumentResult docResult = new DocumentResult(mimir.getDocumentTitle(rank), mimir.getDocumentURI(rank));

            documents.add(docResult);









            for (Binding binding : bindings) {
              String[][] tokens = mimir.getDocumentText(rank, binding.getTermPosition(), binding.getLength());

              StringBuilder text = new StringBuilder();
              for (int t = 0; t < tokens[0].length; ++t) {
                if (tokens[0][t] != null && tokens[1][t] != null) {
                  text.append(tokens[0][t]).append(tokens[1][t]);
                }
              }

              docResult.getSentences().add(text.toString().trim());
            }
          }

        }
      } catch(Exception e) {
        e.printStackTrace();
      }

    });

    mimir.close();
  }
}
