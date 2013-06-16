/*
 *  Copyright (c) 2012-2013 Malhar, Inc.
 *  All Rights Reserved.
 */
package com.datatorrent.contrib.ads_dimension;

import com.datatorrent.contrib.ads_dimension.ApplicationRandomData;
import com.datatorrent.api.LocalMode;
import org.apache.hadoop.conf.Configuration;
import org.junit.Test;

public class ApplicationRandomDataTest
{
  @Test
  public void testApplication() throws Exception
  {
    ApplicationRandomData app = new ApplicationRandomData();
    LocalMode lma = LocalMode.newInstance();
    app.populateDAG(lma.getDAG(), new Configuration(false));
    LocalMode.Controller lc = lma.getController();
    lc.setHeartbeatMonitoringEnabled(false);
    lc.run();
  }

}
