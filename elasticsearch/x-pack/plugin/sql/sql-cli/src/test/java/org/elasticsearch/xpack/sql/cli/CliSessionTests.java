/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License;
 * you may not use this file except in compliance with the Elastic License.
 */
package org.elasticsearch.xpack.sql.cli;

import org.elasticsearch.cluster.ClusterName;
import org.elasticsearch.common.UUIDs;
import org.elasticsearch.test.ESTestCase;
import org.elasticsearch.xpack.sql.cli.command.CliSession;
import org.elasticsearch.xpack.sql.client.ClientException;
import org.elasticsearch.xpack.sql.client.ClientVersion;
import org.elasticsearch.xpack.sql.client.HttpClient;
import org.elasticsearch.xpack.sql.proto.MainResponse;
import org.elasticsearch.xpack.sql.proto.SqlVersion;

import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

public class CliSessionTests extends ESTestCase {

    public void testProperConnection() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        when(httpClient.serverInfo()).thenReturn(new MainResponse(randomAlphaOfLength(5), ClientVersion.CURRENT.toString(),
                ClusterName.DEFAULT.value(), UUIDs.randomBase64UUID()));
        CliSession cliSession = new CliSession(httpClient);
        cliSession.checkConnection();
        verify(httpClient, times(1)).serverInfo();
        verifyNoMoreInteractions(httpClient);
    }

    public void testConnection() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        when(httpClient.serverInfo()).thenThrow(new SQLException("Cannot connect"));
        CliSession cliSession = new CliSession(httpClient);
        Throwable throwable = expectThrows(ClientException.class, cliSession::checkConnection);
        assertEquals("java.sql.SQLException: Cannot connect", throwable.getMessage());
        verify(httpClient, times(1)).serverInfo();
        verifyNoMoreInteractions(httpClient);
    }

    public void testWrongServerVersion() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        SqlVersion version = new SqlVersion((int)SqlVersion.V_7_7_0.major, SqlVersion.V_7_7_0.minor - 1, 0);
        when(httpClient.serverInfo()).thenReturn(new MainResponse(randomAlphaOfLength(5), version.toString(),
                ClusterName.DEFAULT.value(), UUIDs.randomBase64UUID()));
        CliSession cliSession = new CliSession(httpClient);
        Throwable throwable = expectThrows(ClientException.class, cliSession::checkConnection);
        assertEquals("This version of the CLI is only compatible with Elasticsearch version " +
            ClientVersion.CURRENT.majorMinorToString() + " or newer; attempting to connect to a server version " + version.toString(),
            throwable.getMessage());
        verify(httpClient, times(1)).serverInfo();
        verifyNoMoreInteractions(httpClient);
    }

    public void testHigherServerVersion() throws Exception {
        HttpClient httpClient = mock(HttpClient.class);
        byte minor;
        byte major;
        if (randomBoolean()) {
            minor = ClientVersion.CURRENT.minor;
            major = (byte) (ClientVersion.CURRENT.major + 1);
        } else {
            minor = (byte) (ClientVersion.CURRENT.minor + 1);
            major = ClientVersion.CURRENT.major;

        }
        when(httpClient.serverInfo()).thenReturn(new MainResponse(randomAlphaOfLength(5),
            SqlVersion.fromString(major + "." + minor + ".23").toString(),
            ClusterName.DEFAULT.value(), UUIDs.randomBase64UUID()));
        CliSession cliSession = new CliSession(httpClient);
        cliSession.checkConnection();
        verify(httpClient, times(1)).serverInfo();
        verifyNoMoreInteractions(httpClient);
    }
}