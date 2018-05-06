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
package com.ijoic.connect.required

import org.junit.Test

/**
 * Connect manager test(single).
 *
 * @author verstsiu on 2018/5/4.
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
  // FA -> (false, any)
  // TA -> (true, any)
  // Retry -> (true,1) or replaced

  // State Behaviours:
  // Resume: resume connect if connect required when resume from pause

  // State Maps(without retry, always connect required):
  // Connect [enabled] -> connect success/error [enabled]
  //                      server/error closed   [enabled]
  //
  // Disconnect [disabled] -> disconnect success/error [disabled]
  //                          server/error closed      [disabled]
  //
  // Pause connect <-> Resume connect
  // Retry connect
  // Refresh connect

  // State Maps(with retry, always connect required):
  // Connect [enabled] -> connect success       [enabled]
  //                      connect error         [enabled] >> retry
  //                      server/error closed   [enabled] >> retry
  //
  // Disconnect [disabled] -> disconnect success/error [disabled]
  //                          server/error closed      [disabled]
  //
  // Pause connect <-> Resume connect
  // Retry connect
  // Refresh connect

  // State Maps(with retry, changed required):
  // Connect [enabled] -> connect success       [enabled]
  //                      connect error         [enabled + required] >> retry
  //                      server/error closed   [enabled + required] >> retry
  //
  // Disconnect [disabled] -> disconnect success/error [disabled]
  //                          server/error closed      [disabled]
  //
  // Pause connect <-> Resume connect
  // Retry connect
  // Refresh connect

  // Tool methods

  /**
   * Returns connect manager pair.
   */
  protected fun createManagerPair(): Pair<ConnectManager, MockHandler> {
    val handler = MockHandler()
    val manager = ConnectManager(handler)

    handler.connectRequired = true
    handler.maxRetry = 0

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
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
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
  //                        -> ConnectTA
  // STATE          : null     CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :
  //
  //                        -> ConnectFA
  // STATE          : null     null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :
  //
  //                        -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, RsConnect, RfConnect
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

  @Test fun testSingleConnectTA() = testSingleConnectTA(ConnectManager())
  @Test fun testSingleConnectFA() = testSingleConnectFA(createManagerPair())
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

  protected fun testSingleConnectTA(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSingleConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testManagerCreate(manager)

    handler.connectRequired = false

    manager.connect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSingleNtcSuccess(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyConnectSuccess()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSingleNtcError(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyConnectError()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSingleDisconnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.disconnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSingleNtdSuccess(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyDisconnectSuccess()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSingleNtdError(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyDisconnectError()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSingleNtsClosed(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyServerClosed()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSingleNteClosed(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.notifyErrorClosed()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSingleRtConnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.retryConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSinglePsConnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.pauseConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSingleRsConnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.resumeConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSingleRfConnect(manager: ConnectManager) {
    testManagerCreate(manager)

    manager.refreshConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

}