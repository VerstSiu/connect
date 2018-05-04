/*
 *
 *  Copyright(c) 2018 VerstSiu
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */
package com.ijoic.connect

import junit.framework.Assert
import org.junit.Test

/**
 * Connect manager test(single).
 *
 * @author verstsiu on 2018/4/27.
 * @version 1.0
 */
open class ConnectManagerTestSingle {

  // Abbreviations:
  // NtcSuccess -> notifyConnectSuccess
  // NtcError -> notifyConnectError
  // NtdSuccess -> notifyDisconnectSuccess
  // NtdError -> notifyDisconnectError
  // NtsClosed -> notifyServerClosed
  // NteClosed -> notifyErrorClosed
  // RtConnect -> RetryConnect
  // PsConnect -> PauseConnect
  // RsConnect -> ResumeConnect
  // RfConnectFF -> RefreshConnect(forceConnect = false)
  // RfConnectFT -> RefreshConnect(forceConnect = true)
  // (Boolean, Int) -> (isConnectRequired, maxRetryCount)
  // T{:Int} -> (true, Int)
  // F{:Int} -> (false, Int)
  // TN -> (true, negative)
  // FN -> (false, negative)
  // TA -> (true, any)
  // FA -> (false, any)
  // Retry -> (true,1) or replaced

  // State Behaviours:
  // Connect: connect anyway
  // Disconnect: disconnect anyway
  // Retry connect: connect if required, cancel connect if not required
  // Pause connect: disconnect and mark wait status [when pause from resume]
  // Resume connect: connect if required, cancel connect if not required [when resume from pause]
  // Refresh connect: connect, reset retry status if required, disconnect if not required [resume anytime]

  // Tool methods

  /**
   * Returns connect manager pair.
   */
  protected fun createManagerPair(): Pair<ConnectManager, MockHandler> {
    val handler = MockHandler()
    val manager = ConnectManager(handler)

    return Pair(manager, handler)
  }

  // Test Cases:
  //                  Create
  // STATE          : null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :

  @Test
  fun testManagerCreate() = testManagerCreate(ConnectManager())

  protected fun testManagerCreate(manager: ConnectManager) {
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create
  // STATE          : null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> Connect
  // STATE          : null     CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :
  //
  //                        -> Connect, NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, RsConnect, RfConnect
  // STATE          : null     null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :
  //
  //                        -> PsConnect
  // STATE          : null     null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :          TRUE

  @Test fun testSingleConnect() = testSingleConnect(ConnectManager())
  @Test fun testSingleNtcSuccess() = testSingleNtcSuccess(ConnectManager())
  @Test fun testSingleNtcError() = testSingleNtcError(ConnectManager())
  @Test fun testSingleDisconnect() = testSingleDisconnect(ConnectManager())
  @Test fun testSingleNtdSuccess() = testSingleNtdSuccess(ConnectManager())
  @Test fun testSingleNtdError() = testSingleNtdError(ConnectManager())
  @Test fun testSingleNtsClosed() = testSingleNtsClosed(ConnectManager())
  @Test fun testSingleNteClosed() = testSingleNteClosed(ConnectManager())
  @Test fun testSingleRtConnect() = testSingleRtConnect(ConnectManager())
  @Test fun testSinglePsConnect() = testSinglePsConnect(ConnectManager())
  @Test fun testSingleRsConnect() = testSingleRsConnect(ConnectManager())
  @Test fun testSingleRfConnect() = testSingleRfConnect(ConnectManager())

  protected fun testSingleConnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.connect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testSingleNtcSuccess(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyConnectSuccess()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testSingleNtcError(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyConnectError()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testSingleDisconnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.disconnect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testSingleNtdSuccess(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyDisconnectSuccess()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testSingleNtdError(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyDisconnectError()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testSingleNtsClosed(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyServerClosed()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testSingleNteClosed(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyErrorClosed()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testSingleRtConnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.retryConnect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testSinglePsConnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.pauseConnect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testSingleRsConnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.resumeConnect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testSingleRfConnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.refreshConnect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

}