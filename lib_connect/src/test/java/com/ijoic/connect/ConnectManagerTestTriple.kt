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
 * Connect manager test(triple).
 *
 * @author verstsiu on 2018/4/27.
 * @version 1.0
 */
open class ConnectManagerTestTriple: ConnectManagerTestCouple() {

  // Old:
  //
  // STATE          : null    null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :         TRUE
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
  //                  Create -> Connect    -> NtcSuccess
  // STATE          : null      CONNECTING    CONNECT_COMPLETE
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcSuccess       -> Connect, NtcSuccess, NtcError, NtdSuccess, NtdError, RtConnect, RsConnect, RfConnectFF
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    CONNECT_COMPLETE
  // SUCCESS        :                         TRUE                TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> NtsClosed(*,0)
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    RETRY_CONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :                                             0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                                             TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> NteClosed(*,0)
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                             TRUE
  //
  //                  Create -> Connect    -> NtcSuccess       -> RfConnectFT
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :

  @Test fun testTripleConnectNtcSuccessConnect() = testTripleConnectNtcSuccessConnect(ConnectManager())
  @Test fun testTripleConnectNtcSuccessNtcSuccess() = testTripleConnectNtcSuccessNtcSuccess(ConnectManager())
  @Test fun testTripleConnectNtcSuccessNtcError() = testTripleConnectNtcSuccessNtcError(ConnectManager())
  @Test fun testTripleConnectNtcSuccessDisconnect() = testTripleConnectNtcSuccessDisconnect(ConnectManager())
  @Test fun testTripleConnectNtcSuccessNtdSuccess() = testTripleConnectNtcSuccessNtdSuccess(ConnectManager())
  @Test fun testTripleConnectNtcSuccessNtdError() = testTripleConnectNtcSuccessNtdError(ConnectManager())
  @Test fun testTripleConnectNtcSuccessNtsClosed() = testTripleConnectNtcSuccessNtsClosed(ConnectManager())
  @Test fun testTripleConnectNtcSuccessNtsClosedRetry() = testTripleConnectNtcSuccessNtsClosedRetry(createManagerPair())
  @Test fun testTripleConnectNtcSuccessNteClosed() = testTripleConnectNtcSuccessNteClosed(ConnectManager())
  @Test fun testTripleConnectNtcSuccessNteClosedRetry() = testTripleConnectNtcSuccessNteClosedRetry(createManagerPair())
  @Test fun testTripleConnectNtcSuccessRtConnect() = testTripleConnectNtcSuccessRtConnect(ConnectManager())
  @Test fun testTripleConnectNtcSuccessPsConnect() = testTripleConnectNtcSuccessPsConnect(ConnectManager())
  @Test fun testTripleConnectNtcSuccessRsConnect() = testTripleConnectNtcSuccessRsConnect(ConnectManager())
  @Test fun testTripleConnectNtcSuccessRfConnectFF() = testTripleConnectNtcSuccessRfConnectFF(ConnectManager())
  @Test fun testTripleConnectNtcSuccessRfConnectFT() = testTripleConnectNtcSuccessRfConnectFT(ConnectManager())

  protected fun testTripleConnectNtcSuccessConnect(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessNtcSuccess(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessNtcError(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessDisconnect(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

    manager.disconnect()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNtcSuccessNtdSuccess(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessNtdError(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessNtsClosed(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessNtsClosedRetry(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessNteClosed(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessNteClosedRetry(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessRtConnect(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessPsConnect(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

    manager.pauseConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectNtcSuccessRsConnect(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessRfConnectFF(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

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

  protected fun testTripleConnectNtcSuccessRfConnectFT(manager: ConnectManager) {
    testCoupleConnectNtcSuccess(manager)

    manager.refreshConnect(forceConnect = true)
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcError(*,0)
  // STATE          : null      CONNECTING    CONNECT_COMPLETE
  // SUCCESS        :                         FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(*,0)    -> Connect, RtConnect, RfConnectFF, RfConnectFT
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                         FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> NtcSuccess, NtcError, NtdSuccess, NtdError, RsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    CONNECT_COMPLETE
  // SUCCESS        :                         FALSE               FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> Disconnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> NtsClosed(*,0)
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> NteClosed(*,0)
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                             TRUE

  @Test fun testTripleConnectNtcErrorConnect() = testTripleConnectNtcErrorConnect(ConnectManager())
  @Test fun testTripleConnectNtcErrorNtcSuccess() = testTripleConnectNtcErrorNtcSuccess(ConnectManager())
  @Test fun testTripleConnectNtcErrorNtcError() = testTripleConnectNtcErrorNtcError(ConnectManager())
  @Test fun testTripleConnectNtcErrorDisconnect() = testTripleConnectNtcErrorDisconnect(ConnectManager())
  @Test fun testTripleConnectNtcErrorNtdSuccess() = testTripleConnectNtcErrorNtdSuccess(ConnectManager())
  @Test fun testTripleConnectNtcErrorNtdError() = testTripleConnectNtcErrorNtdError(ConnectManager())
  @Test fun testTripleConnectNtcErrorNtsClosed() = testTripleConnectNtcErrorNtsClosed(ConnectManager())
  @Test fun testTripleConnectNtcErrorNteClosed() = testTripleConnectNtcErrorNteClosed(ConnectManager())
  @Test fun testTripleConnectNtcErrorRtConnect() = testTripleConnectNtcErrorRtConnect(ConnectManager())
  @Test fun testTripleConnectNtcErrorPsConnect() = testTripleConnectNtcErrorPsConnect(ConnectManager())
  @Test fun testTripleConnectNtcErrorRsConnect() = testTripleConnectNtcErrorRsConnect(ConnectManager())
  @Test fun testTripleConnectNtcErrorRfConnectFF() = testTripleConnectNtcErrorRfConnectFF(ConnectManager())
  @Test fun testTripleConnectNtcErrorRfConnectFT() = testTripleConnectNtcErrorRfConnectFT(ConnectManager())

  protected fun testTripleConnectNtcErrorConnect(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNtcErrorNtcSuccess(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

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

  protected fun testTripleConnectNtcErrorNtcError(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

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

  protected fun testTripleConnectNtcErrorDisconnect(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

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

  protected fun testTripleConnectNtcErrorNtdSuccess(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

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

  protected fun testTripleConnectNtcErrorNtdError(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

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

  protected fun testTripleConnectNtcErrorNtsClosed(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

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

  protected fun testTripleConnectNtcErrorNteClosed(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

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

  protected fun testTripleConnectNtcErrorRtConnect(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

    manager.retryConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNtcErrorPsConnect(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

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

  protected fun testTripleConnectNtcErrorRsConnect(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

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

  protected fun testTripleConnectNtcErrorRfConnectFF(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

    manager.refreshConnect(forceConnect = false)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNtcErrorRfConnectFT(manager: ConnectManager) {
    testCoupleConnectNtcError(manager)

    manager.refreshConnect(forceConnect = true)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //
  //                  Create -> Connect    -> NtcError(true,1), NtsClosed(true,1), NteClosed(true,1)
  // STATE          : null      CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(true,1) -> Connect, RfConnectFF, RfConnectFT
  // STATE          : null      CONNECTING    RETRY_CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> Disconnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                             TRUE
  // RETRY_COUNT    :                         0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> PsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                             TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                             TRUE

  @Test fun testTripleConnectNtcErrorRetryConnect() = testTripleConnectNtcErrorRetryConnect(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryNtcSuccess() = testTripleConnectNtcErrorRetryNtcSuccess(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryNtcError() = testTripleConnectNtcErrorRetryNtcError(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryDisconnect() = testTripleConnectNtcErrorRetryDisconnect(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryNtdSuccess() = testTripleConnectNtcErrorRetryNtdSuccess(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryNtdError() = testTripleConnectNtcErrorRetryNtdError(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryNtsClosed() = testTripleConnectNtcErrorRetryNtsClosed(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryNteClosed() = testTripleConnectNtcErrorRetryNteClosed(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryRtConnect() = testTripleConnectNtcErrorRetryRtConnect(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryPsConnect() = testTripleConnectNtcErrorRetryPsConnect(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryRsConnect() = testTripleConnectNtcErrorRetryRsConnect(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryRfConnectFF() = testTripleConnectNtcErrorRetryRfConnectFF(createManagerPair())
  @Test fun testTripleConnectNtcErrorRetryRfConnectFT() = testTripleConnectNtcErrorRetryRfConnectFT(createManagerPair())

  protected fun testTripleConnectNtcErrorRetryConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNtcErrorRetryNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

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

  protected fun testTripleConnectNtcErrorRetryNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

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

  protected fun testTripleConnectNtcErrorRetryDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

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

  protected fun testTripleConnectNtcErrorRetryNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

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

  protected fun testTripleConnectNtcErrorRetryNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

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

  protected fun testTripleConnectNtcErrorRetryNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

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

  protected fun testTripleConnectNtcErrorRetryNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

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

  protected fun testTripleConnectNtcErrorRetryRtConnect(pair: Pair<ConnectManager, MockHandler>, s1: ((MockHandler) -> Unit)? = null) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair, s1)

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

  protected fun testTripleConnectNtcErrorRetryPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

    manager.pauseConnect()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(currentState?.retryCount == 0)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectNtcErrorRetryRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

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

  protected fun testTripleConnectNtcErrorRetryRfConnectFF(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

    manager.refreshConnect(forceConnect = false)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNtcErrorRetryRfConnectFT(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testCoupleConnectNtcErrorRetry(pair)

    manager.refreshConnect(forceConnect = true)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //
  //                  Create -> Connect    -> Disconnect
  // STATE          : null      CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> Disconnect    -> Connect
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE                           TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> Disconnect    -> NtcSuccess
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> Disconnect    -> NtcError, NtsClosed
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECT_COMPLETE
  // SUCCESS        :                                          TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> Disconnect    -> Disconnect, NtdSuccess, NtdError, RtConnect, RsConnect, RfConnectFF, RfConnectFT
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE             TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> Disconnect    -> NteClosed
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECT_COMPLETE
  // SUCCESS        :                                          FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> Disconnect    -> PsConnect
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE             TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                          TRUE

  @Test fun testTripleConnectDisconnectConnect() = testTripleConnectDisconnectConnect(ConnectManager())
  @Test fun testTripleConnectDisconnectNtcSuccess() = testTripleConnectDisconnectNtcSuccess(ConnectManager())
  @Test fun testTripleConnectDisconnectNtcError() = testTripleConnectDisconnectNtcError(ConnectManager())
  @Test fun testTripleConnectDisconnectDisconnect() = testTripleConnectDisconnectDisconnect(ConnectManager())
  @Test fun testTripleConnectDisconnectNtdSuccess() = testTripleConnectDisconnectNtdSuccess(ConnectManager())
  @Test fun testTripleConnectDisconnectNtdError() = testTripleConnectDisconnectNtdError(ConnectManager())
  @Test fun testTripleConnectDisconnectNtsClosed() = testTripleConnectDisconnectNtsClosed(ConnectManager())
  @Test fun testTripleConnectDisconnectNteClosed() = testTripleConnectDisconnectNteClosed(ConnectManager())
  @Test fun testTripleConnectDisconnectRtConnect() = testTripleConnectDisconnectRtConnect(ConnectManager())
  @Test fun testTripleConnectDisconnectPsConnect() = testTripleConnectDisconnectPsConnect(ConnectManager())
  @Test fun testTripleConnectDisconnectRsConnect() = testTripleConnectDisconnectRsConnect(ConnectManager())
  @Test fun testTripleConnectDisconnectRfConnectFF() = testTripleConnectDisconnectRfConnectFF(ConnectManager())
  @Test fun testTripleConnectDisconnectRfConnectFT() = testTripleConnectDisconnectRfConnectFT(ConnectManager())

  protected fun testTripleConnectDisconnectConnect(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectDisconnectNtcSuccess(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

    manager.notifyConnectSuccess()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectDisconnectNtcError(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

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

  protected fun testTripleConnectDisconnectDisconnect(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

    manager.disconnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectDisconnectNtdSuccess(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

    manager.notifyDisconnectSuccess()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectDisconnectNtdError(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

    manager.notifyDisconnectError()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectDisconnectNtsClosed(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

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

  protected fun testTripleConnectDisconnectNteClosed(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

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

  protected fun testTripleConnectDisconnectRtConnect(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

    manager.retryConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectDisconnectPsConnect(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

    manager.pauseConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectDisconnectRsConnect(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

    manager.resumeConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectDisconnectRfConnectFF(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

    manager.refreshConnect(forceConnect = false)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectDisconnectRfConnectFT(manager: ConnectManager) {
    testCoupleConnectDisconnect(manager)

    manager.refreshConnect(forceConnect = true)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //
  //                  Create -> Connect    -> NtsClosed(*,0)
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtsClosed(*,0)      -> Connect, RtConnect, RtConnectFF, RtConnectFT
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtsClosed(*,0)      -> NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RsConnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtsClosed(*,0)      -> Disconnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtsClosed(*,0)      -> PsConnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE
  // PAUSED         :                                                TRUE

  @Test fun testTripleConnectNtsClosedConnect() = testTripleConnectNtsClosedConnect(ConnectManager())
  @Test fun testTripleConnectNtsClosedNtcSuccess() = testTripleConnectNtsClosedNtcSuccess(ConnectManager())
  @Test fun testTripleConnectNtsClosedNtcError() = testTripleConnectNtsClosedNtcError(ConnectManager())
  @Test fun testTripleConnectNtsClosedDisconnect() = testTripleConnectNtsClosedDisconnect(ConnectManager())
  @Test fun testTripleConnectNtsClosedNtdSuccess() = testTripleConnectNtsClosedNtdSuccess(ConnectManager())
  @Test fun testTripleConnectNtsClosedNtdError() = testTripleConnectNtsClosedNtdError(ConnectManager())
  @Test fun testTripleConnectNtsClosedNtsClosed() = testTripleConnectNtsClosedNtsClosed(ConnectManager())
  @Test fun testTripleConnectNtsClosedNteClosed() = testTripleConnectNtsClosedNteClosed(ConnectManager())
  @Test fun testTripleConnectNtsClosedRtConnect() = testTripleConnectNtsClosedRtConnect(ConnectManager())
  @Test fun testTripleConnectNtsClosedPsConnect() = testTripleConnectNtsClosedPsConnect(ConnectManager())
  @Test fun testTripleConnectNtsClosedRsConnect() = testTripleConnectNtsClosedRsConnect(ConnectManager())
  @Test fun testTripleConnectNtsClosedRfConnectFF() = testTripleConnectNtsClosedRfConnectFF(ConnectManager())
  @Test fun testTripleConnectNtsClosedRfConnectFT() = testTripleConnectNtsClosedRfConnectFT(ConnectManager())

  protected fun testTripleConnectNtsClosedConnect(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNtsClosedNtcSuccess(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

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

  protected fun testTripleConnectNtsClosedNtcError(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

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

  protected fun testTripleConnectNtsClosedDisconnect(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

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

  protected fun testTripleConnectNtsClosedNtdSuccess(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

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

  protected fun testTripleConnectNtsClosedNtdError(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

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

  protected fun testTripleConnectNtsClosedNtsClosed(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

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

  protected fun testTripleConnectNtsClosedNteClosed(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

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

  protected fun testTripleConnectNtsClosedRtConnect(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

    manager.retryConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNtsClosedPsConnect(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

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

  protected fun testTripleConnectNtsClosedRsConnect(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

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

  protected fun testTripleConnectNtsClosedRfConnectFF(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

    manager.refreshConnect(forceConnect = false)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNtsClosedRfConnectFT(manager: ConnectManager) {
    testCoupleConnectNtsClosed(manager)

    manager.refreshConnect(forceConnect = true)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //
  //                  Create -> Connect    -> NteClosed(*,0)
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NteClosed(*,0)      -> Connect, RtConnect, RfConnectFF, RfConnectFT
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                         FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NteClosed(*,0)      -> NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RsConnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NteClosed(*,0)      -> Disconnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NteClosed(*,0)      -> PsConnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE
  // PAUSED         :                                                TRUE


  @Test fun testTripleConnectNteClosedConnect() = testTripleConnectNteClosedConnect(ConnectManager())
  @Test fun testTripleConnectNteClosedNtcSuccess() = testTripleConnectNteClosedNtcSuccess(ConnectManager())
  @Test fun testTripleConnectNteClosedNtcError() = testTripleConnectNteClosedNtcError(ConnectManager())
  @Test fun testTripleConnectNteClosedDisconnect() = testTripleConnectNteClosedDisconnect(ConnectManager())
  @Test fun testTripleConnectNteClosedNtdSuccess() = testTripleConnectNteClosedNtdSuccess(ConnectManager())
  @Test fun testTripleConnectNteClosedNtdError() = testTripleConnectNteClosedNtdError(ConnectManager())
  @Test fun testTripleConnectNteClosedNtsClosed() = testTripleConnectNteClosedNtsClosed(ConnectManager())
  @Test fun testTripleConnectNteClosedNteClosed() = testTripleConnectNteClosedNteClosed(ConnectManager())
  @Test fun testTripleConnectNteClosedRtConnect() = testTripleConnectNteClosedRtConnect(ConnectManager())
  @Test fun testTripleConnectNteClosedPsConnect() = testTripleConnectNteClosedPsConnect(ConnectManager())
  @Test fun testTripleConnectNteClosedRsConnect() = testTripleConnectNteClosedRsConnect(ConnectManager())
  @Test fun testTripleConnectNteClosedRfConnectFF() = testTripleConnectNteClosedRfConnectFF(ConnectManager())
  @Test fun testTripleConnectNteClosedRfConnectFT() = testTripleConnectNteClosedRfConnectFT(ConnectManager())

  protected fun testTripleConnectNteClosedConnect(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNteClosedNtcSuccess(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

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

  protected fun testTripleConnectNteClosedNtcError(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

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

  protected fun testTripleConnectNteClosedDisconnect(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

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

  protected fun testTripleConnectNteClosedNtdSuccess(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

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

  protected fun testTripleConnectNteClosedNtdError(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

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

  protected fun testTripleConnectNteClosedNtsClosed(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

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

  protected fun testTripleConnectNteClosedNteClosed(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

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

  protected fun testTripleConnectNteClosedRtConnect(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

    manager.retryConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNteClosedPsConnect(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

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

  protected fun testTripleConnectNteClosedRsConnect(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

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

  protected fun testTripleConnectNteClosedRfConnectFF(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

    manager.refreshConnect(forceConnect = false)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectNteClosedRfConnectFT(manager: ConnectManager) {
    testCoupleConnectNteClosed(manager)

    manager.refreshConnect(forceConnect = true)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //
  //                  Create -> Connect    -> PsConnect
  // STATE          : null      CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                         TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> PsConnect  -> Connect, NtdSuccess, NtdError, RtConnect, PsConnect
  // STATE          : null      CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE
  // PAUSED         :                         TRUE          TRUE
  //
  //                  Create -> Connect    -> PsConnect  -> NtcSuccess
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE
  // PAUSED         :                         TRUE          TRUE
  //
  //                  Create -> Connect    -> PsConnect  -> NtcError, NtsClosed
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                       TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE
  // PAUSED         :                         TRUE          TRUE
  //
  //                  Create -> Connect    -> PsConnect  -> Disconnect
  // STATE          : null      CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                       TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                         TRUE          TRUE
  //
  //                  Create -> Connect    -> PsConnect  -> NteClosed
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                       FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE
  // PAUSED         :                         TRUE          TRUE
  //
  //                  Create -> Connect    -> PsConnect  -> RsConnect, RfConnectFF, RfConnectFT
  // STATE          : null      CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE
  // PAUSED         :                         TRUE

  @Test fun testTripleConnectPsConnectConnect() = testTripleConnectPsConnectConnect(ConnectManager())
  @Test fun testTripleConnectPsConnectNtcSuccess() = testTripleConnectPsConnectNtcSuccess(ConnectManager())
  @Test fun testTripleConnectPsConnectNtcError() = testTripleConnectPsConnectNtcError(ConnectManager())
  @Test fun testTripleConnectPsConnectDisconnect() = testTripleConnectPsConnectDisconnect(ConnectManager())
  @Test fun testTripleConnectPsConnectNtdSuccess() = testTripleConnectPsConnectNtdSuccess(ConnectManager())
  @Test fun testTripleConnectPsConnectNtdError() = testTripleConnectPsConnectNtdError(ConnectManager())
  @Test fun testTripleConnectPsConnectNtsClosed() = testTripleConnectPsConnectNtsClosed(ConnectManager())
  @Test fun testTripleConnectPsConnectNteClosed() = testTripleConnectPsConnectNteClosed(ConnectManager())
  @Test fun testTripleConnectPsConnectRtConnect() = testTripleConnectPsConnectRtConnect(ConnectManager())
  @Test fun testTripleConnectPsConnectPsConnect() = testTripleConnectPsConnectPsConnect(ConnectManager())
  @Test fun testTripleConnectPsConnectRsConnect() = testTripleConnectPsConnectRsConnect(ConnectManager())
  @Test fun testTripleConnectPsConnectRfConnectFF() = testTripleConnectPsConnectRfConnectFF(ConnectManager())
  @Test fun testTripleConnectPsConnectRfConnectFT() = testTripleConnectPsConnectRfConnectFT(ConnectManager())

  protected fun testTripleConnectPsConnectConnect(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.connect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectPsConnectNtcSuccess(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.notifyConnectSuccess()
    assert(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectPsConnectNtcError(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    assert(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    assert(currentState?.isSuccess == true)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectPsConnectDisconnect(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.disconnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectPsConnectNtdSuccess(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.notifyDisconnectSuccess()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectPsConnectNtdError(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.notifyDisconnectError()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectPsConnectNtsClosed(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

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

  protected fun testTripleConnectPsConnectNteClosed(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

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

  protected fun testTripleConnectPsConnectRtConnect(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.retryConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectPsConnectPsConnect(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.pauseConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTripleConnectPsConnectRsConnect(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.resumeConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectPsConnectRfConnectFF(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.refreshConnect(forceConnect = false)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTripleConnectPsConnectRfConnectFT(manager: ConnectManager) {
    testCoupleConnectPsConnect(manager)

    manager.refreshConnect(forceConnect = true)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  // Current:
  //
  //                  Create -> PsConnect -> Connect
  // STATE          : null      null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                        TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :                        TRUE
  // PAUSED         :           TRUE         TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> PsConnect -> Connect -> Connect, NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect
  // STATE          : null      null         null       null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                        TRUE       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :                        TRUE       TRUE
  // PAUSED         :           TRUE         TRUE       TRUE
  //
  //                  Create -> PsConnect -> Connect -> Disconnect
  // STATE          : null      null         null       null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                        TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :                        TRUE
  // PAUSED         :           TRUE         TRUE       TRUE
  //
  //                  Create -> PsConnect -> Connect -> RsConnect, RfConnectFF, RfConnectFT
  // STATE          : null      null         null       CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                        TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :                        TRUE       TRUE
  // PAUSED         :           TRUE         TRUE

  @Test fun testTriplePsConnectConnectConnect() = testTriplePsConnectConnectConnect(ConnectManager())
  @Test fun testTriplePsConnectConnectNtcSuccess() = testTriplePsConnectConnectNtcSuccess(ConnectManager())
  @Test fun testTriplePsConnectConnectNtcError() = testTriplePsConnectConnectNtcError(ConnectManager())
  @Test fun testTriplePsConnectConnectDisconnect() = testTriplePsConnectConnectDisconnect(ConnectManager())
  @Test fun testTriplePsConnectConnectNtdSuccess() = testTriplePsConnectConnectNtdSuccess(ConnectManager())
  @Test fun testTriplePsConnectConnectNtdError() = testTriplePsConnectConnectNtdError(ConnectManager())
  @Test fun testTriplePsConnectConnectNtsClosed() = testTriplePsConnectConnectNtsClosed(ConnectManager())
  @Test fun testTriplePsConnectConnectNteClosed() = testTriplePsConnectConnectNteClosed(ConnectManager())
  @Test fun testTriplePsConnectConnectRtConnect() = testTriplePsConnectConnectRtConnect(ConnectManager())
  @Test fun testTriplePsConnectConnectPsConnect() = testTriplePsConnectConnectPsConnect(ConnectManager())
  @Test fun testTriplePsConnectConnectRsConnect() = testTriplePsConnectConnectRsConnect(ConnectManager())
  @Test fun testTriplePsConnectConnectRfConnectFF() = testTriplePsConnectConnectRfConnectFF(ConnectManager())
  @Test fun testTriplePsConnectConnectRfConnectFT() = testTriplePsConnectConnectRfConnectFT(ConnectManager())

  protected fun testTriplePsConnectConnectConnect(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.connect()
    assert(manager.state == null)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectNtcSuccess(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.notifyConnectSuccess()
    assert(manager.state == null)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectNtcError(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.notifyConnectError()
    assert(manager.state == null)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectDisconnect(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.disconnect()
    assert(manager.state == null)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(!manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectNtdSuccess(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.notifyDisconnectSuccess()
    assert(manager.state == null)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectNtdError(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.notifyDisconnectError()
    assert(manager.state == null)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectNtsClosed(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.notifyServerClosed()
    assert(manager.state == null)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectNteClosed(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.notifyErrorClosed()
    assert(manager.state == null)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectRtConnect(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.retryConnect()
    assert(manager.state == null)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectPsConnect(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.pauseConnect()
    assert(manager.state == null)
    assert(manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectRsConnect(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.resumeConnect()
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectRfConnectFF(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.refreshConnect(forceConnect = false)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }

  protected fun testTriplePsConnectConnectRfConnectFT(manager: ConnectManager) {
    testCouplePsConnectConnect(manager)

    manager.refreshConnect(forceConnect = true)
    assert(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    assert(!manager.waitConnect)
    assert(!manager.waitDisconnect)
    assert(!manager.waitRetry)
    assert(manager.connectEnabled)
    assert(!manager.connectPaused)
  }
}