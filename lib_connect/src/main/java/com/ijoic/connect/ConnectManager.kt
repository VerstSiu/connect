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

  /**
   * Returns connect required status.
   */
  private fun isConnectRequired(): Boolean {
    return refHandler.get()?.isConnectRequired() ?: false
  }

  /**
   * Returns max retry count or zero.
   */
  private fun getMaxRetryCount(): Int {
    return refHandler.get()?.getMaxRetryCount() ?: 0
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> connect state :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Connect.
   */
  fun connect() {
    connectEnabled = true
    waitDisconnect = false
    val currentSate = state

    if (currentSate == null) {
      if (connectPaused) {
        waitConnect = true
      } else {
        state = ConnectState.connecting
        executeConnect()
      }
      return
    }
    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        if (!currentSate.isSuccess) {
          state = ConnectState.connecting
          executeConnect()
        }
      }
      ConnectState.STATE_DISCONNECTING -> {
        waitConnect = true
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        if (connectPaused) {
          waitRetry = false
          waitConnect = true
        } else {
          state = ConnectState.connecting
          executeConnect()
        }
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          waitRetry = false
          state = ConnectState.connecting
          executeConnect()
        } else {
          state = ConnectState.connecting
        }
      }
    }
  }

  /**
   * Disconnect.
   */
  fun disconnect() {
    connectEnabled = false
    waitConnect = false
    val currentSate = state ?: return

    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        waitDisconnect = true
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        if (currentSate.isSuccess) {
          state = ConnectState.disconnecting
          executeDisconnect()
        } else {
          state = ConnectState.disconnectComplete(isSuccess = true)
        }
      }
      ConnectState.STATE_DISCONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        waitRetry = false
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          waitRetry = false
          state = ConnectState.disconnectComplete(isSuccess = true)
        } else {
          waitDisconnect = true
        }
      }
    }
  }

  /**
   * Retry connect.
   */
  fun retryConnect() {
    val currentSate = state ?: return

    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        if (!currentSate.isSuccess) {
          state = ConnectState.connecting
          executeConnect()
        }
      }
      ConnectState.STATE_DISCONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        if (connectEnabled) {
          if (connectPaused) {
            if (!waitRetry) {
              waitConnect = true
            }
          } else {
            state = ConnectState.connecting
            executeConnect()
          }
        }
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          waitRetry = false
          executeRetryConnect(currentSate.retryCount)
        }
      }
    }
  }

  /**
   * Pause connect.
   *
   * <p>This will close existing connection, and mark connect status to pause.</p>
   */
  fun pauseConnect() {
    if (connectPaused) {
      return
    }
    connectPaused = true
    val currentSate = state ?: return

    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        if (currentSate.isSuccess) {
          waitConnect = true
          state = ConnectState.disconnecting
          executeDisconnect()
        } else {
          state = ConnectState.disconnectComplete(isSuccess = true)
        }
      }
      ConnectState.STATE_DISCONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          state = ConnectState.disconnectComplete(isSuccess = true)
        }
      }
    }
  }

  /**
   * Resume connect.
   *
   * <p>This will close existing connection, and mark connect status to pause.</p>
   */
  fun resumeConnect() {
    if (!connectPaused) {
      return
    }
    connectPaused = false
    val currentSate = state

    if(currentSate == null) {
      if (waitConnect) {
        waitConnect = false
        state = ConnectState.connecting
        executeConnect()
      }
      return
    }
    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        if (waitRetry) {
          val retryCount = currentSate.retryCount
          state = ConnectState.connectingRetry(retryCount)
          executeRetryConnect(retryCount)
        } else if (waitConnect) {
          waitConnect = false
          state = ConnectState.connecting
          executeConnect()
        }
      }
    }
  }

  /**
   * Refresh connect.
   *
   * <p>This will refresh retry status, and create a new connection if needed(connection paused or connect, retry failed etc.).</p>
   *
   * @param forceConnect force connect status.
   */
  fun refreshConnect(forceConnect: Boolean = false) {
    connectPaused = false
    val currentSate = state

    if(currentSate == null) {
      if (waitConnect) {
        waitConnect = false
        state = ConnectState.connecting
        executeConnect()
      }
      return
    }
    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        if (!currentSate.isSuccess) {
          state = ConnectState.connecting
          executeConnect()
        } else {
          if (!forceConnect) {
            // do nothing.
          } else {
            waitConnect = true
            state = ConnectState.disconnecting
            executeDisconnect()
          }
        }
      }
      ConnectState.STATE_DISCONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        if (connectEnabled) {
          waitConnect = false
          waitRetry = false
          state = ConnectState.connecting
          executeConnect()
        }
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          waitRetry = false
          state = ConnectState.connecting
          executeConnect()
        } else {
          state = ConnectState.connecting
        }
      }
    }
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
    val currentSate = state ?: return

    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        if (connectPaused) {
          if (waitDisconnect) {
            waitDisconnect = false
            state = ConnectState.disconnecting
            executeDisconnect()
          } else {
            waitConnect = true
            state = ConnectState.disconnecting
            executeDisconnect()
          }
        } else {
          if (waitDisconnect) {
            waitDisconnect = false
            state = ConnectState.disconnecting
            executeDisconnect()
          } else {
            state = ConnectState.connectComplete(isSuccess = true)
          }
        }
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          // do nothing.
        } else {
          state = ConnectState.connectingRetryComplete(isSuccess = true)
        }
      }
    }
  }

  /**
   * Notify connect error.
   *
   * @param error error.
   */
  fun notifyConnectError(error: Throwable? = null) {
    val currentSate = state ?: return

    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        if (connectPaused) {
          if (waitDisconnect) {
            waitDisconnect = false
            state = ConnectState.disconnectComplete(isSuccess = true)
          } else {
            waitConnect = true
            state = ConnectState.disconnectComplete(isSuccess = true)
          }
        } else {
          if (waitDisconnect) {
            waitDisconnect = false
            state = ConnectState.disconnectComplete(isSuccess = true)
          } else {
            if (!isRetryConnectRequired()) {
              state = ConnectState.connectComplete(isSuccess = false, error = error)
            } else {
              val retryCount = 0
              waitRetry = true
              state = ConnectState.connectingRetry(retryCount)
              executeRetryConnect(retryCount)
            }
          }
        }
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          // do nothing.
        } else {
          val newRetryCount = currentSate.retryCount + 1
          val maxRetryCount = getMaxRetryCount()
          val connectRequired = isConnectRequired()

          if (connectRequired) {
            if (maxRetryCount < 0 || newRetryCount < maxRetryCount) {
              waitRetry = true

              if (maxRetryCount >= 0) {
                state = ConnectState.connectingRetry(newRetryCount)
                executeRetryConnect(newRetryCount)
              } else {
                executeRetryConnect(0)
              }
            } else {
              state = ConnectState.connectingRetryComplete(isSuccess = false)
            }
          } else {
            state = ConnectState.connectingRetryComplete(isSuccess = false)
          }
        }
      }
    }
  }

  /**
   * Notify disconnect success.
   */
  fun notifyDisconnectSuccess() {
    val currentSate = state ?: return

    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECTING -> {
        if (connectPaused) {
          state = ConnectState.disconnectComplete(isSuccess = true)
        } else {
          if (waitConnect) {
            waitConnect = false
            state = ConnectState.connecting
            executeConnect()
          } else {
            state = ConnectState.disconnectComplete(isSuccess = true)
          }
        }
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        // do nothing.
      }
    }
  }

  /**
   * Notify disconnect error.
   *
   * @param error error.
   */
  fun notifyDisconnectError(error: Throwable? = null) {
    val currentSate = state ?: return

    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        // do nothing.
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_DISCONNECTING -> {
        if (connectPaused) {
          state = ConnectState.disconnectComplete(isSuccess = false, error = error)
        } else {
          if (waitConnect) {
            waitConnect = false
            state = ConnectState.connecting
            executeConnect()
          } else {
            state = ConnectState.disconnectComplete(isSuccess = false, error = error)
          }
        }
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        // do nothing.
      }
    }
  }

  /**
   * Server closed.
   */
  fun notifyServerClosed() {
    val currentSate = state ?: return

    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        if (connectPaused) {
          if (waitDisconnect) {
            waitDisconnect = false
            state = ConnectState.disconnectComplete(isSuccess = true)
          } else {
            waitConnect = true
            state = ConnectState.disconnectComplete(isSuccess = true)
          }
        } else {
          if (waitDisconnect) {
            waitDisconnect = false
            state = ConnectState.disconnectComplete(isSuccess = true)
          } else {
            if (!isRetryConnectRequired()) {
              state = ConnectState.disconnectComplete(isSuccess = true)
            } else {
              val retryCount = 0
              waitRetry = true
              state = ConnectState.connectingRetry(retryCount)
              executeRetryConnect(retryCount)
            }
          }
        }
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        if (currentSate.isSuccess) {
          if (!isRetryConnectRequired()) {
            state = ConnectState.disconnectComplete(isSuccess = true)
          } else {
            val retryCount = 0
            waitRetry = true
            state = ConnectState.connectingRetry(retryCount)
            executeRetryConnect(retryCount)
          }
        } else {
          if (!isRetryConnectRequired()) {
            state = ConnectState.disconnectComplete(isSuccess = true)
          }
        }
      }
      ConnectState.STATE_DISCONNECTING -> {
        if (connectPaused) {
          state = ConnectState.disconnectComplete(isSuccess = true)
        } else {
          if (waitConnect) {
            waitConnect = false
            state = ConnectState.connecting
            executeConnect()
          } else {
            state = ConnectState.disconnectComplete(isSuccess = true)
          }
        }
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          // do nothing.
        } else {
          val newRetryCount = currentSate.retryCount + 1
          val maxRetryCount = getMaxRetryCount()
          val connectRequired = isConnectRequired()

          if (connectRequired) {
            if (maxRetryCount < 0 || newRetryCount < maxRetryCount) {
              waitRetry = true

              if (maxRetryCount >= 0) {
                state = ConnectState.connectingRetry(newRetryCount)
                executeRetryConnect(newRetryCount)
              } else {
                executeRetryConnect(0)
              }
            } else {
              state = ConnectState.connectingRetryComplete(isSuccess = false)
            }
          } else {
            state = ConnectState.connectingRetryComplete(isSuccess = false)
          }
        }
      }
    }
  }

  /**
   * Notify error closed.
   *
   * @param error error.
   */
  fun notifyErrorClosed(error: Throwable? = null) {
    val currentSate = state ?: return

    when(currentSate.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        if (connectPaused) {
          if (waitDisconnect) {
            waitDisconnect = false
            state = ConnectState.disconnectComplete(isSuccess = false, error = error)
          } else {
            waitConnect = true
            state = ConnectState.disconnectComplete(isSuccess = false, error = error)
          }
        } else {
          if (waitDisconnect) {
            waitDisconnect = false
            state = ConnectState.disconnectComplete(isSuccess = false, error = error)
          } else {
            if (!isRetryConnectRequired()) {
              state = ConnectState.disconnectComplete(isSuccess = false, error = error)
            } else {
              val retryCount = 0
              waitRetry = true
              state = ConnectState.connectingRetry(retryCount)
              executeRetryConnect(retryCount)
            }
          }
        }
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        if (currentSate.isSuccess) {
          if (!isRetryConnectRequired()) {
            state = ConnectState.disconnectComplete(isSuccess = false, error = error)
          } else {
            val retryCount = 0
            waitRetry = true
            state = ConnectState.connectingRetry(retryCount)
            executeRetryConnect(retryCount)
          }
        } else {
          if (!isRetryConnectRequired()) {
            state = ConnectState.disconnectComplete(isSuccess = false, error = error)
          }
        }
      }
      ConnectState.STATE_DISCONNECTING -> {
        if (connectPaused) {
          state = ConnectState.disconnectComplete(isSuccess = false, error = error)
        } else {
          if (waitConnect) {
            waitConnect = false
            state = ConnectState.connecting
            executeConnect()
          } else {
            state = ConnectState.disconnectComplete(isSuccess = false, error = error)
          }
        }
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        // do nothing.
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          // do nothing.
        } else {
          val newRetryCount = currentSate.retryCount + 1
          val maxRetryCount = getMaxRetryCount()
          val connectRequired = isConnectRequired()

          if (connectRequired) {
            if (maxRetryCount < 0 || newRetryCount < maxRetryCount) {
              waitRetry = true

              if (maxRetryCount >= 0) {
                state = ConnectState.connectingRetry(newRetryCount)
                executeRetryConnect(newRetryCount)
              } else {
                executeRetryConnect(0)
              }
            } else {
              state = ConnectState.connectingRetryComplete(isSuccess = false)
            }
          } else {
            state = ConnectState.connectingRetryComplete(isSuccess = false)
          }
        }
      }
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> notify methods :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */
}