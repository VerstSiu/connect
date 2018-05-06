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

  // Current:
  //                        -> ConnectTA  -> ConnectFA  -> NtcSuccess
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

  // Current:
  //                        -> ConnectTA  -> ConnectFA  -> PsConnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE          TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :                                      TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

  // Current:
  //                        -> ConnectTA  -> NtcSuccess       -> Disconnect
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

  // Current:
  //                        -> ConnectTA  -> NtcSuccess       -> PsConnect
  // STATE          : null     CONNECTING    CONNECT_COMPLETE    DISCONNECTING
  // SUCCESS        :                        TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                            TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE                TRUE
  // PAUSED         :                                            TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

  // Current:
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

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

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

  // Current:
  //                        -> ConnectTA  -> Disconnect -> NteClosed
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

  // Current:
  //                        -> ConnectTA  -> Disconnect -> PsConnect
  // STATE          : null     CONNECTING    CONNECTING    CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:                        TRUE          TRUE
  // WAIT_RETRY     :
  // ENABLED        :          TRUE
  // PAUSED         :                                      TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

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

  // Current:
  //                        -> ConnectTA  -> PsConnect  -> NtcError
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                      TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                      TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :                        TRUE          TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

  // Current:
  //                        -> ConnectTA  -> PsConnect  -> NteClosed
  // STATE          : null     CONNECTING    CONNECTING    DISCONNECT_COMPLETE
  // SUCCESS        :                                      FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :                                      TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        :          TRUE          TRUE          TRUE
  // PAUSED         :                        TRUE          TRUE

  // <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<> <>-<>-<>-<>-<>-<>-<>-<>-<>-<>

  // Test Cases:

}