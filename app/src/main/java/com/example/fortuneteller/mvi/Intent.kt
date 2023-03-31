package com.gadalka.mvi

sealed class Intent {
    class OnCardClick(val id: Int) : Intent()
    class CardDescriptionClick(val id: Int) : Intent()
    object ShuffleCards : Intent()
    object ClearDescriptions : Intent()
    class ShowInfo(val isShow: Boolean) : Intent()
    class AccelerometerData(val values: FloatArray) : Intent()
    object TurnOffVibrate : Intent()
    class SetAdStatus(val isShow: Boolean) : Intent()
}
