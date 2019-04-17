package tnt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.text.StrSubstitutor;

public class DateRangeSlot implements Slot {

  private String name;

  private Calendar start, end;

  private int days = 0;
  
  private SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

  public DateRangeSlot(String name, Calendar start, Calendar end, int days) {
    this.name = name;
    this.start = start;
    this.end = end;
    this.days = days;
    
    df.setTimeZone(TimeZone.getTimeZone("GMT"));
  }

  @Override
  public List<Shrapnel> explode(Shrapnel query) {
    List<Shrapnel> shrapnel = new ArrayList<Shrapnel>();

    if(!query.getQuery().contains("${start" + name + "}") || !query.getQuery().contains("${end" + name + "}")) {
      shrapnel.add(query);
      return shrapnel;
    }
    
    Map<String, String> valuesMap = new HashMap<String, String>();
        
    if (days == 0) {
      //for this one the things have time stamps so include them
      valuesMap.put("start"+name, df.format(start.getTime())+"00");
      valuesMap.put("end"+name, df.format(end.getTime())+"00");
      StrSubstitutor sub = new StrSubstitutor(valuesMap);
      List<String> fillers = new ArrayList<String>(query.getFillers());
      fillers.add(valuesMap.get("start"+name));
      fillers.add(valuesMap.get("end"+name));
      Shrapnel s = new Shrapnel(sub.replace(query.getQuery()), fillers);
      shrapnel.add(s);
      return shrapnel;
    }
    
    Calendar range = Calendar.getInstance();
    range.setTime(start.getTime());
    
    while (range.compareTo(end) < 0) {
      valuesMap.put("start"+name, df.format(range.getTime())+"00");
      
      range.add(Calendar.DATE, days);      
      if (range.compareTo(end) > 0) {
        range.setTime(end.getTime());
      }      
      valuesMap.put("end"+name, df.format(range.getTime())+"00");
      
      //System.out.println(valuesMap);
      
      StrSubstitutor sub = new StrSubstitutor(valuesMap);
      List<String> fillers = new ArrayList<String>(query.getFillers());
      fillers.add(valuesMap.get("start"+name));
      fillers.add(valuesMap.get("end"+name));
      Shrapnel s = new Shrapnel(sub.replace(query.getQuery()), fillers);
      shrapnel.add(s);
      
    }
    
    //System.out.println(shrapnel);
    
    return shrapnel;
  }
  
  public static void main(String args[]) {
    Calendar today = Calendar.getInstance();
    Calendar weekAgo = Calendar.getInstance();
    weekAgo.roll(Calendar.WEEK_OF_MONTH,-1);
    
    DateRangeSlot slot = new DateRangeSlot("Date", weekAgo, today, 0);
    
    System.out.println(slot.explode(new Shrapnel("{Document date>${startDate} date<${endDate}}",new ArrayList<String>())));
    System.out.println("\n\n");
    
    slot = new DateRangeSlot("Date", weekAgo, today, 2);
    System.out.println(slot.explode(new Shrapnel("{Document date>${startDate} date<${endDate}}",new ArrayList<String>())));
  }
}

