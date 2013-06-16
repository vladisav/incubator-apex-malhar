/*
 *  Copyright (c) 2012 Malhar, Inc.
 *  All Rights Reserved.
 */
package com.datatorrent.demos.mobile;

import org.junit.Test;

import com.datatorrent.api.LocalMode;
import com.datatorrent.demos.mobile.ApplicationAlert;


public class ApplicationAlertTest
{
  public ApplicationAlertTest()
  {
  }

  /**
   * Test of getApplication method, of class Application.
   */
  @Test
  public void testGetApplication() throws Exception
  {
    LocalMode.runApp(new ApplicationAlert(), 60000);
  }
}
