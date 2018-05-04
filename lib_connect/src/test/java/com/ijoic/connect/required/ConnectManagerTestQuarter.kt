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
 * Connect manager test(quarter).
 *
 * @author verstsiu on 2018/5/4.
 * @version 1.0
 */
open class ConnectManagerTestQuarter: ConnectManagerTestTriple() {

  // Old:
  //
  // STATE          : null    null    null    null    null
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   : TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE    TRUE    TRUE
  // PAUSED         : TRUE            TRUE            TRUE
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
}