package dev.mobiledeveloper.composetestapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class SampleUserCellModel(
    val username: String,
    val avatar: String,
    val description: String
)

sealed interface SampleEvent {
    object ScreenLoaded : SampleEvent
    object FetchMore : SampleEvent
    object RefreshScreen : SampleEvent
}

data class SampleViewState(
    val data: List<SampleUserCellModel> = emptyList()
)

class SampleViewModel : ViewModel() {
    private val _viewState = MutableStateFlow(SampleViewState())
    val viewState: StateFlow<SampleViewState> = _viewState

    fun obtainEvent(event: SampleEvent) {
        when (event) {
            SampleEvent.ScreenLoaded -> fetchInitialData()
            SampleEvent.FetchMore -> fetchMoreData()
            SampleEvent.RefreshScreen -> refreshData()
        }
    }

    private fun fetchInitialData() {
        viewModelScope.launch {
            delay(1000)
            val data = generateData(20)
            _viewState.value = SampleViewState(data)
        }
    }

    private fun fetchMoreData() {

    }

    private fun refreshData() {

    }

    private fun generateData(count: Int): List<SampleUserCellModel> {
        val data = mutableListOf<SampleUserCellModel>()
        for (i in 0 until count) {
            data.add(
                SampleUserCellModel(
                    username = generateRandomUsername(),
                    avatar = generateRandomAvatar(),
                    description = generateRandomDescription()
                )
            )
        }

        return data
    }
}

internal fun generateRandomAvatar(): String = listOf(
    "https://games.mail.ru/hotbox/content_files/game/2020/06/16/aed1e04647ec4b64ad63215ef6f57898.jpg",
    "https://games.mail.ru/hotbox/content_files/news/2022/11/30/dc5ac5a2efb0447482dc5c399ab01ff0.jpg",
    "https://digital-report.ru/wp-content/uploads/2022/11/witcher_dr.jpeg",
    "https://pluggedin.ru/images/12-file_2020_07_30_09_15_15.jpg",
    "https://hips.hearstapps.com/hmg-prod/images/thor-1658259662.jpeg",
    "https://cf-images.us-east-1.prod.boltdns.net/v1/static/5359769168001/0a823cb0-01a9-4835-a348-c64187783ccb/d37cb96c-805c-4aa2-9f2f-e62d9eb814c7/1280x720/match/image.jpg",
    "https://www.ixbt.com/img/n1/news/2022/5/5/0c3c20a8d8514501524a0859461f391572ea6e61_large.jpg",
    "https://www.koimoi.com/wp-content/new-galleries/2022/09/robert-downey-jr-is-reprising-iron-man-001.jpg",
    "https://www.looper.com/img/gallery/the-most-memorable-nick-fury-quotes-in-the-mcu/l-intro-1646071443.jpg",
    "https://i.ytimg.com/vi/MAfIvBgChjQ/maxresdefault.jpg",
    "https://m.media-amazon.com/images/M/MV5BZjZjZjFhMmEtODE4ZS00ZDVkLWI5ODMtNDM5NzJmYzJlMTVmXkEyXkFqcGdeQXZ3ZXNsZXk@._V1_.jpg"
).random()


internal fun generateRandomUsername(): String = listOf(
    "Alex Gladkov",
    "Pavel Durov",
    "Tim Cook",
    "Jake Wharton",
    "Dean Winchester",
    "Tommy Gun",
    "Vladimir Filatov",
    "Steve Jobs",
    "Bill Gates",
    "Sergey Brinn"
).random()

internal fun generateRandomDescription(): String = listOf(
    "Scientist, Highest templar of Ancourage",
    "Inventor, Apple CEO",
    "Apple CEO",
    "Telegram CEO, ex-VK CEO",
    "Unholy destroyer, Hunter",
    "Boxer",
    "Businessman",
    "ex-Microsoft CEO",
    "Mobile Developer",
    "Billioner"
).random()