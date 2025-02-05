import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.cityguide.feature.home.presentation.contract.CategoryPageContract.SortType

@Composable
fun SortDropdown(
    currentSort: SortType,
    onSortSelected: (SortType) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    // Store the resource id for the current sort (Int)
    val dropDownCurrentSort = remember { mutableStateOf(currentSort.nameRes) }
    val sortOptions = listOf(SortType.Name(), SortType.Rating(), SortType.Distance())

    Box {
        Button(
            onClick = { expanded.value = true },
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.background,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            modifier = Modifier.padding(4.dp)
        ) {
            Text(text = stringResource(id = dropDownCurrentSort.value))
            Icon(
                imageVector = Icons.Filled.ArrowDropDown,
                contentDescription = "Dropdown Arrow",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier.background(MaterialTheme.colorScheme.background)
        ) {
            sortOptions.forEach { sortType ->
                DropdownMenuItem(
                    onClick = {
                        onSortSelected(sortType)
                        dropDownCurrentSort.value = sortType.nameRes
                        expanded.value = false
                    },
                    text = {
                        Text(
                            text = stringResource(id = sortType.nameRes),
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                )
            }
        }
    }
}
