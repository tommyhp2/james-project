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

package org.apache.james.jmap.draft;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletResponse;

import org.apache.james.core.Username;
import org.apache.james.jmap.draft.api.SimpleTokenFactory;
import org.apache.james.jmap.draft.utils.DownloadPath;
import org.apache.james.mailbox.BlobManager;
import org.apache.james.mailbox.MailboxSession;
import org.apache.james.mailbox.MailboxSessionUtil;
import org.apache.james.mailbox.exception.MailboxException;
import org.apache.james.metrics.tests.RecordingMetricFactory;
import org.junit.Test;

public class DownloadServletTest {

    @Test
    public void downloadMayFailWhenUnknownErrorOnAttachmentManager() throws Exception {
        MailboxSession mailboxSession = MailboxSessionUtil.create(Username.of("User"));
        BlobManager mockedBlobManager = mock(BlobManager.class);
        when(mockedBlobManager.retrieve(any(), eq(mailboxSession)))
            .thenThrow(new MailboxException());
        SimpleTokenFactory nullSimpleTokenFactory = null;

        DownloadServlet testee = new DownloadServlet(mockedBlobManager, nullSimpleTokenFactory, new RecordingMetricFactory());

        HttpServletResponse resp = mock(HttpServletResponse.class);
        testee.download(mailboxSession, DownloadPath.from("/blobId"), resp);

        verify(resp).setStatus(500);
    }
}
