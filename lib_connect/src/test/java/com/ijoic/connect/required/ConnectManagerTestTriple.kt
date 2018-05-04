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
 * Connect manager test(triple).
 *
 * @author verstsiu on 2018/5/4.
 * @version 1.0
 */
open class ConnectManagerTestTriple: ConnectManagerTestCouple() {

  // Old:
  //
  // STATE          : null    null    null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :                 TRUE
  //
  // STATE          : CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :

  // Current:
  //                        -> ConnectTA  -> ConnectFA
  // STATE          : null     CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> ConnectFA  -> ConnectTA, RtConnectTA, RfConnectTA
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> ConnectFA  -> ConnectFA, NtdSuccess, NtdError, RtConnectFA, RsConnect, RfConnectFA
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE          TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> ConnectFA  -> NtcSuccess, NtcError
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> ConnectFA  -> Disconnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE          TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> ConnectFA  -> NtsClosed(true,0), NtsClosedFA
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                      TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> ConnectFA  -> NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null     CONNECTING    CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                                      0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE
  // WAIT_RETRY     :                                      TRUE
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> ConnectFA  -> NteClosed(true,0), NteClosedFA
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> ConnectFA  -> PsConnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE          TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :                                      TRUE

  @Test fun testTripleConnectTAConnectFAConnectTA() = testTripleConnectTAConnectFAConnectTA(createManagerPair())
  @Test fun testTripleConnectTAConnectFAConnectFA() = testTripleConnectTAConnectFAConnectFA(createManagerPair())
  @Test fun testTripleConnectTAConnectFANtcSuccess() = testTripleConnectTAConnectFANtcSuccess(createManagerPair())
  @Test fun testTripleConnectTAConnectFANtcError() = testTripleConnectTAConnectFANtcError(createManagerPair())
  @Test fun testTripleConnectTAConnectFADisconnect() = testTripleConnectTAConnectFADisconnect(createManagerPair())
  @Test fun testTripleConnectTAConnectFANtdSuccess() = testTripleConnectTAConnectFANtdSuccess(createManagerPair())
  @Test fun testTripleConnectTAConnectFANtdError() = testTripleConnectTAConnectFANtdError(createManagerPair())
  @Test fun testTripleConnectTAConnectFANtsClosedT0() = testTripleConnectTAConnectFANtsClosedT0(createManagerPair())
  @Test fun testTripleConnectTAConnectFANtsClosedT1() = testTripleConnectTAConnectFANtsClosedT1(createManagerPair())
  @Test fun testTripleConnectTAConnectFANtsClosedFA() = testTripleConnectTAConnectFANtsClosedFA(createManagerPair())
  @Test fun testTripleConnectTAConnectFANteClosedT0() = testTripleConnectTAConnectFANteClosedT0(createManagerPair())
  @Test fun testTripleConnectTAConnectFANteClosedT1() = testTripleConnectTAConnectFANteClosedT1(createManagerPair())
  @Test fun testTripleConnectTAConnectFANteClosedFA() = testTripleConnectTAConnectFANteClosedFA(createManagerPair())
  @Test fun testTripleConnectTAConnectFARtConnectTA() = testTripleConnectTAConnectFARtConnectTA(createManagerPair())
  @Test fun testTripleConnectTAConnectFARtConnectFA() = testTripleConnectTAConnectFARtConnectFA(createManagerPair())
  @Test fun testTripleConnectTAConnectFAPsConnect() = testTripleConnectTAConnectFAPsConnect(createManagerPair())
  @Test fun testTripleConnectTAConnectFARsConnect() = testTripleConnectTAConnectFARsConnect(createManagerPair())
  @Test fun testTripleConnectTAConnectFARfConnectTA() = testTripleConnectTAConnectFARfConnectTA(createManagerPair())
  @Test fun testTripleConnectTAConnectFARfConnectFA() = testTripleConnectTAConnectFARfConnectFA(createManagerPair())

  protected fun testTripleConnectTAConnectFAConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTAConnectFA(pair)

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFAConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTAConnectFA(pair)

    handler.connectRequired = false

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFANtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTAConnectFA(pair)

    manager.notifyConnectSuccess()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFANtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTAConnectFA(pair)

    manager.notifyConnectError()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFADisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTAConnectFA(pair)

    manager.disconnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFANtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTAConnectFA(pair)

    manager.notifyDisconnectSuccess()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFANtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTAConnectFA(pair)

    manager.notifyDisconnectError()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFANtsClosedT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTAConnectFA(pair)

    handler.connectRequired = true
    handler.maxRetry = 0

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFANtsClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTAConnectFA(pair)

    handler.connectRequired = true
    handler.maxRetry = 1

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFANtsClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTAConnectFA(pair)

    handler.connectRequired = false

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFANteClosedT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTAConnectFA(pair)

    handler.connectRequired = true
    handler.maxRetry = 0

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFANteClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTAConnectFA(pair)

    handler.connectRequired = true
    handler.maxRetry = 1

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFANteClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTAConnectFA(pair)

    handler.connectRequired = false

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFARtConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTAConnectFA(pair)

    manager.retryConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFARtConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTAConnectFA(pair)

    handler.connectRequired = false

    manager.retryConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFAPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTAConnectFA(pair)

    manager.pauseConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFARsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTAConnectFA(pair)

    manager.resumeConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFARfConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTAConnectFA(pair)

    manager.refreshConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAConnectFARfConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTAConnectFA(pair)

    handler.connectRequired = false

    manager.refreshConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcSuccess
  // STATE          : null     CONNECTING    CONNECT_COMPLETE
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcSuccess       -> ConnectTA, NtcSuccess, NtcError, NtdSuccess, NtdError, RtConnectTA, RsConnect, RfConnectFFTA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    CONNECT_COMPLETE
  // SUCCESS        :                        TRUE                TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccess       -> ConnectFA, RtConnectFA, RfConnectFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccess       -> Disconnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccess       -> NtsClosed(true,0), NtsClosedFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccess       -> NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    RETRY_CONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :                                            0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                                            TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccess       -> NteClosed(true,0), NteClosedFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccess       -> PsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE
  //
  //                        -> ConnectTA  -> NtcSuccess       -> RfConnectFTTA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :

  @Test fun testTripleConnectTANtcSuccessConnectTA() = testTripleConnectTANtcSuccessConnectTA(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessConnectFA() = testTripleConnectTANtcSuccessConnectFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessNtcSuccess() = testTripleConnectTANtcSuccessNtcSuccess(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessNtcError() = testTripleConnectTANtcSuccessNtcError(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessDisconnect() = testTripleConnectTANtcSuccessDisconnect(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessNtdSuccess() = testTripleConnectTANtcSuccessNtdSuccess(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessNtdError() = testTripleConnectTANtcSuccessNtdError(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessNtsClosedT0() = testTripleConnectTANtcSuccessNtsClosedT0(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessNtsClosedT1() = testTripleConnectTANtcSuccessNtsClosedT1(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessNtsClosedFA() = testTripleConnectTANtcSuccessNtsClosedFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessNteClosedT0() = testTripleConnectTANtcSuccessNteClosedT0(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessNteClosedT1() = testTripleConnectTANtcSuccessNteClosedT1(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessNteClosedFA() = testTripleConnectTANtcSuccessNteClosedFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessRtConnectTA() = testTripleConnectTANtcSuccessRtConnectTA(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessRtConnectFA() = testTripleConnectTANtcSuccessRtConnectFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessPsConnect() = testTripleConnectTANtcSuccessPsConnect(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessRsConnect() = testTripleConnectTANtcSuccessRsConnect(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessRfConnectFFTA() = testTripleConnectTANtcSuccessRfConnectFFTA(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessRfConnectFTTA() = testTripleConnectTANtcSuccessRfConnectFTTA(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessRfConnectFA() = testTripleConnectTANtcSuccessRfConnectFA(createManagerPair())

  protected fun testTripleConnectTANtcSuccessConnectTA(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccess(manager)

    handler.connectRequired = false

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessNtcSuccess(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessNtcError(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessDisconnect(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessNtdSuccess(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessNtdError(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessNtsClosedT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccess(manager)

    handler.connectRequired = true
    handler.maxRetry = 0

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessNtsClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccess(manager)

    handler.connectRequired = true
    handler.maxRetry = 1

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessNtsClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccess(manager)

    handler.connectRequired = false

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessNteClosedT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccess(manager)

    handler.connectRequired = true
    handler.maxRetry = 0

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessNteClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccess(manager)

    handler.connectRequired = true
    handler.maxRetry = 1

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessNteClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccess(manager)

    handler.connectRequired = false

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessRtConnectTA(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessRtConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccess(manager)

    handler.connectRequired = false

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessPsConnect(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessRsConnect(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessRfConnectFFTA(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessRfConnectFTTA(manager: ConnectManager) {
    testCoupleConnectTANtcSuccess(manager)

    manager.refreshConnect(forceConnect = true)
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessRfConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccess(manager)

    handler.connectRequired = false

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcError(true,0)
  // STATE          : null     CONNECTING    CONNECT_COMPLETE
  // SUCCESS        :                        FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,0) -> ConnectTA, RtConnectTA, RfConnectTA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                        FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> ConnectFA, NtsClosed, RtConnectFA, RfConnectFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> NtcSuccess, NtcError, NtdSuccess, NtdError, RsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    CONNECT_COMPLETE
  // SUCCESS        :                        FALSE               FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> Disconnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> NteClosed
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> PsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE

  @Test fun testTripleConnectTANtcErrorT0ConnectTA() = testTripleConnectTANtcErrorT0ConnectTA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0ConnectFA() = testTripleConnectTANtcErrorT0ConnectFA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0NtcSuccess() = testTripleConnectTANtcErrorT0NtcSuccess(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0NtcError() = testTripleConnectTANtcErrorT0NtcError(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0Disconnect() = testTripleConnectTANtcErrorT0Disconnect(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0NtdSuccess() = testTripleConnectTANtcErrorT0NtdSuccess(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0NtdError() = testTripleConnectTANtcErrorT0NtdError(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0NtsClosed() = testTripleConnectTANtcErrorT0NtsClosed(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0NteClosed() = testTripleConnectTANtcErrorT0NteClosed(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0RtConnectTA() = testTripleConnectTANtcErrorT0RtConnectTA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0RtConnectFA() = testTripleConnectTANtcErrorT0RtConnectFA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0PsConnect() = testTripleConnectTANtcErrorT0PsConnect(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0RsConnect() = testTripleConnectTANtcErrorT0RsConnect(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0RfConnectTA() = testTripleConnectTANtcErrorT0RfConnectTA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT0RfConnectFA() = testTripleConnectTANtcErrorT0RfConnectFA(createManagerPair())

  protected fun testTripleConnectTANtcErrorT0ConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0ConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorT0(pair)

    handler.connectRequired = false

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0NtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0NtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0Disconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0NtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0NtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0NtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0NteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0RtConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0RtConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorT0(pair)

    handler.connectRequired = false

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0PsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0RsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0RfConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT0(pair)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT0RfConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorT0(pair)

    handler.connectRequired = false

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcError(true,1)
  // STATE          : null     CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,1) -> ConnectTA, RfConnectTA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   [0]
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> ConnectFA, RtConnectFA, RfConnectFA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                            TRUE
  // RETRY_COUNT    :                        0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE                TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> Disconnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                            TRUE
  // RETRY_COUNT    :                        0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> PsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE                TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE

  @Test fun testTripleConnectTANtcErrorT1ConnectTA() = testTripleConnectTANtcErrorT1ConnectTA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1ConnectFA() = testTripleConnectTANtcErrorT1ConnectFA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1NtcSuccess() = testTripleConnectTANtcErrorT1NtcSuccess(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1NtcError() = testTripleConnectTANtcErrorT1NtcError(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1Disconnect() = testTripleConnectTANtcErrorT1Disconnect(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1NtdSuccess() = testTripleConnectTANtcErrorT1NtdSuccess(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1NtdError() = testTripleConnectTANtcErrorT1NtdError(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1NtsClosed() = testTripleConnectTANtcErrorT1NtsClosed(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1NteClosed() = testTripleConnectTANtcErrorT1NteClosed(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1RtConnectTA() = testTripleConnectTANtcErrorT1RtConnectTA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1RtConnectFA() = testTripleConnectTANtcErrorT1RtConnectFA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1PsConnect() = testTripleConnectTANtcErrorT1PsConnect(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1RsConnect() = testTripleConnectTANtcErrorT1RsConnect(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1RfConnectTA() = testTripleConnectTANtcErrorT1RfConnectTA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorT1RfConnectFA() = testTripleConnectTANtcErrorT1RfConnectFA(createManagerPair())

  protected fun testTripleConnectTANtcErrorT1ConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1ConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorT1(pair)

    handler.connectRequired = false

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1NtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1NtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1Disconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1NtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1NtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1NtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1NteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1RtConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1RtConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorT1(pair)

    handler.connectRequired = false

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1PsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1RsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1RfConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorT1RfConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorT1(pair)

    handler.connectRequired = false

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcErrorFA
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

  // Current:
  //                        -> ConnectTA  -> Disconnect
  // STATE          : null     CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

  // Current:
  //                        -> ConnectTA  -> NteClosed(true,0)
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

  // Current:
  //                        -> ConnectTA  -> PsConnect
  // STATE          : null     CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :                        TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

  // Current:
  //                        -> ConnectFA -> PsConnect
  // STATE          : null     null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE         TRUE
  // PAUSED         :                       TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

  // Current:
  //                        -> PsConnect -> ConnectTA
  // STATE          : null     null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :                       TRUE
  // PAUSED         :          TRUE         TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

}