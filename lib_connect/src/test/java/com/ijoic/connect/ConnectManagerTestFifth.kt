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
 * Connect manager test(fifth).
 *
 * @author verstsiu on 2018/5/6.
 * @version 1.0
 */
open class ConnectManagerTestFifth: ConnectManagerTestQuarter() {

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
  // STATE          : DISCONNECTING DISCONNECTING DISCONNECTING DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   : TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE          TRUE          TRUE
  // PAUSED         :                             TRUE
  //
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : TRUE                TRUE                TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE                TRUE
  // PAUSED         :                     TRUE
  //
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : FALSE               FALSE               FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE                TRUE
  // PAUSED         :                     TRUE
  //
  // STATE          : RETRY_CONNECTING  RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    : 0                 0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     : TRUE
  // ENABLED        : TRUE              TRUE
  // PAUSED         :

  // Current:
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> Disconnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE             TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> Disconnect    -> Connect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE                              TRUE
  // PAUSED         :                                            TRUE             TRUE             TRUE
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> Disconnect    -> NtcSuccess, NtcError, Disconnect, RtConnect, PsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE             TRUE             TRUE
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> Disconnect    -> NtdSuccess, NtsClosed
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                                                  TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE             TRUE             TRUE
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> Disconnect    -> NtdError, NteClosed
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                        TRUE                                                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE             TRUE             TRUE
  //
  //                        -> ConnectTA  -> NtcSuccessTA     -> PsConnect     -> Disconnect    -> RsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE             TRUE

  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectConnect() = testFifthConnectTANtcSuccessTAPsConnectDisconnectConnect(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNtcSuccess() = testFifthConnectTANtcSuccessTAPsConnectDisconnectNtcSuccess(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNtcError() = testFifthConnectTANtcSuccessTAPsConnectDisconnectNtcError(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectDisconnect() = testFifthConnectTANtcSuccessTAPsConnectDisconnectDisconnect(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNtdSuccess() = testFifthConnectTANtcSuccessTAPsConnectDisconnectNtdSuccess(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNtdError() = testFifthConnectTANtcSuccessTAPsConnectDisconnectNtdError(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNtsClosed() = testFifthConnectTANtcSuccessTAPsConnectDisconnectNtsClosed(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNteClosed() = testFifthConnectTANtcSuccessTAPsConnectDisconnectNteClosed(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectRtConnect() = testFifthConnectTANtcSuccessTAPsConnectDisconnectRtConnect(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectPsConnect() = testFifthConnectTANtcSuccessTAPsConnectDisconnectPsConnect(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectRsConnect() = testFifthConnectTANtcSuccessTAPsConnectDisconnectRsConnect(ConnectManager())
  @Test fun testFifthConnectTANtcSuccessTAPsConnectDisconnectRfConnect() = testFifthConnectTANtcSuccessTAPsConnectDisconnectRfConnect(ConnectManager())

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectConnect(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNtcSuccess(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNtcError(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectDisconnect(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNtdSuccess(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNtdError(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNtsClosed(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

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

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectNteClosed(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

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

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectRtConnect(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectPsConnect(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectRsConnect(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcSuccessTAPsConnectDisconnectRfConnect(manager: ConnectManager) {
    testQuarterConnectTANtcSuccessTAPsConnectDisconnect(manager)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcError(true,0) -> Disconnect          -> PsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :                                                                   TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,0) -> Disconnect          -> PsConnect           -> Connect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE                   TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                                                              TRUE
  // PAUSED         :                                                                   TRUE                   TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> Disconnect          -> PsConnect           -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE                   TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :                                                                   TRUE                   TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,0) -> Disconnect          -> PsConnect           -> RsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                        FALSE               TRUE                   TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE
  // PAUSED         :                                                                   TRUE

  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectConnect() = testFifthConnectTANtcErrorT0DisconnectPsConnectConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectNtcSuccess() = testFifthConnectTANtcErrorT0DisconnectPsConnectNtcSuccess(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectNtcError() = testFifthConnectTANtcErrorT0DisconnectPsConnectNtcError(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectDisconnect() = testFifthConnectTANtcErrorT0DisconnectPsConnectDisconnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectNtdSuccess() = testFifthConnectTANtcErrorT0DisconnectPsConnectNtdSuccess(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectNtdError() = testFifthConnectTANtcErrorT0DisconnectPsConnectNtdError(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectNtsClosed() = testFifthConnectTANtcErrorT0DisconnectPsConnectNtsClosed(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectNteClosed() = testFifthConnectTANtcErrorT0DisconnectPsConnectNteClosed(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectRtConnect() = testFifthConnectTANtcErrorT0DisconnectPsConnectRtConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectPsConnect() = testFifthConnectTANtcErrorT0DisconnectPsConnectPsConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectRsConnect() = testFifthConnectTANtcErrorT0DisconnectPsConnectRsConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT0DisconnectPsConnectRfConnect() = testFifthConnectTANtcErrorT0DisconnectPsConnectRfConnect(createManagerPair())

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT0DisconnectPsConnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT0DisconnectPsConnect(pair)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessTA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessTA           -> ConnectTA, NtcSuccess, NtcError, NtdSuccess, NtdError, RtConnectTA, RsConnect, RfConnectFFTA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                TRUE                      TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessTA           -> ConnectFA, RtConnectFA, RfConnectFA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                                                                TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessTA           -> Disconnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                                                                TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessTA           -> NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    RETRY_CONNECTING
  // SUCCESS        :                                                                TRUE
  // RETRY_COUNT    :                        0                   0                                             0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE                                                              TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessTA           -> NtsClosedFA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                TRUE                      TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessTA           -> NteClosedFA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                TRUE                      FALSE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessTA           -> PsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                                                                TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :                                                                                          TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcSuccessTA           -> RfConnectFTTA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                                                                TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :                                                                                          TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :

  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTAConnectTA() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTAConnectTA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTAConnectFA() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTAConnectFA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtcSuccess() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtcSuccess(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtcError() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtcError(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTADisconnect() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTADisconnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtdSuccess() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtdSuccess(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtdError() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtdError(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtsClosedT1() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtsClosedT1(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtsClosedFA() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtsClosedFA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANteClosedT1() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANteClosedT1(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANteClosedFA() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANteClosedFA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARtConnectTA() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARtConnectTA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARtConnectFA() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARtConnectFA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTAPsConnect() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTAPsConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARsConnect() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARsConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARfConnectFFTA() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARfConnectFFTA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARfConnectFTTA() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARfConnectFTTA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARfConnectFA() = testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARfConnectFA(createManagerPair())

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTAConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

    handler.connectRequired = true

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTAConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTADisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtsClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANtsClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANteClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTANteClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARtConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

    handler.connectRequired = true

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARtConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTAPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARfConnectFFTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

    handler.connectRequired = true

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARfConnectFTTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

    handler.connectRequired = true

    manager.refreshConnect(forceConnect = true)
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcSuccessTARfConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcSuccessTA(pair)

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
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcError(true,1)
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                FALSE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcError(true,1)       -> ConnectTA, RtConnectTA, RfConnectTA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                                                                FALSE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcError(true,1)       -> ConnectFA, NtcSuccess, NtcError, NtdSuccess, NtdError, RtConnectFA, RsConnect, RfConnectFA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                FALSE                     FALSE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcError(true,1)       -> Disconnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                FALSE                     TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcError(true,1)       -> NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    RETRY_CONNECTING
  // SUCCESS        :                                                                FALSE
  // RETRY_COUNT    :                        0                   0                                             0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE                                                              TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcError(true,1)       -> NtsClosedFA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                FALSE                     TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcError(true,1)       -> NteClosedA
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                FALSE                     FALSE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> NtcError(true,1)       -> PsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                FALSE                     TRUE
  // RETRY_COUNT    :                        0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :                                                                                          TRUE

  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1ConnectTA() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1ConnectTA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1ConnectFA() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1ConnectFA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtcSuccess() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtcSuccess(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtcError() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtcError(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1Disconnect() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1Disconnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtdSuccess() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtdSuccess(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtdError() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtdError(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtsClosedT1() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtsClosedT1(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtsClosedFA() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtsClosedFA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NteClosedT1() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NteClosedT1(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NteClosedFA() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NteClosedFA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RtConnectTA() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RtConnectTA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RtConnectFA() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RtConnectFA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1PsConnect() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1PsConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RsConnect() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RsConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RfConnectTA() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RfConnectTA(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RfConnectFA() = testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RfConnectFA(createManagerPair())

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1ConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1ConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

    handler.connectRequired = false

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1Disconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtsClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NtsClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NteClosedT1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1NteClosedFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RtConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RtConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

    handler.connectRequired = false

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1PsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

    manager.resumeConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RfConnectTA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTANtcErrorT1RfConnectFA(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectTANtcErrorT1RtConnectTANtcErrorT1(pair)

    handler.connectRequired = false

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> Connect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                                    TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> NtcSuccess
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> NtcError, NtsClosed
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                    TRUE
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> Disconnect, NtdSuccess, NtdError, RtConnect, RsConnect, RfConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> NteClosed
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                    FALSE
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> Disconnect       -> PsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                                                                    TRUE

  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectConnect() = testFifthConnectTANtcErrorT1RtConnectTADisconnectConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNtcSuccess() = testFifthConnectTANtcErrorT1RtConnectTADisconnectNtcSuccess(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNtcError() = testFifthConnectTANtcErrorT1RtConnectTADisconnectNtcError(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectDisconnect() = testFifthConnectTANtcErrorT1RtConnectTADisconnectDisconnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNtdSuccess() = testFifthConnectTANtcErrorT1RtConnectTADisconnectNtdSuccess(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNtdError() = testFifthConnectTANtcErrorT1RtConnectTADisconnectNtdError(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNtsClosed() = testFifthConnectTANtcErrorT1RtConnectTADisconnectNtsClosed(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNteClosed() = testFifthConnectTANtcErrorT1RtConnectTADisconnectNteClosed(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectRtConnect() = testFifthConnectTANtcErrorT1RtConnectTADisconnectRtConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect() = testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectRsConnect() = testFifthConnectTANtcErrorT1RtConnectTADisconnectRsConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTADisconnectRfConnect() = testFifthConnectTANtcErrorT1RtConnectTADisconnectRfConnect(createManagerPair())

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTADisconnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTADisconnect(pair)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> PsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :                                                                TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> PsConnect        -> Connect, NtdSuccess, NtdError, RtConnect, PsConnect, RfConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                TRUE                TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> PsConnect        -> NtcSuccess
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                TRUE                TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> PsConnect        -> NtcError, NtsClosed
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                    TRUE
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                TRUE                TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> PsConnect        -> Disconnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE
  // PAUSED         :                                                                TRUE                TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> PsConnect        -> NteClosed
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                    FALSE
  // RETRY_COUNT    :                        0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                TRUE                TRUE
  //
  //                        -> ConnectTA  -> NtcError(true,1) -> RtConnectTA      -> PsConnect        -> RsConnect
  // STATE          : null     CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                        0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                        TRUE
  // ENABLED        :          TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                TRUE

  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectConnect() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtcSuccess() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtcSuccess(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtcError() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtcError(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectDisconnect() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectDisconnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtdSuccess() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtdSuccess(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtdError() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtdError(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtsClosed() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtsClosed(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNteClosed() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectNteClosed(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectRtConnect() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectRtConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectPsConnect() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectPsConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectRsConnect() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectRsConnect(createManagerPair())
  @Test fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectRfConnect() = testFifthConnectTANtcErrorT1RtConnectTAPsConnectRfConnect(createManagerPair())

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

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

  protected fun testFifthConnectTANtcErrorT1RtConnectTAPsConnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectTANtcErrorT1RtConnectTAPsConnect(pair)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  // Current:
  //                        -> ConnectTA  -> Disconnect -> NteClosed           -> PsConnect
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                                             TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                        -> ConnectTA  -> Disconnect -> NteClosed           -> PsConnect           -> Connect
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE                                                                      TRUE
  // PAUSED         :                                                             TRUE                   TRUE
  //
  //                        -> ConnectTA  -> Disconnect -> NteClosed           -> PsConnect           -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect, RfConnect
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                                             TRUE                   TRUE
  //
  //                        -> ConnectTA  -> Disconnect -> NteClosed           -> PsConnect           -> RsConnect
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                                             TRUE

  @Test fun testFifthConnectTADisconnectNteClosedPsConnectConnect() = testFifthConnectTADisconnectNteClosedPsConnectConnect(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectNtcSuccess() = testFifthConnectTADisconnectNteClosedPsConnectNtcSuccess(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectNtcError() = testFifthConnectTADisconnectNteClosedPsConnectNtcError(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectDisconnect() = testFifthConnectTADisconnectNteClosedPsConnectDisconnect(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectNtdSuccess() = testFifthConnectTADisconnectNteClosedPsConnectNtdSuccess(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectNtdError() = testFifthConnectTADisconnectNteClosedPsConnectNtdError(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectNtsClosed() = testFifthConnectTADisconnectNteClosedPsConnectNtsClosed(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectNteClosed() = testFifthConnectTADisconnectNteClosedPsConnectNteClosed(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectRtConnect() = testFifthConnectTADisconnectNteClosedPsConnectRtConnect(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectPsConnect() = testFifthConnectTADisconnectNteClosedPsConnectPsConnect(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectRsConnect() = testFifthConnectTADisconnectNteClosedPsConnectRsConnect(ConnectManager())
  @Test fun testFifthConnectTADisconnectNteClosedPsConnectRfConnect() = testFifthConnectTADisconnectNteClosedPsConnectRfConnect(ConnectManager())

  protected fun testFifthConnectTADisconnectNteClosedPsConnectConnect(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

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

  protected fun testFifthConnectTADisconnectNteClosedPsConnectNtcSuccess(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTADisconnectNteClosedPsConnectNtcError(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTADisconnectNteClosedPsConnectDisconnect(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

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

  protected fun testFifthConnectTADisconnectNteClosedPsConnectNtdSuccess(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTADisconnectNteClosedPsConnectNtdError(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTADisconnectNteClosedPsConnectNtsClosed(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTADisconnectNteClosedPsConnectNteClosed(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

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

  protected fun testFifthConnectTADisconnectNteClosedPsConnectRtConnect(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFifthConnectTADisconnectNteClosedPsConnectPsConnect(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

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

  protected fun testFifthConnectTADisconnectNteClosedPsConnectRsConnect(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

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

  protected fun testFifthConnectTADisconnectNteClosedPsConnectRfConnect(manager: ConnectManager) {
    testQuarterConnectTADisconnectNteClosedPsConnect(manager)

    manager.refreshConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

}