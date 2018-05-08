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
 * @author verstsiu on 2018/5/4.
 * @version 1.0
 */
open class ConnectManagerTestQuarter: ConnectManagerTestTriple() {

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
  // STATE          : CONNECTING  CONNECTING  CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE        TRUE
  // PAUSED         :             TRUE
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
  // STATE          : DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :
  //
  // STATE          : DISCONNECT_COMPLETE
  // SUCCESS        : TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :
  //
  // STATE          : DISCONNECT_COMPLETE
  // SUCCESS        : FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :
  //
  // STATE          : RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    : 0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     : TRUE
  // ENABLED        : TRUE
  // PAUSED         :

  // Current:
  //                        -> ConnectTA  -> NtcSuccessTA     -> Disconnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcSuccessTA     -> Disconnect    -> Connect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                                 TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> Disconnect    -> NtcSuccess, NtcError, Disconnect, RtConnect, RsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> Disconnect    -> NtdSuccess, NtsClosed
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                                 TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> Disconnect    -> NtdError, NteClosed
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                                 FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> Disconnect    -> PsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                                 TRUE
  // PAUSED         :

  @Test fun testQuarterConnectTANtcSuccessTADisconnectConnect() = testQuarterConnectTANtcSuccessTADisconnectConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectNtcSuccess() = testQuarterConnectTANtcSuccessTADisconnectNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectNtcError() = testQuarterConnectTANtcSuccessTADisconnectNtcError(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectDisconnect() = testQuarterConnectTANtcSuccessTADisconnectDisconnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectNtdSuccess() = testQuarterConnectTANtcSuccessTADisconnectNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectNtdError() = testQuarterConnectTANtcSuccessTADisconnectNtdError(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectNtsClosed() = testQuarterConnectTANtcSuccessTADisconnectNtsClosed(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectNteClosed() = testQuarterConnectTANtcSuccessTADisconnectNteClosed(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectRtConnect() = testQuarterConnectTANtcSuccessTADisconnectRtConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectRsConnect() = testQuarterConnectTANtcSuccessTADisconnectRsConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectPsConnect() = testQuarterConnectTANtcSuccessTADisconnectPsConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTADisconnectRfConnect() = testQuarterConnectTANtcSuccessTADisconnectRfConnect(ConnectManager())

  protected fun testQuarterConnectTANtcSuccessTADisconnectConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTADisconnectNtcSuccess(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTADisconnectNtcError(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTADisconnectDisconnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTADisconnectNtdSuccess(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTADisconnectNtdError(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTADisconnectNtsClosed(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

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

  protected fun testQuarterConnectTANtcSuccessTADisconnectNteClosed(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

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

  protected fun testQuarterConnectTANtcSuccessTADisconnectRtConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTADisconnectRsConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTADisconnectPsConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTADisconnectRfConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTADisconnect(manager)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> Connect, NtcSuccess, NtcError, RtConnect, PsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE             TRUE
  // PAUSED         :                                            TRUE             TRUE
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> Disconnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE             TRUE
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> NtdSuccess, NtsClosed
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                                 TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE             TRUE
  // PAUSED         :                                            TRUE             TRUE
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> NtdError, NteClosed
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                                 FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE             TRUE
  // PAUSED         :                                            TRUE             TRUE
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> RsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE             TRUE
  // PAUSED         :                                            TRUE

  @Test fun testQuarterConnectTANtcSuccessTAPsConnectConnect() = testQuarterConnectTANtcSuccessTAPsConnectConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectNtcSuccess() = testQuarterConnectTANtcSuccessTAPsConnectNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectNtcError() = testQuarterConnectTANtcSuccessTAPsConnectNtcError(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectDisconnect() = testQuarterConnectTANtcSuccessTAPsConnectDisconnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectNtdSuccess() = testQuarterConnectTANtcSuccessTAPsConnectNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectNtdError() = testQuarterConnectTANtcSuccessTAPsConnectNtdError(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectNtsClosed() = testQuarterConnectTANtcSuccessTAPsConnectNtsClosed(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectNteClosed() = testQuarterConnectTANtcSuccessTAPsConnectNteClosed(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectRtConnect() = testQuarterConnectTANtcSuccessTAPsConnectRtConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectPsConnect() = testQuarterConnectTANtcSuccessTAPsConnectPsConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectRsConnect() = testQuarterConnectTANtcSuccessTAPsConnectRsConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTAPsConnectRfConnect() = testQuarterConnectTANtcSuccessTAPsConnectRfConnect(ConnectManager())

  protected fun testQuarterConnectTANtcSuccessTAPsConnectConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTAPsConnectNtcSuccess(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTAPsConnectNtcError(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTAPsConnectNtdSuccess(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTAPsConnectNtdError(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTAPsConnectNtsClosed(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

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

  protected fun testQuarterConnectTANtcSuccessTAPsConnectNteClosed(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

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


  protected fun testQuarterConnectTANtcSuccessTAPsConnectRtConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTAPsConnectPsConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTAPsConnectRsConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTAPsConnectRfConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTAPsConnect(manager)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcSuccessTA     -> RfConnectFTTA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcSuccessTA     -> RfConnectFTTA -> Connect, NtcSuccess, NtcError, RtConnect, RsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE             TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> RfConnectFTTA -> Disconnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> RfConnectFTTA -> NtdSuccessTA, NtdErrorTA, NtsClosedTA, NteClosedTA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    CONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE             TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> RfConnectFTTA -> NtdSuccessFA, NtsClosedFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                                 TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE             TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> RfConnectFTTA -> NtdErrorFA, NteClosedFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                                 FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE             TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> RfConnectFTTA -> PsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE             TRUE
  // PAUSED         :                                                             TRUE

  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTAConnect() = testQuarterConnectTANtcSuccessTARfConnectFTTAConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTANtcSuccess() = testQuarterConnectTANtcSuccessTARfConnectFTTANtcSuccess(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTANtcError() = testQuarterConnectTANtcSuccessTARfConnectFTTANtcError(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTADisconnect() = testQuarterConnectTANtcSuccessTARfConnectFTTADisconnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTANtdSuccessTA() = testQuarterConnectTANtcSuccessTARfConnectFTTANtdSuccessTA(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTANtdSuccessFA() = testQuarterConnectTANtcSuccessTARfConnectFTTANtdSuccessFA(createManagerPair())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTANtdErrorTA() = testQuarterConnectTANtcSuccessTARfConnectFTTANtdErrorTA(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTANtdErrorFA() = testQuarterConnectTANtcSuccessTARfConnectFTTANtdErrorFA(createManagerPair())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTANtsClosedTA() = testQuarterConnectTANtcSuccessTARfConnectFTTANtsClosedTA(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTANtsClosedFA() = testQuarterConnectTANtcSuccessTARfConnectFTTANtsClosedFA(createManagerPair())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTANteClosedTA() = testQuarterConnectTANtcSuccessTARfConnectFTTANteClosedTA(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTANteClosedFA() = testQuarterConnectTANtcSuccessTARfConnectFTTANteClosedFA(createManagerPair())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTARtConnect() = testQuarterConnectTANtcSuccessTARfConnectFTTARtConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTAPsConnect() = testQuarterConnectTANtcSuccessTARfConnectFTTAPsConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTARsConnect() = testQuarterConnectTANtcSuccessTARfConnectFTTARsConnect(ConnectManager())
  @Test fun testQuarterConnectTANtcSuccessTARfConnectFTTARfConnect() = testQuarterConnectTANtcSuccessTARfConnectFTTARfConnect(ConnectManager())

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTAConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTANtcSuccess(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTANtcError(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTADisconnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTANtdSuccessTA(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTANtdSuccessFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

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

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTANtdErrorTA(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTANtdErrorFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

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

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTANtsClosedTA(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTANtsClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

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

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTANteClosedTA(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTANteClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

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

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTARtConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTAPsConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTARsConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcSuccessTARfConnectFTTARfConnect(manager: ConnectManager) {
    testTripleConnectTANtcSuccessTARfConnectFTTA(manager)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcError(true,0) -> Disconnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,0) -> Disconnect          -> ConnectTA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                        FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                                       TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> Disconnect          -> ConnectFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                                       TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> Disconnect          -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, RsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> Disconnect          -> PsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :                                                                   TRUE

  @Test fun testQuarterConnectTANtcErrorT0DisconnectConnectTA() = testQuarterConnectTANtcErrorT0DisconnectConnectTA(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectConnectFA() = testQuarterConnectTANtcErrorT0DisconnectConnectFA(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectNtcSuccess() = testQuarterConnectTANtcErrorT0DisconnectNtcSuccess(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectNtcError() = testQuarterConnectTANtcErrorT0DisconnectNtcError(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectDisconnect() = testQuarterConnectTANtcErrorT0DisconnectDisconnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectNtdSuccess() = testQuarterConnectTANtcErrorT0DisconnectNtdSuccess(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectNtdError() = testQuarterConnectTANtcErrorT0DisconnectNtdError(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectNtsClosed() = testQuarterConnectTANtcErrorT0DisconnectNtsClosed(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectNteClosed() = testQuarterConnectTANtcErrorT0DisconnectNteClosed(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectRtConnect() = testQuarterConnectTANtcErrorT0DisconnectRtConnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectPsConnect() = testQuarterConnectTANtcErrorT0DisconnectPsConnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectRsConnect() = testQuarterConnectTANtcErrorT0DisconnectRsConnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0DisconnectRfConnect() = testQuarterConnectTANtcErrorT0DisconnectRfConnect(createManagerPair())

  protected fun testQuarterConnectTANtcErrorT0DisconnectConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT0Disconnect(pair)

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

  protected fun testQuarterConnectTANtcErrorT0DisconnectConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT0Disconnect(pair)

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

  protected fun testQuarterConnectTANtcErrorT0DisconnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0DisconnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

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

  protected fun testQuarterConnectTANtcErrorT0DisconnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

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

  protected fun testQuarterConnectTANtcErrorT0DisconnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0DisconnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0DisconnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

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

  protected fun testQuarterConnectTANtcErrorT0DisconnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0DisconnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0DisconnectRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0DisconnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0Disconnect(pair)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcError(true,0) -> PsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,0) -> PsConnect           -> Connect, NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteError, RtConnect, PsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                            TRUE                   TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> PsConnect           -> Disconnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE                   TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> PsConnect           -> RsConnectTA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                        FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                            TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> PsConnect           -> RsConnectFA
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                            TRUE

  @Test fun testQuarterConnectTANtcErrorT0PsConnectConnect() = testQuarterConnectTANtcErrorT0PsConnectConnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectNtcSuccess() = testQuarterConnectTANtcErrorT0PsConnectNtcSuccess(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectNtcError() = testQuarterConnectTANtcErrorT0PsConnectNtcError(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectDisconnect() = testQuarterConnectTANtcErrorT0PsConnectDisconnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectNtdSuccess() = testQuarterConnectTANtcErrorT0PsConnectNtdSuccess(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectNtdError() = testQuarterConnectTANtcErrorT0PsConnectNtdError(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectNtsClosed() = testQuarterConnectTANtcErrorT0PsConnectNtsClosed(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectNteClosed() = testQuarterConnectTANtcErrorT0PsConnectNteClosed(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectRtConnect() = testQuarterConnectTANtcErrorT0PsConnectRtConnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectPsConnect() = testQuarterConnectTANtcErrorT0PsConnectPsConnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectRsConnectTA() = testQuarterConnectTANtcErrorT0PsConnectRsConnectTA(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectRsConnectFA() = testQuarterConnectTANtcErrorT0PsConnectRsConnectFA(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT0PsConnectRfConnect() = testQuarterConnectTANtcErrorT0PsConnectRfConnect(createManagerPair())

  protected fun testQuarterConnectTANtcErrorT0PsConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0PsConnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0PsConnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

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

  protected fun testQuarterConnectTANtcErrorT0PsConnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0PsConnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0PsConnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0PsConnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

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

  protected fun testQuarterConnectTANtcErrorT0PsConnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0PsConnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0PsConnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

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

  protected fun testQuarterConnectTANtcErrorT0PsConnectRsConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT0PsConnect(pair)

    handler.connectRequired = true

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT0PsConnectRsConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT0PsConnect(pair)

    handler.connectRequired = false

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

  protected fun testQuarterConnectTANtcErrorT0PsConnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT0PsConnect(pair)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> ConnectTA, RtConnectTA, RfConnectTA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING[1]
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   [0]
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> ConnectFA, RtConnectFA, RfConnectFA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   [0]
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtdSuccess, NtdError, RsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessTA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessFA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcError(true,1), NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                FALSE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,2) -> RtConnectTA      -> NtcError(true,2), NtsClosed(true,2), NteClosed(true,2)
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   1
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE                                    TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcErrorFA, NtsClosedFA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NteClosedFA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                FALSE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> PsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :                                                                TRUE

  @Test fun testQuarterConnectTANtcErrorT1RtConnectTAConnect() = testQuarterConnectTANtcErrorT1RtConnectTAConnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA() = testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessFA() = testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessFA(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1() = testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT2RtConnectTANtcErrorT2() = testQuarterConnectTANtcErrorT2RtConnectTANtcErrorT2(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTANtcErrorFA() = testQuarterConnectTANtcErrorT1RtConnectTANtcErrorFA(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTADisconnect() = testQuarterConnectTANtcErrorT1RtConnectTADisconnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTANtdSuccess() = testQuarterConnectTANtcErrorT1RtConnectTANtdSuccess(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTANtdError() = testQuarterConnectTANtcErrorT1RtConnectTANtdError(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTANtsClosedT1() = testQuarterConnectTANtcErrorT1RtConnectTANtsClosedT1(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT2RtConnectTANtsClosedT2() = testQuarterConnectTANtcErrorT2RtConnectTANtsClosedT2(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTANtsClosedFA() = testQuarterConnectTANtcErrorT1RtConnectTANtsClosedFA(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTANteClosedT1() = testQuarterConnectTANtcErrorT1RtConnectTANteClosedT1(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT2RtConnectTANteClosedT2() = testQuarterConnectTANtcErrorT2RtConnectTANteClosedT2(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTANteClosedFA() = testQuarterConnectTANtcErrorT1RtConnectTANteClosedFA(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTARtConnect() = testQuarterConnectTANtcErrorT1RtConnectTARtConnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTAPsConnect() = testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTARsConnect() = testQuarterConnectTANtcErrorT1RtConnectTARsConnect(createManagerPair())
  @Test fun testQuarterConnectTANtcErrorT1RtConnectTARfConnect() = testQuarterConnectTANtcErrorT1RtConnectTARfConnect(createManagerPair())

  protected fun testQuarterConnectTANtcErrorT1RtConnectTAConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

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

  protected fun testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

    handler.connectRequired = true

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

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

  protected fun testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

    handler.connectRequired = true
    handler.maxRetry = 1

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT2RtConnectTANtcErrorT2(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair, {
      it.connectRequired = true
      it.maxRetry = 2
    })

    handler.connectRequired = true
    handler.maxRetry = 2

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 1)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT1RtConnectTANtcErrorFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

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

  protected fun testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT1RtConnectTANtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT1RtConnectTANtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT1RtConnectTANtsClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

    handler.connectRequired = true
    handler.maxRetry = 1

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT2RtConnectTANtsClosedT2(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair, {
      it.connectRequired = true
      it.maxRetry = 2
    })

    handler.connectRequired = true
    handler.maxRetry = 2

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 1)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT1RtConnectTANtsClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

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

  protected fun testQuarterConnectTANtcErrorT1RtConnectTANteClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

    handler.connectRequired = true
    handler.maxRetry = 1

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT2RtConnectTANteClosedT2(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair, {
      it.connectRequired = true
      it.maxRetry = 2
    })

    handler.connectRequired = true
    handler.maxRetry = 2

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 1)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT1RtConnectTANteClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

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

  protected fun testQuarterConnectTANtcErrorT1RtConnectTARtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

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

  protected fun testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT1RtConnectTARsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANtcErrorT1RtConnectTARfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANtcErrorT1RfConnectTA(pair)

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

  // Current:
  //                        -> ConnectTA  -> Disconnect -> NteClosed
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> Disconnect -> NteClosed           -> ConnectTA
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                                      FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE                                               TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> Disconnect -> NteClosed           -> ConnectFA
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE                                               TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> Disconnect -> NteClosed           -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, RsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> Disconnect -> NteClosed           -> PsConnect
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                                             TRUE

  @Test fun testQuarterConnectTADisconnectNteClosedConnectTA() = testQuarterConnectTADisconnectNteClosedConnectTA(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedConnectFA() = testQuarterConnectTADisconnectNteClosedConnectFA(createManagerPair())
  @Test fun testQuarterConnectTADisconnectNteClosedNtcSuccess() = testQuarterConnectTADisconnectNteClosedNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedNtcError() = testQuarterConnectTADisconnectNteClosedNtcError(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedDisconnect() = testQuarterConnectTADisconnectNteClosedDisconnect(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedNtdSuccess() = testQuarterConnectTADisconnectNteClosedNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedNtdError() = testQuarterConnectTADisconnectNteClosedNtdError(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedNtsClosed() = testQuarterConnectTADisconnectNteClosedNtsClosed(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedNteClosed() = testQuarterConnectTADisconnectNteClosedNteClosed(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedRtConnect() = testQuarterConnectTADisconnectNteClosedRtConnect(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedPsConnect() = testQuarterConnectTADisconnectNteClosedPsConnect(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedRsConnect() = testQuarterConnectTADisconnectNteClosedRsConnect(ConnectManager())
  @Test fun testQuarterConnectTADisconnectNteClosedRfConnect() = testQuarterConnectTADisconnectNteClosedRfConnect(ConnectManager())

  protected fun testQuarterConnectTADisconnectNteClosedConnectTA(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectNteClosedConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTADisconnectNteClosed(manager)

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

  protected fun testQuarterConnectTADisconnectNteClosedNtcSuccess(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectNteClosedNtcError(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectNteClosedDisconnect(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

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

  protected fun testQuarterConnectTADisconnectNteClosedNtdSuccess(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectNteClosedNtdError(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectNteClosedNtsClosed(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectNteClosedNteClosed(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

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

  protected fun testQuarterConnectTADisconnectNteClosedRtConnect(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectNteClosedPsConnect(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectNteClosedRsConnect(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectNteClosedRfConnect(manager: ConnectManager) {
    testTripleConnectTADisconnectNteClosed(manager)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> Disconnect -> PsConnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                      TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> Disconnect -> PsConnect  -> Connect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE                                      TRUE
  // PAUSED         :                                      TRUE          TRUE
  //
  //                        -> ConnectTA  -> Disconnect -> PsConnect  -> NtcSuccess
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                      TRUE          TRUE
  //
  //                        -> ConnectTA  -> Disconnect -> PsConnect  -> NtcError, NtsClosed
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                    TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                      TRUE          TRUE
  //
  //                        -> ConnectTA  -> Disconnect -> PsConnect  -> Disconnect, NtdSuccess, NtdError, RtConnect, PsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                      TRUE          TRUE
  //
  //                        -> ConnectTA  -> Disconnect -> PsConnect  -> NteClosed
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                    FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                      TRUE          TRUE
  //
  //                        -> ConnectTA  -> Disconnect -> PsConnect  -> RsConnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                      TRUE

  @Test fun testQuarterConnectTADisconnectPsConnectConnect() = testQuarterConnectTADisconnectPsConnectConnect(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectNtcSuccess() = testQuarterConnectTADisconnectPsConnectNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectNtcError() = testQuarterConnectTADisconnectPsConnectNtcError(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectDisconnect() = testQuarterConnectTADisconnectPsConnectDisconnect(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectNtdSuccess() = testQuarterConnectTADisconnectPsConnectNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectNtdError() = testQuarterConnectTADisconnectPsConnectNtdError(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectNtsClosed() = testQuarterConnectTADisconnectPsConnectNtsClosed(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectNteClosed() = testQuarterConnectTADisconnectPsConnectNteClosed(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectRtConnect() = testQuarterConnectTADisconnectPsConnectRtConnect(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectPsConnect() = testQuarterConnectTADisconnectPsConnectPsConnect(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectRsConnect() = testQuarterConnectTADisconnectPsConnectRsConnect(ConnectManager())
  @Test fun testQuarterConnectTADisconnectPsConnectRfConnect() = testQuarterConnectTADisconnectPsConnectRfConnect(ConnectManager())

  protected fun testQuarterConnectTADisconnectPsConnectConnect(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectPsConnectNtcSuccess(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectPsConnectNtcError(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

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

  protected fun testQuarterConnectTADisconnectPsConnectDisconnect(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectPsConnectNtdSuccess(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectPsConnectNtdError(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectPsConnectNtsClosed(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

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

  protected fun testQuarterConnectTADisconnectPsConnectNteClosed(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

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

  protected fun testQuarterConnectTADisconnectPsConnectRtConnect(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectPsConnectPsConnect(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectPsConnectRsConnect(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTADisconnectPsConnectRfConnect(manager: ConnectManager) {
    testTripleConnectTADisconnectPsConnect(manager)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NteClosed(true,0)   -> PsConnect
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                  TRUE
  // PAUSED         :                                              TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NteClosed(true,0)   -> PsConnect           -> Connect, NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect, RfConnect
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                  TRUE                    TRUE
  // PAUSED         :                                              TRUE                    TRUE
  //
  //                        -> ConnectTA  -> NteClosed(true,0)   -> PsConnect           -> Disconnect
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                  TRUE
  // PAUSED         :                                              TRUE                    TRUE
  //
  //                        -> ConnectTA  -> NteClosed(true,0)   -> PsConnect           -> RsConnectTA
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                        FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                  TRUE                    TRUE
  // PAUSED         :                                              TRUE
  //
  //                        -> ConnectTA  -> NteClosed(true,0)   -> PsConnect           -> RsConnectFA
  // STATE          : null     CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                  TRUE                    TRUE
  // PAUSED         :                                              TRUE

  @Test fun testQuarterConnectTANteClosedT0PsConnectConnect() = testQuarterConnectTANteClosedT0PsConnectConnect(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectNtcSuccess() = testQuarterConnectTANteClosedT0PsConnectNtcSuccess(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectNtcError() = testQuarterConnectTANteClosedT0PsConnectNtcError(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectDisconnect() = testQuarterConnectTANteClosedT0PsConnectDisconnect(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectNtdSuccess() = testQuarterConnectTANteClosedT0PsConnectNtdSuccess(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectNtdError() = testQuarterConnectTANteClosedT0PsConnectNtdError(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectNtsClosed() = testQuarterConnectTANteClosedT0PsConnectNtsClosed(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectNteClosed() = testQuarterConnectTANteClosedT0PsConnectNteClosed(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectRtConnect() = testQuarterConnectTANteClosedT0PsConnectRtConnect(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectPsConnect() = testQuarterConnectTANteClosedT0PsConnectPsConnect(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectRsConnectTA() = testQuarterConnectTANteClosedT0PsConnectRsConnectTA(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectRsConnectFA() = testQuarterConnectTANteClosedT0PsConnectRsConnectFA(createManagerPair())
  @Test fun testQuarterConnectTANteClosedT0PsConnectRfConnect() = testQuarterConnectTANteClosedT0PsConnectRfConnect(createManagerPair())

  protected fun testQuarterConnectTANteClosedT0PsConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANteClosedT0PsConnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANteClosedT0PsConnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANteClosedT0PsConnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANteClosedT0PsConnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANteClosedT0PsConnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANteClosedT0PsConnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANteClosedT0PsConnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

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

  protected fun testQuarterConnectTANteClosedT0PsConnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testQuarterConnectTANteClosedT0PsConnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

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

  protected fun testQuarterConnectTANteClosedT0PsConnectRsConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANteClosedT0PsConnect(pair)

    handler.connectRequired = true

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testQuarterConnectTANteClosedT0PsConnectRsConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectTANteClosedT0PsConnect(pair)

    handler.connectRequired = false

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

  protected fun testQuarterConnectTANteClosedT0PsConnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectTANteClosedT0PsConnect(pair)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

}