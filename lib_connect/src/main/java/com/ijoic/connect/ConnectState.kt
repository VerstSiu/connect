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

/**
 * Connect state.
 *
 * @author verstsiu on 2018/4/27.
 * @version 1.0
 */
data class ConnectState(
    val stateValue: Int,
    val isSuccess: Boolean = false,
    val error: Throwable? = null,
    val retryCount: Int = 0) {

  companion object {

    const val STATE_CONNECTING = 0x01
    const val STATE_CONNECT_COMPLETE = 0x02
    const val STATE_DISCONNECTING = 0x03
    const val STATE_DISCONNECT_COMPLETE = 0x04
    const val STATE_RETRY_CONNECTING = 0x05
    const val STATE_RETRY_CONNECT_COMPLETE = 0x06

    internal val connecting = ConnectState(STATE_CONNECTING)
    internal val disconnecting = ConnectState(STATE_DISCONNECTING)

    /**
     * Returns connect complete socket state.
     *
     * @param isSuccess success status.
     * @param error error.
     */
    internal fun connectComplete(isSuccess: Boolean = false, error: Throwable? = null) = ConnectState(STATE_CONNECT_COMPLETE, isSuccess, error)

    /**
     * Returns disconnect complete socket state.
     *
     * @param isSuccess success status.
     * @param error error.
     * @param retryCount retry count.
     */
    internal fun disconnectComplete(isSuccess: Boolean = false, error: Throwable? = null, retryCount: Int = 0) = ConnectState(STATE_DISCONNECT_COMPLETE, isSuccess, error, retryCount)

    /**
     * Connecting retry.
     *
     * @param retryCount retry count.
     */
    internal fun connectingRetry(retryCount: Int = 0) = ConnectState(STATE_RETRY_CONNECTING, retryCount = retryCount)

    /**
     * Connecting retry complete.
     *
     * @param isSuccess success status.
     * @param error error.
     */
    internal fun connectingRetryComplete(isSuccess: Boolean = false, error: Throwable? = null) = ConnectState(STATE_RETRY_CONNECT_COMPLETE, isSuccess, error)
  }
}