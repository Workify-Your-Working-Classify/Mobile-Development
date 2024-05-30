package com.capbatu.workify.view

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import com.capbatu.workify.R

@RequiresApi(Build.VERSION_CODES.O)
class NameEditText
    @JvmOverloads
    constructor(
        context: Context,
        attrs: AttributeSet? = null,
    ) : AppCompatEditText(context, attrs) {
        private var nameDrawable: Drawable =
            ContextCompat.getDrawable(context, R.drawable.ic_person) as Drawable

        init {
            hint = context.getString(R.string.hint_input_name)
            compoundDrawablePadding = 16
            setAutofillHints(AUTOFILL_HINT_NAME)
            setButtonDrawables(startOfTheText = nameDrawable)

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
                        if (s.isNullOrEmpty()) {
                            error = context.getString(R.string.err_input_reqired)
                        }
                    }
                },
            )
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
