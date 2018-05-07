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

import org.junit.Test

/**
 * Connect manager test(quarter).
 *
 * @author verstsiu on 2018/5/2.
 * @version 1.0
 */
open class ConnectManagerTestSix: ConnectManagerTestFive() {

  // Old:
  //
  // STATE          : null    null    null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :         TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :         TRUE
  // PAUSED         :         TRUE    TRUE
  //
  // STATE          : CONNECTING  CONNECTING  CONNECTING  CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT: TRUE        TRUE
  // WAIT_RETRY     :
  // ENABLED        :                         TRUE        TRUE
  // PAUSED         :             TRUE                    TRUE
  //
  // STATE          : CONNECT_COMPLETE  CONNECT_COMPLETE
  // SUCCESS        : TRUE              FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE              TRUE
  // PAUSED         :
  //
  // STATE          : DISCONNECTING DISCONNECTING DISCONNECTING DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   : TRUE          TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE          TRUE
  // PAUSED         :               TRUE                        TRUE
  //
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : TRUE                TRUE                TRUE                TRUE                TRUE                TRUE
  // RETRY_COUNT    :                                         0
  // WAIT_CONNECT   :                                                             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                                         TRUE
  // ENABLED        :                                         TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                     TRUE                TRUE                TRUE                                    TRUE
  //
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : FALSE               FALSE               FALSE               FALSE               FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                         TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :                                         TRUE                TRUE                TRUE
  // PAUSED         :                     TRUE                TRUE                                    TRUE
  //
  // STATE          : RETRY_CONNECTING  RETRY_CONNECTING  RETRY_CONNECTING  RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    : 0                 0                 0                 0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT: TRUE
  // WAIT_RETRY     :                   TRUE
  // ENABLED        :                   TRUE              TRUE              TRUE
  // PAUSED         :                                                       TRUE
  //
  // STATE          : RETRY_CONNECT_COMPLETE  RETRY_CONNECT_COMPLETE
  // SUCCESS        : TRUE                    FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE                    TRUE
  // PAUSED         :

  // Current:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> PsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE                TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                                                                     TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> PsConnect        -> Connect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0                   [0]
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE                TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                                                        TRUE
  // PAUSED         :                                                                                     TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> PsConnect        -> NtcSuccess, NtcError
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE                TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                                                                     TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> PsConnect        -> NtcSuccess, NtcError
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                                         TRUE
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE                TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                                                                     TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> PsConnect        -> Disconnect, NtdSuccess, NtdError, RtConnect, PsConnect, RfConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE                TRUE                TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                                                                     TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> PsConnect        -> NtsClosed
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                                         TRUE
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE                TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                                                                     TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> PsConnect        -> NteClosed
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                                         FAlSE
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE                TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                                                                     TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> PsConnect        -> RsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE                TRUE                TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                                                                     TRUE

  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectConnect() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectConnect(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtcSuccess() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtcSuccess(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtcError() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtcError(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectDisconnect() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectDisconnect(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtdSuccess() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtdSuccess(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtdError() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtdError(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtsClosed() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtsClosed(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNteClosed() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNteClosed(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectRtConnect() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectRtConnect(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectPsConnect() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectPsConnect(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectRsConnect() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectRsConnect(createManagerPair())
  @Test fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectRfConnect() = testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectRfConnect(createManagerPair())

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSixConnectNtcErrorRetryRtConnectDisconnectPsConnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

}