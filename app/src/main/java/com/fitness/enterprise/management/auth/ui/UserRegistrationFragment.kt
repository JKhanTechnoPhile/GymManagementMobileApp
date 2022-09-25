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
import com.fitness.enterprise.management.common.api.gym.branch.model.GymBranch
import com.fitness.enterprise.management.databinding.FragmentUserRegistrationBinding
import com.fitness.enterprise.management.utils.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserRegistrationFragment : Fragment() {

    private val TAG = UserRegistrationFragment::class.java.name

    private var _binding: FragmentUserRegistrationBinding? = null
    private val binding get() = _binding!!

    private val userAuthViewModel by viewModels<UserAuthViewModel>()

    private var selectedUserRole: UserRoleEnum? = null
    private var selectedUserIdType: UserIdTypeEnum? = null
    private lateinit var selectedUserBranch: GymBranch

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
            selectedUserRole = userRolesAsList.get(position)
            Log.d(TAG, "Selected User Role: ${selectedUserRole?.getUserRoleAsString()} and Code: ${selectedUserRole?.getUserRoleAsCode()}")
        }

//        https://developer.android.com/codelabs/camerax-getting-started#0
//        https://www.geeksforgeeks.org/how-to-select-an-image-from-gallery-in-android/
        val userIdTypeAsList = UserIdTypeEnum.values()
        val userIdType = userIdTypeAsList.map { userIdTypeEnum -> userIdTypeEnum.getUserIdAsString() }
        val userIdTypeAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, userIdType)
        (binding.userIdTypeTextField.editText as? AutoCompleteTextView)?.setAdapter(userIdTypeAdapter)
        (binding.userIdTypeTextField.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, position, id ->
            selectedUserIdType = userIdTypeAsList.get(position)
            Log.d(TAG, "Selected User Id Type: ${selectedUserIdType?.getUserIdAsString()} and Code: ${selectedUserIdType?.getUserIdAsCode()}")
        }

        binding.signUpButton.setOnClickListener {

            val userRoleAsString = selectedUserRole?.getUserRoleAsString()
            val userRoleAsCode = selectedUserRole?.getUserRoleAsCode()
            val userName = binding.userNameTextField.editText?.text.toString()
            val userPhoneNumber = binding.userPhoneNumberTextField.editText?.text.toString()
            val userEmailId = binding.userEmailIdTextField.editText?.text.toString()
            val userIdAsString = selectedUserIdType?.getUserIdAsString()
            val userIdNumber = binding.userIdNumberTextField.editText?.text.toString()
            val userPassword = binding.userPasswordTextField.editText?.text.toString()

            userAuthViewModel.registerUser(userEmailId, userPassword, userPhoneNumber, userRoleAsCode ?: Constants.DEFAULT_VALUE, userIdNumber, userIdAsString ?: Constants.EMPTY_STRING, userName, userRoleAsString ?: Constants.EMPTY_STRING)
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

        userAuthViewModel.registerUserResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    findNavController().navigate(R.id.action_userRegistrationFragment_to_userLoginFragment)
                }
                is NetworkResult.Error -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    AlertDialog.showAlert(
                        requireContext(),
                        "User Registration",
                        it.message,
                        positiveButtonText = "OK"
                    )
                }
                is NetworkResult.Loading -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.VISIBLE
                }
            }
        }

        userAuthViewModel.gymBranchesResponseLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    it.data?.let { gymBranches ->
                        userAuthViewModel.showGymBranchSelection(requireActivity(), gymBranches)
                    }
                }
                is NetworkResult.Error -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    AlertDialog.showAlert(
                        requireContext(),
                        "User Registration",
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
                "User Registration",
                it,
                positiveButtonText = "OK"
            )
        }

        userAuthViewModel.selectedGymBranchLiveData.observe(viewLifecycleOwner) {
            selectedUserBranch = it
            val stringBuilder = StringBuilder()
            stringBuilder.append(it.gymName)
            stringBuilder.append(", ")
            stringBuilder.append(it.gymFullAddress)
            binding.userBranchTextField.editText?.setText(stringBuilder.toString())
            binding.userBranchTextField.setEndIconDrawable(android.R.drawable.ic_menu_edit)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}