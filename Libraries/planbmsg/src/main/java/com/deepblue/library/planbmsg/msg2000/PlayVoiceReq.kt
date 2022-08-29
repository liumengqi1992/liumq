package com.deepblue.library.planbmsg.msg2000

import com.deepblue.library.planbmsg.Request

class PlayVoiceReq(voice_text: String, voice_flag: Int) : Request(2026, "播放语音") {
    init {
        json = Data(voice_text, voice_flag)
    }

    class Data(val voice_text: String, val voice_flag: Int)
}