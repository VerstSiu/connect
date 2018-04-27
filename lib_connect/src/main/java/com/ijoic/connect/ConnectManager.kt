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

import java.lang.ref.WeakReference

/**
 * Connect manager.
 *
 * @author verstsiu on 2018/4/27.
 * @version 1.0
 */
class ConnectManager(handler: ConnectHandler? = null) {

  private val refHandler = WeakReference(handler)

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> connect handler :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Connect handler.
   */
  interface ConnectHandler {
    /**
     * Connect.
     */
    fun onConnect()

    /**
     * Disconnect.
     */
    fun onDisconnect()

    /**
     * Retry connect.
     *
     * @param retryCount retry count.
     */
    fun onRetryConnect(retryCount: Int)

    /**
     * Returns connect required status.
     */
    fun isConnectRequired(): Boolean = true

    /**
     * Returns max retry count.
     *
     * <p>0 means retry disable, negative means retry forever otherwise.</p>
     */
    fun getMaxRetryCount(): Int = 5
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> connect handler :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> connect state :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Connect state.
   */
  var state: ConnectState? = null
      private set

  /**
   * Wait connect status.
   */
  internal var waitConnect = false
      private set

  /**
   * Wait disconnect status.
   */
  internal var waitDisconnect = false
      private set

  /**
   * Wait retry status.
   */
  internal var waitRetry = false
      private set

  /**
   * Connect enabled status.
   */
  internal var connectEnabled = false
      private set

  /**
   * Connect paused status.
   */
  internal var connectPaused = false
      private set

  /**
   * Returns retry connect required status.
   */
  private fun isRetryConnectRequired(): Boolean {
    val handler = refHandler.get() ?: return false
    val maxRetry = handler.getMaxRetryCount()

    return handler.isConnectRequired() && maxRetry != 0
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> connect state :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Connect.
   */
  fun connect() {
    connectEnabled = true
    val currentSate = state

    if (currentSate == null) {
      state = ConnectState.connecting
      executeConnect()
    }
  }

  /**
   * Disconnect.
   */
  fun disconnect() {
    state ?: return
  }

  /**
   * Retry connect.
   */
  fun retryConnect() {
    state ?: return
  }

  /**
   * Pause connect.
   *
   * <p>This will close existing connection, and mark connect status to pause.</p>
   */
  fun pauseConnect() {
    connectPaused = true
    state ?: return
  }

  /**
   * Resume connect.
   *
   * <p>This will close existing connection, and mark connect status to pause.</p>
   */
  fun resumeConnect() {
    state ?: return
  }

  /**
   * Refresh connect.
   *
   * <p>This will refresh retry status, and create a new connection if needed(connection paused or connect, retry failed etc.).</p>
   *
   * @param forceConnect force connect status.
   */
  fun refreshConnect(forceConnect: Boolean = false) {
    state ?: return
  }

  /**
   * Execute connect.
   */
  private fun executeConnect() {
    refHandler.get()?.onConnect()
  }

  /**
   * Execute disconnect.
   */
  private fun executeDisconnect() {
    refHandler.get()?.onDisconnect()
  }

  /**
   * Execute retry connect.
   *
   * @param retryCount retry count.
   */
  private fun executeRetryConnect(retryCount: Int) {
    refHandler.get()?.onRetryConnect(retryCount)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> notify methods :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Notify connect success.
   */
  fun notifyConnectSuccess() {
    state ?: return
  }

  /**
   * Notify connect error.
   *
   * @param error error.
   */
  fun notifyConnectError(error: Throwable? = null) {
    state ?: return
  }

  /**
   * Notify disconnect success.
   */
  fun notifyDisconnectSuccess() {
    state ?: return
  }

  /**
   * Notify disconnect error.
   *
   * @param error error.
   */
  fun notifyDisconnectError(error: Throwable? = null) {
    state ?: return
  }

  /**
   * Server closed.
   */
  fun notifyServerClosed() {
    state ?: return
  }

  /**
   * Notify error closed.
   *
   * @param error error.
   */
  fun notifyErrorClosed(error: Throwable? = null) {
    state ?: return
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> notify methods :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */
}