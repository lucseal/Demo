package com.samuel.demo.nav

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import com.samuel.demo.R
import com.samuel.demo.base.BaseActivity

/**
 * @Description:
 * @author sunyao
 * @date 2018/10/26 6:04 PM
 */
class NavActivity : BaseActivity() {

    companion object {
        fun intent(context: Context): Intent {
            val intent = Intent(context, NavActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nav)
    }


    override fun onSupportNavigateUp(): Boolean {
        return findNavController(this, R.id.my_nav_host_fragment).navigateUp()
    }
}