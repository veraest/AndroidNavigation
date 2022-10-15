package info.fandroid.androidconditionalnavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import info.fandroid.androidconditionalnavigation.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, { authenticationState ->
            when (authenticationState) {
                LoginViewModel.AuthenticationState.AUTHENTICATED -> {

                    binding.accountButton.setOnClickListener {
                        findNavController().navigate(R.id.accountFragment)
                    }
                } else -> {
                    binding.accountButton.setOnClickListener {
                        findNavController().navigate(R.id.loginFragment)
                    }
                }
            }
        })
    }
}