/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.demo.ui.components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.orange.ods.demo.ui.components.bottomnavigation.ComponentBottomNavigation
import com.orange.ods.demo.ui.components.checkboxes.ComponentCheckboxes
import com.orange.ods.demo.ui.components.dialogs.ComponentDialog
import com.orange.ods.demo.ui.components.lists.ComponentLists
import com.orange.ods.demo.ui.components.progress.ComponentProgress
import com.orange.ods.demo.ui.components.radiobuttons.ComponentRadioButtons
import com.orange.ods.demo.ui.components.sliders.ComponentSliders
import com.orange.ods.demo.ui.components.switches.ComponentSwitches

@ExperimentalMaterialApi
@Composable
fun ComponentDemoScreen(
    componentId: Long,
    updateTopBarTitle: (Int) -> Unit,
) {
    val component = remember { components.firstOrNull { it.id == componentId } }

    component?.let {
        updateTopBarTitle(component.titleRes)
        when (component) {
            Component.BottomNavigation -> ComponentBottomNavigation()
            Component.Checkboxes -> ComponentCheckboxes()
            Component.Dialogs -> ComponentDialog()
            Component.Lists -> ComponentLists()
            Component.Progress -> ComponentProgress()
            Component.RadioButtons -> ComponentRadioButtons()
            Component.Sliders -> ComponentSliders()
            Component.Switches -> ComponentSwitches()
            else -> {}
        }
    }

}
