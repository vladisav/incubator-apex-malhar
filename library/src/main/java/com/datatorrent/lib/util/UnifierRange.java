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


  /**
 *
 * Combiner for an output port that emits object with ArrayList<V>(2) interface and has the processing done
 * with round robin partition. The first element in the ArrayList is high, the next is low
 *
 *
 */
public class UnifierRange implements Unifier<HighLow>
{
  public HighLow mergedTuple = null;
  public final transient DefaultOutputPort<HighLow> mergedport = new DefaultOutputPort<HighLow>();

  /**
   * combines the tuple into a single final tuple which is emitted in endWindow
   * @param tuple incoming tuple from a partition
   */
  @Override
  public void process(HighLow tuple)
  {
    if (mergedTuple == null) {
      mergedTuple = new HighLow(tuple.getHigh(), tuple.getLow());
    }
    else {
      if (mergedTuple.getHigh().doubleValue() < tuple.getHigh().doubleValue()) {
        mergedTuple.setHigh(tuple.getHigh());
      }
      if (mergedTuple.getLow().doubleValue() > tuple.getLow().doubleValue()) {
        mergedTuple.setLow(tuple.getLow());
      }
    }
  }

  @Override
  public void beginWindow(long windowId)
  {
  }

  @Override
  public void endWindow()
  {
    if (mergedTuple != null) {
      mergedport.emit(mergedTuple);
      mergedTuple = null;
    }
  }

  @Override
  public void setup(OperatorContext context)
  {
  }

  @Override
  public void teardown()
  {
  }
}
