package com.capbatu.workify.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.PasswordTransformationMethod
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capbatu.workify.R

@RequiresApi(Build.VERSION_CODES.O)
class PasswordEditText
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
    ) : AppCompatEditText(context, attrs) {
        private var passwordDrawable: Drawable =
            ContextCompat.getDrawable(context, R.drawable.ic_lock) as Drawable
        private var passwordRegex: Regex = Regex(context.getString(R.string.regex_password))

        init {
            inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
            hint = context.getString(R.string.hint_input_password)
            compoundDrawablePadding = 16
            setAutofillHints(AUTOFILL_HINT_PASSWORD)
            setButtonDrawables(startOfTheText = passwordDrawable)

            addTextChangedListener(
                object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int,
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int,
                    ) {
                    }

                    override fun afterTextChanged(s: Editable?) {
                        if (!s.isNullOrEmpty() && !s.matches(passwordRegex)) {
                            error = context.getString(R.string.err_not_a_password)
                        }
                    }
                },
            )
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            transformationMethod = PasswordTransformationMethod.getInstance()
        }

        private fun setButtonDrawables(
            startOfTheText: Drawable? = null,
            topOfTheText: Drawable? = null,
            endOfTheText: Drawable? = null,
            bottomOfTheText: Drawable? = null,
        ) {
            setCompoundDrawablesWithIntrinsicBounds(
                startOfTheText,
                topOfTheText,
                endOfTheText,
                bottomOfTheText,
            )
        }
    }
