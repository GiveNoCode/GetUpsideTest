package com.givenocode.getupsidetest.ui

sealed class DisplayMode {
    object Map: DisplayMode()
    object List: DisplayMode()
}