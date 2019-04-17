package tnt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.text.StrSubstitutor;

public abstract class TimeRangeSlot implements Slot {

  protected String name;

  protected Calendar start, end;

  protected int window = 0;
  
  protected int field;
  
  protected SimpleDateFormat df = null;//new SimpleDateFormat("yyyyMMddHHmm");

  @Override
  public List<Shrapnel> explode(Shrapnel query) {
    List<Shrapnel> shrapnel = new ArrayList<Shrapnel>();

    if(!query.getQuery().contains("${start" + name + "}") || !query.getQuery().contains("${end" + name + "}")) {
      shrapnel.add(query);
      return shrapnel;
    }
    
    Map<String, String> valuesMap = new HashMap<String, String>();
        
    if (window == 0) {
      //for this one the things have time stamps so include them
      valuesMap.put("start"+name, df.format(start.getTime()));
      valuesMap.put("end"+name, df.format(end.getTime()));
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
    
    System.out.println("start: " + start.getTime());
    System.out.println("end: " + end.getTime());
    while (range.compareTo(end) < 0) {
      valuesMap.put("start"+name, df.format(range.getTime()));
      
      range.add(field, window);
      if (range.compareTo(end) > 0) {
        range.setTime(end.getTime());
      }      
      valuesMap.put("end"+name, df.format(range.getTime()));
      
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

  public static class Minutes extends TimeRangeSlot {
    public Minutes(String name, Calendar start, Calendar end, int minutes) {
      this.name = name;
      this.start = start;
      this.end = end;
      this.window = minutes;
      
      this.field = Calendar.MINUTE;
      
      df = new SimpleDateFormat("yyyyMMddHHmm");
      
      df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
  }
  
  public static class Hours extends TimeRangeSlot {
    public Hours(String name, Calendar start, Calendar end, int hours) {
      this.name = name;
      this.start = start;
      this.end = end;
      this.window = hours;
      
      this.field = Calendar.HOUR_OF_DAY;
      
      df = new SimpleDateFormat("yyyyMMddHH");
      
      df.setTimeZone(TimeZone.getTimeZone("GMT"));
    }
  }
}

