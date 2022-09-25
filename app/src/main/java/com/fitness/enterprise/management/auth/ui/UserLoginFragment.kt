package com.fitness.enterprise.management.auth.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.auth.model.LoginUserRequest
import com.fitness.enterprise.management.auth.model.RegisterUserRequest
import com.fitness.enterprise.management.auth.viewmodel.UserAuthViewModel
import com.fitness.enterprise.management.databinding.FragmentUserLoginBinding
import com.fitness.enterprise.management.databinding.FragmentUserRegistrationBinding
import com.fitness.enterprise.management.utils.AlertDialog
import com.fitness.enterprise.management.utils.NetworkResult
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserLoginFragment : Fragment() {

    private var _binding: FragmentUserLoginBinding? = null
    private val binding get() = _binding!!

    private val userAuthViewModel by viewModels<UserAuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserLoginBinding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener {
            val loginUserRequest = LoginUserRequest("password123", "00123456789")
            userAuthViewModel.loginUser(loginUserRequest)
        }

        binding.alreadyHaveAnAccountTextview.setOnClickListener {
            findNavController().navigate(R.id.action_userLoginFragment_to_userRegistrationFragment)
        }

        binding.forgotPasswordTextview.setOnClickListener {
            findNavController().navigate(R.id.action_userLoginFragment_to_userResetPasswordFragment)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userAuthViewModel.loginUserResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    findNavController().navigate(R.id.action_userRegistrationFragment_to_userLoginFragment)
                }
                is NetworkResult.Error -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    AlertDialog.showAlert(
                        requireContext(),
                        "User Login",
                        it.message,
                        positiveButtonText = "OK"
                    )
                }
                is NetworkResult.Loading -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.VISIBLE
                }
            }
        }
    }
}