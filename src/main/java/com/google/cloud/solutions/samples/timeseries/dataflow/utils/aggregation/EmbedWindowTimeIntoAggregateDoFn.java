/*
 * Copyright (C) 2016 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.google.cloud.solutions.samples.timeseries.dataflow.utils.aggregation;

import java.io.Serializable;

import com.google.cloud.dataflow.sdk.transforms.DoFn;
import com.google.cloud.dataflow.sdk.values.KV;
import com.google.cloud.solutions.samples.timeseries.dataflow.application.proto.TimeSeriesProtos.TSAggValueProto;


/**
 * This DoFn will embed the boundary timestamp from the window into the data object for later usage
 */
@SuppressWarnings("serial")
public class EmbedWindowTimeIntoAggregateDoFn extends
    DoFn<KV<String, TSAggValueProto>, KV<String, TSAggValueProto>> implements
    com.google.cloud.dataflow.sdk.transforms.DoFn.RequiresWindowAccess , Serializable{

  @Override
  public void processElement(
      DoFn<KV<String, TSAggValueProto>, KV<String, TSAggValueProto>>.ProcessContext c)
      throws Exception {

    c.output(KV.of(c.element().getKey(), TSAggValueProto.newBuilder(c.element().getValue())
        .setCloseTime(c.window().maxTimestamp().getMillis()).build()));

  }

}
