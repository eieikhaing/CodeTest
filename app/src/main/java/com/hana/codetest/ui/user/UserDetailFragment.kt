package com.hana.codetest.ui.user

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hana.codetest.base.BaseFragment
import com.hana.codetest.databinding.FragmentSplashBinding
import com.hana.codetest.databinding.FragmentUserDetailBinding
import com.hana.codetest.models.user.User
import com.hana.codetest.navigator.AppNavigator
import com.hana.codetest.navigator.Screens
import com.hana.codetest.utilities.provideNavigator

class UserDetailFragment : BaseFragment() {
    private lateinit var binding: FragmentUserDetailBinding

    private lateinit var navigator: AppNavigator

    override fun observeViewModel() {

    }

    override fun initViewBinding() {
        binding = FragmentUserDetailBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navigator = requireView().provideNavigator()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var user = arguments?.getSerializable("user") as User
        binding.apply {
            tvId.text = user.id.toString()
            tvName.text = user.name
            tvEmail.text = user.email
        }
        return binding.root
    }
}