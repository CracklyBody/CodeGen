package com.maxplugin.codegen.model.template.mvvm


import com.maxplugin.codegen.model.Variable

val DEFAULT_FRAGMENT_MVVM_TEMPLATE = """package ${Variable.PACKAGE_NAME.value}
import ${Variable.BASE_PACKAGE_NAME.value}.R
import kotlinx.android.synthetic.main.${Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value}_${Variable.NAME_SNAKE_CASE.value}.*
${Variable.RECYCLER_VIEW_ADAPTER_FRAGMENT_IMPORT.value}

class ${Variable.NAME.value}Fragment : ${Variable.FIND_CLASS.value}BaseFragment(), ${Variable.FIND_CLASS.value}FragmentWithPreloader {

    @javax.inject.Inject
    lateinit var viewModelFactory: androidx.lifecycle.ViewModelProvider.Factory

    lateinit var viewModel: ${Variable.NAME.value}ViewModelInterface

    companion object {

        fun newInstance() :${Variable.NAME.value}Fragment {
            return ${Variable.NAME.value}Fragment()
        }
    }

    //region ===================== Lifecycle callbacks ======================

    override fun onCreate(savedInstanceState: android.os.Bundle?) {
        configureDI()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: android.os.Bundle?
    ): android.view.View? {
        return inflater.inflate(R.layout.${Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value}_${Variable.NAME_SNAKE_CASE.value}, container, false)
    }

    override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(view)
        observeViewModel()
    }

    //endregion

    //region ===================== UI handlers ======================

    private val btnBackClickListener = android.view.View.OnClickListener { viewModel.onBackButtonClicked() }

    //endregion

    //region ===================== DI ======================

    private fun configureDI() {
        val component = getAppComponent().plus(${Variable.NAME.value}Module())
        component.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory).get(${Variable.NAME.value}ViewModel::class.java)
    }

    //endregion

    //region ===================== UI ======================

    private fun initUI(view: View) {
    }

    private fun observeViewModel() {

    }

    //endregion

}
        """