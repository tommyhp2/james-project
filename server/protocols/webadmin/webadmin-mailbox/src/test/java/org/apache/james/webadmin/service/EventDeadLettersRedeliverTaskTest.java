/****************************************************************
 * Licensed to the Apache Software Foundation (ASF) under one   *
 * or more contributor license agreements.  See the NOTICE file *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The ASF licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 ****************************************************************/

package org.apache.james.webadmin.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.james.server.task.json.JsonTaskSerializer;

import net.javacrumbs.jsonunit.assertj.JsonAssertions;
import org.junit.jupiter.api.Test;

class EventDeadLettersRedeliverTaskTest {
    private static final String SERIALIZED = "{\"type\":\"eventDeadLettersRedeliverTask\"}";
    private static final EventDeadLettersRedeliverService SERVICE = mock(EventDeadLettersRedeliverService.class);
    private static final EventRetriever EVENT_RETRIEVER = mock(EventRetriever.class);
    private static final EventDeadLettersRedeliverTask TASK = new EventDeadLettersRedeliverTask(SERVICE, EVENT_RETRIEVER);
    private static final JsonTaskSerializer TESTEE = new JsonTaskSerializer(EventDeadLettersRedeliverTask.MODULE.apply(SERVICE, EVENT_RETRIEVER));

    @Test
    void taskShouldBeSerializable() throws JsonProcessingException {
        JsonAssertions.assertThatJson(TESTEE.serialize(TASK))
            .isEqualTo(SERIALIZED);
    }

    @Test
    void taskShouldBeDeserializable() throws IOException {
        assertThat(TESTEE.deserialize(SERIALIZED))
            .isEqualToComparingFieldByFieldRecursively(TASK);
    }
}