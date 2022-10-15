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
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import info.fandroid.androidconditionalnavigation.databinding.FragmentAccountBinding

class AccountFragment : Fragment() {

    // Get a reference to the ViewModel scoped to this Fragment
    private val viewModel by viewModels<LoginViewModel>()
    private lateinit var binding: FragmentAccountBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeAuthenticationState()
    }

    private fun observeAuthenticationState() {
        viewModel.authenticationState.observe(viewLifecycleOwner, Observer { authenticationState ->
            if (authenticationState == LoginViewModel.AuthenticationState.AUTHENTICATED) {

                val hello = String.format(
                    resources.getString(
                        R.string.welcome_message,
                        FirebaseAuth.getInstance().currentUser?.displayName))

                binding.tvWelcome.text = hello

                binding.logoutButton.setOnClickListener {
                    AuthUI.getInstance().signOut(requireContext())
                    findNavController().popBackStack()

                }
            }
        })
    }


}