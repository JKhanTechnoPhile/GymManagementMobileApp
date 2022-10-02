package com.fitness.enterprise.management.auth.ui

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.auth.viewmodel.UserAuthViewModel
import com.fitness.enterprise.management.branch.ui.GymBranchDashboardActivity
import com.fitness.enterprise.management.dashboard.ui.UserDashboardActivity
import com.fitness.enterprise.management.databinding.FragmentUserLoginBinding
import com.fitness.enterprise.management.utils.AlertDialog
import com.fitness.enterprise.management.utils.NetworkResult
import com.fitness.enterprise.management.utils.TokenManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserLoginFragment : Fragment() {

    private var _binding: FragmentUserLoginBinding? = null
    private val binding get() = _binding!!

    private val userAuthViewModel by viewModels<UserAuthViewModel>()

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserLoginBinding.inflate(inflater, container, false)

        binding.signInButton.setOnClickListener {
            userAuthViewModel.loginUser(binding.loginUserIdTextField.editText?.text.toString(), binding.loginUserPasswordTextField.editText?.text.toString())
        }

        binding.alreadyHaveAnAccountTextview.setOnClickListener {
            findNavController().navigate(R.id.action_userLoginFragment_to_userRegistrationFragment)
//            findNavController().popBackStack()
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
                    if (!TextUtils.isEmpty(it.data?.token)) {
                        tokenManager.saveToken(it.data!!.token)
                        val userDashboardActivity = Intent(requireActivity(), GymBranchDashboardActivity::class.java)
                        requireActivity().startActivity(userDashboardActivity)
                        requireActivity().finish()
                    } else {
                        AlertDialog.showAlert(
                            requireContext(),
                            "User Login",
                            "Login failed.",
                            positiveButtonText = "OK"
                        )
                    }
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

        userAuthViewModel.validationMessageLiveData.observe(viewLifecycleOwner) {
            AlertDialog.showAlert(
                requireContext(),
                "User Login",
                it,
                positiveButtonText = "OK"
            )
        }
    }
}