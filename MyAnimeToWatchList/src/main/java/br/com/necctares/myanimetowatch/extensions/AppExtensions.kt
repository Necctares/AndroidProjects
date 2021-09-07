package br.com.necctares.myanimetowatch.extensions

import br.com.necctares.myanimetowatch.R
import com.google.android.material.textfield.TextInputLayout
import java.text.SimpleDateFormat
import java.util.*

private val locale = Locale("pt", "BR")

fun Date.format() : String{
    return SimpleDateFormat("dd/MM/yyyy", locale).format(this)
}

var TextInputLayout.text : String
    get() = editText?.text?.toString() ?: context.getString(R.string.empty_string_error)
    set(value){
        editText?.setText(value)
    }