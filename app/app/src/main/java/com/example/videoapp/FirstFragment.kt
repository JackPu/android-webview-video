package com.example.videoapp

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.TimelineChangeReason
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.source.hls.HlsManifest
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Log
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.fragment_first.*

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (context != null) {
            val player = SimpleExoPlayer.Builder(context!!).build()
            exoplayer_view.player = player
            val dataSourceFactory: DataSource.Factory = DefaultDataSourceFactory(
                context,
                Util.getUserAgent(context!!, "yourApplicationName")
            )

            val mp4uri = "https://media.vued.vanthink.cn/CJ7%20-%20Trailer.mp4"
            val hlsuri = "https://media.wxzxzj.com/the_garden_of_words_trailer_english__1080p.m3u8"
            val hlsMediaSource =
                HlsMediaSource.Factory(dataSourceFactory).setAllowChunklessPreparation(true).createMediaSource(Uri.parse(hlsuri))
//            val videoSource: MediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(Uri.parse(hlsuri))
            player.prepare(hlsMediaSource)
            player.addListener(
                object : Player.EventListener {
                    override fun onTimelineChanged(
                        timeline: Timeline, @TimelineChangeReason reason: Int
                    ) {
                        val manifest: Any? = player.getCurrentManifest()
                        if (manifest != null) {
                            val hlsManifest = manifest as HlsManifest
                            for (item in hlsManifest.mediaPlaylist.segments) {
                                Log.d("hls", item.url)
                            }
                            // Do something with the manifest.
                        }
                    }
                })
        }




    }
}
