/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.compose.component.appbar.top

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.orange.ods.compose.component.OdsComposable
import com.orange.ods.compose.component.textfield.search.OdsSearchTextField
import com.orange.ods.compose.component.utilities.Preview
import com.orange.ods.compose.component.utilities.UiModePreviews
import com.orange.ods.compose.theme.OdsTheme

/**
 * <a href="https://system.design.orange.com/0c1af118d/p/23e0e6-app-bars/b/620966" class="external" target="_blank">ODS Top App Bar</a>.
 *
 * The search variant contains a search text field and an optional navigation icon. Search results are often displayed in the screen below.
 *
 * @param searchHint Hint displayed in the search text field when search value is empty.
 * @param onSearchValueChange Callback invoked when the search value changes. The new value is available in parameter.
 * @param modifier [Modifier] applied to the top app bar.
 * @param searchValue Value of the search text field.
 * @param navigationIcon Icon displayed at the start of the search top app bar before the text field.
 * @param elevated Controls the elevation of the top app bar: `true` to set an elevation to the top app bar (a shadow is displayed below), `false` otherwise.
 */
@Composable
@OdsComposable
fun OdsSearchTopAppBar(
    searchHint: String,
    onSearchValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    searchValue: TextFieldValue = TextFieldValue(),
    navigationIcon: OdsTopAppBar.NavigationIcon? = null,
    elevated: Boolean = true
) {
    TopAppBar(
        title = { },
        modifier = modifier,
        navigationIcon = navigationIcon?.let { { it.Content() } },
        actions = {
            val focusRequester = remember { FocusRequester() }
            OdsSearchTextField(
                value = searchValue,
                onValueChange = onSearchValueChange,
                placeholder = searchHint,
                modifier = modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester)
            )
            LaunchedEffect(Unit) {
                focusRequester.requestFocus()
            }
        },
        backgroundColor = OdsTheme.colors.component.topAppBar.barBackground,
        contentColor = OdsTheme.colors.component.topAppBar.barContent,
        elevation = if (elevated) AppBarDefaults.TopAppBarElevation else 0.dp
    )
}

@UiModePreviews.Default
@Composable
private fun PreviewOdsSearchTopAppBar() = Preview {
    OdsSearchTopAppBar(
        searchHint = "Enter text to search",
        onSearchValueChange = {},
        navigationIcon = OdsTopAppBar.NavigationIcon(Icons.Filled.ArrowBack, "") {},
    )
}