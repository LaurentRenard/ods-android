/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui.components.navigationdrawers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.rememberAsyncImagePainter
import com.orange.ods.app.R
import com.orange.ods.app.domain.recipes.LocalCategories
import com.orange.ods.app.domain.recipes.LocalRecipes
import com.orange.ods.app.ui.components.utilities.ComponentCustomizationBottomSheetScaffold
import com.orange.ods.app.ui.components.utilities.ComponentLaunchContentColumn
import com.orange.ods.app.ui.utilities.composable.CodeImplementationColumn
import com.orange.ods.app.ui.utilities.composable.CommonTechnicalTextColumn
import com.orange.ods.app.ui.utilities.composable.Subtitle
import com.orange.ods.compose.component.OdsComponent
import com.orange.ods.compose.component.chip.OdsChoiceChip
import com.orange.ods.compose.component.chip.OdsChoiceChipsFlowRow
import com.orange.ods.compose.component.list.OdsListItem
import com.orange.ods.compose.component.list.OdsSwitchTrailing
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawer
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerDivider
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerHeader
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerItem
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerListItem
import com.orange.ods.compose.component.navigationdrawer.OdsModalDrawerSectionLabel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ComponentModalDrawers() {
    val customizationState = rememberNavigationDrawersCustomizationState()
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val recipes = LocalRecipes.current
    val categories = LocalCategories.current
    val list: MutableList<OdsModalDrawerItem> = categories.map {
        OdsModalDrawerListItem(if (customizationState.isListIconChecked) recipes[1].iconResId else null, it.name)
    }.toMutableList()
    if (customizationState.hasDivider) list.add(3, OdsModalDrawerDivider)
    if (customizationState.hasLabel) list.add(3, OdsModalDrawerSectionLabel("Ingredients"))
    OdsModalDrawer(
        drawerHeader = OdsModalDrawerHeader(
            title = stringResource(id = R.string.component_modal_drawers),
            backgroundImage = if (customizationState.hasBackground) rememberAsyncImagePainter(
                model = rememberSaveable { recipes.filter { it.description.isNotBlank() }.random() }.imageUrl,
                placeholder = painterResource(id = R.drawable.placeholder),
                error = painterResource(id = R.drawable.placeholder)
            ) else null,
            subtitle = if (customizationState.isSubTitleChecked) stringResource(id = R.string.component_element_example) else null,
            avatar = if (customizationState.hasAvatar) painterResource(id = R.drawable.placeholder) else null
        ),
        drawerContentList = if (customizationState.isContentExampleChecked) list else emptyList(),
        drawerState = drawerState,
        content = {
            with(customizationState) {
                if (!isContentExampleChecked) {
                    listIconChecked.value = false
                    subTitleChecked.value = false
                }
                ComponentCustomizationBottomSheetScaffold(
                    bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
                    bottomSheetContent = {
                        OdsListItem(
                            text = stringResource(id = R.string.component_modal_drawer_content_example),
                            trailing = OdsSwitchTrailing(checked = contentExampleChecked)
                        )
                        OdsChoiceChipsFlowRow(
                            selectedChip = header,
                            outlinedChips = true,
                            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.screen_horizontal_margin)),
                        ) {
                            Subtitle(textRes = R.string.component_modal_drawer_header_image)
                            OdsChoiceChip(textRes = R.string.component_element_avatar, value = ComponentNavigationDrawersContentState.HeaderImage.Avatar)
                            OdsChoiceChip(
                                textRes = R.string.component_modal_drawer_background,
                                value = ComponentNavigationDrawersContentState.HeaderImage.Background
                            )
                            OdsChoiceChip(textRes = R.string.component_element_none, value = ComponentNavigationDrawersContentState.HeaderImage.None)
                        }
                        OdsListItem(
                            text = stringResource(id = R.string.component_modal_drawer_subtitle),
                            trailing = OdsSwitchTrailing(
                                checked = subTitleChecked,
                                enabled = isContentExampleChecked
                            )
                        )
                        OdsListItem(
                            text = stringResource(id = R.string.component_modal_drawer_list_icon),
                            trailing = OdsSwitchTrailing(
                                checked = listIconChecked,
                                enabled = isContentExampleChecked
                            ),
                        )
                        OdsChoiceChipsFlowRow(
                            selectedChip = content,
                            outlinedChips = true,
                            modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.screen_horizontal_margin)),
                        ) {
                            Subtitle(textRes = R.string.component_modal_drawer_list_example)
                            OdsChoiceChip(
                                textRes = R.string.component_element_divider,
                                value = ComponentNavigationDrawersContentState.SectionListExample.Divider
                            )
                            OdsChoiceChip(
                                textRes = R.string.component_element_label,
                                value = ComponentNavigationDrawersContentState.SectionListExample.Label
                            )
                            OdsChoiceChip(textRes = R.string.component_element_none, value = ComponentNavigationDrawersContentState.SectionListExample.None)
                        }
                    }) {
                    Column {
                        ComponentLaunchContentColumn(
                            textRes = R.string.component_modal_drawer_content,
                            buttonLabelRes = R.string.component_modal_drawer_open,
                            onButtonClick = { scope.launch { drawerState.open() } }
                        )
                        CodeImplementationColumn {
                            CommonTechnicalTextColumn(
                                componentName = OdsComponent.OdsModalDrawer.name
                            ) {
                            }
                        }
                    }
                }
            }
        }
    )

}