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
package com.ijoic.connect.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ijoic.connect.ConnectManager
import com.ijoic.connect.ConnectState

/**
 * Connect manager.
 *
 * @author verstsiu on 2018/5/4.
 * @version 1.0
 */
class StateLiveConnectManager(handler: ConnectHandler? = null): ConnectManager(handler) {

  /**
   * Inner connect state live data.
   */
  private var innerStateLive = MutableLiveData<ConnectState>()

  /**
   * Connect state live data.
   */
  val stateLive: LiveData<ConnectState>
    get() = innerStateLive

  override fun setLastState(state: ConnectState) {
    super.setLastState(state)
    innerStateLive.postValue(state)
  }
}