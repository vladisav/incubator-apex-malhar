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
package com.datatorrent.lib.stream;

import com.datatorrent.api.DefaultInputPort;
import com.datatorrent.api.annotation.InputPortFieldAnnotation;

/**
 * Merges up to ten streams with identical schema and emits tuples on to the output port in order.<p>
 * This is a pass through operator<br>
 * <br>
 * <b>Ports</b>:<br>
 * <b>data1</b>: expects K<br>
 * <b>data2</b>: expects K<br>
 * <b>data3</b>: expects K<br>
 * <b>data4</b>: expects K<br>
 * <b>data5</b>: expects K<br>
 * <b>data6</b>: expects K<br>
 * <b>data7</b>: expects K<br>
 * <b>data8</b>: expects K<br>
 * <b>data9</b>: expects K<br>
 * <b>data10</b>: expects K<br>
 * <b>data11</b>: expects K<br>
 * <b>data12</b>: expects K<br>
 * <b>data13</b>: expects K<br>
 * <b>data14</b>: expects K<br>
 * <b>data15</b>: expects K<br>
 * <b>data16</b>: expects K<br>
 * <b>out</b>: emits K<br>
 * <br>
 * <b>Properties</b>: None<br>
 * <br>
 * <b>Specific compile time checks</b>: None<br>
 * <b>Specific run time checks</b>: None<br>
 * <p>
 * <b>Benchmarks</b>: Blast as many tuples as possible in inline mode<br>
 * <table border="1" cellspacing=1 cellpadding=1 summary="Benchmark table for StreamMerger10&lt;K&gt; operator template">
 * <tr><th>In-Bound</th><th>Out-bound</th><th>Comments</th></tr>
 * <tr><td><b>&gt; 500 Million tuples/s</td><td>Each in-bound tuple results in emit of 1 out-bound tuples</td><td>In-bound rate is the main determinant of performance</td></tr>
 * </table><br>
 * <p>
 * <b>Function Table (K=String)</b>:
 * <table border="1" cellspacing=1 cellpadding=1 summary="Function table for StreamMerger10&lt;K&gt; operator template">
 * <tr><th rowspan=2>Tuple Type (api)</th><th colspan=10>In-bound (process)</th><th>Out-bound (emit)</th></tr>
 * <tr><th><i>data1</i>(K)</th><th><i>data2</i>(K)</th><th><i>data3</i>(K)</th><th><i>data4</i>(K)</th>
 * <th><i>data5</i>(K)</th><th><i>data6</i>(K)</th><th><i>data7</i>(K)</th><th><i>data8</i>(K)</th>
 * <th><i>data9</i>(K)</th><th><i>data10</i>(K)</th><th><i>data11</i>(K)</th><th><i>data12</i>(K)</th>
 * <th><i>data13</i>(K)</th><th><i>data14</i>(K)</th><th><i>data15</i>(K)</th><th><i>data16</i>(K)</th>
 * <th><i>out</i>(K)</th></tr>
 * <tr><td>Begin Window (beginWindow())</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td>
 * <td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td></tr>
 * <tr><td>Data (process())</td><td>a</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td>a</td></tr>
 * <tr><td>Data (process())</td><td></td><td>b</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td>b</td></tr>
 * <tr><td>Data (process())</td><td></td><td></td><td>c</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td>c</td></tr>
 * <tr><td>Data (process())</td><td></td><td></td><td></td><td>d</td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td>d</td></tr>
 * <tr><td>Data (process())</td><td></td><td></td><td></td><td></td><td>e</td><td></td><td></td><td></td><td></td><td></td>
 * <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td>e</td></tr>
 * <tr><td>Data (process())</td><td></td><td></td><td></td><td></td><td></td><td>d</td><td></td><td></td><td></td><td></td>
 * <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td>d</td></tr>
 * <tr><td>Data (process())</td><td></td><td></td><td></td><td></td><td></td><td></td><td>c</td><td></td><td></td><td></td>
 * <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td>c</td></tr>
 * <tr><td>Data (process())</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>b</td><td></td><td></td>
 * <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td>b</td></tr>
 * <tr><td>Data (process())</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>a</td><td></td>
 * <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td>a</td></tr>
 * <tr><td>Data (process())</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td></td><td>z</td>
 * <td></td><td></td><td></td><td></td><td></td><td></td><td></td>
 * <td>z</td></tr>
 * <tr><td>End Window (endWindow())</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td>
 * <td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td><td>N/A</td>
 * <td>N/A</td></tr>
 * </table>
 * <br>
 * <br>
 */
public class StreamMerger16<K> extends StreamMerger5<K>
{
  @InputPortFieldAnnotation(name = "data6", optional = true)
  public final transient DefaultInputPort<K> data6 = new DefaultInputPort<K>()
  {
    /**
     * Emits to port "out"
     */
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  @InputPortFieldAnnotation(name = "data7", optional = true)
  public final transient DefaultInputPort<K> data7 = new DefaultInputPort<K>()
  {
    /**
     * Emits to port "out"
     */
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  @InputPortFieldAnnotation(name = "data8", optional = true)
  public final transient DefaultInputPort<K> data8 = new DefaultInputPort<K>()
  {
    /**
     * Emits to port "out"
     */
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  @InputPortFieldAnnotation(name = "data9", optional = true)
  public final transient DefaultInputPort<K> data9 = new DefaultInputPort<K>()
  {
    /**
     * Emits to port "out"
     */
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  @InputPortFieldAnnotation(name = "data10", optional = true)
  public final transient DefaultInputPort<K> data10 = new DefaultInputPort<K>()
  {
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  @InputPortFieldAnnotation(name = "data11", optional = true)
  public final transient DefaultInputPort<K> data11 = new DefaultInputPort<K>()
  {
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  @InputPortFieldAnnotation(name = "data12", optional = true)
  public final transient DefaultInputPort<K> data12 = new DefaultInputPort<K>()
  {
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  @InputPortFieldAnnotation(name = "data13", optional = true)
  public final transient DefaultInputPort<K> data13 = new DefaultInputPort<K>()
  {
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  @InputPortFieldAnnotation(name = "data14", optional = true)
  public final transient DefaultInputPort<K> data14 = new DefaultInputPort<K>()
  {
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  @InputPortFieldAnnotation(name = "data15", optional = true)
  public final transient DefaultInputPort<K> data15 = new DefaultInputPort<K>()
  {
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  @InputPortFieldAnnotation(name = "data16", optional = true)
  public final transient DefaultInputPort<K> data16 = new DefaultInputPort<K>()
  {
    @Override
    public void process(K tuple)
    {
      out.emit(tuple);
    }
  };
  /**
   * Enables dynamic construction of port name
   *
   * @param i the port number
   * @return the proper InputPort
   */
  @Override
  public DefaultInputPort<K> getInputPort(int i)
  {
    DefaultInputPort<K> ret;
    switch (i) {
      case 6:
        ret = data6;
        break;
      case 7:
        ret = data7;
        break;
      case 8:
        ret = data8;
        break;
      case 9:
        ret = data9;
        break;
      case 10:
        ret = data10;
        break;
      default:
        ret = super.getInputPort(i);
        break;
    }
    return ret;
  }

  /**
   * Allows usage of StreamMerger in a automated way
   *
   * @param i port index
   * @return returns the proper input port name
   */
  static public String getInputName(int i)
  {
    String ret = "illegal_portnumber";
    if ((i != 0) && (i <= getNumberOfInputPorts())) {
      ret = "data";
      ret += Integer.toString(i);
    }
    return ret;
  }

  /**
   * Number of input ports in this operator
   */
  static public int getNumberOfInputPorts()
  {
    return 10;
  }
}
