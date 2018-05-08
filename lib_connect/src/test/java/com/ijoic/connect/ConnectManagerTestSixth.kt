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
 * Connect manager test(sixth).
 *
 * @author verstsiu on 2018/5/7.
 * @version 1.0
 */
open class ConnectManagerTestSixth: ConnectManagerTestFifth() {

  // Old:
  //
  // STATE          : null    null    null    null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE    TRUE
  // PAUSED         :         TRUE            TRUE
  //
  // STATE          : CONNECTING  CONNECTING  CONNECTING  CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE        TRUE
  // PAUSED         :             TRUE                    TRUE
  //
  // STATE          : CONNECT_COMPLETE
  // SUCCESS        : TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :
  //
  // STATE          : CONNECT_COMPLETE
  // SUCCESS        : FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :
  //
  // STATE          : DISCONNECTING DISCONNECTING DISCONNECTING DISCONNECTING DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   : TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE          TRUE          TRUE
  // PAUSED         :                             TRUE                        TRUE
  //
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : TRUE                TRUE                TRUE                TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE                TRUE
  // PAUSED         :                     TRUE                                    TRUE
  //
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : FALSE               FALSE               FALSE               FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE                TRUE
  // PAUSED         :                     TRUE                                    TRUE
  //
  // STATE          : RETRY_CONNECTING  RETRY_CONNECTING  RETRY_CONNECTING  RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    : 0                 0                 0                 0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     : TRUE
  // ENABLED        : TRUE              TRUE              TRUE
  // PAUSED         :                                     TRUE
  //
  // STATE          : RETRY_CONNECT_COMPLETE
  // SUCCESS        : TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :
  //
  // STATE          : RETRY_CONNECT_COMPLETE
  // SUCCESS        : FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :

  // Current:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> PsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                                                                    TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> PsConnect        -> Connect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                                                        TRUE
  // PAUSED         :                                                                                    TRUE                TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> PsConnect        -> NtcSuccess
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                                                                    TRUE                TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> PsConnect        -> NtcError, NtsClosed
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                                        TRUE
  // RETRY_COUNT    :                        0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                                                                    TRUE                TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> PsConnect        -> Disconnect, NtdSuccess, NtdError, RtConnect, PsConnect, RfConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                                                                    TRUE                TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> PsConnect        -> NteClosed
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                                        FALSE
  // RETRY_COUNT    :                        0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                                                                    TRUE                TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> PsConnect        -> RsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                                                                    TRUE

  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectConnect() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectConnect(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtcSuccess() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtcSuccess(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtcError() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtcError(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectDisconnect() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectDisconnect(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtdSuccess() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtdSuccess(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtdError() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtdError(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtsClosed() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtsClosed(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNteClosed() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNteClosed(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectRtConnect() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectRtConnect(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectRsConnect() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectRsConnect(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectPsConnect() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectPsConnect(createManagerPair())
  @Test fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectRfConnect() = testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectRfConnect(createManagerPair())

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

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

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

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

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

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

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

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

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testSixthConnectTANtcErrorT1RtConnectTADisconnectPsConnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

}