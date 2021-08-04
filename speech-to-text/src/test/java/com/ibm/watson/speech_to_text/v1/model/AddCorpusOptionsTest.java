/*
 * (C) Copyright IBM Corp. 2020.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.ibm.watson.speech_to_text.v1.model;

import static org.testng.Assert.*;

import com.ibm.cloud.sdk.core.service.model.FileWithMetadata;
import com.ibm.watson.speech_to_text.v1.utils.TestUtilities;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.testng.annotations.Test;

/** Unit test class for the AddCorpusOptions model. */
public class AddCorpusOptionsTest {
  final HashMap<String, InputStream> mockStreamMap = TestUtilities.createMockStreamMap();
  final List<FileWithMetadata> mockListFileWithMetadata =
      TestUtilities.creatMockListFileWithMetadata();

  @Test
  public void testAddCorpusOptions() throws Throwable {
    AddCorpusOptions addCorpusOptionsModel =
        new AddCorpusOptions.Builder()
            .customizationId("testString")
            .corpusName("testString")
            .corpusFile(TestUtilities.createMockStream("This is a mock file."))
            .allowOverwrite(true)
            .build();
    assertEquals(addCorpusOptionsModel.customizationId(), "testString");
    assertEquals(addCorpusOptionsModel.corpusName(), "testString");
    assertEquals(
        IOUtils.toString(addCorpusOptionsModel.corpusFile()),
        IOUtils.toString(TestUtilities.createMockStream("This is a mock file.")));
    assertEquals(addCorpusOptionsModel.allowOverwrite(), Boolean.valueOf(true));
  }

  @Test(expectedExceptions = IllegalArgumentException.class)
  public void testAddCorpusOptionsError() throws Throwable {
    new AddCorpusOptions.Builder().build();
  }
}
