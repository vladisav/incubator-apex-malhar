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
package com.datatorrent.lib.samplecode.math;

import java.util.Random;

import com.datatorrent.api.DefaultOutputPort;
import com.datatorrent.api.InputOperator;
import com.datatorrent.api.Context.OperatorContext;
import com.datatorrent.common.util.Pair;

/**
 * Input port operator for generating random values Pair for sample application.
 * 
 */
public class RandomPairIntegers implements InputOperator
{

	public final transient DefaultOutputPort<Pair<Integer, Integer>> outport = new DefaultOutputPort<Pair<Integer, Integer>>();
	private Random random = new Random(11111);
	private boolean equal = false;

	@Override
	public void beginWindow(long windowId)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void endWindow()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void setup(OperatorContext context)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void teardown()
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void emitTuples()
	{
		if (equal)
		{
			int val = random.nextInt();
			outport.emit(new Pair<Integer, Integer>(new Integer(val),
					new Integer(val)));
		} else
		{
			outport.emit(new Pair<Integer, Integer>(new Integer(random.nextInt()),
					new Integer(random.nextInt())));
		}
		equal = !equal;
	}

}
