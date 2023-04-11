/*
 *
 *  Copyright 2021 Orange
 *
 *  Use of this source code is governed by an MIT-style
 *  license that can be found in the LICENSE file or at
 *  https://opensource.org/licenses/MIT.
 * /
 */

package com.orange.ods.app.ui.components.textfields

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.orange.ods.app.R
import com.orange.ods.app.ui.utilities.composable.CodeImplementationColumn
import com.orange.ods.app.ui.utilities.composable.ComponentCode
import com.orange.ods.app.ui.utilities.composable.TextValueParameter
import com.orange.ods.compose.component.OdsComponent
import com.orange.ods.compose.component.textfield.password.OdsPasswordTextField

@Composable
fun TextFieldPassword(customizationState: TextFieldCustomizationState) {
    val label = stringResource(id = R.string.component_element_label)
    val placeholder = stringResource(id = R.string.component_text_field_placeholder)
    val errorMessage = stringResource(id = R.string.component_text_field_error_message)

    with(customizationState) {
        Column {
            OdsPasswordTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimensionResource(id = R.dimen.spacing_s)),
                enabled = isEnabled,
                isError = isError,
                errorMessage = if (isError) errorMessage else null,
                value = displayedText,
                onValueChange = { updateText(it) },
                label = label,
                placeholder = placeholder,
                visualisationIcon = hasVisualisationIcon,
                keyboardOptions = keyboardOptions,
                characterCounter = if (hasCharacterCounter) {
                    {
                        TextFieldCharacterCounter(valueLength = displayedText.length, enabled = isEnabled)
                    }
                } else null
            )

            CodeImplementationColumn {
                ComponentCode(name = OdsComponent.OdsPasswordTextField.name, parameters = mutableListOf(
                    TextValueParameter.BetweenQuotesParameter("value", displayedText),
                    TextValueParameter.LambdaParameter("onValueChange"),
                    TextValueParameter.Label(label),
                    TextValueParameter.Placeholder(placeholder),
                    TextValueParameter.ValueOnlyParameter("keyboardOptions", "<KeyboardOptions>") // TODO
                ).apply {
                    if (!hasVisualisationIcon) add(TextValueParameter.StringRepresentationParameter("visualisationIcon", false))
                    if (!isEnabled) add(TextValueParameter.Enabled(false))
                    if (isError) {
                        add(TextValueParameter.StringRepresentationParameter("isError", true))
                        add(TextValueParameter.BetweenQuotesParameter("errorMessage", errorMessage))
                    }
                    if (hasCharacterCounter) add(
                        TextValueParameter.ValueOnlyParameter(
                            "characterCounter",
                            "<OdsTextFieldCharacterCounter>"
                        )
                    ) // TODO
                })
            }
        }
    }
}