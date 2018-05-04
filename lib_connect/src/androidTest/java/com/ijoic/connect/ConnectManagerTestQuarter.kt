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

import junit.framework.Assert
import org.junit.Test

/**
 * Connect manager test(quarter).
 *
 * @author verstsiu on 2018/4/28.
 * @version 1.0
 */
open class ConnectManagerTestQuarter: ConnectManagerTestTriple() {

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
  // ENABLED        :             TRUE        TRUE        TRUE
  // PAUSED         :                                     TRUE
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
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : TRUE                FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE                TRUE
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
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> Connect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                                              TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                                 TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> NtcSuccess, NtcError, Disconnect, RtConnect, RsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> NtdSuccess, NtsClosed
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                                 TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> NtdError, NteClosed
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                                 FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> Disconnect    -> PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                                                              TRUE

  @Test fun testQuarterConnectNtcSuccessDisconnectConnect() = testQuarterConnectNtcSuccessDisconnectConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectNtcSuccess() = testQuarterConnectNtcSuccessDisconnectNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectNtcError() = testQuarterConnectNtcSuccessDisconnectNtcError(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectDisconnect() = testQuarterConnectNtcSuccessDisconnectDisconnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectNtdSuccess() = testQuarterConnectNtcSuccessDisconnectNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectNtdError() = testQuarterConnectNtcSuccessDisconnectNtdError(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectNtsClosed() = testQuarterConnectNtcSuccessDisconnectNtsClosed(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectNteClosed() = testQuarterConnectNtcSuccessDisconnectNteClosed(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectRtConnect() = testQuarterConnectNtcSuccessDisconnectRtConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectPsConnect() = testQuarterConnectNtcSuccessDisconnectPsConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectRsConnect() = testQuarterConnectNtcSuccessDisconnectRsConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessDisconnectRfConnect() = testQuarterConnectNtcSuccessDisconnectRfConnect(ConnectManager())

  protected fun testQuarterConnectNtcSuccessDisconnectConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.connect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessDisconnectNtcSuccess(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.notifyConnectSuccess()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessDisconnectNtcError(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.notifyConnectError()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessDisconnectDisconnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.disconnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessDisconnectNtdSuccess(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessDisconnectNtdError(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessDisconnectNtsClosed(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessDisconnectNteClosed(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessDisconnectRtConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.retryConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessDisconnectPsConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.pauseConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessDisconnectRsConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.resumeConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }


  protected fun testQuarterConnectNtcSuccessDisconnectRfConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessDisconnect(manager)

    manager.refreshConnect(forceConnect = false)
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcSuccess       -> PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                             TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcSuccess       -> PsConnect     -> Connect, NtcSuccess, NtcError, RtConnect, PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE             TRUE
  // PAUSED         :                                             TRUE             TRUE
  //
  //                  Create -> Connect    -> NtcSuccess       -> PsConnect     -> Disconnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                             TRUE             TRUE
  //
  //                  Create -> Connect    -> NtcSuccess       -> PsConnect     -> NtdSuccess, NtsClosed
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                                 TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE             TRUE
  // PAUSED         :                                             TRUE             TRUE
  //
  //                  Create -> Connect    -> NtcSuccess       -> PsConnect     -> NtdError, NteClosed
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                         TRUE                                 FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE             TRUE
  // PAUSED         :                                             TRUE             TRUE
  //
  //                  Create -> Connect    -> NtcSuccess       -> PsConnect     -> RsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE             TRUE
  // PAUSED         :                                             TRUE

  @Test fun testQuarterConnectNtcSuccessPsConnectConnect() = testQuarterConnectNtcSuccessPsConnectConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectNtcSuccess() = testQuarterConnectNtcSuccessPsConnectNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectNtcError() = testQuarterConnectNtcSuccessPsConnectNtcError(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectDisconnect() = testQuarterConnectNtcSuccessPsConnectDisconnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectNtdSuccess() = testQuarterConnectNtcSuccessPsConnectNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectNtdError() = testQuarterConnectNtcSuccessPsConnectNtdError(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectNtsClosed() = testQuarterConnectNtcSuccessPsConnectNtsClosed(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectNteClosed() = testQuarterConnectNtcSuccessPsConnectNteClosed(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectRtConnect() = testQuarterConnectNtcSuccessPsConnectRtConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectPsConnect() = testQuarterConnectNtcSuccessPsConnectPsConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectRsConnect() = testQuarterConnectNtcSuccessPsConnectRsConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessPsConnectRfConnect() = testQuarterConnectNtcSuccessPsConnectRfConnect(ConnectManager())

  protected fun testQuarterConnectNtcSuccessPsConnectConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.connect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectNtcSuccess(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.notifyConnectSuccess()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectNtcError(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.notifyConnectError()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectDisconnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.disconnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectNtdSuccess(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectNtdError(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectNtsClosed(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectNteClosed(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectRtConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.retryConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectPsConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.pauseConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectRsConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.resumeConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessPsConnectRfConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessPsConnect(manager)

    manager.refreshConnect(forceConnect = false)
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcSuccess       -> RfConnectFT
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcSuccess       -> RfConnectFT   -> Connect, NtcSuccess, NtcError, RtConnect, RsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE             TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> RfConnectFT   -> Disconnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> RfConnectFT   -> NtdSuccess, NtdError, NtsClosed, NteClosed
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    CONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE             TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess       -> RfConnectFT   -> PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECTING    DISCONNECTING
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                             TRUE             TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE             TRUE
  // PAUSED         :                                                              TRUE

  @Test fun testQuarterConnectNtcSuccessRfConnectFTConnect() = testQuarterConnectNtcSuccessRfConnectFTConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTNtcSuccess() = testQuarterConnectNtcSuccessRfConnectFTNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTNtcError() = testQuarterConnectNtcSuccessRfConnectFTNtcError(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTDisconnect() = testQuarterConnectNtcSuccessRfConnectFTDisconnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTNtdSuccess() = testQuarterConnectNtcSuccessRfConnectFTNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTNtdError() = testQuarterConnectNtcSuccessRfConnectFTNtdError(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTNtsClosed() = testQuarterConnectNtcSuccessRfConnectFTNtsClosed(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTNteClosed() = testQuarterConnectNtcSuccessRfConnectFTNteClosed(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTRtConnect() = testQuarterConnectNtcSuccessRfConnectFTRtConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTPsConnect() = testQuarterConnectNtcSuccessRfConnectFTPsConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTRsConnect() = testQuarterConnectNtcSuccessRfConnectFTRsConnect(ConnectManager())
  @Test fun testQuarterConnectNtcSuccessRfConnectFTRfConnect() = testQuarterConnectNtcSuccessRfConnectFTRfConnect(ConnectManager())

  protected fun testQuarterConnectNtcSuccessRfConnectFTConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.connect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTNtcSuccess(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.notifyConnectSuccess()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTNtcError(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.notifyConnectError()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTDisconnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.disconnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTNtdSuccess(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.notifyDisconnectSuccess()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTNtdError(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.notifyDisconnectError()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTNtsClosed(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.notifyServerClosed()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTNteClosed(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.notifyErrorClosed()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTRtConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.retryConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTPsConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.pauseConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTRsConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.resumeConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcSuccessRfConnectFTRfConnect(manager: ConnectManager) {
    testTripleConnectNtcSuccessRfConnectFT(manager)

    manager.refreshConnect(forceConnect = false)
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcError(*,0)    -> Disconnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(*,0)    -> Disconnect          -> Connect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                         FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                                       TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> Disconnect          -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, RsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> Disconnect          -> PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :                                                                    TRUE

  @Test fun testQuarterConnectNtcErrorDisconnectConnect() = testQuarterConnectNtcErrorDisconnectConnect(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectNtcSuccess() = testQuarterConnectNtcErrorDisconnectNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectNtcError() = testQuarterConnectNtcErrorDisconnectNtcError(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectDisconnect() = testQuarterConnectNtcErrorDisconnectDisconnect(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectNtdSuccess() = testQuarterConnectNtcErrorDisconnectNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectNtdError() = testQuarterConnectNtcErrorDisconnectNtdError(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectNtsClosed() = testQuarterConnectNtcErrorDisconnectNtsClosed(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectNteClosed() = testQuarterConnectNtcErrorDisconnectNteClosed(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectRtConnect() = testQuarterConnectNtcErrorDisconnectRtConnect(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectPsConnect() = testQuarterConnectNtcErrorDisconnectPsConnect(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectRsConnect() = testQuarterConnectNtcErrorDisconnectRsConnect(ConnectManager())
  @Test fun testQuarterConnectNtcErrorDisconnectRfConnect() = testQuarterConnectNtcErrorDisconnectRfConnect(ConnectManager())

  protected fun testQuarterConnectNtcErrorDisconnectConnect(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.connect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectNtcSuccess(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectNtcError(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectDisconnect(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.disconnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectNtdSuccess(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectNtdError(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectNtsClosed(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectNteClosed(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectRtConnect(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectPsConnect(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.pauseConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectRsConnect(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.resumeConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorDisconnectRfConnect(manager: ConnectManager) {
    testTripleConnectNtcErrorDisconnect(manager)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcError(*,0)    -> PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                             TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(*,0)    -> PsConnect           -> Connect, RtConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                                                    TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                             TRUE                   TRUE
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> PsConnect           -> NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, PsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                             TRUE                   TRUE
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> PsConnect           -> Disconnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                             TRUE                   TRUE
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> PsConnect           -> RsConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE               TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                             TRUE
  //
  //                  Create -> Connect    -> NtcError(*,0)    -> PsConnect           -> RfConnect
  // STATE          : null      CONNECTING    CONNECT_COMPLETE    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                         FALSE               TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                             TRUE

  @Test fun testQuarterConnectNtcErrorPsConnectConnect() = testQuarterConnectNtcErrorPsConnectConnect(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectNtcSuccess() = testQuarterConnectNtcErrorPsConnectNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectNtcError() = testQuarterConnectNtcErrorPsConnectNtcError(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectDisconnect() = testQuarterConnectNtcErrorPsConnectDisconnect(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectNtdSuccess() = testQuarterConnectNtcErrorPsConnectNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectNtdError() = testQuarterConnectNtcErrorPsConnectNtdError(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectNtsClosed() = testQuarterConnectNtcErrorPsConnectNtsClosed(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectNteClosed() = testQuarterConnectNtcErrorPsConnectNteClosed(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectRtConnect() = testQuarterConnectNtcErrorPsConnectRtConnect(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectPsConnect() = testQuarterConnectNtcErrorPsConnectPsConnect(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectRsConnect() = testQuarterConnectNtcErrorPsConnectRsConnect(ConnectManager())
  @Test fun testQuarterConnectNtcErrorPsConnectRfConnect() = testQuarterConnectNtcErrorPsConnectRfConnect(ConnectManager())

  protected fun testQuarterConnectNtcErrorPsConnectConnect(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.connect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectNtcSuccess(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectNtcError(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectDisconnect(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.disconnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectNtdSuccess(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectNtdError(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectNtsClosed(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectNteClosed(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectRtConnect(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectPsConnect(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.pauseConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectRsConnect(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.resumeConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorPsConnectRfConnect(manager: ConnectManager) {
    testTripleConnectNtcErrorPsConnect(manager)

    manager.refreshConnect(forceConnect = false)
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Connect, NtdSuccess, NtdError, RtConnect, RsConnect, RfConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcSuccess
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                 TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> NtcError, NtsClosed, NteClosed
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                 FALSE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,-1) -> RtConnect       -> NtcError(true,-1), NtsClosed(true,-1), NteClosed(true,-1)
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                                    TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,-1) -> RtConnect       -> NtcError(false,-1), NtcError(false,2), NtsClosed(false,-1), NtsClosed(false,2), NteClosed(false,-1), NteClosed(false,2)
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECT_COMPLETE
  // SUCCESS        :                                                                 FALSE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,2) -> RtConnect        -> NtcError(true,2), NtsClosed(true,2), NteClosed(true,2)
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   1
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                                    TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> Disconnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                                                                 TRUE
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(true,1) -> RtConnect        -> PsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING    RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :                         0                   0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                TRUE
  // PAUSED         :                                                                 TRUE

  @Test fun testQuarterConnectNtcErrorRetryRtConnectConnect() = testQuarterConnectNtcErrorRetryRtConnectConnect(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryRtConnectNtcSuccess() = testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(createManagerPair())
  @Test fun testQuarterConnectNtcErrorT1RtConnectNtcError() = testQuarterConnectNtcErrorT1RtConnectNtcError(createManagerPair())
  @Test fun testQuarterConnectNtcErrorTNRtConnectNtcErrorTN() = testQuarterConnectNtcErrorTNRtConnectNtcErrorTN(createManagerPair())
  @Test fun testQuarterConnectNtcErrorTNRtConnectNtcErrorFN() = testQuarterConnectNtcErrorTNRtConnectNtcErrorFN(createManagerPair())
  @Test fun testQuarterConnectNtcErrorT2RtConnectNtcErrorT2() = testQuarterConnectNtcErrorT2RtConnectNtcErrorT2(createManagerPair())
  @Test fun testQuarterConnectNtcErrorT2RtConnectNtcErrorF2() = testQuarterConnectNtcErrorT2RtConnectNtcErrorF2(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryRtConnectDisconnect() = testQuarterConnectNtcErrorRetryRtConnectDisconnect(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryRtConnectNtdSuccess() = testQuarterConnectNtcErrorRetryRtConnectNtdSuccess(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryRtConnectNtdError() = testQuarterConnectNtcErrorRetryRtConnectNtdError(createManagerPair())
  @Test fun testQuarterConnectNtcErrorT1RtConnectNtsClosed() = testQuarterConnectNtcErrorT1RtConnectNtsClosed(createManagerPair())
  @Test fun testQuarterConnectNtcErrorTNRtConnectNtsClosedTN() = testQuarterConnectNtcErrorTNRtConnectNtsClosedTN(createManagerPair())
  @Test fun testQuarterConnectNtcErrorTNRtConnectNtsClosedFN() = testQuarterConnectNtcErrorTNRtConnectNtsClosedFN(createManagerPair())
  @Test fun testQuarterConnectNtcErrorT2RtConnectNtsClosedT2() = testQuarterConnectNtcErrorT2RtConnectNtsClosedT2(createManagerPair())
  @Test fun testQuarterConnectNtcErrorT2RtConnectNtsClosedF2() = testQuarterConnectNtcErrorT2RtConnectNtsClosedF2(createManagerPair())
  @Test fun testQuarterConnectNtcErrorT1RtConnectNteClosed() = testQuarterConnectNtcErrorT1RtConnectNteClosed(createManagerPair())
  @Test fun testQuarterConnectNtcErrorTNRtConnectNteClosedTN() = testQuarterConnectNtcErrorTNRtConnectNteClosedTN(createManagerPair())
  @Test fun testQuarterConnectNtcErrorTNRtConnectNteClosedFN() = testQuarterConnectNtcErrorTNRtConnectNteClosedFN(createManagerPair())
  @Test fun testQuarterConnectNtcErrorT2RtConnectNteClosedT2() = testQuarterConnectNtcErrorT2RtConnectNteClosedT2(createManagerPair())
  @Test fun testQuarterConnectNtcErrorT2RtConnectNteClosedF2() = testQuarterConnectNtcErrorT2RtConnectNteClosedF2(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryRtConnectRtConnect() = testQuarterConnectNtcErrorRetryRtConnectRtConnect(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryRtConnectPsConnect() = testQuarterConnectNtcErrorRetryRtConnectPsConnect(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryRtConnectRsConnect() = testQuarterConnectNtcErrorRetryRtConnectRsConnect(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryRtConnectRfConnect() = testQuarterConnectNtcErrorRetryRtConnectRfConnect(createManagerPair())

  protected fun testQuarterConnectNtcErrorRetryRtConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair)

    manager.connect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryRtConnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>, s1: ((MockHandler) -> Unit)? = null) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair, s1)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorT1RtConnectNtcError(pair: Pair<ConnectManager, MockHandler>, s1: ((MockHandler) -> Unit)? = null) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = s1 ?: { it.connectRequired = true; it.maxRetry = 1 })

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorTNRtConnectNtcErrorTN(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = -1 })

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorTNRtConnectNtcErrorFN(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = -1 })

    handler.connectRequired = false

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorT2RtConnectNtcErrorT2(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = 2 })

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 1)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorT2RtConnectNtcErrorF2(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = 2 })

    handler.connectRequired = false

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryRtConnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair)

    manager.disconnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryRtConnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryRtConnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorT1RtConnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = 1 })

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorTNRtConnectNtsClosedTN(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = -1 })

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorTNRtConnectNtsClosedFN(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = -1 })

    handler.connectRequired = false

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorT2RtConnectNtsClosedT2(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = 2 })

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 1)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorT2RtConnectNtsClosedF2(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = 2 })

    handler.connectRequired = false

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorT1RtConnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = 1 })

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorTNRtConnectNteClosedTN(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = -1 })

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorTNRtConnectNteClosedFN(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = -1 })

    handler.connectRequired = false

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorT2RtConnectNteClosedT2(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = 2 })

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 1)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorT2RtConnectNteClosedF2(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testTripleConnectNtcErrorRetryRtConnect(pair, s1 = { it.connectRequired = true; it.maxRetry = 2 })

    handler.connectRequired = false

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryRtConnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryRtConnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair)

    manager.pauseConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryRtConnectRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair)

    manager.resumeConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryRtConnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryRtConnect(pair)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NtcError(true,1) -> PsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                             TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                             TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NtcError(true,1) -> PsConnect           -> Connect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                             TRUE                   TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :                                                                    TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                             TRUE                   TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> PsConnect           -> NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                             TRUE                   TRUE
  // RETRY_COUNT    :                         0                   0                      0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                TRUE                   TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                             TRUE                   TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> PsConnect           -> Disconnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                             TRUE                   TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                TRUE
  // ENABLED        :           TRUE          TRUE                TRUE
  // PAUSED         :                                             TRUE                   TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> PsConnect           -> RsConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE    RETRY_CONNECTING
  // SUCCESS        :                                             TRUE
  // RETRY_COUNT    :                         0                   0                      0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                TRUE                   TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                             TRUE
  //
  //                  Create -> Connect    -> NtcError(true,1) -> PsConnect           -> RfConnect
  // STATE          : null      CONNECTING    RETRY_CONNECTING    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                                             TRUE
  // RETRY_COUNT    :                         0                   0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :                         TRUE                TRUE
  // ENABLED        :           TRUE          TRUE                TRUE                   TRUE
  // PAUSED         :                                             TRUE

  @Test fun testQuarterConnectNtcErrorRetryPsConnectConnect() = testQuarterConnectNtcErrorRetryPsConnectConnect(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectNtcSuccess() = testQuarterConnectNtcErrorRetryPsConnectNtcSuccess(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectNtcError() = testQuarterConnectNtcErrorRetryPsConnectNtcError(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectDisconnect() = testQuarterConnectNtcErrorRetryPsConnectDisconnect(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectNtdSuccess() = testQuarterConnectNtcErrorRetryPsConnectNtdSuccess(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectNtdError() = testQuarterConnectNtcErrorRetryPsConnectNtdError(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectNtsClosed() = testQuarterConnectNtcErrorRetryPsConnectNtsClosed(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectNteClosed() = testQuarterConnectNtcErrorRetryPsConnectNteClosed(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectRtConnect() = testQuarterConnectNtcErrorRetryPsConnectRtConnect(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectPsConnect() = testQuarterConnectNtcErrorRetryPsConnectPsConnect(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectRsConnect() = testQuarterConnectNtcErrorRetryPsConnectRsConnect(createManagerPair())
  @Test fun testQuarterConnectNtcErrorRetryPsConnectRfConnect() = testQuarterConnectNtcErrorRetryPsConnectRfConnect(createManagerPair())

  protected fun testQuarterConnectNtcErrorRetryPsConnectConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.connect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectNtcSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectNtcError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectDisconnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.disconnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectNtdSuccess(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectNtdError(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectNtsClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectNteClosed(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectRtConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.retryConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectPsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.pauseConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectRsConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.resumeConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_RETRY_CONNECTING)
    Assert.assertTrue(currentState?.retryCount == 0)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNtcErrorRetryPsConnectRfConnect(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    testTripleConnectNtcErrorRetryPsConnect(pair)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> Disconnect    -> NteClosed
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECT_COMPLETE
  // SUCCESS        :                                          FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> Disconnect    -> NteClosed           -> Connect
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                                          FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE                                                  TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> Disconnect    -> NteClosed           -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, RsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                          FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> Disconnect    -> NteClosed           -> PsConnect
  // STATE          : null      CONNECTING    CONNECTING       DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                          FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                                                 TRUE

  @Test fun testQuarterConnectDisconnectNteClosedConnect() = testQuarterConnectDisconnectNteClosedConnect(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedNtcSuccess() = testQuarterConnectDisconnectNteClosedNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedNtcError() = testQuarterConnectDisconnectNteClosedNtcError(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedDisconnect() = testQuarterConnectDisconnectNteClosedDisconnect(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedNtdSuccess() = testQuarterConnectDisconnectNteClosedNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedNtdError() = testQuarterConnectDisconnectNteClosedNtdError(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedNtsClosed() = testQuarterConnectDisconnectNteClosedNtsClosed(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedNteClosed() = testQuarterConnectDisconnectNteClosedNteClosed(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedRtConnect() = testQuarterConnectDisconnectNteClosedRtConnect(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedPsConnect() = testQuarterConnectDisconnectNteClosedPsConnect(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedRsConnect() = testQuarterConnectDisconnectNteClosedRsConnect(ConnectManager())
  @Test fun testQuarterConnectDisconnectNteClosedRfConnect() = testQuarterConnectDisconnectNteClosedRfConnect(ConnectManager())

  protected fun testQuarterConnectDisconnectNteClosedConnect(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.connect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedNtcSuccess(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedNtcError(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedDisconnect(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.disconnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedNtdSuccess(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedNtdError(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedNtsClosed(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedNteClosed(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedRtConnect(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.retryConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedPsConnect(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.pauseConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedRsConnect(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.resumeConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectNteClosedRfConnect(manager: ConnectManager) {
    testTripleConnectDisconnectNteClosed(manager)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> Disconnect    -> PsConnect
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE             TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                          TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> Disconnect    -> PsConnect  -> Connect
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE             TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE                                         TRUE
  // PAUSED         :                                          TRUE          TRUE
  //
  //                  Create -> Connect    -> Disconnect    -> PsConnect  -> NtcSuccess
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE             TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                          TRUE          TRUE
  //
  //                  Create -> Connect    -> Disconnect    -> PsConnect  -> NtcError
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE             TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                          TRUE          TRUE
  //
  //                  Create -> Connect    -> Disconnect    -> PsConnect  -> Disconnect, NtdSuccess, NtdError, RtConnect, PsConnect
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE             TRUE          TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                          TRUE          TRUE
  //
  //                  Create -> Connect    -> Disconnect    -> PsConnect  -> NtsClosed
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE             TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                          TRUE          TRUE
  //
  //                  Create -> Connect    -> Disconnect    -> PsConnect  -> NteClosed
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                                        FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE             TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                          TRUE          TRUE
  //
  //                  Create -> Connect    -> Disconnect    -> PsConnect  -> RsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECTING       CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                         TRUE             TRUE          TRUE
  // WAIT_RETRY     :
  // ENABLED        :           TRUE
  // PAUSED         :                                          TRUE

  @Test fun testQuarterConnectDisconnectPsConnectConnect() = testQuarterConnectDisconnectPsConnectConnect(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectNtcSuccess() = testQuarterConnectDisconnectPsConnectNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectNtcError() = testQuarterConnectDisconnectPsConnectNtcError(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectDisconnect() = testQuarterConnectDisconnectPsConnectDisconnect(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectNtdSuccess() = testQuarterConnectDisconnectPsConnectNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectNtdError() = testQuarterConnectDisconnectPsConnectNtdError(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectNtsClosed() = testQuarterConnectDisconnectPsConnectNtsClosed(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectNteClosed() = testQuarterConnectDisconnectPsConnectNteClosed(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectRtConnect() = testQuarterConnectDisconnectPsConnectRtConnect(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectPsConnect() = testQuarterConnectDisconnectPsConnectPsConnect(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectRsConnect() = testQuarterConnectDisconnectPsConnectRsConnect(ConnectManager())
  @Test fun testQuarterConnectDisconnectPsConnectRfConnect() = testQuarterConnectDisconnectPsConnectRfConnect(ConnectManager())

  protected fun testQuarterConnectDisconnectPsConnectConnect(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.connect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectNtcSuccess(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectNtcError(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectDisconnect(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.disconnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectNtdSuccess(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectNtdError(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectNtsClosed(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectNteClosed(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectRtConnect(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectPsConnect(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.pauseConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectRsConnect(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.resumeConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectDisconnectPsConnectRfConnect(manager: ConnectManager) {
    testTripleConnectDisconnectPsConnect(manager)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> NteClosed(*,0)      -> PsConnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE
  // PAUSED         :                                                TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> NteClosed(*,0)      -> PsConnect           -> Connect, RtConnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                                                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE                   TRUE
  // PAUSED         :                                                TRUE                   TRUE
  //
  //                  Create -> Connect    -> NteClosed(*,0)      -> PsConnect           -> NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, PsConnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE                   TRUE
  // PAUSED         :                                                TRUE                   TRUE
  //
  //                  Create -> Connect    -> NteClosed(*,0)      -> PsConnect           -> Disconnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE
  // PAUSED         :                                                TRUE                   TRUE
  //
  //                  Create -> Connect    -> NteClosed(*,0)      -> PsConnect           -> RsConnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                         FALSE                  FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE                   TRUE
  // PAUSED         :                                                TRUE
  //
  //                  Create -> Connect    -> NteClosed(*,0)      -> PsConnect           -> RfConnect
  // STATE          : null      CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                         FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE                   TRUE                   TRUE
  // PAUSED         :                                                TRUE

  @Test fun testQuarterConnectNteClosedPsConnectConnect() = testQuarterConnectNteClosedPsConnectConnect(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectNtcSuccess() = testQuarterConnectNteClosedPsConnectNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectNtcError() = testQuarterConnectNteClosedPsConnectNtcError(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectDisconnect() = testQuarterConnectNteClosedPsConnectDisconnect(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectNtdSuccess() = testQuarterConnectNteClosedPsConnectNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectNtdError() = testQuarterConnectNteClosedPsConnectNtdError(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectNtsClosed() = testQuarterConnectNteClosedPsConnectNtsClosed(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectNteClosed() = testQuarterConnectNteClosedPsConnectNteClosed(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectRtConnect() = testQuarterConnectNteClosedPsConnectRtConnect(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectPsConnect() = testQuarterConnectNteClosedPsConnectPsConnect(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectRsConnect() = testQuarterConnectNteClosedPsConnectRsConnect(ConnectManager())
  @Test fun testQuarterConnectNteClosedPsConnectRfConnect() = testQuarterConnectNteClosedPsConnectRfConnect(ConnectManager())

  protected fun testQuarterConnectNteClosedPsConnectConnect(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.connect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectNtcSuccess(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectNtcError(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectDisconnect(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.disconnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectNtdSuccess(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectNtdError(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectNtsClosed(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectNteClosed(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectRtConnect(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.retryConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectPsConnect(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.pauseConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectRsConnect(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.resumeConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectNteClosedPsConnectRfConnect(manager: ConnectManager) {
    testTripleConnectNteClosedPsConnect(manager)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> PsConnect  -> NtcError
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                       TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE
  // PAUSED         :                         TRUE          TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> PsConnect  -> NtcError            -> Connect, NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                       TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE                   TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE                   TRUE
  // PAUSED         :                         TRUE          TRUE                   TRUE
  //
  //                  Create -> Connect    -> PsConnect  -> NtcError            -> Disconnect
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                       TRUE                   TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE
  // PAUSED         :                         TRUE          TRUE                   TRUE
  //
  //                  Create -> Connect    -> PsConnect  -> NtcError            -> RsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                                       TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE                   TRUE
  // PAUSED         :                         TRUE          TRUE

  @Test fun testQuarterConnectPsConnectNtcErrorConnect() = testQuarterConnectPsConnectNtcErrorConnect(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorNtcSuccess() = testQuarterConnectPsConnectNtcErrorNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorNtcError() = testQuarterConnectPsConnectNtcErrorNtcError(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorDisconnect() = testQuarterConnectPsConnectNtcErrorDisconnect(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorNtdSuccess() = testQuarterConnectPsConnectNtcErrorNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorNtdError() = testQuarterConnectPsConnectNtcErrorNtdError(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorNtsClosed() = testQuarterConnectPsConnectNtcErrorNtsClosed(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorNteClosed() = testQuarterConnectPsConnectNtcErrorNteClosed(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorRtConnect() = testQuarterConnectPsConnectNtcErrorRtConnect(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorPsConnect() = testQuarterConnectPsConnectNtcErrorPsConnect(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorRsConnect() = testQuarterConnectPsConnectNtcErrorRsConnect(ConnectManager())
  @Test fun testQuarterConnectPsConnectNtcErrorRfConnect() = testQuarterConnectPsConnectNtcErrorRfConnect(ConnectManager())

  protected fun testQuarterConnectPsConnectNtcErrorConnect(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.connect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorNtcSuccess(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorNtcError(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorDisconnect(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.disconnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorNtdSuccess(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorNtdError(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorNtsClosed(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorNteClosed(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorRtConnect(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.retryConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorPsConnect(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.pauseConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorRsConnect(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.resumeConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNtcErrorRfConnect(manager: ConnectManager) {
    testTripleConnectPsConnectNtcError(manager)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  // Current:
  //                  Create -> Connect    -> PsConnect  -> NteClosed
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                       FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE
  // PAUSED         :                         TRUE          TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:
  //                  Create -> Connect    -> PsConnect  -> NteClosed           -> Connect, NtcSuccess, NtcError, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                       FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE                   TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE                   TRUE
  // PAUSED         :                         TRUE          TRUE                   TRUE
  //
  //                  Create -> Connect    -> PsConnect  -> NteClosed           -> Disconnect
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECT_COMPLETE    DISCONNECT_COMPLETE
  // SUCCESS        :                                       FALSE                  FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE
  // PAUSED         :                         TRUE          TRUE                   TRUE
  //
  //                  Create -> Connect    -> PsConnect  -> NtcError            -> RsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECTING    DISCONNECT_COMPLETE    CONNECTING
  // SUCCESS        :                                       FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                       TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE          TRUE                   TRUE
  // PAUSED         :                         TRUE          TRUE

  @Test fun testQuarterConnectPsConnectNteClosedConnect() = testQuarterConnectPsConnectNteClosedConnect(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedNtcSuccess() = testQuarterConnectPsConnectNteClosedNtcSuccess(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedNtcError() = testQuarterConnectPsConnectNteClosedNtcError(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedDisconnect() = testQuarterConnectPsConnectNteClosedDisconnect(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedNtdSuccess() = testQuarterConnectPsConnectNteClosedNtdSuccess(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedNtdError() = testQuarterConnectPsConnectNteClosedNtdError(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedNtsClosed() = testQuarterConnectPsConnectNteClosedNtsClosed(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedNteClosed() = testQuarterConnectPsConnectNteClosedNteClosed(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedRtConnect() = testQuarterConnectPsConnectNteClosedRtConnect(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedPsConnect() = testQuarterConnectPsConnectNteClosedPsConnect(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedRsConnect() = testQuarterConnectPsConnectNteClosedRsConnect(ConnectManager())
  @Test fun testQuarterConnectPsConnectNteClosedRfConnect() = testQuarterConnectPsConnectNteClosedRfConnect(ConnectManager())

  protected fun testQuarterConnectPsConnectNteClosedConnect(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.connect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedNtcSuccess(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedNtcError(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedDisconnect(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.disconnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedNtdSuccess(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.notifyDisconnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedNtdError(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.notifyDisconnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedNtsClosed(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedNteClosed(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedRtConnect(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.retryConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedPsConnect(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.pauseConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedRsConnect(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.resumeConnect()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testQuarterConnectPsConnectNteClosedRfConnect(manager: ConnectManager) {
    testTripleConnectPsConnectNteClosed(manager)

    manager.refreshConnect(forceConnect = false)
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

}