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
 * Connect manager test(couple).
 *
 * @author verstsiu on 2018/5/4.
 * @version 1.0
 */
open class ConnectManagerTestCouple: ConnectManagerTestSingle() {

  // Old:
  //
  // STATE          : null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :

  // Current:
  //                        -> ConnectTA
  // STATE          : null     CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> ConnectTA, RtConnectTA, RfConnectTA
  // STATE          : null     CONNECTING    CONNECTING[1]
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> ConnectFA, NtdSuccess, NtdError, RtConnectFA, RsConnect, RfConnectFA
  // STATE          : null     CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessFA
  // STATE          : null     CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,0)
  // STATE          : null     CONNECTING    CONNECT_COMPLETE
  // SUCCESS        :                        FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1), NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null     CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcErrorFA, NtsClosed(true,0), NtsClosedFA
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> Disconnect
  // STATE          : null     CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NteClosed(true,0), NteClosedFA
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> PsConnect
  // STATE          : null     CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :                        TRUE

  @Test fun testCoupleConnectTAConnect() = testCoupleConnectTAConnect(ConnectManager())
  @Test fun testCoupleConnectTANtcSuccessTA() = testCoupleConnectTANtcSuccessTA(ConnectManager())
  @Test fun testCoupleConnectTANtcSuccessFA() = testCoupleConnectTANtcSuccessFA(createManagerPair())
  @Test fun testCoupleConnectTANtcErrorT0() = testCoupleConnectTANtcErrorT0(createManagerPair())
  @Test fun testCoupleConnectTANtcErrorT1() = testCoupleConnectTANtcErrorT1(createManagerPair())
  @Test fun testCoupleConnectTANtcErrorFA() = testCoupleConnectTANtcErrorFA(createManagerPair())
  @Test fun testCoupleConnectTADisconnect() = testCoupleConnectTADisconnect(ConnectManager())
  @Test fun testCoupleConnectTANtdSuccess() = testCoupleConnectTANtdSuccess(ConnectManager())
  @Test fun testCoupleConnectTANtdError() = testCoupleConnectTANtdError(ConnectManager())
  @Test fun testCoupleConnectTANtsClosedT0() = testCoupleConnectTANtsClosedT0(createManagerPair())
  @Test fun testCoupleConnectTANtsClosedT1() = testCoupleConnectTANtsClosedT1(createManagerPair())
  @Test fun testCoupleConnectTANtsClosedFA() = testCoupleConnectTANtsClosedFA(createManagerPair())
  @Test fun testCoupleConnectTANteClosedT0() = testCoupleConnectTANteClosedT0(createManagerPair())
  @Test fun testCoupleConnectTANteClosedT1() = testCoupleConnectTANteClosedT1(createManagerPair())
  @Test fun testCoupleConnectTANteClosedFA() = testCoupleConnectTANteClosedFA(createManagerPair())
  @Test fun testCoupleConnectTARtConnect() = testCoupleConnectTARtConnect(ConnectManager())
  @Test fun testCoupleConnectTAPsConnect() = testCoupleConnectTAPsConnect(ConnectManager())
  @Test fun testCoupleConnectTARsConnect() = testCoupleConnectTARsConnect(ConnectManager())
  @Test fun testCoupleConnectTARfConnect() = testCoupleConnectTARfConnect(ConnectManager())

  protected fun testCoupleConnectTAConnect(manager: ConnectManager) {
    testSingleConnectTA(manager)

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectTANtcSuccessTA(manager: ConnectManager) {
    testSingleConnectTA(manager)

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

  protected fun testCoupleConnectTANtcSuccessFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectTA(manager)

    handler.connectRequired = false

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectTANtcErrorT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectTA(manager)

    handler.connectRequired = true
    handler.maxRetry = 0

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

  protected fun testCoupleConnectTANtcErrorT1(pair: Pair<ConnectManager, MockHandler>, p1: ((MockHandler) -> Unit)? = null) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectTA(manager)

    if (p1 == null) {
      handler.connectRequired = true
      handler.maxRetry = 1
    } else {
      p1.invoke(handler)
    }

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

  protected fun testCoupleConnectTANtcErrorFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectTA(manager)

    handler.connectRequired = false

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

  protected fun testCoupleConnectTADisconnect(manager: ConnectManager) {
    testSingleConnectTA(manager)

    manager.disconnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectTANtdSuccess(manager: ConnectManager) {
    testSingleConnectTA(manager)

    manager.notifyDisconnectSuccess()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectTANtdError(manager: ConnectManager) {
    testSingleConnectTA(manager)

    manager.notifyDisconnectError()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectTANtsClosedT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectTA(manager)

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

  protected fun testCoupleConnectTANtsClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectTA(manager)

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

  protected fun testCoupleConnectTANtsClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectTA(manager)

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

  protected fun testCoupleConnectTANteClosedT0(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectTA(manager)

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

  protected fun testCoupleConnectTANteClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectTA(manager)

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

  protected fun testCoupleConnectTANteClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectTA(manager)

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

  protected fun testCoupleConnectTARtConnect(manager: ConnectManager) {
    testSingleConnectTA(manager)

    manager.retryConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectTAPsConnect(manager: ConnectManager) {
    testSingleConnectTA(manager)

    manager.pauseConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCoupleConnectTARsConnect(manager: ConnectManager) {
    testSingleConnectTA(manager)

    manager.resumeConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectTARfConnect(manager: ConnectManager) {
    testSingleConnectTA(manager)

    manager.refreshConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectFA
  // STATE          : null     null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectFA -> ConnectTA, RtConnectTA, RfConnectTA
  // STATE          : null     null         CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE         TRUE
  // PAUSED         :
  //
  //                        -> ConnectFA -> ConnectFA, NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnectFA, RsConnect, RfConnectFA
  // STATE          : null     null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE         TRUE
  // PAUSED         :
  //
  //                        -> ConnectFA -> Disconnect
  // STATE          : null     null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :
  //
  //                        -> ConnectFA -> PsConnect
  // STATE          : null     null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE         TRUE
  // PAUSED         :                       TRUE

  @Test fun testCoupleConnectFAConnectTA() = testCoupleConnectFAConnectTA(createManagerPair())
  @Test fun testCoupleConnectFAConnectFA() = testCoupleConnectFAConnectFA(createManagerPair())
  @Test fun testCoupleConnectFANtcSuccess() = testCoupleConnectFANtcSuccess(createManagerPair())
  @Test fun testCoupleConnectFANtcError() = testCoupleConnectFANtcError(createManagerPair())
  @Test fun testCoupleConnectFADisconnect() = testCoupleConnectFADisconnect(createManagerPair())
  @Test fun testCoupleConnectFANtdSuccess() = testCoupleConnectFANtdSuccess(createManagerPair())
  @Test fun testCoupleConnectFANtdError() = testCoupleConnectFANtdError(createManagerPair())
  @Test fun testCoupleConnectFANtsClosed() = testCoupleConnectFANtsClosed(createManagerPair())
  @Test fun testCoupleConnectFANteClosed() = testCoupleConnectFANteClosed(createManagerPair())
  @Test fun testCoupleConnectFARtConnectTA() = testCoupleConnectFARtConnectTA(createManagerPair())
  @Test fun testCoupleConnectFARtConnectFA() = testCoupleConnectFARtConnectFA(createManagerPair())
  @Test fun testCoupleConnectFAPsConnect() = testCoupleConnectFAPsConnect(createManagerPair())
  @Test fun testCoupleConnectFARsConnect() = testCoupleConnectFARsConnect(createManagerPair())
  @Test fun testCoupleConnectFARfConnectTA() = testCoupleConnectFARfConnectTA(createManagerPair())
  @Test fun testCoupleConnectFARfConnectFA() = testCoupleConnectFARfConnectFA(createManagerPair())

  protected fun testCoupleConnectFAConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectFA(pair)

    handler.connectRequired = true

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFAConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectFA(pair)

    handler.connectRequired = false

    manager.connect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFANtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testSingleConnectFA(pair)

    manager.notifyConnectSuccess()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFANtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testSingleConnectFA(pair)

    manager.notifyConnectError()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFADisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testSingleConnectFA(pair)

    manager.disconnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFANtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testSingleConnectFA(pair)

    manager.notifyDisconnectSuccess()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFANtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testSingleConnectFA(pair)

    manager.notifyDisconnectError()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFANtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testSingleConnectFA(pair)

    manager.notifyServerClosed()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFANteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testSingleConnectFA(pair)

    manager.notifyErrorClosed()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFARtConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectFA(pair)

    handler.connectRequired = true

    manager.retryConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFARtConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectFA(pair)

    handler.connectRequired = false

    manager.retryConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFAPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testSingleConnectFA(pair)

    manager.pauseConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }


  protected fun testCoupleConnectFARsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testSingleConnectFA(pair)

    manager.resumeConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFARfConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectFA(pair)

    handler.connectRequired = true

    manager.refreshConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCoupleConnectFARfConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnectFA(pair)

    handler.connectRequired = false

    manager.refreshConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> PsConnect
  // STATE          : null     null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :          TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> PsConnect -> Connect
  // STATE          : null     null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :                       TRUE
  // PAUSED         :          TRUE         TRUE
  //
  //                        -> PsConnect -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect, RfConnect
  // STATE          : null     null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :          TRUE         TRUE
  //
  //                        -> PsConnect -> RsConnect
  // STATE          : null     null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :          TRUE

  @Test fun testCouplePsConnectConnect() = testCouplePsConnectConnect(createManagerPair())
  @Test fun testCouplePsConnectNtcSuccess() = testCouplePsConnectNtcSuccess(ConnectManager())
  @Test fun testCouplePsConnectNtcError() = testCouplePsConnectNtcError(ConnectManager())
  @Test fun testCouplePsConnectDisconnect() = testCouplePsConnectDisconnect(ConnectManager())
  @Test fun testCouplePsConnectNtdSuccess() = testCouplePsConnectNtdSuccess(ConnectManager())
  @Test fun testCouplePsConnectNtdError() = testCouplePsConnectNtdError(ConnectManager())
  @Test fun testCouplePsConnectNtsClosed() = testCouplePsConnectNtsClosed(ConnectManager())
  @Test fun testCouplePsConnectNteClosed() = testCouplePsConnectNteClosed(ConnectManager())
  @Test fun testCouplePsConnectRtConnect() = testCouplePsConnectRtConnect(ConnectManager())
  @Test fun testCouplePsConnectPsConnect() = testCouplePsConnectPsConnect(ConnectManager())
  @Test fun testCouplePsConnectRsConnect() = testCouplePsConnectRsConnect(ConnectManager())
  @Test fun testCouplePsConnectRfConnect() = testCouplePsConnectRfConnect(ConnectManager())

  protected fun testCouplePsConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testSinglePsConnect(manager)

    manager.connect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCouplePsConnectNtcSuccess(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyConnectSuccess()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCouplePsConnectNtcError(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyConnectError()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCouplePsConnectDisconnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.disconnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCouplePsConnectNtdSuccess(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyDisconnectSuccess()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCouplePsConnectNtdError(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyDisconnectError()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCouplePsConnectNtsClosed(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyServerClosed()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCouplePsConnectNteClosed(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyErrorClosed()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCouplePsConnectRtConnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.retryConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCouplePsConnectPsConnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.pauseConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testCouplePsConnectRsConnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.resumeConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testCouplePsConnectRfConnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.refreshConnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

}