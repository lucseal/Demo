package com.samuel.demo.nav

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.samuel.demo.R
import com.samuel.demo.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_2.*

/**
 * @Description:
 * @author sunyao
 * @date 2018/10/26 5:46 PM
 */
class Page2Fragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_2, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        to_fragment_main.setOnClickListener {
            Navigation.findNavController(it).navigateUp()
        }

        to_fragment_3.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.action_page2Fragment_to_nav_3)
        }
    }
}