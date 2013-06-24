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
package com.datatorrent.lib.logs;

import com.datatorrent.engine.TestSink;
import com.datatorrent.lib.logs.ApacheLogParseOperator;
import com.datatorrent.lib.testbench.ArrayListTestSink;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * Functional tests for {@link com.datatorrent.lib.logs.ApacheLogParseOperator}<p>
 *
 */
public class ApacheLogParseOperatorTest
{
  private static Logger log = LoggerFactory.getLogger(ApacheLogParseOperatorTest.class);

  /**
   * Test oper logic emits correct results
   */
  @Test
  public void testNodeProcessing()
  {

    ApacheLogParseOperator oper = new ApacheLogParseOperator();
    TestSink ipSink = new TestSink();
    TestSink urlSink = new TestSink();
    TestSink scSink = new TestSink();
    TestSink bytesSink = new TestSink();
    TestSink refSink = new TestSink();
    TestSink agentSink = new TestSink();

    oper.outputIPAddress.setSink(ipSink);
    oper.outputUrl.setSink(urlSink);
    oper.outputStatusCode.setSink(scSink);
    oper.outputBytes.setSink(bytesSink);
    oper.outputReferer.setSink(refSink);
    oper.outputAgent.setSink(agentSink);

    String token = "127.0.0.1 - - [04/Apr/2013:17:17:21 -0700] \"GET /favicon.ico HTTP/1.1\" 404 498 \"-\" \"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.43 Safari/537.31\"";
    oper.beginWindow(0);
    oper.data.process(token);
    oper.endWindow(); //

    Assert.assertEquals("number emitted tuples", 1, ipSink.collectedTuples.size());
    Assert.assertEquals("number emitted tuples", 1, urlSink.collectedTuples.size());
    Assert.assertEquals("number emitted tuples", 1, scSink.collectedTuples.size());
    Assert.assertEquals("number emitted tuples", 1, bytesSink.collectedTuples.size());
    Assert.assertEquals("number emitted tuples", 1, refSink.collectedTuples.size());
    Assert.assertEquals("number emitted tuples", 1, agentSink.collectedTuples.size());

    log.debug(String.format("\nLine is \"%s\"", token.toString()));
    log.debug(String.format("IP is %s\n", ipSink.collectedTuples.toString()));
    log.debug(String.format("Url is %s\n", urlSink.collectedTuples.toString()));
    log.debug(String.format("Status code is %s\n", scSink.collectedTuples.toString()));
    log.debug(String.format("Bytes are %s\n", bytesSink.collectedTuples.toString()));
    log.debug(String.format("Referer is %s\n", refSink.collectedTuples.toString()));
    log.debug(String.format("Agent is %s\n", agentSink.collectedTuples.toString()));
  }
}
