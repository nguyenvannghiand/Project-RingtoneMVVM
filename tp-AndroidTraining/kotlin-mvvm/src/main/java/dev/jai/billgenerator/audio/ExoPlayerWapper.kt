package dev.jai.billgenerator.audio

import android.net.Uri
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.audio.AudioAttributes
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import dev.jai.billgenerator.app.MainApplication

class ExoPlayerWapper {
    private var player: SimpleExoPlayer
    private var dataSourceFactory: DefaultDataSourceFactory
    private var eventCallback: PlayerEventCallback
    private var audioDuration: Int = -1
    private var listener: PlayerEventListener? = null

    constructor(eventCallback: PlayerEventCallback) {
        this.eventCallback = eventCallback
        val trackSelector = DefaultTrackSelector()
        player = ExoPlayerFactory.newSimpleInstance(MainApplication.getInstance(), trackSelector)
        listener = PlayerEventListener()
        player.setAudioAttributes(AudioAttributes.DEFAULT,  /* handleAudioFocus= */true)
        dataSourceFactory = DefaultDataSourceFactory(MainApplication.getInstance(), Util.getUserAgent(MainApplication.getInstance(), "Ringtone"))
    }

    public fun setDataSource(audioPath: String) {
        audioDuration = -1
        player.stop()
        player.removeListener(listener)
        var audioSource: MediaSource = ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(audioPath))
        player.playWhenReady = true
        player.prepare(audioSource)
        player.addListener(listener)
    }

    fun pause() {
        player.playWhenReady = false
    }

    fun start() {
        player.playWhenReady = true
    }

    fun release() {
        player.release()
    }

    fun getDuration(): Int {
        return player.duration.toInt()
    }


    fun getCurrentDuration(): Int {
        return player.currentPosition.toInt()
    }
    fun isPlaying(): Boolean {
        return player.playbackState == Player.STATE_READY && player.playWhenReady
    }

    fun isBuffering(): Boolean {
        return player.playbackState != Player.STATE_READY
    }

    private inner class PlayerEventListener : Player.EventListener {
        override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
            //Log.d(TAG, "playbackState: " + playbackState + "playWhenReady: " + playWhenReady);
            if (playbackState == Player.STATE_READY && (audioDuration == -1)) {
                audioDuration = getDuration()
                if (eventCallback != null) {
                    eventCallback.onPrepared()
                }
            } else if ((playbackState == Player.STATE_ENDED) && (audioDuration > 0)) {
                if (eventCallback != null) {
                    eventCallback.onCompletion()
                }
            }
        }

        override fun onPlayerError(error: ExoPlaybackException?) {
            if (eventCallback != null) {
                error?.let { eventCallback.onError(it) }
            }
        }

        @SuppressWarnings("ReferenceEquality")
        override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
        }
    }

    interface PlayerEventCallback {
        fun onCompletion()
        fun onPrepared()
        fun onError(e: ExoPlaybackException)
    }
}