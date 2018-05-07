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
 * @author verstsiu on 2018/4/28.
 * @version 1.0
 */
open class ConnectManagerTestFive: ConnectManagerTestQuarter() {

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
  // STATE          : DISCONNECTING DISCONNECTING DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   : TRUE          TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE          TRUE
  // PAUSED         :               TRUE
  //
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : TRUE                TRUE                TRUE                TRUE                TRUE
  // RETRY_COUNT    :                     0
  // WAIT_CONNECT   :                                         TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                     TRUE
  // ENABLED        :                     TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                     TRUE                TRUE                                    TRUE
  //
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : FALSE               FALSE               FALSE               FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                     TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :                     TRUE                TRUE                TRUE
  // PAUSED         :                     TRUE                                    TRUE
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
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                                                              TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> PsConnect     -> Connect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                                                               TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                                                  TRUE
  // PAUSED         :                                                              TRUE             TRUE
  //
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> PsConnect     -> NtcSuccess, NtcError, Disconnect, RtConnect, PsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                                                              TRUE             TRUE
  //
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> PsConnect     -> NtdSuccess, NtsClosed
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                                                  TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                                                              TRUE             TRUE
  //
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> PsConnect     -> NtdError, NteClosed
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                                                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                                                              TRUE             TRUE
  //
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> PsConnect     -> RsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                                                              TRUE

  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectConnect() = testFiveConnectNtcSuccessDisconnectPsConnectConnect(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectNtcSuccess() = testFiveConnectNtcSuccessDisconnectPsConnectNtcSuccess(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectNtcError() = testFiveConnectNtcSuccessDisconnectPsConnectNtcError(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectDisconnect() = testFiveConnectNtcSuccessDisconnectPsConnectDisconnect(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectNtdSuccess() = testFiveConnectNtcSuccessDisconnectPsConnectNtdSuccess(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectNtdError() = testFiveConnectNtcSuccessDisconnectPsConnectNtdError(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectNtsClosed() = testFiveConnectNtcSuccessDisconnectPsConnectNtsClosed(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectNteClosed() = testFiveConnectNtcSuccessDisconnectPsConnectNteClosed(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectRtConnect() = testFiveConnectNtcSuccessDisconnectPsConnectRtConnect(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectPsConnect() = testFiveConnectNtcSuccessDisconnectPsConnectPsConnect(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectRsConnect() = testFiveConnectNtcSuccessDisconnectPsConnectRsConnect(ConnectManager())
  @Test fun testFiveConnectNtcSuccessDisconnectPsConnectRfConnect() = testFiveConnectNtcSuccessDisconnectPsConnectRfConnect(ConnectManager())

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectConnect(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectNtcSuccess(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

    manager.notifyConnectSuccess()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectNtcError(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

    manager.notifyConnectError()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectDisconnect(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

    manager.disconnect()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectNtdSuccess(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectNtdError(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectNtsClosed(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectNteClosed(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectRtConnect(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

    manager.retryConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectPsConnect(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

    manager.pauseConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectRsConnect(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

    manager.resumeConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFiveConnectNtcSuccessDisconnectPsConnectRfConnect(manager: ConnectManager) {
    testQuarterConnectNtcSuccessDisconnectPsConnect(manager)

    manager.refreshConnect(forceConnect = false)
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcError(*,0)    -> Disconnect          -> PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                                                                    TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(*,0)    -> Disconnect          -> PsConnect           -> Connect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE                   TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                                                                           TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                                                              TRUE
  // PAUSED         :                                                                    TRUE                   TRUE
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> Disconnect          -> PsConnect           -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE                   TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                                                                    TRUE                   TRUE
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> Disconnect          -> PsConnect           -> RsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE                   TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                                                                    TRUE

  @Test fun testFiveConnectNtcErrorDisconnectPsConnectConnect() = testFiveConnectNtcErrorDisconnectPsConnectConnect(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectNtcSuccess() = testFiveConnectNtcErrorDisconnectPsConnectNtcSuccess(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectNtcError() = testFiveConnectNtcErrorDisconnectPsConnectNtcError(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectDisconnect() = testFiveConnectNtcErrorDisconnectPsConnectDisconnect(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectNtdSuccess() = testFiveConnectNtcErrorDisconnectPsConnectNtdSuccess(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectNtdError() = testFiveConnectNtcErrorDisconnectPsConnectNtdError(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectNtsClosed() = testFiveConnectNtcErrorDisconnectPsConnectNtsClosed(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectNteClosed() = testFiveConnectNtcErrorDisconnectPsConnectNteClosed(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectRtConnect() = testFiveConnectNtcErrorDisconnectPsConnectRtConnect(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectPsConnect() = testFiveConnectNtcErrorDisconnectPsConnectPsConnect(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectRsConnect() = testFiveConnectNtcErrorDisconnectPsConnectRsConnect(ConnectManager())
  @Test fun testFiveConnectNtcErrorDisconnectPsConnectRfConnect() = testFiveConnectNtcErrorDisconnectPsConnectRfConnect(ConnectManager())

  protected fun testFiveConnectNtcErrorDisconnectPsConnectConnect(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorDisconnectPsConnectNtcSuccess(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcErrorDisconnectPsConnectNtcError(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcErrorDisconnectPsConnectDisconnect(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcErrorDisconnectPsConnectNtdSuccess(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcErrorDisconnectPsConnectNtdError(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcErrorDisconnectPsConnectNtsClosed(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcErrorDisconnectPsConnectNteClosed(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcErrorDisconnectPsConnectRtConnect(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcErrorDisconnectPsConnectPsConnect(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcErrorDisconnectPsConnectRsConnect(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

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

  protected fun testFiveConnectNtcErrorDisconnectPsConnectRfConnect(manager: ConnectManager) {
    testQuarterConnectNtcErrorDisconnectPsConnect(manager)

    manager.refreshConnect(forceConnect = false)
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
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcSuccess
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                 TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcSuccess             -> Connect, NtcSuccess, NtcError, NtdSuccess, NtdError, RtConnect, RsConnect, RfConnectFF
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                 TRUE                      TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcSuccess             -> Disconnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                                                                 TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcSuccess             -> NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    RETRY_CONNECTING
  // SUCCESS        :                                                                 TRUE
  // RETRY_COUNT    :                         0                   0                                             0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                                                              TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcSuccess             -> NtsClosed(false,1)
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                 TRUE                      TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcSuccess             -> NteClosed(false,1)
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                 TRUE                      FALSE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcSuccess             -> PsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                                                                 TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :                                                                                           TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :                                                                                           TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcSuccess             -> RfConnectFT
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                                                                 TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :                                                                                           TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :

  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessConnect() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtcSuccess() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtcSuccess(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtcError() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtcError(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessDisconnect() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessDisconnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtdSuccess() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtdSuccess(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtdError() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtdError(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtsClosed() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtsClosed(createManagerPair())
  @Test fun testFiveConnectNtcErrorT1RtConnectNtcSuccessNtsClosedF1() = testFiveConnectNtcErrorT1RtConnectNtcSuccessNtsClosedF1(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNteClosed() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessNteClosed(createManagerPair())
  @Test fun testFiveConnectNtcErrorT1RtConnectNtcSuccessNteClosedF1() = testFiveConnectNtcErrorT1RtConnectNtcSuccessNteClosedF1(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessRtConnect() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessRtConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessPsConnect() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessPsConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessRsConnect() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessRsConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessRfConnectFF() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessRfConnectFF(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessRfConnectFT() = testFiveConnectNtcErrorRetryRtConnectNtcSuccessRfConnectFT(createManagerPair())

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorT1RtConnectNtcSuccessNtsClosedF1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair, s1 = { it.connectRequired = true; it.maxRetry = 1 })

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorT1RtConnectNtcSuccessNteClosedF1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair, s1 = { it.connectRequired = true; it.maxRetry = 1 })

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessRfConnectFF(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcSuccessRfConnectFT(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

    manager.refreshConnect(forceConnect = true)
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcError
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                 FALSE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcError               -> Connect, RtConnect, RfConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                                                                 FALSE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcError               -> NtcSuccess, NtcError, NtdSuccess, NtdError, RsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                 FALSE                     FALSE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcError               -> Disconnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                 FALSE                     TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcError               -> NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    RETRY_CONNECTING
  // SUCCESS        :                                                                 FALSE
  // RETRY_COUNT    :                         0                   0                                             0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                                                              TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcError               -> NtsClosed(false,1)
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                 FALSE                     TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcError               -> NteClosed(false,1)
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                 FALSE                     FALSE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcError               -> PsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                 FALSE                     TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :                                                                                           TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                      TRUE
  // PAUSED         :                                                                                           TRUE

  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorConnect() = testFiveConnectNtcErrorRetryRtConnectNtcErrorConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtcSuccess() = testFiveConnectNtcErrorRetryRtConnectNtcErrorNtcSuccess(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtcError() = testFiveConnectNtcErrorRetryRtConnectNtcErrorNtcError(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorDisconnect() = testFiveConnectNtcErrorRetryRtConnectNtcErrorDisconnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtdSuccess() = testFiveConnectNtcErrorRetryRtConnectNtcErrorNtdSuccess(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtdError() = testFiveConnectNtcErrorRetryRtConnectNtcErrorNtdError(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtsClosed() = testFiveConnectNtcErrorRetryRtConnectNtcErrorNtsClosed(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtsClosedF1() = testFiveConnectNtcErrorRetryRtConnectNtcErrorNtsClosedF1(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNteClosed() = testFiveConnectNtcErrorRetryRtConnectNtcErrorNteClosed(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNteClosedF1() = testFiveConnectNtcErrorRetryRtConnectNtcErrorNteClosedF1(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorRtConnect() = testFiveConnectNtcErrorRetryRtConnectNtcErrorRtConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorPsConnect() = testFiveConnectNtcErrorRetryRtConnectNtcErrorPsConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorRsConnect() = testFiveConnectNtcErrorRetryRtConnectNtcErrorRsConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectNtcErrorRfConnect() = testFiveConnectNtcErrorRetryRtConnectNtcErrorRfConnect(createManagerPair())

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNtsClosedF1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair, s1 = { it.connectRequired = true; it.maxRetry = 1 })

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorNteClosedF1(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair, s1 = { it.connectRequired = true; it.maxRetry = 1 })

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectNtcErrorRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorT1RtConnectNtcError(pair)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> Connect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                                    TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> NtcSuccess, NtcError
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> Disconnect, NtdSuccess, NtdError, RtConnect, RsConnect, RfConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE                TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> NtsClosed
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                     TRUE
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> NteClosed
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                     FALSE
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect       -> PsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE                TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                                                                     TRUE

  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectConnect() = testFiveConnectNtcErrorRetryRtConnectDisconnectConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectNtcSuccess() = testFiveConnectNtcErrorRetryRtConnectDisconnectNtcSuccess(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectNtcError() = testFiveConnectNtcErrorRetryRtConnectDisconnectNtcError(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectDisconnect() = testFiveConnectNtcErrorRetryRtConnectDisconnectDisconnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectNtdSuccess() = testFiveConnectNtcErrorRetryRtConnectDisconnectNtdSuccess(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectNtdError() = testFiveConnectNtcErrorRetryRtConnectDisconnectNtdError(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectNtsClosed() = testFiveConnectNtcErrorRetryRtConnectDisconnectNtsClosed(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectNteClosed() = testFiveConnectNtcErrorRetryRtConnectDisconnectNteClosed(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectRtConnect() = testFiveConnectNtcErrorRetryRtConnectDisconnectRtConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect() = testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectRsConnect() = testFiveConnectNtcErrorRetryRtConnectDisconnectRsConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectDisconnectRfConnect() = testFiveConnectNtcErrorRetryRtConnectDisconnectRfConnect(createManagerPair())

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

    manager.disconnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectDisconnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> PsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :                                                                 TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> PsConnect        -> Connect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   [0]
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                 TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> PsConnect        -> NtdSuccess, NtdError, RtConnect, PsConnect, RfConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                 TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> PsConnect        -> NtcSuccess, NtcError
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :                                                                                     TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                 TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> PsConnect        -> Disconnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                                     TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :                                                                 TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> PsConnect        -> NtsClosed
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                     TRUE
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :                                                                                     TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                 TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> PsConnect        -> NteClosed
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                                                     FALSE
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :                                                                                     TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                 TRUE                TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> PsConnect        -> RsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE                TRUE
  // PAUSED         :                                                                 TRUE

  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectConnect() = testFiveConnectNtcErrorRetryRtConnectPsConnectConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectNtcSuccess() = testFiveConnectNtcErrorRetryRtConnectPsConnectNtcSuccess(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectNtcError() = testFiveConnectNtcErrorRetryRtConnectPsConnectNtcError(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectDisconnect() = testFiveConnectNtcErrorRetryRtConnectPsConnectDisconnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectNtdSuccess() = testFiveConnectNtcErrorRetryRtConnectPsConnectNtdSuccess(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectNtdError() = testFiveConnectNtcErrorRetryRtConnectPsConnectNtdError(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectNtsClosed() = testFiveConnectNtcErrorRetryRtConnectPsConnectNtsClosed(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectNteClosed() = testFiveConnectNtcErrorRetryRtConnectPsConnectNteClosed(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectRtConnect() = testFiveConnectNtcErrorRetryRtConnectPsConnectRtConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectPsConnect() = testFiveConnectNtcErrorRetryRtConnectPsConnectPsConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectRsConnect() = testFiveConnectNtcErrorRetryRtConnectPsConnectRsConnect(createManagerPair())
  @Test fun testFiveConnectNtcErrorRetryRtConnectPsConnectRfConnect() = testFiveConnectNtcErrorRetryRtConnectPsConnectRfConnect(createManagerPair())

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

    manager.notifyServerClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

    manager.notifyErrorClosed()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

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

  protected fun testFiveConnectNtcErrorRetryRtConnectPsConnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair)

    manager.refreshConnect(forceConnect = false)
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
  //                  Create -> Connect    -> Disconnect    -> NteClosed           -> PsConnect
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                          FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                                                 TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> Disconnect    -> NteClosed           -> PsConnect           -> Connect
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                          FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                                                                        TRUE
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE                                                                         TRUE
  // PAUSED         :                                                                 TRUE                   TRUE
  //
  //                  Create -> Connect    -> Disconnect    -> NteClosed           -> PsConnect           -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                          FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                                                 TRUE                   TRUE
  //
  //                  Create -> Connect    -> Disconnect    -> NteClosed           -> PsConnect           -> RsConnect
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                          FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                                                 TRUE

  @Test fun testFiveConnectDisconnectNteClosedPsConnectConnect() = testFiveConnectDisconnectNteClosedPsConnectConnect(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectNtcSuccess() = testFiveConnectDisconnectNteClosedPsConnectNtcSuccess(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectNtcError() = testFiveConnectDisconnectNteClosedPsConnectNtcError(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectDisconnect() = testFiveConnectDisconnectNteClosedPsConnectDisconnect(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectNtdSuccess() = testFiveConnectDisconnectNteClosedPsConnectNtdSuccess(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectNtdError() = testFiveConnectDisconnectNteClosedPsConnectNtdError(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectNtsClosed() = testFiveConnectDisconnectNteClosedPsConnectNtsClosed(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectNteClosed() = testFiveConnectDisconnectNteClosedPsConnectNteClosed(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectRtConnect() = testFiveConnectDisconnectNteClosedPsConnectRtConnect(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectPsConnect() = testFiveConnectDisconnectNteClosedPsConnectPsConnect(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectRsConnect() = testFiveConnectDisconnectNteClosedPsConnectRsConnect(ConnectManager())
  @Test fun testFiveConnectDisconnectNteClosedPsConnectRfConnect() = testFiveConnectDisconnectNteClosedPsConnectRfConnect(ConnectManager())

  protected fun testFiveConnectDisconnectNteClosedPsConnectConnect(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

    manager.connect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == false)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testFiveConnectDisconnectNteClosedPsConnectNtcSuccess(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

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

  protected fun testFiveConnectDisconnectNteClosedPsConnectNtcError(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

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

  protected fun testFiveConnectDisconnectNteClosedPsConnectDisconnect(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

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

  protected fun testFiveConnectDisconnectNteClosedPsConnectNtdSuccess(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

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

  protected fun testFiveConnectDisconnectNteClosedPsConnectNtdError(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

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

  protected fun testFiveConnectDisconnectNteClosedPsConnectNtsClosed(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

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

  protected fun testFiveConnectDisconnectNteClosedPsConnectNteClosed(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

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

  protected fun testFiveConnectDisconnectNteClosedPsConnectRtConnect(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

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

  protected fun testFiveConnectDisconnectNteClosedPsConnectPsConnect(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

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

  protected fun testFiveConnectDisconnectNteClosedPsConnectRsConnect(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

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

  protected fun testFiveConnectDisconnectNteClosedPsConnectRfConnect(manager: ConnectManager) {
    testQuarterConnectDisconnectNteClosedPsConnect(manager)

    manager.refreshConnect(forceConnect = false)
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