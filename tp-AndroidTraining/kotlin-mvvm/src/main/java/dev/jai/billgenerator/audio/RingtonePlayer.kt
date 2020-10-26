package dev.jai.billgenerator.audio

import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import com.google.android.exoplayer2.ExoPlaybackException
import dev.jai.billgenerator.data.model.Ringtone
import dev.jai.billgenerator.data.model.RingtoneInfo
import dev.jai.billgenerator.utils.AppLogger
import java.lang.Exception
import java.lang.IllegalStateException
import javax.inject.Singleton

@Singleton
class RingtonePlayer private constructor() : ExoPlayerWapper.PlayerEventCallback {
    private var state: State
    private lateinit var mPlayer: ExoPlayerWapper
    private var callback: PlayerCallback? = null
    private lateinit var currentRingtoneInfo: RingtoneInfo
    private var currentPlayingRing: Ringtone? = null
    private var currentRingPosition: Int = 0
    private lateinit var countDownTimer: CountDownTimer


    companion object {
        const val GET_CURRENT_POS_INTERVAL: Int = 500
        var sInstance: RingtonePlayer? = RingtonePlayer()

    }

    private var hander: Handler = Handler(Looper.getMainLooper())
    private var progressCounting: Runnable = object : Runnable {

        override fun run() {
            var currentDuration: Int = mPlayer.getCurrentDuration()
            var totalDuration: Int = mPlayer.getDuration()
            if (totalDuration < 0) {
                callback?.let { callback!!.onProgress(0) }
                return
            }
            var currentSeconds: Long = (currentDuration / 1000).toLong()
            var totalSeconds: Long = (totalDuration / 1000).toLong()


            if (totalSeconds.toInt() == 0) {
                callback?.onProgress(0)
                return
            }
            currentPlayingRing?.let { currentPlayingRing!!.currentPosition = currentDuration }
            //calculating percentage
            var percentage: Int = ((currentSeconds * 100) / totalSeconds).toInt()
            callback?.let { callback!!.onProgress(percentage) }

            // Lood counting current position
            hander.postDelayed(this, GET_CURRENT_POS_INTERVAL.toLong())
        }

    }

    init {
        mPlayer = ExoPlayerWapper(this)
        state = State.INIT
        AppLogger.d("Init media player")
    }

    public fun setState(state: State) {
        this.state = state
    }

    public fun setCallback(callback: PlayerCallback) {
        this.callback = callback
    }

    public fun getCurrentPlayingRing(): Ringtone? {
        return currentPlayingRing
    }

    public fun getCurrentPlayPos(): Int {
        return currentRingPosition
    }

    public fun forcePlay(playRingtoneInfo: RingtoneInfo, callback: PlayerCallback) {
        if (state == State.COUNTING1 || state == State.COUNTING2 || state == State.COUNTING3) {
            countDownTimer.cancel()
        }
        this.currentRingtoneInfo = playRingtoneInfo
        if (currentRingtoneInfo.rings.isNotEmpty()) {
            currentRingPosition = 0
            newSession(currentRingtoneInfo.rings.get(0), callback)
        } else {
            callback.onError()
        }
    }

    public fun getCurrentPosPlaying(): Int {
        mPlayer.let {
            if (state == State.PLAY || state == State.PAUSE) {
                return mPlayer.getCurrentDuration()
            }
        }
        return 0
    }

    public fun stop(needRelease: Boolean) {
        try {
            AppLogger.d(">>>> Stop Player")
            mPlayer.pause()
            hander.removeCallbacks(progressCounting)
            setState(State.RESET)
            callback?.onResetPlayer()
            currentPlayingRing == null
            if (needRelease) {
                mPlayer.release()
                setState(State.RELEASE)
                countDownTimer.cancel()
            }
            sInstance = null
        } catch (e: IllegalStateException) {
            stop(false)
            callback?.onError()
        }
    }

    public fun isSameRingtone(ringtoneInfo: RingtoneInfo): Boolean {
        return ((currentRingtoneInfo != null) && (currentRingtoneInfo.id != null) && (currentRingtoneInfo.id.equals(ringtoneInfo.id)))
    }

    public fun getPlayerState(): State {
        return state
    }

    private fun newSession(ringtone: Ringtone, newCallback: PlayerCallback) {
        try {
            if (state != State.INIT) {
                //stop(false)
                callback?.onResetPlayer()

            }
            if (state == State.PLAY) {
                if (currentPlayingRing != null) {
                    currentPlayingRing?.currentPosition = mPlayer.getCurrentDuration()
                }
            }
            callback = newCallback
            callback?.onBeforePlay(ringtone.name)
            if (ringtone.isOnline()) {
                if (ringtone.ringUrl.isEmpty()) {
                    AppLogger.d(">>>>>>>>>>> Play Offline Null Url File: %s", ringtone.file);
                    mPlayer.setDataSource(ringtone.file)
                } else {
                    // TODO Import URLBuilder
                    var url: String = ""
                    AppLogger.d(">>>>>>>>>>> Play Online File: %s", url);
                    mPlayer.setDataSource(url)
                }
            } else {
                AppLogger.d(">>>>>>>>>>> Play Offline File: %s", ringtone.file);
                mPlayer.setDataSource(ringtone.file)
            }
            setState(State.PREPARE)
            currentPlayingRing = ringtone
            currentPlayingRing?.setLoading(true)

        } catch (e: Exception) {
            AppLogger.e(e, "newSession")
            callback?.onError()
        }
    }
    public fun forcePause() {
        if (mPlayer.isBuffering()) {
            setState(State.PAUSE_BUFFERING)
        }
    }
    public fun playPause() {
        try {
            if (mPlayer.isPlaying()) {
                mPlayer.pause()
                setState(State.PAUSE)
                hander.removeCallbacks(progressCounting)
                callback?.onPauseRingtone()
            } else {
                if (mPlayer.isBuffering()) {
                    countDownTimer.cancel()
                    var ringPlay: Ringtone = currentRingtoneInfo.rings.get(currentRingPosition)
                    callback?.let { newSession(ringPlay, it) }
                } else {
                    mPlayer.start()
                    hander.post(progressCounting)
                    setState(State.PLAY)
                    callback?.onResumeRingtone()
                }
            }

        } catch (e: IllegalStateException) {
            AppLogger.e(e, "playPause");
            stop(false)
            callback?.onError()
        }

    }
    public fun isMediaPlaying(): Boolean {
        try {
            return mPlayer.isPlaying()

        } catch (e: Exception) {
            return false
        }
    }

    override fun onCompletion() {
        TODO("Not yet implemented")
    }

    override fun onPrepared() {
        TODO("Not yet implemented")
    }

    override fun onError(e: ExoPlaybackException) {
        TODO("Not yet implemented")
    }

    enum class State {
        INIT,
        PREPARE,
        READY,
        PLAY,
        PAUSE,
        PAUSE_BUFFERING,
        COMPLETE,
        RESET,
        RELEASE,
        COUNTING1,
        COUNTING2,
        COUNTING3
    }
}

