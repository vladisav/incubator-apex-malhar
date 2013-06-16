/*
 *  Copyright (c) 2012-2013 Malhar, Inc.
 *  All Rights Reserved.
 */
package com.datatorrent.lib.io;

import com.datatorrent.api.BaseOperator;
import com.datatorrent.api.DefaultInputPort;
import com.datatorrent.api.annotation.InputPortFieldAnnotation;

import java.util.ArrayList;

/**
 * Reusable operator to collect any tuple.
 * Mainly used for testing.
 *
 * @author Locknath Shil <locknath@malhar-inc.com>
 */
public class TestTupleCollector<T> extends BaseOperator
{
  public ArrayList<T> collectedTuples = new ArrayList<T>();
  @InputPortFieldAnnotation(name = "input")
  public final transient DefaultInputPort<T> input = new DefaultInputPort<T>(this)
  {
    @Override
    public void process(T tuple)
    {
      collectedTuples.add(tuple);
      count++;
    }
  };

  public long count = 0;

  public String firstTuple()
  {
    if (collectedTuples.isEmpty()){
      return null;
    }
    else {
      return collectedTuples.get(0).toString();
    }
  }

}
