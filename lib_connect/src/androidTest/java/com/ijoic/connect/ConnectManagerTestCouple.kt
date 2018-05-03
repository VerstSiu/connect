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
 * Connect manager test(couple).
 *
 * @author verstsiu on 2018/4/27.
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
  //                        -> Connect
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
  //                  Create -> Connect    -> Connect, NtdSuccess, NtdError, RtConnect, RsConnect, RfConnect
  // STATE          : null      CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcSuccess
  // STATE          : null      CONNECTING    CONNECT_COMPLETE
  // SUCCESS        :                         TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
  //
  //                  Create -> Connect    -> NtcError(*,0)
  // STATE          : null      CONNECTING    CONNECT_COMPLETE
  // SUCCESS        :                         FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :           TRUE          TRUE
  // PAUSED         :
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

  @Test fun testCoupleConnectConnect() = testCoupleConnectConnect(ConnectManager())
  @Test fun testCoupleConnectNtcSuccess() = testCoupleConnectNtcSuccess(ConnectManager())
  @Test fun testCoupleConnectNtcError() = testCoupleConnectNtcError(ConnectManager())
  @Test fun testCoupleConnectNtcErrorRetry() = testCoupleConnectNtcErrorRetry(createManagerPair())
  @Test fun testCoupleConnectDisconnect() = testCoupleConnectDisconnect(ConnectManager())
  @Test fun testCoupleConnectNtdSuccess() = testCoupleConnectNtdSuccess(ConnectManager())
  @Test fun testCoupleConnectNtdError() = testCoupleConnectNtdError(ConnectManager())
  @Test fun testCoupleConnectNtsClosed() = testCoupleConnectNtsClosed(ConnectManager())
  @Test fun testCoupleConnectNtsClosedRetry() = testCoupleConnectNtsClosedRetry(createManagerPair())
  @Test fun testCoupleConnectNteClosed() = testCoupleConnectNteClosed(ConnectManager())
  @Test fun testCoupleConnectNteClosedRetry() = testCoupleConnectNteClosedRetry(createManagerPair())
  @Test fun testCoupleConnectRtConnect() = testCoupleConnectRtConnect(ConnectManager())
  @Test fun testCoupleConnectPsConnect() = testCoupleConnectPsConnect(ConnectManager())
  @Test fun testCoupleConnectRsConnect() = testCoupleConnectRsConnect(ConnectManager())
  @Test fun testCoupleConnectRfConnectFF() = testCoupleConnectRfConnect(ConnectManager())

  protected fun testCoupleConnectConnect(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.connect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCoupleConnectNtcSuccess(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.notifyConnectSuccess()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCoupleConnectNtcError(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.notifyConnectError()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_CONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCoupleConnectNtcErrorRetry(pair: Pair<ConnectManager, MockHandler>, s1: ((MockHandler) -> Unit)? = null) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnect(manager)

    if (s1 == null) {
      handler.connectRequired = true
      handler.maxRetry = 1
    } else {
      s1(handler)
    }

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

  protected fun testCoupleConnectDisconnect(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.disconnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCoupleConnectNtdSuccess(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.notifyDisconnectSuccess()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCoupleConnectNtdError(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.notifyDisconnectSuccess()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCoupleConnectNtsClosed(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.notifyServerClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == true)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCoupleConnectNtsClosedRetry(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnect(manager)

    handler.connectRequired = true
    handler.maxRetry = 1

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

  protected fun testCoupleConnectNteClosed(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.notifyErrorClosed()
    val currentState = manager.state
    Assert.assertTrue(currentState?.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE)
    Assert.assertTrue(currentState?.isSuccess == false)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCoupleConnectNteClosedRetry(pair: Pair<ConnectManager, MockHandler>) {
    val manager = pair.first
    val handler = pair.second
    testSingleConnect(manager)

    handler.connectRequired = true
    handler.maxRetry = 1

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

  protected fun testCoupleConnectRtConnect(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.retryConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCoupleConnectPsConnect(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.pauseConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCoupleConnectRsConnect(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.resumeConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCoupleConnectRfConnect(manager: ConnectManager) {
    testSingleConnect(manager)

    manager.refreshConnect()
    Assert.assertTrue(manager.state?.stateValue == ConnectState.STATE_CONNECTING)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
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
  //                  Create -> PsConnect -> Connect
  // STATE          : null      null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                        TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :                        TRUE
  // PAUSED         :           TRUE         TRUE
  //
  //                  Create -> PsConnect -> NtcSuccess, NtcError, Disconnect, NtdSuccess, NtdError, NtsClosed, NteClosed, RtConnect, PsConnect
  // STATE          : null      null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :           TRUE         TRUE
  //
  //                  Create -> PsConnect -> RsConnect, RfConnect
  // STATE          : null      null         null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :
  // PAUSED         :           TRUE

  @Test fun testCouplePsConnectConnect() = testCouplePsConnectConnect(ConnectManager())
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

  protected fun testCouplePsConnectConnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.connect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCouplePsConnectNtcSuccess(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyConnectSuccess()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCouplePsConnectNtcError(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyConnectError()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCouplePsConnectDisconnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.disconnect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCouplePsConnectNtdSuccess(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyDisconnectSuccess()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCouplePsConnectNtdError(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyDisconnectError()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCouplePsConnectNtsClosed(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyServerClosed()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCouplePsConnectNteClosed(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.notifyErrorClosed()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCouplePsConnectRtConnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.retryConnect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCouplePsConnectPsConnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.pauseConnect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(manager.connectPaused)
  }

  protected fun testCouplePsConnectRsConnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.resumeConnect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

  protected fun testCouplePsConnectRfConnect(manager: ConnectManager) {
    testSinglePsConnect(manager)

    manager.refreshConnect()
    Assert.assertTrue(manager.state == null)
    Assert.assertTrue(!manager.waitConnect)
    Assert.assertTrue(!manager.waitDisconnect)
    Assert.assertTrue(!manager.waitRetry)
    Assert.assertTrue(!manager.connectEnabled)
    Assert.assertTrue(!manager.connectPaused)
  }

}