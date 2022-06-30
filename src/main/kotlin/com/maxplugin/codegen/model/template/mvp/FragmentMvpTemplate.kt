package com.maxplugin.codegen.model.template.mvp

import com.maxplugin.codegen.model.Variable

val DEFAULT_FRAGMENT_MVP_TEMPLATE = """package ${Variable.PACKAGE_NAME.value}
import ${Variable.BASE_PACKAGE_NAME.value}.R
import kotlinx.android.synthetic.main.${Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value}_${Variable.NAME_SNAKE_CASE.value}.*

class ${Variable.NAME.value}Fragment : ${Variable.FIND_CLASS.value}BaseFragment(), ${Variable.NAME.value}Contract.View, ${Variable.FIND_CLASS.value}FragmentWithPreloader {

	@moxy.presenter.InjectPresenter
	lateinit var presenter: ${Variable.NAME.value}Contract.Presenter

	@javax.inject.Inject
	lateinit var presenterProvider: javax.inject.Provider<${Variable.NAME.value}Contract.Presenter>

	//region ==================== Fragment creation ====================

	companion object {

		fun newInstance(): ${Variable.NAME.value}Fragment {
			val args = android.os.Bundle()

			val fragment = ${Variable.NAME.value}Fragment()
			fragment.arguments = args

			return fragment
		}
	}

	//endregion


	//region ==================== Lifecycle ====================

	override fun onCreate(savedInstanceState: android.os.Bundle?) {
		configureDI()
		super.onCreate(savedInstanceState)
	}

	override fun onCreateView(inflater: android.view.LayoutInflater,
	                          container: android.view.ViewGroup?,
	                          savedInstanceState: android.os.Bundle?): android.view.View? {
		return inflater.inflate(R.layout.${Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value}_${Variable.NAME_SNAKE_CASE.value}, container, false)
	}

	override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initUI(view)
	}

	//endregion

	//region ==================== ${Variable.NAME.value}Contract.View ====================

	override fun showErrorMessage(message: String) {
		 //${Variable.FIND_CLASS.value}UpScreenMessage?.qualifiedName ?: "io.mobilife.upscreenmessage.UpScreenMessage"}.showSuccessMessage(message, upScreenMessageContainer)
	}
	
	//endregion

	//region ==================== UI handlers ====================

	private val btnBackClickListener = android.view.View.OnClickListener { presenter.onBackButtonClicked() }


	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val component = getAppComponent().plus(${Variable.NAME.value}Module())
		component.inject(this)
	}

	@moxy.presenter.ProvidePresenter
	internal fun providePresenter(): ${Variable.NAME.value}Contract.Presenter {
		return presenterProvider.get()
	}
	//endregion


	//region ==================== UI ====================

	private fun initUI(view: View) {
		
	}

	//endregion
}
        """