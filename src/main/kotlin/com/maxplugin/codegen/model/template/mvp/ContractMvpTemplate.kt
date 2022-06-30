package com.maxplugin.codegen.model.template.mvp

import com.maxplugin.codegen.model.Variable


val DEFAULT_CONTRACT_MVP_TEMPLATE = """package ${Variable.PACKAGE_NAME.value}
                          
interface ${Variable.NAME.value}Contract {
	@moxy.viewstate.strategy.StateStrategyType(value = moxy.viewstate.strategy.AddToEndSingleStrategy::class)
	interface View : moxy.MvpView, ${Variable.FIND_CLASS.value}ViewWithPreloader {

		@moxy.viewstate.strategy.StateStrategyType(moxy.viewstate.strategy.OneExecutionStateStrategy::class)
		fun showErrorMessage(message: String)

        ${Variable.RECYCLER_VIEW_ADAPTER_SWAP_ITEMS_CONTRACT.value}
	}


	abstract class Presenter : ${Variable.FIND_CLASS.value}BaseDisposablePresenter<View>() {

		abstract fun onBackButtonClicked()

        ${Variable.RECYCLER_VIEW_ADAPTER_ON_ITEM_CLICKED_CONTRACT.value}

	}

}
"""