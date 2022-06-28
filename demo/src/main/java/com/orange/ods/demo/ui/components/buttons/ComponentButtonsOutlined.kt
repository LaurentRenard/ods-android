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

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.orange.ods.compose.component.button.OdsButtonOutlined
import com.orange.ods.compose.theme.OdsDisplayAppearance
import com.orange.ods.demo.R

@Composable
fun ButtonsOutlined() {
    EnabledDisabledOutlinedButtons(hasIcon = false)

    EnabledDisabledOutlinedButtons(hasIcon = true)

    LightSurface {
        EnabledDisabledOutlinedButtons(hasIcon = false, displayAppearance = OdsDisplayAppearance.ON_LIGHT)
    }
    DarkSurface {
        EnabledDisabledOutlinedButtons(hasIcon = false, displayAppearance = OdsDisplayAppearance.ON_DARK)
    }
}

@Composable
private fun EnabledDisabledOutlinedButtons(hasIcon: Boolean, displayAppearance: OdsDisplayAppearance = OdsDisplayAppearance.DEFAULT) {
    OdsButtonOutlined(
        modifier = Modifier.fullWidthButton(),
        text = stringResource(R.string.component_state_enabled),
        onClick = {},
        iconRes = if (hasIcon) R.drawable.ic_search else null,
        displayAppearance = displayAppearance
    )
    OdsButtonOutlined(
        modifier = Modifier.fullWidthButton(false),
        text = stringResource(R.string.component_state_disabled),
        onClick = {},
        iconRes = if (hasIcon) R.drawable.ic_search else null,
        enabled = false,
        displayAppearance = displayAppearance
    )
}