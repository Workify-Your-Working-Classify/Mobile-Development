package com.capbatu.workify.ui.describe

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.capbatu.workify.databinding.ActivityDescribeBinding

class DescribeActivity : AppCompatActivity() {
    private val binding: ActivityDescribeBinding by lazy {
        ActivityDescribeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}
