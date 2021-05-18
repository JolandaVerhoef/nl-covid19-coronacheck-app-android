/*
 *  Copyright (c) 2021 De Staat der Nederlanden, Ministerie van Volksgezondheid, Welzijn en Sport.
 *   Licensed under the EUROPEAN UNION PUBLIC LICENCE v. 1.2
 *
 *   SPDX-License-Identifier: EUPL-1.2
 *
 */

package nl.rijksoverheid.ctr.design.views

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.google.android.material.button.MaterialButton
import nl.rijksoverheid.ctr.design.R
import nl.rijksoverheid.ctr.design.ext.getThemeColor

class OutlinedToggleButtonWidget @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.style.Widget_App_Button_Toggle
) : MaterialButton(context, attrs, defStyleAttr) {

    private var isToggled: Boolean = false

    fun setToggled(isToggled: Boolean) {
        this.isToggled = isToggled
        if (isToggled) {
            backgroundTintList = context.getThemeColor(R.attr.colorSurface)
            setStrokeColorResource(R.color.primary_blue)
            ViewCompat.setElevation(this, 2f)
        } else {
            backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(
                    this.context,
                    android.R.color.transparent
                )
            )
            setStrokeColorResource(android.R.color.transparent)
            ViewCompat.setElevation(this, 0f)
        }
    }


}