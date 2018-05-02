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

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
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
   * Inner connect state live data.
   */
  private var innerStateLive = MutableLiveData<ConnectState>()

  /**
   * Connect state live data.
   */
  val stateLive: LiveData<ConnectState>
      get() = innerStateLive

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
  private fun setLastState(state: ConnectState) {
    this.state = state
    innerStateLive.postValue(state)
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> connect state :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */

  /**
   * Connect.
   */
  fun connect() {
    connectEnabled = true
    waitDisconnect = false
    val currentState = getLastState()

    if (!connectPaused) {
      if (currentState == null) {
        setLastState(ConnectState.connecting)
        executeConnect()
        return
      }
      when(currentState.stateValue) {
        ConnectState.STATE_CONNECT_COMPLETE,
        ConnectState.STATE_RETRY_CONNECT_COMPLETE -> {
          if (!currentState.isSuccess) {
            setLastState(ConnectState.connecting)
            executeConnect()
          }
        }
        ConnectState.STATE_DISCONNECTING -> {
          waitConnect = true
        }
        ConnectState.STATE_DISCONNECT_COMPLETE -> {
          setLastState(ConnectState.connecting)
          executeConnect()
        }
        ConnectState.STATE_RETRY_CONNECTING -> {
          if (waitRetry) {
            waitRetry = false
            setLastState(ConnectState.connecting)
            executeConnect()
          } else {
            setLastState(ConnectState.connecting)
          }
        }
      }
    } else {
      if (currentState == null) {
        waitConnect = true
        return
      }
      when(currentState.stateValue) {
        ConnectState.STATE_DISCONNECTING -> {
          waitConnect = true
        }
        ConnectState.STATE_DISCONNECT_COMPLETE -> {
          if (currentState.isSuccess) {
            waitRetry = false
          }
          waitConnect = true
        }
        ConnectState.STATE_RETRY_CONNECTING -> {
          setLastState(ConnectState.connecting)
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
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      when(currentState.stateValue) {
        ConnectState.STATE_CONNECTING -> {
          waitDisconnect = true
        }
        ConnectState.STATE_CONNECT_COMPLETE,
        ConnectState.STATE_RETRY_CONNECT_COMPLETE -> {
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
          } else {
            waitDisconnect = true
          }
        }
      }
    } else {
      when(currentState.stateValue) {
        ConnectState.STATE_CONNECTING,
        ConnectState.STATE_RETRY_CONNECTING -> {
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
    val currentState = getLastState() ?: return

    if (!connectEnabled) {
      return
    }
    if (!connectPaused) {
      when (currentState.stateValue) {
        ConnectState.STATE_CONNECT_COMPLETE,
        ConnectState.STATE_RETRY_CONNECT_COMPLETE -> {
          if (!currentState.isSuccess) {
            setLastState(ConnectState.connecting)
            executeConnect()
          }
        }
        ConnectState.STATE_DISCONNECT_COMPLETE -> {
          setLastState(ConnectState.connecting)
          executeConnect()
        }
        ConnectState.STATE_RETRY_CONNECTING -> {
          if (waitRetry) {
            waitRetry = false
            executeConnect()
          }
        }
      }
    } else {
      if (currentState.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE) {
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
    val currentState = getLastState() ?: return

    if (!connectEnabled) {
      return
    }
    when(currentState.stateValue) {
      ConnectState.STATE_CONNECT_COMPLETE -> {
        if (currentState.isSuccess) {
          waitConnect = true
          setLastState(ConnectState.disconnecting)
          executeDisconnect()
        } else {
          setLastState(ConnectState.disconnectComplete(isSuccess = true))
        }
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          setLastState(ConnectState.disconnectComplete(isSuccess = true, retryCount = currentState.retryCount))
        }
      }
      ConnectState.STATE_RETRY_CONNECT_COMPLETE -> {
        if (currentState.isSuccess) {
          waitConnect = true
          setLastState(ConnectState.disconnecting)
          executeDisconnect()
        } else {
          waitConnect = true
          setLastState(ConnectState.disconnectComplete(isSuccess = true))
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
    val currentState = getLastState()

    if (!connectEnabled) {
      return
    }
    if (currentState == null) {
      if (waitConnect) {
        waitConnect = false
        setLastState(ConnectState.connecting)
        executeConnect()
      }
      return
    }
    if (currentState.stateValue == ConnectState.STATE_DISCONNECT_COMPLETE) {
      if (currentState.isSuccess) {
        if (waitRetry) {
          val retryCount = currentState.retryCount
          setLastState(ConnectState.connectingRetry(retryCount))
          executeRetryConnect(retryCount)
        } else {
          if (waitConnect) {
            waitConnect = false
            setLastState(ConnectState.connecting)
            executeConnect()
          }
        }
      } else {
        if (waitConnect) {
          waitConnect = false
          setLastState(ConnectState.connecting)
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
    val currentState = getLastState()

    if (!connectEnabled) {
      return
    }
    if (currentState == null) {
      if (waitConnect) {
        waitConnect = false
        setLastState(ConnectState.connecting)
        executeConnect()
      }
      return
    }

    when(currentState.stateValue) {
      ConnectState.STATE_CONNECT_COMPLETE,
      ConnectState.STATE_RETRY_CONNECT_COMPLETE -> {
        if (currentState.isSuccess) {
          if (forceConnect) {
            waitConnect = true
            setLastState(ConnectState.disconnecting)
            executeDisconnect()
          }
        } else {
          setLastState(ConnectState.connecting)
          executeConnect()
        }
      }
      ConnectState.STATE_DISCONNECT_COMPLETE -> {
        if (waitConnect) {
          waitConnect = false
        } else {
          waitRetry = false
        }
        setLastState(ConnectState.connecting)
        executeConnect()
      }
      ConnectState.STATE_RETRY_CONNECTING -> {
        if (waitRetry) {
          waitRetry = false
          setLastState(ConnectState.connecting)
          executeConnect()
        } else {
          setLastState(ConnectState.connecting)
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
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            setLastState(ConnectState.connectComplete(isSuccess = true))
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (!waitRetry) {
              setLastState(ConnectState.connectingRetryComplete(isSuccess = true))
            }
          }
        }
      } else {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              setLastState(ConnectState.disconnecting)
              executeDisconnect()
            }
          }
        }
      }
    } else {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECTING -> {
            waitConnect = true
            setLastState(ConnectState.disconnecting)
            executeDisconnect()
          }
        }
      } else {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              setLastState(ConnectState.disconnecting)
              executeDisconnect()
            }
          }
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
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            matchRetry(0, {
              setLastState(ConnectState.connectComplete(isSuccess = false, error = error))
            })
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (!waitRetry) {
              matchRetry(currentState.retryCount + 1, {
                setLastState(ConnectState.connectingRetryComplete(isSuccess = false, error = error))
              })
            }
          }
        }
      } else {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              setLastState(ConnectState.disconnecting)
              executeDisconnect()
            }
          }
        }
      }
    } else {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            waitConnect = true
            setLastState(ConnectState.disconnectComplete(isSuccess = true))
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            waitConnect = true
            setLastState(ConnectState.disconnecting)
            executeDisconnect()
          }
        }
      } else {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
        }
      }
    }
  }

  /**
   * Notify disconnect success.
   */
  fun notifyDisconnectSuccess() {
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING && waitConnect) {
          waitConnect = false
          setLastState(ConnectState.connecting)
          executeConnect()
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING) {
          setLastState(ConnectState.disconnectComplete(isSuccess = true))
        }
      }
    } else {
      if (connectEnabled) {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING && waitConnect) {
          setLastState(ConnectState.disconnectComplete(isSuccess = true))
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING) {
          setLastState(ConnectState.disconnectComplete(isSuccess = true))
        }
      }
    }
  }

  /**
   * Notify disconnect error.
   *
   * @param error error.
   */
  fun notifyDisconnectError(error: Throwable? = null) {
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING && waitConnect) {
          waitConnect = false
          setLastState(ConnectState.connecting)
          executeConnect()
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING) {
          setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
        }
      }
    } else {
      if (connectEnabled) {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING && waitConnect) {
          setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
        }
      } else {
        if (currentState.stateValue == ConnectState.STATE_DISCONNECTING) {
          setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
        }
      }
    }
  }

  /**
   * Server closed.
   */
  fun notifyServerClosed() {
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECT_COMPLETE -> {
            matchRetry(0, {
              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            })
          }
          ConnectState.STATE_CONNECT_COMPLETE -> {
            if (currentState.isSuccess) {
              matchRetry(0, {
                setLastState(ConnectState.disconnectComplete(isSuccess = true))
              })
            } else {
              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
          ConnectState.STATE_DISCONNECTING -> {
            if (waitConnect) {
              waitConnect = false
              setLastState(ConnectState.connecting)
              executeConnect()
            }
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (!waitRetry) {
              matchRetry(currentState.retryCount + 1, {
                setLastState(ConnectState.connectingRetryComplete(isSuccess = false))
              })
            }
          }
        }
      } else {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
          ConnectState.STATE_DISCONNECTING -> {
            setLastState(ConnectState.disconnectComplete(isSuccess = true))
          }
        }
      }
    } else {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECTING -> {
            waitConnect = true
            setLastState(ConnectState.disconnectComplete(isSuccess = true))
          }
          ConnectState.STATE_DISCONNECTING -> {
            if (waitConnect) {
              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
        }
      } else {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              setLastState(ConnectState.disconnectComplete(isSuccess = true))
            }
          }
          ConnectState.STATE_DISCONNECTING -> {
            setLastState(ConnectState.disconnectComplete(isSuccess = true))
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
    val currentState = getLastState() ?: return

    if (!connectPaused) {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECT_COMPLETE -> {
            matchRetry(0, {
              setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
            })
          }
          ConnectState.STATE_CONNECT_COMPLETE -> {
            if (currentState.isSuccess) {
              matchRetry(0, {
                setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
              })
            } else {
              setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
            }
          }
          ConnectState.STATE_DISCONNECTING -> {
            if (waitConnect) {
              waitConnect = false
              setLastState(ConnectState.connecting)
              executeConnect()
            }
          }
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (!waitRetry) {
              matchRetry(currentState.retryCount + 1, {
                setLastState(ConnectState.connectingRetryComplete(isSuccess = false, error = error))
              })
            }
          }
        }
      } else {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
            }
          }
          ConnectState.STATE_DISCONNECTING -> {
            setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
          }
        }
      }
    } else {
      if (connectEnabled) {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECTING -> {
            waitConnect = true
            setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
          }
          ConnectState.STATE_DISCONNECTING -> {
            if (waitConnect) {
              setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
            }
          }
        }
      } else {
        when(currentState.stateValue) {
          ConnectState.STATE_CONNECTING,
          ConnectState.STATE_RETRY_CONNECTING -> {
            if (waitDisconnect) {
              waitDisconnect = false
              setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
            }
          }
          ConnectState.STATE_DISCONNECTING -> {
            setLastState(ConnectState.disconnectComplete(isSuccess = false, error = error))
          }
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
        setLastState(ConnectState.connectingRetry(runCount))
        executeRetryConnect(runCount)
      }
    }
  }

  /* <>-<>-<>-<>-<>-<>-<>-<>-<>-<> state methods :end <>-<>-<>-<>-<>-<>-<>-<>-<>-<> */
}