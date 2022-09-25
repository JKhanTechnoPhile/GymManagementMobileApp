package com.fitness.enterprise.management.auth.ui

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.auth.model.RegisterUserRequest
import com.fitness.enterprise.management.auth.viewmodel.UserAuthViewModel
import com.fitness.enterprise.management.databinding.FragmentUserRegistrationBinding
import com.fitness.enterprise.management.utils.AlertDialog
import com.fitness.enterprise.management.utils.NetworkResult
import com.fitness.enterprise.management.utils.UserIdTypeEnum
import com.fitness.enterprise.management.utils.UserRoleEnum
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRegistrationFragment : Fragment() {

    private val TAG = UserRegistrationFragment::class.java.name

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    private val userAuthViewModel by viewModels<UserAuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentUserRegistrationBinding.inflate(inflater, container, false)
        val userRolesAsList = UserRoleEnum.values()
        val userRoles = userRolesAsList.map { userRoleEnum -> userRoleEnum.getUserRoleAsString() }
        val userRolesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, userRoles)
        (binding.userRoleTextField.editText as? AutoCompleteTextView)?.setAdapter(userRolesAdapter)
        (binding.userRoleTextField.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, position, id ->
            val userRoleEnum = userRolesAsList.get(position)
            Log.d(TAG, "Selected User Role: ${userRoleEnum.getUserRoleAsString()} and Code: ${userRoleEnum.getUserRoleAsCode()}")
        }

//        https://developer.android.com/codelabs/camerax-getting-started#0
//        https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/
        val userIdTypeAsList = UserIdTypeEnum.values()
        val userIdType = userIdTypeAsList.map { userIdTypeEnum -> userIdTypeEnum.getUserIdAsString() }
        val userIdTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, userIdType)
        (binding.userIdTypeTextField.editText as? AutoCompleteTextView)?.setAdapter(userIdTypeAdapter)
        (binding.userIdTypeTextField.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, position, id ->
            val userIdTypeEnum = userIdTypeAsList.get(position)
            Log.d(TAG, "Selected User Id Type: ${userIdTypeEnum.getUserIdAsString()} and Code: ${userIdTypeEnum.getUserIdAsCode()}")
        }

        binding.signUpButton.setOnClickListener {
            val registerUserRequest = RegisterUserRequest("devTestRolePlatformAdmin@gmail.com", "BLRHE", 1, "devTest123", "0123456789", 101, "", "", "", "Dev Test RolePlatformAdmin", "ROLE_PLATFORM_ADMIN")
            userAuthViewModel.registerUser(registerUserRequest)
        }
        binding.alreadyHaveAnAccountTextview.setOnClickListener {
            findNavController().navigate(R.id.action_userRegistrationFragment_to_userLoginFragment)
        }

        binding.userBranchTextField.editText?.inputType = InputType.TYPE_NULL
        binding.userBranchTextField.setEndIconOnClickListener {
            Log.d(TAG, "userBranchTextField clicked")
            userAuthViewModel.getAllGymBranches()
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAuthViewModel.registerUserResponseLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    findNavController().navigate(R.id.action_userRegistrationFragment_to_userLoginFragment)
                }
                is NetworkResult.Error -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    AlertDialog.showAlert(requireContext(), "User Registration", it.message, positiveButtonText = "OK")
                }
                is NetworkResult.Loading -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.VISIBLE
                }
            }
        })

        userAuthViewModel.gymBranchesResponseLiveData.observe(viewLifecycleOwner, {
            when(it) {
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    //TODO:Show simple dialog with branch selection
                    it.data?.let { gymBranches ->
                        userAuthViewModel.showGymBranchSelection(requireActivity(), gymBranches)
                    }
                }
                is NetworkResult.Error -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    AlertDialog.showAlert(requireContext(), "User Registration", it.message, positiveButtonText = "OK")
                }
                is NetworkResult.Loading -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.VISIBLE
                }
            }
        })

        userAuthViewModel.selectedGymBranchLiveData.observe(viewLifecycleOwner, {
            val stringBuilder = StringBuilder()
            stringBuilder.append(it.gymName)
            stringBuilder.append(", ")
            stringBuilder.append(it.gymFullAddress)
            binding.userBranchTextField.editText?.setText(stringBuilder.toString())
            binding.userBranchTextField.setEndIconDrawable(android.R.drawable.ic_menu_edit)
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}