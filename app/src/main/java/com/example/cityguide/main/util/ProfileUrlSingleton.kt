import androidx.compose.runtime.mutableStateOf

object ProfileUrlSingleton {

    private val _profileImageUrl = mutableStateOf("")

    val getUrl: String
        get() = _profileImageUrl.value


    fun setUrl(url: String) {
        _profileImageUrl.value = url
    }
}
