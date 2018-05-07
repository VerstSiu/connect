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

import java.lang.ref.WeakReference

/**
 * Connect manager.
 *
 * @author verstsiu on 2018/4/27.
 * @version 1.0
 */
open class ConnectManager(handler: ConnectHandler? = null) {

  private val refHandler = WeakReference(handler)

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> connect handler :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Connect handler.
   *
   * <p>Do invoke notifyServerClosed or notifyErrorClosed when needed.</p>
   *
   * @see notifyServerClosed
   * @see notifyErrorClosed
   */
  interface ConnectHandler {
    /**
     * Connect.
     *
     * <p>Do invoke notifyConnectSuccess or notifyConnectError when disconnect complete.</p>
     *
     * @param manager connect manager.
     *
     * @see notifyConnectSuccess
     * @see notifyConnectError
     */
    fun onConnect(manager: ConnectManager)

    /**
     * Disconnect.
     *
     * <p>Do invoke notifyDisconnectSuccess or notifyDisconnectError when disconnect complete.</p>
     *
     * @param manager connect manager.
     *
     * @see notifyDisconnectSuccess
     * @see notifyDisconnectError
     */
    fun onDisconnect(manager: ConnectManager)

    /**
     * Retry connect.
     *
     * <p>Call manager.retryConnect directly or post with specific delay milliseconds according to retry count.</p>
     *
     * @param manager connect manager.
     * @param retryCount retry count.
     *
     * @see retryConnect
     */
    fun onRetryConnect(manager: ConnectManager, retryCount: Int)

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
   * Connect ready status(connect complete success).
   */
  val isConnectReady: Boolean
      get() {
        val currentState = state ?: return false
        return when(currentState.stateValue) {
          ConnectState.STATE_CONNECT_COMPLETE,
          ConnectState.STATE_RETRY_CONNECT_COMPLETE -> {
            currentState.isSuccess
          }
          else -> false
        }
      }

  /**
   * Returns last connect state.
   */
  private fun getLastState(): ConnectState? {
    return state
  }

  /**
   * Set last connect state.
   *
   * @param state connect state.
   */
  protected open fun setLastState(state: ConnectState) {
    this.state = state
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> connect state :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Connect.
   */
  fun connect() {
    connectEnabled = true
    val currentState = getLastState()

    if (!connectPaused) {
      if (currentState == null) {
        if (isConnectRequired) {
          setLastState(ConnectState.connecting)
          executeConnect()
        }
        return
      }

      when(currentState.stateValue) {
        ConnectState.STATE_CONNECTING -> {
          waitDisconnect = !isConnectRequired
        }
        ConnectState.STATE_CONNECT_COMPLETE -> {
          if (currentState.isSuccess) {
            if (!isConnectRequired) {
              setLastState(ConnectState.disconnecting)
              executeDisconnect()
            }
          } else {
            if (isConnectRequired) {
              setLastState(ConnectState.connecting)
              executeConnect()
            } else {
              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
        }
        ConnectState.STATE_DISCONNECT_COMPLETE -> {
          if (isConnectRequired) {
            setLastState(ConnectState.connecting)
            executeConnect()
          }
        }
        ConnectState.STATE_RETRY_CONNECTING -> {
          if (isConnectRequired) {
            if (waitRetry) {
              waitRetry = false
              val retryCount = 0

              if (currentState.retryCount != 0) {
                setLastState(ConnectState.connectingRetry(retryCount))
              }
              executeRetryConnect(retryCount)
            }
          } else {
            if (waitRetry) {
              waitRetry = false

              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
        }
      }
    } else {
      if (currentState == null) {
        waitConnect = isConnectRequired
        return
      }

      if (currentState.stateValue == ConnectState.STATE_CONNECTING && !isConnectRequired) {
        waitDisconnect = true
      }
    }
  }

  /**
   * Disconnect.
   */
  fun disconnect() {
    connectEnabled = false
    val currentState = getLastState()

    if (!connectPaused) {
      if (currentState == null) {
        return
      }

      when(currentState.stateValue) {
        ConnectState.STATE_CONNECTING -> {
          waitDisconnect = true
        }
        ConnectState.STATE_CONNECT_COMPLETE -> {
          if (currentState.isSuccess) {
            setLastState(ConnectState.disconnecting)
            executeDisconnect()
          } else {
            setLastState(ConnectState.disconnectComplete(isSuccess = true))
          }
        }
        ConnectState.STATE_RETRY_CONNECTING -> {
          if (waitRetry) {
            waitRetry = false

            setLastState(ConnectState.disconnectComplete(isSuccess = true))
          }
        }
      }
    } else {
      if (currentState == null) {
        waitConnect = false
        return
      }

      if (currentState.stateValue == ConnectState.STATE_CONNECTING) {
        waitDisconnect = true
      }
    }
  }

  /**
   * Retry connect.
   */
  fun retryConnect() {
    if (!connectEnabled) {
      return
    }
    val currentState = getLastState()

    if (!connectPaused) {
      if (currentState == null) {
        if (isConnectRequired) {
          setLastState(ConnectState.connecting)
          executeConnect()
        }
        return
      }

      when(currentState.stateValue) {
        ConnectState.STATE_CONNECTING -> {
          waitDisconnect = !isConnectRequired
        }
        ConnectState.STATE_CONNECT_COMPLETE -> {
          if (currentState.isSuccess) {
            if (!isConnectRequired) {
              setLastState(ConnectState.disconnecting)
              executeDisconnect()
            }
          } else {
            if (isConnectRequired) {
              setLastState(ConnectState.connecting)
              executeConnect()
            } else {
              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
        }
        ConnectState.STATE_DISCONNECT_COMPLETE -> {
          if (isConnectRequired) {
            setLastState(ConnectState.connecting)
            executeConnect()
          }
        }
        ConnectState.STATE_RETRY_CONNECTING -> {
          if (isConnectRequired) {
            if (waitRetry) {
              waitRetry = false
              val retryCount = 0

              if (currentState.retryCount != 0) {
                setLastState(ConnectState.connectingRetry(retryCount))
              }
              executeRetryConnect(retryCount)
            }
          } else {
            if (waitRetry) {
              waitRetry = false

              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
        }
      }
    } else {
      if (currentState == null) {
        waitConnect = isConnectRequired
        return
      }

      if (currentState.stateValue == ConnectState.STATE_CONNECTING && !isConnectRequired) {
        waitDisconnect = true
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

    if (!connectEnabled) {
      return
    }
    val currentState = getLastState() ?: return

    if (currentState.stateValue == ConnectState.STATE_CONNECT_COMPLETE) {
      if (currentState.isSuccess) {
        waitConnect = true
        setLastState(ConnectState.disconnecting)
        executeDisconnect()
      } else {
        setLastState(ConnectState.disconnectComplete(isSuccess = true))
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

    if (!connectEnabled) {
      return
    }
    val currentState = getLastState()

    if (currentState == null) {
      waitConnect = false

      if (isConnectRequired) {
        setLastState(ConnectState.connecting)
        executeConnect()
      }
      return
    }
    if (currentState.stateValue == ConnectState.STATE_CONNECTING && !isConnectRequired) {
      waitDisconnect = true
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

    if (!connectEnabled) {
      return
    }
    val currentState = getLastState()

    if (currentState == null) {
      waitConnect = false

      if (isConnectRequired) {
        setLastState(ConnectState.connecting)
        executeConnect()
      }
      return
    }

    when(currentState.stateValue) {
      ConnectState.STATE_CONNECTING -> {
        waitDisconnect = !isConnectRequired
      }
      ConnectState.STATE_CONNECT_COMPLETE -> {
        if (currentState.isSuccess) {
          if (isConnectRequired) {
            if (forceConnect) {
              waitConnect = true
              setLastState(ConnectState.disconnecting)
              executeDisconnect()
            }
          } else {
            setLastState(ConnectState.disconnecting)
            executeDisconnect()
          }
        } else {
          if (isConnectRequired) {
            setLastState(ConnectState.connecting)
            executeConnect()
          } else {
            setLastState(ConnectState.disconnectComplete(isSuccess = true))
          }
        }
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        if (isConnectRequired) {
          setLastState(ConnectState.connecting)
          executeConnect()
        }
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (isConnectRequired) {
          if (waitRetry) {
            waitRetry = false
            val retryCount = 0

            if (currentState.retryCount != 0) {
              setLastState(ConnectState.connectingRetry(retryCount))
            }
            executeRetryConnect(retryCount)
          }
        } else {
          if (waitRetry) {
            waitRetry = false

            setLastState(ConnectState.disconnectComplete(isSuccess = true))
          }
        }
      }
    }
  }

  /**
   * Execute connect.
   */
  private fun executeConnect() {
    refHandler.get()?.onConnect(this)
  }

  /**
   * Execute disconnect.
   */
  private fun executeDisconnect() {
    refHandler.get()?.onDisconnect(this)
  }

  /**
   * Execute retry connect.
   *
   * @param retryCount retry count.
   */
  private fun executeRetryConnect(retryCount: Int) {
    refHandler.get()?.onRetryConnect(this, retryCount)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> notify methods :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Notify connect success.
   */
  fun notifyConnectSuccess() {
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING) {
          if (waitDisconnect) {
            waitDisconnect = false
            setLastState(ConnectState.disconnecting)
            executeDisconnect()
          } else {
            setLastState(ConnectState.connectComplete(isSuccess = true))
          }
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING) {
          if (waitDisconnect) {
            waitDisconnect = false

            setLastState(ConnectState.disconnecting)
            executeDisconnect()
          }
        }
      }
    } else {
      if (connectEnabled && currentState.stateValue == ConnectState.STATE_CONNECTING) {
        waitConnect = true
        setLastState(ConnectState.disconnecting)
        executeDisconnect()
      }
    }
  }

  /**
   * Notify connect error.
   *
   * @param error error.
   */
  fun notifyConnectError(error: Throwable? = null) {
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING) {
          if (waitDisconnect) {
            waitDisconnect = false
            setLastState(ConnectState.disconnecting)
            executeDisconnect()
          } else {
            matchRetry(
                retryCount = 0,
                cancel = { setLastState(ConnectState.connectComplete(isSuccess = false, error = error)) },
                notRequired = { setLastState(ConnectState.disconnectComplete(isSuccess = true)) }
            )
          }
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING && waitDisconnect) {
          waitDisconnect = false

          setLastState(ConnectState.disconnectComplete(isSuccess = true))
        }
      }
    } else {
      if (connectEnabled && currentState.stateValue == ConnectState.STATE_CONNECTING) {
        waitConnect = true
        setLastState(ConnectState.disconnectComplete(isSuccess = true))
      }
    }
  }

  /**
   * Notify disconnect success.
   */
  fun notifyDisconnectSuccess() {
    val currentState = getLastState() ?: return
  }

  /**
   * Notify disconnect error.
   *
   * @param error error.
   */
  fun notifyDisconnectError(error: Throwable? = null) {
    val currentState = getLastState() ?: return
  }

  /**
   * Server closed.
   */
  fun notifyServerClosed() {
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            waitDisconnect = false

            matchRetry(
                retryCount = 0,
                cancel = { setLastState(ConnectState.disconnectComplete(isSuccess = true)) }
            )
          }
          ConnectState.STATE_CONNECT_COMPLETE -> {
            matchRetry(
                retryCount = 0,
                cancel = { setLastState(ConnectState.disconnectComplete(isSuccess = true)) }
            )
          }
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING && waitDisconnect) {
          waitDisconnect = false

          setLastState(ConnectState.disconnectComplete(isSuccess = true))
        }
      }
    } else {
      if (connectEnabled && currentState.stateValue == ConnectState.STATE_CONNECTING) {
        waitConnect = true
        setLastState(ConnectState.disconnectComplete(isSuccess = true))
      }
    }
  }

  /**
   * Notify error closed.
   *
   * @param error error.
   */
  fun notifyErrorClosed(error: Throwable? = null) {
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            waitDisconnect = false

            matchRetry(
                retryCount = 0,
                cancel = { setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error)) }
            )
          }
          ConnectState.STATE_CONNECT_COMPLETE -> {
            matchRetry(
                retryCount = 0,
                cancel = { setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error)) }
            )
          }
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_CONNECTING && waitDisconnect) {
          waitDisconnect = false

          setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
        }
      }
    } else {
      if (connectEnabled && currentState.stateValue == ConnectState.STATE_CONNECTING) {
        waitConnect = true
        setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
      }
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> notify methods :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> state methods :start <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Connect required status, default as true.
   */
  private val isConnectRequired: Boolean
    get() = refHandler.get()?.isConnectRequired() ?: true

  /**
   * Match retry.
   *
   * @param retryCount expected retry count.
   * @param cancel cancel action: fun().
   * @param notRequired connect not required action: fun().
   */
  private fun matchRetry(retryCount: Int, cancel: () -> Unit, notRequired: (() -> Unit)? = null) {
    val handler = refHandler.get()

    if (handler == null) {
      cancel.invoke()
    } else {
      val maxRetry = handler.getMaxRetryCount()
      val connectRequired = handler.isConnectRequired()

      when {
        !connectRequired -> {
          (notRequired ?: cancel).invoke()
        }
        maxRetry == 0 || (maxRetry in 1..retryCount) -> {
          cancel.invoke()
        }
        else -> {
          val runCount = if (maxRetry < 0) 0 else retryCount

          waitRetry = true
          setLastState(ConnectState.connectingRetry(runCount))
          executeRetryConnect(runCount)
        }
      }
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> state methods :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */
}