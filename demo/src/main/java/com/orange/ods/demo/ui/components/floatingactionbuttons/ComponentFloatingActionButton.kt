/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.demo.ui.components.floatingactionbuttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FabPosition
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.orange.ods.compose.component.OdsComponent
import com.orange.ods.compose.component.button.OdsExtendedFloatingActionButton
import com.orange.ods.compose.component.button.OdsFloatingActionButton
import com.orange.ods.compose.component.chip.OdsChoiceChip
import com.orange.ods.compose.component.chip.OdsChoiceChipsFlowRow
import com.orange.ods.demo.R
import com.orange.ods.demo.ui.components.utilities.ComponentCustomizationBottomSheetScaffold
import com.orange.ods.demo.ui.components.utilities.clickOnElement
import com.orange.ods.demo.ui.utilities.composable.CodeImplementationColumn
import com.orange.ods.demo.ui.utilities.composable.FloatingActionButtonTechnicalText
import com.orange.ods.demo.ui.utilities.composable.SwitchListItem

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComponentFloatingActionButton() {
    val context = LocalContext.current
    val fabCustomizationState = rememberFabCustomizationState()

    with(fabCustomizationState) {
        if (size.value == FabCustomizationState.Size.Mini) {
            resetTextAndWidth()
        }

        val modifier = Modifier
            .let {
                if (isFullScreenWidth) it
                    .padding(
                        start = dimensionResource(id = R.dimen.spacing_m),
                        end = dimensionResource(id = R.dimen.spacing_m),
                        bottom = 64.dp
                    )
                    .fillMaxWidth()
                else it
            }

        ComponentCustomizationBottomSheetScaffold(
            bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
            floatingActionButton = {
                if (hasText) {
                    OdsExtendedFloatingActionButton(
                        onClick = {
                            clickOnElement(context, context.getString(R.string.component_floating_action_button))
                        },
                        text = stringResource(id = R.string.component_floating_action_button_add),
                        icon = painterResource(id = R.drawable.ic_plus),
                        modifier = modifier
                    )
                } else {
                    OdsFloatingActionButton(
                        onClick = {
                            clickOnElement(context, context.getString(R.string.component_floating_action_button))
                        },
                        mini = size.value == FabCustomizationState.Size.Mini,
                        icon = painterResource(id = R.drawable.ic_plus),
                        iconContentDescription = stringResource(id = R.string.component_floating_action_button_add),
                        modifier = modifier
                    )
                }
            },
            floatingActionButtonPosition = if (isFullScreenWidth) FabPosition.Center else FabPosition.End,
            bottomSheetContent = {
                OdsChoiceChipsFlowRow(
                    selectedChip = size,
                    modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.spacing_m)),
                    outlinedChips = true
                ) {
                    OdsChoiceChip(textRes = R.string.component_floating_action_button_size_default, value = FabCustomizationState.Size.Default)
                    OdsChoiceChip(textRes = R.string.component_floating_action_button_size_mini, value = FabCustomizationState.Size.Mini)
                }
                SwitchListItem(labelRes = R.string.component_element_text, checked = text, enabled = isTextEnabled)
                SwitchListItem(
                    labelRes = R.string.component_floating_action_button_full_screen_width,
                    checked = fullScreenWidth,
                    enabled = isFullScreenWidthEnabled
                )
            }) {

            CodeImplementationColumn {
                FloatingActionButtonTechnicalText(
                    componentName = if (hasText) OdsComponent.OdsExtendedFloatingActionButton.name else OdsComponent.OdsFloatingActionButton.name,
                    text = hasText,
                    fullScreenWidth = isFullScreenWidth,
                    mini = size.value == FabCustomizationState.Size.Mini
                )
            }
        }
    }
}