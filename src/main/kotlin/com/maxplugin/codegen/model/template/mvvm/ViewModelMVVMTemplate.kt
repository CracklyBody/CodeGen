package com.maxplugin.codegen.model.template.mvvm

import com.maxplugin.codegen.model.Variable

val DEFAULT_VIEW_MODEL_MVVM_TEMPLATE = """package ${Variable.PACKAGE_NAME.value}

class ${Variable.NAME.value}ViewModel @javax.inject.Inject constructor(
    private val router: ru.terrakok.cicerone.Router,
) : ${Variable.FIND_CLASS.value}BaseViewModel(), ${Variable.NAME.value}ViewModelInterface {

    init {

    }

    //region ===================== ${Variable.NAME.value}ViewModelInterface ======================

    override fun onBackButtonClicked() {
        router.exit()
    }

    //endregion


    //region ===================== Internal ======================

    //endregion
}

        """