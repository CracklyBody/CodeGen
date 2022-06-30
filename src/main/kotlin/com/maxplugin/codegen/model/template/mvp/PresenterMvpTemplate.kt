package com.maxplugin.codegen.model.template.mvp

import com.maxplugin.codegen.model.Variable


val DEFAULT_PRESENTER_MVP_TEMPLATE = """package ${Variable.PACKAGE_NAME.value}
         
@moxy.InjectViewState
class ${Variable.NAME.value}Presenter @javax.inject.Inject constructor(private val router: ru.terrakok.cicerone.Router) : ${Variable.NAME.value}Contract.Presenter() {

    //region ==================== MVP Presenter ====================

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    //endregion

    //region ==================== ${Variable.NAME.value}Contract.Presenter ====================

    override fun onBackButtonClicked() {
        router.exit()
    }

    //endregion

    //region ============= Internal =============



    //endregion
}
"""