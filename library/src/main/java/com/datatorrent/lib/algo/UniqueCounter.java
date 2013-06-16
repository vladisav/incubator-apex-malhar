/*
 *  Copyright (c) 2012 Malhar, Inc.
 *  All Rights Reserved.
 */
package com.datatorrent.lib.algo;

import com.datatorrent.api.DefaultInputPort;
import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.api.annotation.InputPortFieldAnnotation;
import com.datatorrent.api.annotation.OutputPortFieldAnnotation;
import com.datatorrent.lib.util.BaseUniqueKeyCounter;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.mutable.MutableInt;

/**
 * Counts the number of times a key exists in a window; Count is emitted at end of window in a single HashMap<p>
 * This is an end of window operator<br>
 * <br>
 * <b>Ports</b>:<br>
 * <b>data</b>: expects K<br>
 * <b>count</b>: emits HashMap&lt;K,Integer&gt;<br>
 * <b>Properties</b>: None<br>
 * <br>
 * <b>Specific compile time checks</b>: None<br>
 * <b>Specific run time checks</b>:<br>
 * <br>
 * <b>Benchmarks</b>: Blast as many tuples as possible in inline mode<br>
 * <table border="1" cellspacing=1 cellpadding=1 summary="Benchmark table for UniqueCounter&lt;K&gt; operator template">
 * <tr><th>In-Bound</th><th>Out-bound</th><th>Comments</th></tr>
 * <tr><td><b>&gt; processes 110 Million K,V pairs/s</b></td><td>Emits one tuple per window</td><td>In-bound throughput
 * and number of unique k are the main determinant of performance. Tuples are assumed to be immutable. If you use mutable tuples and have lots of keys,
 * the benchmarks may be lower</td></tr>
 * </table><br>
 * <p>
 * <b>Function Table (K=String)</b>:
 * <table border="1" cellspacing=1 cellpadding=1 summary="Function table for UniqueCounter&lt;K&gt; operator template">
 * <tr><th rowspan=2>Tuple Type (api)</th><th>In-bound (process)</th><th>Out-bound (emit)</th></tr>
 * <tr><th><i>data</i>(K)</th><th><i>count</i>(HashMap&lt;K,Integer&gt;)</th></tr>
 * <tr><td>Begin Window (beginWindow())</td><td>N/A</td><td>N/A</td></tr>
 * <tr><td>Data (process())</td><td>a</td><td></td></tr>
 * <tr><td>Data (process())</td><td>b</td><td></td></tr>
 * <tr><td>Data (process())</td><td>c</td><td></td></tr>
 * <tr><td>Data (process())</td><td>4</td><td></td></tr>
 * <tr><td>Data (process())</td><td>5ah</td><td></td></tr>
 * <tr><td>Data (process())</td><td>h</td><td></td></tr>
 * <tr><td>Data (process())</td><td>a</td><td></td></tr>
 * <tr><td>Data (process())</td><td>a</td><td></td></tr>
 * <tr><td>Data (process())</td><td>a</td><td>d</td></tr>
 * <tr><td>Data (process())</td><td>a</td><td></td></tr>
 * <tr><td>Data (process())</td><td>5ah</td><td></td></tr>
 * <tr><td>Data (process())</td><td>a</td><td></td></tr>
 * <tr><td>Data (process())</td><td>c</td><td></td></tr>
 * <tr><td>Data (process())</td><td>c</td><td></td></tr>
 * <tr><td>Data (process())</td><td>b</td><td></td></tr>
 * <tr><td>End Window (endWindow())</td><td>N/A</td><td>{a=6,b=2,c=3,5ah=2,h=1,4=1}</td></tr>
 * </table>
 * <br>
 *
 * @author Chetan Narsude <chetan@malhar-inc.com>
 */
public class UniqueCounter<K> extends BaseUniqueKeyCounter<K>
{
  @InputPortFieldAnnotation(name = "data")
  public final transient DefaultInputPort<K> data = new DefaultInputPort<K>(this)
  {
    /**
     * Reference counts tuples
     */
    @Override
    public void process(K tuple)
    {
      processTuple(tuple);
    }

  };
  @OutputPortFieldAnnotation(name = "count")
  public final transient DefaultOutputPort<HashMap<K, Integer>> count = new DefaultOutputPort<HashMap<K, Integer>>(this);

  /**
   * Emits one HashMap as tuple
   */
  @Override
  public void endWindow()
  {
    HashMap<K, Integer> tuple = null;
    for (Map.Entry<K, MutableInt> e: map.entrySet()) {
      if (tuple == null) {
        tuple = new HashMap<K, Integer>();
      }
      tuple.put(e.getKey(), e.getValue().toInteger());
    }
    if (tuple != null) {
      count.emit(tuple);
    }
    map.clear();
  }
}
