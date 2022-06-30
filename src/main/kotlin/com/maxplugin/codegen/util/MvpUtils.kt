package com.maxplugin.codegen.util

import android.databinding.tool.ext.toCamelCase
import com.maxplugin.codegen.CreatePageObjectResult

fun CreatePageObjectResult.getPageObjectFragmentClassText(): String {
    return "package ${packageName}" +
            """
                
        class ${className}Fragment : ${getClassPathByName("BaseFragment")}(), ${className}Contract.View, ${getClassPathByName("FragmentWithPreloader")} {

	@moxy.presenter.InjectPresenter
	lateinit var presenter: ${className}Contract.Presenter

	@javax.inject.Inject
	lateinit var presenterProvider: javax.inject.Provider<${className}Contract.Presenter>

	//region ==================== Fragment creation ====================

	companion object {

		fun newInstance(): ${className}Fragment {
			val args = android.os.Bundle()

			val fragment = ${className}Fragment()
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
		return inflater.inflate(R.layout.fragment_auth, container, false)
	}

	override fun onViewCreated(view: android.view.View, savedInstanceState: android.os.Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initUI(view)
	}

	//endregion

	//region ==================== ${className}Contract.View ====================

	override fun showErrorMessage(message: String) {
		${getClassByName("UpScreenMessage")?.qualifiedName ?: "io.mobilife.upscreenmessage.UpScreenMessage"}.showSuccessMessage(message, upScreenMessageContainer)
	}
	
	//endregion

	//region ==================== UI handlers ====================

	private val btnBackClickListener = android.view.View.OnClickListener { presenter.onBackButtonClicked() }


	//endregion

	//region ==================== DI ====================

	private fun configureDI() {
		val component = getAppComponent().plus(AuthModule())
		component.inject(this)
	}

	@moxy.presenter.ProvidePresenter
	internal fun providePresenter(): AuthContract.Presenter {
		return presenterProvider.get()
	}
	//endregion


	//region ==================== UI ====================

	private fun initUI(view: View) {
		
	}

	//endregion
}
        """

}



fun CreatePageObjectResult.getPageObjectPresenterClassText(): String {
    return "package ${packageName}" +
            """
                
                @moxy.InjectViewState
class ${className}Presenter @javax.inject.Inject constructor(private val router: ru.terrakok.cicerone.Router) : ${className}Contract.Presenter() {

	//region ==================== MVP Presenter ====================

	override fun onFirstViewAttach() {
		super.onFirstViewAttach()
	}

	//endregion

	//region ==================== ${className}Contract.Presenter ====================

	override fun onBackButtonClicked() {
		router.exit()
	}

	//endregion

	//region ============= Internal =============



	//endregion
}
        """

}

fun CreatePageObjectResult.getPageObjectContractClassText(): String {
    return "package ${packageName}" +
            """
                
                
interface ${className}Contract {
	@moxy.viewstate.strategy.StateStrategyType(value = moxy.viewstate.strategy.AddToEndSingleStrategy::class)
	interface View : moxy.MvpView, ${getClassPathByName("ViewWithPreloader")} {

		@moxy.viewstate.strategy.StateStrategyType(moxy.viewstate.strategy.OneExecutionStateStrategy::class)
		fun showErrorMessage(message: String)

	}


	abstract class Presenter : ${getClassPathByName("BaseDisposablePresenter")}<View>() {

		abstract fun onBackButtonClicked()

	}

}
        """

}

fun CreatePageObjectResult.getPageObjectDIClassText(): String {
    return "package ${packageName}" +
            """
                
            
@dagger.Subcomponent(modules = [${className}Module::class])
interface ${className}Component {

	fun inject(fragment: ${className}Fragment)

}

@dagger.Module
class ${className}Module() {

	@dagger.Provides
	fun presenter(${className.toCamelCase()}Presenter: ${className}Presenter): ${className}Contract.Presenter {
		return ${className.toCamelCase()}Presenter
	}

}
        """

}