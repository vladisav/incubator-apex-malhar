/*
 * Copyright (c) 2013 Malhar Inc. ALL Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. See accompanying LICENSE file.
 */
package com.datatorrent.lib.util;

import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.api.Context.OperatorContext;
import com.datatorrent.api.Operator.Unifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * Combiner for an output port that emits object with Map<K,ArrayList<V>(2)> interface and has the processing done
 * with round robin partition. The first element in the ArrayList is high, the next is low
 *
 *
 */
public class UnifierHashMapRange<K> implements Unifier<HashMap<K, HighLow>>
{
  public HashMap<K, HighLow> mergedTuple = new HashMap<K, HighLow>();
  public final transient DefaultOutputPort<HashMap<K, HighLow>> mergedport = new DefaultOutputPort<HashMap<K, HighLow>>();

  /**
   * combines the tuple into a single final tuple which is emitted in endWindow
   * @param tuple incoming tuple from a partition
   */
  @Override
  public void process(HashMap<K, HighLow> tuple)
  {
    for (Map.Entry<K, HighLow> e: tuple.entrySet()) {
      HighLow val = mergedTuple.get(e.getKey());
      if (val == null) {
        val = new HighLow(e.getValue().getHigh(), e.getValue().getLow());
        mergedTuple.put(e.getKey(), val);
      }
      else {
        if (val.getHigh().doubleValue() < e.getValue().getHigh().doubleValue()) {
          val.setHigh(e.getValue().getHigh());
        }
        if (val.getLow().doubleValue() > e.getValue().getLow().doubleValue()) {
          val.setLow(e.getValue().getLow());
        }
      }
    }
  }

  /**
   * a no op
   * @param windowId
   */
  @Override
  public void beginWindow(long windowId)
  {
  }


  /**
   * emits mergedTuple on mergedport if it is not empty
   */
  @Override
  public void endWindow()
  {
    if (!mergedTuple.isEmpty())  {
      mergedport.emit(mergedTuple);
      mergedTuple = new HashMap<K, HighLow>();
    }
  }

  /**
   * a no-op
   * @param context
   */
  @Override
  public void setup(OperatorContext context)
  {
  }

  /**
   * a noop
   */
  @Override
  public void teardown()
  {
  }
}
