package com.diebietse.mtlsandroidexample

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_web_view).setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_WebViewFragment)
        }

        view.findViewById<Button>(R.id.button_ok_http).setOnClickListener {
            findNavController().navigate(R.id.action_MainFragment_to_OkHttpFragment)
        }
    }
}
