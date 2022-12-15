/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.demo.ui.components.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.orange.ods.compose.component.OdsComponent
import com.orange.ods.compose.component.button.OdsIconButton
import com.orange.ods.demo.R
import com.orange.ods.demo.ui.components.utilities.ComponentCustomizationBottomSheetScaffold
import com.orange.ods.demo.ui.utilities.composable.ButtonTechnicalText
import com.orange.ods.demo.ui.utilities.composable.CodeImplementationColumn
import com.orange.ods.demo.ui.utilities.composable.SwitchListItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ButtonsIcon() {
    val buttonCustomizationState = rememberButtonCustomizationState()

    with(buttonCustomizationState) {
        ComponentCustomizationBottomSheetScaffold(
            bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
            bottomSheetContent = {
                SwitchListItem(labelRes = R.string.component_state_disabled, checked = disabled)
            }) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = dimensionResource(id = R.dimen.screen_vertical_margin))
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    OdsIconButton(
                        onClick = {},
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "",
                        enabled = isEnabled
                    )
                }

                Spacer(modifier = Modifier.padding(top = dimensionResource(R.dimen.spacing_s)))

                InvertedBackgroundColumn(horizontalAlignment = Alignment.CenterHorizontally) {
                    OdsIconButton(
                        onClick = {},
                        painter = painterResource(id = R.drawable.ic_search),
                        contentDescription = "",
                        enabled = isEnabled,
                        displaySurface = displaySurface
                    )
                }

                CodeImplementationColumn {
                    ButtonTechnicalText(
                        componentName = OdsComponent.OdsIconButton.name,
                        enabled = isEnabled,
                        painter = true
                    )
                }
            }
        }
    }
}
