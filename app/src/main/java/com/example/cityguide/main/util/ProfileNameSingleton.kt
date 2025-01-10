import androidx.compose.runtime.mutableStateOf

object ProfileNameSingleton {

    private val _profileName = mutableStateOf("")

    val getName: String
        get() = _profileName.value


    fun setName(url: String) {
        _profileName.value = url
    }
}
