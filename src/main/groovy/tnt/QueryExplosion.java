package tnt;

import gate.mimir.search.RemoteQueryRunner;
import gate.mimir.tool.WebUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class QueryExplosion {



  public static List<Shrapnel> explode(Shrapnel query, Slot... slots) {
    List<Shrapnel> shrapnel = new ArrayList<Shrapnel>();

    Slot head = slots[0];

    if(slots.length == 1) { return head.explode(query); }

    List<Shrapnel> triggers =
        explode(query, Arrays.copyOfRange(slots, 1, slots.length));

    for(Shrapnel nq : triggers) {
      shrapnel.addAll(head.explode(nq));
    }

    return shrapnel;
  }
}
