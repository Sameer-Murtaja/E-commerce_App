package com.unit_one.e_commerceapp.ui.select_language

import android.content.Intent
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.unit_one.e_commerceapp.R
import com.unit_one.e_commerceapp.databinding.ActivitySelectLanguageBinding
import com.unit_one.e_commerceapp.ui.SignActivity
import com.unit_one.e_commerceapp.ui.base.BaseActivity

class SelectLanguageActivity : BaseActivity<ActivitySelectLanguageBinding, SelectLanguageViewModel>(
    R.layout.activity_select_language,
    SelectLanguageViewModel::class.java
) {

    override fun addCallbacks() {
        viewModel.selectEnglish.observe(this) {
            if (it) onClickEnglish()
            else onClickArabic()
        }
        viewModel.onDoneClicked.observe(this){
            if(it) onDoneClicked()
        }
    }

    private fun onClickEnglish() {
        unselectAllLanguages()
        selectEnglish()
    }

    private fun onClickArabic() {
        unselectAllLanguages()
        selectArabic()
    }

    private fun onDoneClicked() {
        val intent = Intent(this, SignActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun unselectAllLanguages() {
        binding.tvEnglish.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.tvArabic.setTextColor(ContextCompat.getColor(this, R.color.black))
        binding.checkmarkEnglish.visibility = View.INVISIBLE
        binding.checkmarkArabic.visibility = View.INVISIBLE
        binding.tvEnglish.typeface = ResourcesCompat.getFont(this, R.font.cairo_regular)
        binding.tvArabic.typeface = ResourcesCompat.getFont(this, R.font.cairo_regular)
    }

    private fun selectEnglish() {
        binding.tvEnglish.setTextColor(ContextCompat.getColor(this, R.color.color_primary))
        binding.checkmarkEnglish.visibility = View.VISIBLE
        binding.tvEnglish.typeface = ResourcesCompat.getFont(this, R.font.cairo_semibold)
    }

    private fun selectArabic() {
        binding.tvArabic.setTextColor(ContextCompat.getColor(this, R.color.color_primary))
        binding.checkmarkArabic.visibility = View.VISIBLE
        binding.tvArabic.typeface = ResourcesCompat.getFont(this, R.font.cairo_semibold)
    }


}