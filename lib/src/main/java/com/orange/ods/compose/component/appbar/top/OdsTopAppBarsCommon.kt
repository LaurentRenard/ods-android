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

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.orange.ods.R
import com.orange.ods.compose.component.content.OdsComponentContent
import com.orange.ods.compose.component.content.OdsComponentIcon
import com.orange.ods.compose.component.menu.OdsDropdownMenu
import com.orange.ods.compose.component.menu.OdsDropdownMenuItem
import com.orange.ods.compose.theme.OdsTheme

@Composable
internal fun OdsTopAppBarActions(actions: List<OdsComponentContent>, overflowMenuActions: List<OdsTopAppBarOverflowMenuActionItem>) {
    val maxTotalActionCount = 3
    val maxActionCount = if (overflowMenuActions.isNotEmpty()) maxTotalActionCount - 1 else maxTotalActionCount
    actions.take(maxActionCount).forEach { it.Content() }
    if (overflowMenuActions.isNotEmpty()) {
        Box {
            var showMenu by remember { mutableStateOf(false) }
            val contentDescription = stringResource(id = R.string.top_app_bar_overflow_menu_content_description)
            val dropdownMenuAction = OdsTopAppBarActionButton(Icons.Filled.MoreVert, contentDescription, true) { showMenu = !showMenu }
            dropdownMenuAction.Content()
            OdsDropdownMenu(
                items = overflowMenuActions,
                expanded = showMenu,
                onDismissRequest = { showMenu = false }
            )
        }
    }
}

/**
 * A navigation icon in an [OdsTopAppBar].
 */
class OdsTopAppBarNavigationIcon : OdsComponentIcon {

    /**
     * Creates an instance of [OdsTopAppBarNavigationIcon].
     *
     * @param painter Painter of the icon.
     * @param contentDescription The content description associated to this [OdsTopAppBarNavigationIcon].
     * @param onClick Will be called when the user clicks on the action icon button.
     */
    constructor(painter: Painter, contentDescription: String, onClick: () -> Unit) : super(painter as Any, contentDescription, onClick = onClick)

    /**
     * Creates an instance of [OdsTopAppBarNavigationIcon].
     *
     * @param imageVector Image vector of the icon.
     * @param contentDescription The content description associated to this [OdsTopAppBarNavigationIcon].
     * @param onClick Will be called when the user clicks on the action icon button.
     */
    constructor(imageVector: ImageVector, contentDescription: String, onClick: () -> Unit) : super(imageVector as Any, contentDescription, onClick = onClick)

    /**
     * Creates an instance of [OdsTopAppBarNavigationIcon].
     *
     * @param bitmap Image bitmap of the icon.
     * @param contentDescription The content description associated to this [OdsTopAppBarNavigationIcon].
     * @param onClick Will be called when the user clicks on the action icon button.
     */
    constructor(bitmap: ImageBitmap, contentDescription: String, onClick: () -> Unit) : super(bitmap as Any, contentDescription, onClick = onClick)
}

/**
 * An action button displayed in an [OdsTopAppBar].
 */
open class OdsTopAppBarActionButton : OdsComponentIcon {

    /**
     * Creates an instance of [OdsTopAppBarActionButton].
     *
     * @param painter Painter of the icon.
     * @param contentDescription The content description associated to this [OdsTopAppBarActionButton].
     * @param enabled whether or not this [OdsTopAppBarActionButton] will handle input events and appear enabled for
     * semantics purposes, true by default.
     * @param onClick Will be called when the user clicks on the action icon button.
     */
    constructor(
        painter: Painter,
        contentDescription: String,
        enabled: Boolean = true,
        onClick: () -> Unit
    ) : super(painter as Any, contentDescription, enabled, onClick)

    /**
     * Creates an instance of [OdsTopAppBarActionButton].
     *
     * @param imageVector Image vector of the icon.
     * @param contentDescription The content description associated to this [OdsTopAppBarActionButton].
     * @param enabled whether or not this [OdsTopAppBarActionButton] will handle input events and appear enabled for
     * semantics purposes, true by default.
     * @param onClick Will be called when the user clicks on the action icon button.
     */
    constructor(
        imageVector: ImageVector,
        contentDescription: String,
        enabled: Boolean = true,
        onClick: () -> Unit
    ) : super(imageVector as Any, contentDescription, enabled, onClick)

    /**
     * Creates an instance of [OdsTopAppBarActionButton].
     *
     * @param bitmap Image bitmap of the icon.
     * @param contentDescription The content description associated to this [OdsTopAppBarActionButton].
     * @param enabled whether or not this [OdsTopAppBarActionButton] will handle input events and appear enabled for
     * semantics purposes, true by default.
     * @param onClick Will be called when the user clicks on the action icon button.
     */
    constructor(
        bitmap: ImageBitmap,
        contentDescription: String,
        enabled: Boolean = true,
        onClick: () -> Unit
    ) : super(bitmap as Any, contentDescription, enabled, onClick)

    override val tint: Color?
        @Composable
        get() = OdsTheme.colors.component.topAppBar.barContent
}

typealias OdsTopAppBarOverflowMenuActionItem = OdsDropdownMenuItem
