package com.fitness.enterprise.management.subscription.ui

import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.fitness.enterprise.management.R
import com.fitness.enterprise.management.auth.viewmodel.UserAuthViewModel
import com.fitness.enterprise.management.branch.model.GymBranch
import com.fitness.enterprise.management.databinding.FragmentCreateGymSubscriptionBinding
import com.fitness.enterprise.management.subscription.model.GymSubscriptionCreateRequest
import com.fitness.enterprise.management.subscription.viewmodel.GymSubscriptionDashboardViewModel
import com.fitness.enterprise.management.utils.AlertDialog
import com.fitness.enterprise.management.utils.Constants
import com.fitness.enterprise.management.utils.GymSubscriptionEnum
import com.fitness.enterprise.management.utils.NetworkResult
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.TextInputLayout
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class CreateGymSubscriptionFragment : Fragment() {

    private val TAG = CreateGymSubscriptionFragment::class.java.name

    private var _binding: FragmentCreateGymSubscriptionBinding? = null
    private val binding get() = _binding!!

    private val gymSubscriptionDashboardViewModel by viewModels<GymSubscriptionDashboardViewModel>()

    private var selectedBranch: GymBranch? = null

    private var selectedSubscriptionFrequency: GymSubscriptionEnum? = null

    private val userAuthViewModel by viewModels<UserAuthViewModel>()

    private val selectedDates = mutableMapOf<Int, Long>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.apply {
            title = "Create Subscription Plan"
        }
        _binding = FragmentCreateGymSubscriptionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gymSubscriptionsAsList = GymSubscriptionEnum.values()
        val gymSubscriptions = gymSubscriptionsAsList.map { gymSubscriptionEnum -> gymSubscriptionEnum.getGymSubscriptionPlanAsString() }
        val userRolesAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_list_item, gymSubscriptions)
        (binding.subscriptionFrequencyTextField.editText as? AutoCompleteTextView)?.setAdapter(userRolesAdapter)
        (binding.subscriptionFrequencyTextField.editText as? AutoCompleteTextView)?.setOnItemClickListener { parent, view, position, id ->
            selectedSubscriptionFrequency = gymSubscriptionsAsList.get(position)
            Log.d(TAG, "Selected User Role: ${selectedSubscriptionFrequency?.getGymSubscriptionPlanAsString()} and Code: ${selectedSubscriptionFrequency?.getGymSubscriptionPlanAsCode()}")
            selectedSubscriptionFrequency?.let {
                binding.subscriptionNameTextField.editText?.setText(it.getGymSubscriptionPlanAsString())
            }
        }

        binding.selectBranchTextField.editText?.inputType = InputType.TYPE_NULL
        binding.selectBranchTextField.setEndIconOnClickListener {
            Log.d(TAG, "selectBranchTextField clicked")
            userAuthViewModel.getAllGymBranches()
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

        binding.planStartDateTextField.editText?.inputType = InputType.TYPE_NULL
        binding.planStartDateTextField.setEndIconOnClickListener {
            Log.d(TAG, "planStartDateTextField clicked")
            chooseDate(binding.planStartDateTextField)
        }

        binding.planEndDateTextField.editText?.inputType = InputType.TYPE_NULL
        binding.planEndDateTextField.setEndIconOnClickListener {
            Log.d(TAG, "planEndDateTextField clicked")
            chooseDate(binding.planEndDateTextField)
        }

        userAuthViewModel.selectedGymBranchLiveData.observe(viewLifecycleOwner) {
            selectedBranch = it
            val stringBuilder = StringBuilder()
            stringBuilder.append(it.gymName)
            stringBuilder.append(", ")
            stringBuilder.append(it.gymFullAddress)
            binding.selectBranchTextField.editText?.setText(stringBuilder.toString())
            binding.selectBranchTextField.setEndIconDrawable(android.R.drawable.ic_menu_edit)
        }

        binding.createSubscriptionButton.setOnClickListener {
            val gymSubscriptionName = binding.subscriptionNameTextField.editText?.text.toString()
            val gymSubscriptionFrequency = selectedSubscriptionFrequency?.getGymSubscriptionPlanAsCode() ?: Constants.EMPTY_STRING
            val gymSubscriptionBaseFare = binding.subscriptionBasePriceTextField.editText?.text.toString()
            val gymSubscriptionStartDate = if (selectedDates.isNotEmpty() && selectedDates.containsKey(binding.planStartDateTextField.id)) {
                selectedDates[binding.planStartDateTextField.id].toString()
            } else Constants.EMPTY_STRING
            val gymSubscriptionEndDate = if (selectedDates.isNotEmpty() && selectedDates.containsKey(binding.planEndDateTextField.id)) {
                selectedDates[binding.planEndDateTextField.id].toString()
            } else Constants.EMPTY_STRING
            val gymBranchCode = selectedBranch?.gymCode ?: Constants.EMPTY_STRING

            val createGymSubscriptionRequest = GymSubscriptionCreateRequest(
                gymPlanName = gymSubscriptionName,
                gymPlanFrequency = gymSubscriptionFrequency,
                gymPlanBaseFare = if (gymSubscriptionBaseFare.isNotEmpty()) gymSubscriptionBaseFare.toFloat() else 0.0f,
                gymPlanCreatedDate = if (gymSubscriptionStartDate.isNotEmpty()) gymSubscriptionStartDate.toLong() else null,
                gymPlanEndDate = if (gymSubscriptionEndDate.isNotEmpty()) gymSubscriptionEndDate.toLong() else null,
                gymBranchCode = gymBranchCode
            )

            gymSubscriptionDashboardViewModel.createGymSubscription(createGymSubscriptionRequest = createGymSubscriptionRequest)
        }

        gymSubscriptionDashboardViewModel.validationMessageLiveData.observe(viewLifecycleOwner) {
            AlertDialog.showAlert(
                requireContext(),
                "Subscription Plan",
                it,
                positiveButtonText = "OK"
            )
        }

        gymSubscriptionDashboardViewModel.statusLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Error -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    AlertDialog.showAlert(
                        requireContext(),
                        "Subscription Plan",
                        it.message,
                        positiveButtonText = "OK"
                    )
                }
                is NetworkResult.Loading -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.VISIBLE
                }
                is NetworkResult.Success -> {
                    binding.progressIndicatorLayout.progressIndicator.visibility = View.GONE
                    findNavController().navigateUp()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun chooseDate(selectedField: TextInputLayout) {
        // now create instance of the material date picker
        // builder make sure to add the "datePicker" which
        // is normal material date picker which is the first
        // type of the date picker in material design date
        // picker
        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()

        // now define the properties of the
        // materialDateBuilder that is title text as SELECT A DATE
        materialDateBuilder.setTitleText("SELECT A DATE")

        // now create the instance of the material date
        // picker
        val materialDatePicker = materialDateBuilder.build()

        // getSupportFragmentManager() to
        // interact with the fragments
        // associated with the material design
        // date picker tag is to get any error
        // in logcat
        materialDatePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")

        // now handle the positive button click from the
        // material design date picker
        materialDatePicker.addOnPositiveButtonClickListener {
            // if the user clicks on the positive
            // button that is ok button update the
            // selected date
            if (it is Long) {
                val selectedDate = Date(it)
                Log.d(TAG, "selectedDate: $selectedDate")
                selectedDates[selectedField.id] = it
            }
            selectedField.editText?.setText(materialDatePicker.headerText)
            // in the above statement, getHeaderText
            // is the selected date preview from the
            // dialog
        }
    }
}