import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object ProfileNameSingleton {
    private val _profileName = MutableStateFlow("")

    val profileName: StateFlow<String> get() = _profileName

    fun setName(name: String) {
        _profileName.value = name
    }
}