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

/**
 * Connect manager test(seventh).
 *
 * @author verstsiu on 2018/5/8.
 * @version 1.0
 */
open class ConnectManagerTestSeventh: ConnectManagerTestSixth() {

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
  // STATE          : DISCONNECTING DISCONNECTING DISCONNECTING DISCONNECTING DISCONNECTING
  // SUCCESS        :
  // RETRY_COUNT    :
  // WAIT_CONNECT   : TRUE
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE          TRUE          TRUE
  // PAUSED         :                             TRUE                        TRUE
  //
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : TRUE                TRUE                TRUE                TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE                TRUE
  // PAUSED         :                     TRUE                                    TRUE
  //
  // STATE          : DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE DISCONNECT_COMPLETE
  // SUCCESS        : FALSE               FALSE               FALSE               FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE                TRUE
  // PAUSED         :                     TRUE                                    TRUE
  //
  // STATE          : RETRY_CONNECTING  RETRY_CONNECTING  RETRY_CONNECTING  RETRY_CONNECTING  RETRY_CONNECTING
  // SUCCESS        :
  // RETRY_COUNT    : 0                 0                 0                 0                 0
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     : TRUE
  // ENABLED        : TRUE              TRUE              TRUE
  // PAUSED         :                                     TRUE                                TRUE
  //
  // STATE          : RETRY_CONNECT_COMPLETE
  // SUCCESS        : TRUE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :
  //
  // STATE          : RETRY_CONNECT_COMPLETE
  // SUCCESS        : FALSE
  // RETRY_COUNT    :
  // WAIT_CONNECT   :
  // WAIT_DISCONNECT:
  // WAIT_RETRY     :
  // ENABLED        : TRUE
  // PAUSED         :

}