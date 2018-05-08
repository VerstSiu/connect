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
  //                        -> ConnectTA  -> NtcSuccessTA
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
  //                        -> ConnectTA  -> NtcSuccessTA     -> ConnectTA, NtcSuccess, NtcError, NtdSuccess, NtdError, RtConnectTA, RsConnect, RfConnectFFTA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    CONNECT_COMPLETE
  // SUCCESS        :                        TRUE                TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> ConnectFA, RtConnectFA, RfConnectFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> Disconnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> NtsClosed(true,0), NtsClosedFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    RETRY_CONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :                                            0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                                            TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> NteClosed(true,0), NteClosedFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> RfConnectFTTA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :

  @Test fun testTripleConnectTANtcSuccessTAConnectTA() = testTripleConnectTANtcSuccessTAConnectTA(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTAConnectFA() = testTripleConnectTANtcSuccessTAConnectFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessTANtcSuccess() = testTripleConnectTANtcSuccessTANtcSuccess(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTANtcError() = testTripleConnectTANtcSuccessTANtcError(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTADisconnect() = testTripleConnectTANtcSuccessTADisconnect(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTANtdSuccess() = testTripleConnectTANtcSuccessTANtdSuccess(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTANtdError() = testTripleConnectTANtcSuccessTANtdError(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTANtsClosedT0() = testTripleConnectTANtcSuccessTANtsClosedT0(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessTANtsClosedT1() = testTripleConnectTANtcSuccessTANtsClosedT1(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessTANtsClosedFA() = testTripleConnectTANtcSuccessTANtsClosedFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessTANteClosedT0() = testTripleConnectTANtcSuccessTANteClosedT0(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessTANteClosedT1() = testTripleConnectTANtcSuccessTANteClosedT1(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessTANteClosedFA() = testTripleConnectTANtcSuccessTANteClosedFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessTARtConnectTA() = testTripleConnectTANtcSuccessTARtConnectTA(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTARtConnectFA() = testTripleConnectTANtcSuccessTARtConnectFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessTAPsConnect() = testTripleConnectTANtcSuccessTAPsConnect(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTARsConnect() = testTripleConnectTANtcSuccessTARsConnect(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTARfConnectFFTA() = testTripleConnectTANtcSuccessTARfConnectFFTA(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTARfConnectFTTA() = testTripleConnectTANtcSuccessTARfConnectFTTA(ConnectManager())
  @Test fun testTripleConnectTANtcSuccessTARfConnectFA() = testTripleConnectTANtcSuccessTARfConnectFA(createManagerPair())

  protected fun testTripleConnectTANtcSuccessTAConnectTA(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTAConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTANtcSuccess(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTANtcError(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTADisconnect(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessTANtdSuccess(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTANtdError(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTANtsClosedT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTANtsClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTANtsClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTANteClosedT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTANteClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTANteClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTARtConnectTA(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTARtConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTAPsConnect(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessTARsConnect(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTARfConnectFFTA(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

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

  protected fun testTripleConnectTANtcSuccessTARfConnectFTTA(manager: ConnectManager) {
    testCoupleConnectTANtcSuccessTA(manager)

    manager.refreshConnect(forceConnect = true)
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessTARfConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessTA(manager)

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
  //                        -> ConnectTA  -> NtcSuccessFA
  // STATE          : null     CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcSuccessFA  -> Connect, NtcSuccess, NtcError, RtConnect, RsConnect, RfConnect
  // STATE          : null     CONNECTING    DISCONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE             TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessFA  -> Disconnect
  // STATE          : null     CONNECTING    DISCONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessFA  -> NtdSuccess(true,0), NtdSuccessFA, NtsClosed(true,0), NtsClosedFA
  // STATE          : null     CONNECTING    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE             TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessFA  -> NtdSuccess(true,1), NtdError(true,1), NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null     CONNECTING    DISCONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                                         0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                                         TRUE
  // ENABLED        :          TRUE          TRUE             TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessFA  -> NtdError(true,0), NtErrorFA, NteClosed(true,0), NteClosedFA
  // STATE          : null     CONNECTING    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                         FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE             TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessFA  -> PsConnect
  // STATE          : null     CONNECTING    DISCONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE             TRUE
  // PAUSED         :                                         TRUE

  @Test fun testTripleConnectTANtcSuccessFAConnect() = testTripleConnectTANtcSuccessFAConnect(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtcSuccess() = testTripleConnectTANtcSuccessFANtcSuccess(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtcError() = testTripleConnectTANtcSuccessFANtcError(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFADisconnect() = testTripleConnectTANtcSuccessFADisconnect(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtdSuccessT0() = testTripleConnectTANtcSuccessFANtdSuccessT0(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtdSuccessT1() = testTripleConnectTANtcSuccessFANtdSuccessT1(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtdSuccessFA() = testTripleConnectTANtcSuccessFANtdSuccessFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtdErrorT0() = testTripleConnectTANtcSuccessFANtdErrorT0(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtdErrorT1() = testTripleConnectTANtcSuccessFANtdErrorT1(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtdErrorFA() = testTripleConnectTANtcSuccessFANtdErrorFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtsClosedT0() = testTripleConnectTANtcSuccessFANtsClosedT0(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtsClosedT1() = testTripleConnectTANtcSuccessFANtsClosedT1(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANtsClosedFA() = testTripleConnectTANtcSuccessFANtsClosedFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANteClosedT0() = testTripleConnectTANtcSuccessFANteClosedT0(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANteClosedT1() = testTripleConnectTANtcSuccessFANteClosedT1(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFANteClosedFA() = testTripleConnectTANtcSuccessFANteClosedFA(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFARtConnect() = testTripleConnectTANtcSuccessFARtConnect(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFAPsConnect() = testTripleConnectTANtcSuccessFAPsConnect(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFARsConnect() = testTripleConnectTANtcSuccessFARsConnect(createManagerPair())
  @Test fun testTripleConnectTANtcSuccessFARfConnect() = testTripleConnectTANtcSuccessFARfConnect(createManagerPair())

  protected fun testTripleConnectTANtcSuccessFAConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcSuccessFA(pair)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFANtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcSuccessFA(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFANtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcSuccessFA(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFADisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcSuccessFA(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFANtdSuccessT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

    handler.connectRequired = true
    handler.maxRetry = 0

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFANtdSuccessT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

    handler.connectRequired = true
    handler.maxRetry = 1

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

  protected fun testTripleConnectTANtcSuccessFANtdSuccessFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

    handler.connectRequired = false

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFANtdErrorT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

    handler.connectRequired = true
    handler.maxRetry = 0

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFANtdErrorT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

    handler.connectRequired = true
    handler.maxRetry = 1

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

  protected fun testTripleConnectTANtcSuccessFANtdErrorFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

    handler.connectRequired = false

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFANtsClosedT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

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

  protected fun testTripleConnectTANtcSuccessFANtsClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

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

  protected fun testTripleConnectTANtcSuccessFANtsClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

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

  protected fun testTripleConnectTANtcSuccessFANteClosedT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

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

  protected fun testTripleConnectTANtcSuccessFANteClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

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

  protected fun testTripleConnectTANtcSuccessFANteClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcSuccessFA(pair)

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

  protected fun testTripleConnectTANtcSuccessFARtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcSuccessFA(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFAPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcSuccessFA(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFARsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcSuccessFA(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcSuccessFARfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcSuccessFA(pair)

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
    val handler = pair.second
    testCoupleConnectTANtcErrorT0(pair)

    handler.connectRequired = true

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
  // STATE          : null     CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                            TRUE
  // RETRY_COUNT    :                        0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
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

    manager.retryConnect()
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

  protected fun testTripleConnectTANtcErrorT1RfConnectTA(pair: Pair<ConnectManager, MockHandler>, p1: ((MockHandler) -> Unit)? = null) {
    val manager = pair.first
    testCoupleConnectTANtcErrorT1(pair, p1)

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
  //                        -> ConnectTA  -> NtcErrorFA          -> ConnectTA, RtConnectTA, RfConnectTA
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                   TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcErrorFA          -> ConnectFA, NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnectFA, RsConnect, RfConnectFA
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                   TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcErrorFA          -> Disconnect
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcErrorFA          -> PsConnect
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                   TRUE
  // PAUSED         :                                               TRUE

  @Test fun testTripleConnectTANtcErrorFAConnectTA() = testTripleConnectTANtcErrorFAConnectTA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFAConnectFA() = testTripleConnectTANtcErrorFAConnectFA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFANtcSuccess() = testTripleConnectTANtcErrorFANtcSuccess(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFANtcError() = testTripleConnectTANtcErrorFANtcError(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFADisconnect() = testTripleConnectTANtcErrorFADisconnect(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFANtdSuccess() = testTripleConnectTANtcErrorFANtdSuccess(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFANtdError() = testTripleConnectTANtcErrorFANtdError(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFANtsClosed() = testTripleConnectTANtcErrorFANtsClosed(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFANteClosed() = testTripleConnectTANtcErrorFANteClosed(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFARtConnectTA() = testTripleConnectTANtcErrorFARtConnectTA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFARtConnectFA() = testTripleConnectTANtcErrorFARtConnectFA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFAPsConnect() = testTripleConnectTANtcErrorFAPsConnect(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFARsConnect() = testTripleConnectTANtcErrorFARsConnect(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFARfConnectTA() = testTripleConnectTANtcErrorFARfConnectTA(createManagerPair())
  @Test fun testTripleConnectTANtcErrorFARfConnectFA() = testTripleConnectTANtcErrorFARfConnectFA(createManagerPair())

  protected fun testTripleConnectTANtcErrorFAConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorFA(pair)

    handler.connectRequired = true

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorFAConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorFA(pair)

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

  protected fun testTripleConnectTANtcErrorFANtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorFA(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorFANtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorFA(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorFADisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorFA(pair)

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

  protected fun testTripleConnectTANtcErrorFANtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorFA(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorFANtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorFA(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorFANtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorFA(pair)

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

  protected fun testTripleConnectTANtcErrorFANteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorFA(pair)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorFARtConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorFA(pair)

    handler.connectRequired = true

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorFARtConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorFA(pair)

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

  protected fun testTripleConnectTANtcErrorFAPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorFA(pair)

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

  protected fun testTripleConnectTANtcErrorFARsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANtcErrorFA(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorFARfConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorFA(pair)

    handler.connectRequired = true

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANtcErrorFARfConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANtcErrorFA(pair)

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
  //                        -> ConnectTA  -> Disconnect
  // STATE          : null     CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> Disconnect -> Connect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE                       TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> Disconnect -> NtcSuccess
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> Disconnect -> NtcError, NtsClosed
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                      TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> Disconnect -> Disconnect, NtdSuccess, NtdError, RtConnect, RsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> Disconnect -> NteClosed
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> Disconnect -> PsConnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                      TRUE

  @Test fun testTripleConnectTADisconnectConnect() = testTripleConnectTADisconnectConnect(ConnectManager())
  @Test fun testTripleConnectTADisconnectNtcSuccess() = testTripleConnectTADisconnectNtcSuccess(ConnectManager())
  @Test fun testTripleConnectTADisconnectNtcError() = testTripleConnectTADisconnectNtcError(ConnectManager())
  @Test fun testTripleConnectTADisconnectDisconnect() = testTripleConnectTADisconnectDisconnect(ConnectManager())
  @Test fun testTripleConnectTADisconnectNtdSuccess() = testTripleConnectTADisconnectNtdSuccess(ConnectManager())
  @Test fun testTripleConnectTADisconnectNtdError() = testTripleConnectTADisconnectNtdError(ConnectManager())
  @Test fun testTripleConnectTADisconnectNtsClosed() = testTripleConnectTADisconnectNtsClosed(ConnectManager())
  @Test fun testTripleConnectTADisconnectNteClosed() = testTripleConnectTADisconnectNteClosed(ConnectManager())
  @Test fun testTripleConnectTADisconnectRtConnect() = testTripleConnectTADisconnectRtConnect(ConnectManager())
  @Test fun testTripleConnectTADisconnectPsConnect() = testTripleConnectTADisconnectPsConnect(ConnectManager())
  @Test fun testTripleConnectTADisconnectRsConnect() = testTripleConnectTADisconnectRsConnect(ConnectManager())
  @Test fun testTripleConnectTADisconnectRfConnect() = testTripleConnectTADisconnectRfConnect(ConnectManager())

  protected fun testTripleConnectTADisconnectConnect(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTADisconnect(manager)

    handler.connectRequired = false

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectNtcSuccess(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectNtcError(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectDisconnect(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectNtdSuccess(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectNtdError(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectNtsClosed(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectNteClosed(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectRtConnect(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectPsConnect(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectRsConnect(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTADisconnectRfConnect(manager: ConnectManager) {
    testCoupleConnectTADisconnect(manager)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

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
  //                        -> ConnectTA  -> NteClosed(true,0)   -> ConnectTA, RtConnectTA, RfConnectTA
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                        FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                   TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NteClosed(true,0)   -> ConnectFA, NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnectFA, RsConnect, RfConnectFA
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                   TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NteClosed(true,0)   -> Disconnect
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NteClosed(true,0)   -> PsConnect
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                  TRUE
  // PAUSED         :                                              TRUE

  @Test fun testTripleConnectTANteClosedT0ConnectTA() = testTripleConnectTANteClosedT0ConnectTA(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0ConnectFA() = testTripleConnectTANteClosedT0ConnectFA(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0NtcSuccess() = testTripleConnectTANteClosedT0NtcSuccess(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0NtcError() = testTripleConnectTANteClosedT0NtcError(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0Disconnect() = testTripleConnectTANteClosedT0Disconnect(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0NtdSuccess() = testTripleConnectTANteClosedT0NtdSuccess(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0NtdError() = testTripleConnectTANteClosedT0NtdError(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0NtsClosed() = testTripleConnectTANteClosedT0NtsClosed(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0NteClosed() = testTripleConnectTANteClosedT0NteClosed(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0RtConnectTA() = testTripleConnectTANteClosedT0RtConnectTA(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0RtConnectFA() = testTripleConnectTANteClosedT0RtConnectFA(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0PsConnect() = testTripleConnectTANteClosedT0PsConnect(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0RsConnect() = testTripleConnectTANteClosedT0RsConnect(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0RfConnectTA() = testTripleConnectTANteClosedT0RfConnectTA(createManagerPair())
  @Test fun testTripleConnectTANteClosedT0RfConnectFA() = testTripleConnectTANteClosedT0RfConnectFA(createManagerPair())

  protected fun testTripleConnectTANteClosedT0ConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANteClosedT0(pair)

    handler.connectRequired = true

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0ConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANteClosedT0(pair)

    handler.connectRequired = false

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0NtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANteClosedT0(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0NtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANteClosedT0(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0Disconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANteClosedT0(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0NtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANteClosedT0(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0NtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANteClosedT0(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0NtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANteClosedT0(pair)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0NteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANteClosedT0(pair)

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

  protected fun testTripleConnectTANteClosedT0RtConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANteClosedT0(pair)

    handler.connectRequired = true

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0RtConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANteClosedT0(pair)

    handler.connectRequired = false

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0PsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANteClosedT0(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0RsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectTANteClosedT0(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0RfConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANteClosedT0(pair)

    handler.connectRequired = true

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTANteClosedT0RfConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTANteClosedT0(pair)

    handler.connectRequired = false

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

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
  //                        -> ConnectTA  -> PsConnect  -> Connect, NtdSuccess, NtdError, RtConnect, PsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :                        TRUE          TRUE
  //
  //                        -> ConnectTA  -> PsConnect  -> NtcSuccess
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :                        TRUE          TRUE
  //
  //                        -> ConnectTA  -> PsConnect  -> NtcError, NtsClosed
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                      TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :                        TRUE          TRUE
  //
  //                        -> ConnectTA  -> PsConnect  -> Disconnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :                        TRUE          TRUE
  //
  //                        -> ConnectTA  -> PsConnect  -> NteClosed
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :                        TRUE          TRUE
  //
  //                        -> ConnectTA  -> PsConnect  -> RsConnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :                        TRUE

  @Test fun testTripleConnectTAPsConnectConnect() = testTripleConnectTAPsConnectConnect(ConnectManager())
  @Test fun testTripleConnectTAPsConnectNtcSuccess() = testTripleConnectTAPsConnectNtcSuccess(ConnectManager())
  @Test fun testTripleConnectTAPsConnectNtcError() = testTripleConnectTAPsConnectNtcError(ConnectManager())
  @Test fun testTripleConnectTAPsConnectDisconnect() = testTripleConnectTAPsConnectDisconnect(ConnectManager())
  @Test fun testTripleConnectTAPsConnectNtdSuccess() = testTripleConnectTAPsConnectNtdSuccess(ConnectManager())
  @Test fun testTripleConnectTAPsConnectNtdError() = testTripleConnectTAPsConnectNtdError(ConnectManager())
  @Test fun testTripleConnectTAPsConnectNtsClosed() = testTripleConnectTAPsConnectNtsClosed(ConnectManager())
  @Test fun testTripleConnectTAPsConnectNteClosed() = testTripleConnectTAPsConnectNteClosed(ConnectManager())
  @Test fun testTripleConnectTAPsConnectRtConnect() = testTripleConnectTAPsConnectRtConnect(ConnectManager())
  @Test fun testTripleConnectTAPsConnectPsConnect() = testTripleConnectTAPsConnectPsConnect(ConnectManager())
  @Test fun testTripleConnectTAPsConnectRsConnect() = testTripleConnectTAPsConnectRsConnect(ConnectManager())
  @Test fun testTripleConnectTAPsConnectRfConnect() = testTripleConnectTAPsConnectRfConnect(ConnectManager())

  protected fun testTripleConnectTAPsConnectConnect(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectTAPsConnect(manager)

    handler.connectRequired = false

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectNtcSuccess(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectNtcError(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectDisconnect(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectNtdSuccess(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectNtdError(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectNtsClosed(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectNteClosed(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectRtConnect(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectPsConnect(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectRsConnect(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectTAPsConnectRfConnect(manager: ConnectManager) {
    testCoupleConnectTAPsConnect(manager)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

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
  //                        -> ConnectFA -> PsConnect -> Connect, NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnectA, RfConnect
  // STATE          : null     null         null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE         TRUE         TRUE
  // PAUSED         :                       TRUE         TRUE
  //
  //                        -> ConnectFA -> PsConnect -> Disconnect
  // STATE          : null     null         null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE         TRUE
  // PAUSED         :                       TRUE         TRUE
  //
  //                        -> ConnectFA -> PsConnect -> RsConnectTA
  // STATE          : null     null         null         CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE         TRUE         TRUE
  // PAUSED         :                       TRUE
  //
  //                        -> ConnectFA -> PsConnect -> RsConnectFA
  // STATE          : null     null         null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE         TRUE         TRUE
  // PAUSED         :                       TRUE

  @Test fun testTripleConnectFAPsConnectConnect() = testTripleConnectFAPsConnectConnect(createManagerPair())
  @Test fun testTripleConnectFAPsConnectNtcSuccess() = testTripleConnectFAPsConnectNtcSuccess(createManagerPair())
  @Test fun testTripleConnectFAPsConnectNtcError() = testTripleConnectFAPsConnectNtcError(createManagerPair())
  @Test fun testTripleConnectFAPsConnectDisconnect() = testTripleConnectFAPsConnectDisconnect(createManagerPair())
  @Test fun testTripleConnectFAPsConnectNtdSuccess() = testTripleConnectFAPsConnectNtdSuccess(createManagerPair())
  @Test fun testTripleConnectFAPsConnectNtdError() = testTripleConnectFAPsConnectNtdError(createManagerPair())
  @Test fun testTripleConnectFAPsConnectNtsClosed() = testTripleConnectFAPsConnectNtsClosed(createManagerPair())
  @Test fun testTripleConnectFAPsConnectNteClosed() = testTripleConnectFAPsConnectNteClosed(createManagerPair())
  @Test fun testTripleConnectFAPsConnectRtConnect() = testTripleConnectFAPsConnectRtConnect(createManagerPair())
  @Test fun testTripleConnectFAPsConnectPsConnect() = testTripleConnectFAPsConnectPsConnect(createManagerPair())
  @Test fun testTripleConnectFAPsConnectRsConnectTA() = testTripleConnectFAPsConnectRsConnectTA(createManagerPair())
  @Test fun testTripleConnectFAPsConnectRsConnectFA() = testTripleConnectFAPsConnectRsConnectFA(createManagerPair())
  @Test fun testTripleConnectFAPsConnectRfConnect() = testTripleConnectFAPsConnectRfConnect(createManagerPair())

  protected fun testTripleConnectFAPsConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.connect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.notifyConnectSuccess()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.notifyConnectError()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.disconnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.notifyDisconnectSuccess()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.notifyDisconnectError()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.notifyServerClosed()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.notifyErrorClosed()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.retryConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.pauseConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectRsConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectFAPsConnect(pair)

    handler.connectRequired = true

    manager.resumeConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectRsConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectFAPsConnect(pair)

    handler.connectRequired = false

    manager.resumeConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectFAPsConnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectFAPsConnect(pair)

    manager.refreshConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

}