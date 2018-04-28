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

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> connect state :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Connect.
   */
  fun connect() {
    connectEnabled = true
    waitDisconnect = false
    val currentState = state

    if (!connectPaused) {
      if (currentState == null) {
        state = ConnectState.connecting
        executeConnect()
        return
      }
      when(currentState.stateValue) {
        ConnectState.STATE_CONNECT_COMPLETE -> {
          if (!currentState.isSuccess) {
            state = ConnectState.connecting
            executeConnect()
          }
        }
        ConnectState.STATE_DISCONNECTING -> {
          waitConnect = true
        }
        ConnectState.STATE_DISCONNECT_COMPLETE -> {
          state = ConnectState.connecting
          executeConnect()
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
    } else {
      if (currentState == null) {
        waitConnect = true
        return
      }
      when(currentState.stateValue) {
        ConnectState.STATE_DISCONNECT_COMPLETE -> {
          if (currentState.isSuccess) {
            waitRetry = false
          }
          waitConnect = true
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
    val currentState = state ?: return

    if (!connectPaused) {
      when(currentState.stateValue) {
        ConnectState.STATE_CONNECTING -> {
          waitDisconnect = true
        }
        ConnectState.STATE_CONNECT_COMPLETE -> {
          if (currentState.isSuccess) {
            state = ConnectState.disconnecting
            executeDisconnect()
          } else {
            state = ConnectState.disconnectComplete(isSuccess = true)
          }
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
    } else {
      when(currentState.stateValue) {
        ConnectState.STATE_CONNECTING -> {
          waitDisconnect = true
        }
        ConnectState.STATE_DISCONNECT_COMPLETE -> {
          if (currentState.isSuccess && waitRetry) {
            waitRetry = false
          }
        }
      }
    }
  }

  /**
   * Retry connect.
   */
  fun retryConnect() {
    val currentState = state ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when (currentState.stateValue) {
          ConnectState.STATE_CONNECT_COMPLETE -> {
            if (!currentState.isSuccess) {
              state = ConnectState.connecting
              executeConnect()
            }
          }
          ConnectState.STATE_DISCONNECT_COMPLETE -> {
            state = ConnectState.connecting
            executeConnect()
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (waitRetry) {
              waitRetry = false
              executeConnect()
            }
          }
        }
      }
    } else {
      if (connectEnabled && currentState.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE) {
        if (currentState.isSuccess) {
          if (!waitRetry) {
            waitConnect = true
          }
        } else {
          waitConnect = true
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
    val currentState = state ?: return

    if (connectEnabled) {
      when(currentState.stateValue) {
        ConnectState.STATE_CONNECT_COMPLETE -> {
          if (currentState.isSuccess) {
            waitConnect = true
            state = ConnectState.disconnecting
            executeDisconnect()
          } else {
            state = ConnectState.disconnectComplete(isSuccess = true)
          }
        }
        ConnectState.STATE_RETRY_CONNECTING -> {
          if (waitRetry) {
            state = ConnectState.disconnectComplete(isSuccess = true, retryCount = currentState.retryCount)
          }
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
    val currentState = state

    if (connectEnabled) {
      if (currentState == null) {
        if (waitConnect) {
          waitConnect = false
          state = ConnectState.connecting
          executeConnect()
        }
        return
      }
      if (currentState.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE) {
        if (currentState.isSuccess) {
          if (waitRetry) {
            val retryCount = currentState.retryCount
            state = ConnectState.connectingRetry(retryCount)
            executeRetryConnect(retryCount)
          } else {
            if (waitConnect) {
              waitConnect = false
              state = ConnectState.connecting
              executeConnect()
            }
          }
        } else {
          if (waitConnect) {
            waitConnect = false
            state = ConnectState.connecting
            executeConnect()
          }
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
    val currentState = state

    if (connectEnabled) {
      if (currentState == null) {
        if (waitConnect) {
          waitConnect = false
          state = ConnectState.connecting
          executeConnect()
        }
        return
      }

      when(currentState.stateValue) {
        ConnectState.STATE_CONNECT_COMPLETE -> {
          if (currentState.isSuccess) {
            if (forceConnect) {
              waitConnect = true
              state = ConnectState.disconnecting
              executeDisconnect()
            }
          } else {
            state = ConnectState.connecting
            executeConnect()
          }
        }
        ConnectState.STATE_DISCONNECT_COMPLETE -> {
          if (waitConnect) {
            waitConnect = false
          } else {
            waitRetry = false
          }
          state = ConnectState.connecting
          executeConnect()
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
    val currentState = state ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            state = ConnectState.connectComplete(isSuccess = true)
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (!waitRetry) {
              state = ConnectState.connectingRetryComplete(isSuccess = true)
            }
          }
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING && waitDisconnect) {
          waitDisconnect = false
          state = ConnectState.disconnecting
          executeDisconnect()
        }
      }
    } else {
      if (connectEnabled) {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING) {
          waitConnect = true
          state = ConnectState.disconnecting
          executeDisconnect()
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING && waitDisconnect) {
          waitDisconnect = false
          state = ConnectState.disconnecting
          executeDisconnect()
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
    val currentState = state ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            matchRetry(0, {
              state = ConnectState.connectComplete(isSuccess = false, error = error)
            })
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (!waitRetry) {
              matchRetry(currentState.retryCount + 1, {
                state = ConnectState.connectingRetryComplete(isSuccess = false, error = error)
              })
            }
          }
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING && waitDisconnect) {
          waitDisconnect = false
          state = ConnectState.disconnectComplete(isSuccess = true)
        }
      }
    } else {
      if (connectEnabled) {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING) {
          waitConnect = true
          state = ConnectState.disconnectComplete(isSuccess = true)
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING && waitDisconnect) {
          waitDisconnect = false
          state = ConnectState.disconnectComplete(isSuccess = true)
        }
      }
    }
  }

  /**
   * Notify disconnect success.
   */
  fun notifyDisconnectSuccess() {
    val currentState = state ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING && waitConnect) {
          waitConnect = false
          state = ConnectState.connecting
          executeConnect()
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING) {
          state = ConnectState.disconnectComplete(isSuccess = true)
        }
      }
    } else {
      if (connectEnabled && currentState.stateValue == ConnectState.STATE_DISCONNECTING && waitConnect) {
        state = ConnectState.disconnectComplete(isSuccess = true)
      }
    }
  }

  /**
   * Notify disconnect error.
   *
   * @param error error.
   */
  fun notifyDisconnectError(error: Throwable? = null) {
    val currentState = state ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING && waitConnect) {
          waitConnect = false
          state = ConnectState.connecting
          executeConnect()
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING) {
          state = ConnectState.disconnectComplete(isSuccess = false, error = error)
        }
      }
    } else {
      if (connectEnabled && currentState.stateValue == ConnectState.STATE_DISCONNECTING && waitConnect) {
        state = ConnectState.disconnectComplete(isSuccess = false, error = error)
      }
    }
  }

  /**
   * Server closed.
   */
  fun notifyServerClosed() {
    val currentState = state ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            matchRetry(0, {
              state = ConnectState.disconnectComplete(isSuccess = true)
            })
          }
          ConnectState.STATE_CONNECT_COMPLETE -> {
            if (currentState.isSuccess) {
              matchRetry(0, {
                state = ConnectState.disconnectComplete(isSuccess = true)
              })
            } else {
              state = ConnectState.disconnectComplete(isSuccess = true)
            }
          }
          ConnectState.STATE_DISCONNECTING -> {
            if (waitConnect) {
              waitConnect = false
              state = ConnectState.connecting
              executeConnect()
            }
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (!waitRetry) {
              matchRetry(currentState.retryCount + 1, {
                state = ConnectState.connectingRetryComplete(isSuccess = false)
              })
            }
          }
        }
      } else {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              state = ConnectState.disconnectComplete(isSuccess = true)
            }
          }
          ConnectState.STATE_DISCONNECTING -> {
            state = ConnectState.disconnectComplete(isSuccess = true)
          }
        }
      }
    } else {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            waitConnect = true
            state = ConnectState.disconnectComplete(isSuccess = true)
          }
          ConnectState.STATE_DISCONNECTING -> {
            if (waitConnect) {
              state = ConnectState.disconnectComplete(isSuccess = true)
            }
          }
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING && waitDisconnect) {
          waitDisconnect = false
          state = ConnectState.disconnectComplete(isSuccess = true)
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
    val currentState = state ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            matchRetry(0, {
              state = ConnectState.disconnectComplete(isSuccess = false, error = error)
            })
          }
          ConnectState.STATE_CONNECT_COMPLETE -> {
            if (currentState.isSuccess) {
              matchRetry(0, {
                state = ConnectState.disconnectComplete(isSuccess = false, error = error)
              })
            } else {
              state = ConnectState.disconnectComplete(isSuccess = false, error = error)
            }
          }
          ConnectState.STATE_DISCONNECTING -> {
            if (waitConnect) {
              waitConnect = false
              state = ConnectState.connecting
              executeConnect()
            }
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (!waitRetry) {
              matchRetry(currentState.retryCount + 1, {
                state = ConnectState.connectingRetryComplete(isSuccess = false, error = error)
              })
            }
          }
        }
      } else {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              state = ConnectState.disconnectComplete(isSuccess = false, error = error)
            }
          }
          ConnectState.STATE_DISCONNECTING -> {
            state = ConnectState.disconnectComplete(isSuccess = false, error = error)
          }
        }
      }
    } else {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            waitConnect = true
            state = ConnectState.disconnectComplete(isSuccess = false, error = error)
          }
          ConnectState.STATE_DISCONNECTING -> {
            if (waitConnect) {
              state = ConnectState.disconnectComplete(isSuccess = false, error = error)
            }
          }
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING && waitDisconnect) {
          waitDisconnect = false
          state = ConnectState.disconnectComplete(isSuccess = false, error = error)
        }
      }
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> notify methods :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> state methods :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Match retry.
   *
   * @param retryCount expected retry count.
   * @param cancel cancel action: fun().
   */
  private fun matchRetry(retryCount: Int, cancel: () -> Unit) {
    val handler = refHandler.get()

    if (handler == null) {
      cancel.invoke()
    } else {
      val maxRetry = handler.getMaxRetryCount()
      val connectRequired = handler.isConnectRequired()

      if (!connectRequired || maxRetry == 0 || (maxRetry in 1..retryCount)) {
        cancel.invoke()
      } else {
        val runCount = if (maxRetry < 0) 0 else retryCount

        waitRetry = true
        state = ConnectState.connectingRetry(runCount)
        executeRetryConnect(runCount)
      }
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> state methods :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */
}