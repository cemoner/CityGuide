import com.google.firebase.firestore.DocumentSnapshot
import com.example.cityguide.feature.favorites.domain.model.FavoritePlace
import com.example.cityguide.feature.home.domain.model.Place

fun documentToFavoritePlace(document: DocumentSnapshot): FavoritePlace? {
    val userId = document.getString("userId") ?: return null
    val cityName = document.getString("cityName") ?: return null


    val placeMap = document.get("place") as? Map<String, Any> ?: return null

    // Extract fields from the nested map
    val placeId = placeMap["placeId"] as? String ?: ""
    val name = placeMap["name"] as? String ?: ""
    val address = placeMap["address"] as? String ?: ""
    val rating = (placeMap["rating"] as? Number)?.toDouble() ?: 0.0
    val latitude = (placeMap["latitude"] as? Number)?.toDouble() ?: 0.0
    val longitude = (placeMap["longitude"] as? Number)?.toDouble() ?: 0.0

    // Firestore may return the array as a List<*> so cast safely.
    val photoReferences = (placeMap["photoReferences"] as? List<*>)?.mapNotNull { it as? String } ?: emptyList()

    // Create the Place object
    val place = Place(
        placeId = placeId,
        name = name,
        photoReferences = photoReferences,
        rating = rating,
        latitude = latitude,
        longitude = longitude,
        address = address // Ensure your Place model includes this field if needed.
    )

    return FavoritePlace(
        place = place,
        cityName = cityName,
        userId = userId
    )
}
