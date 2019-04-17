package tnt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.text.StrSubstitutor;

public class NominalSlot implements Slot {
  private String name;

  private List<String> values;

  public NominalSlot(String name, String ... values ) {
    this.name = name;
    this.values = Arrays.asList(values);
  }

  @Override
  public List<Shrapnel> explode(Shrapnel query) {
    
    
    List<Shrapnel> shrapnel = new ArrayList<Shrapnel>();

    if(!query.getQuery().contains("${" + name + "}")) {
      shrapnel.add(query);
      return shrapnel;
    }

    Map<String, String> valuesMap = new HashMap<String, String>();

    for(String value : values) {
      valuesMap.put(name,value);
      StrSubstitutor sub = new StrSubstitutor(valuesMap);
      List<String> fillers = new ArrayList<String>(query.getFillers());
      fillers.add(value);
      Shrapnel s = new Shrapnel(sub.replace(query.getQuery()), fillers);
      shrapnel.add(s);

    }
    
    //System.out.println(shrapnel);
    
    return shrapnel;
  }
  
  public List<String> getValues() {
	  return Collections.unmodifiableList(values);
  }
  
  public static void main(String args[]) {
    NominalSlot gender = new NominalSlot("gender", "male","female");
    Shrapnel query = new Shrapnel("{Person gender=${gender}}",new ArrayList<String>());
    System.out.println(gender.explode(query));
  }
}
