package dev.jai.billgenerator.audio

interface PlayerCallback {
    fun onBeforePlay(ringName: String)
    fun onPlay()
    fun onPauseRingtone()
    fun onResumeRingtone()
    fun onComplete()
    fun onProgress(progress: Int)
    fun onPrepared()
    fun onError()
    fun onResetPlayer()
    fun onAutoPlayFinish()
    fun onCounting(count: Int)
}