import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ProfileImageUrlSingleton {

    private val _profileImageUrl = MutableStateFlow("")
    val profileImageUrl: StateFlow<String> get() = _profileImageUrl

    fun setProfileImageUrl(url: String) {
        _profileImageUrl.value = url
    }
}