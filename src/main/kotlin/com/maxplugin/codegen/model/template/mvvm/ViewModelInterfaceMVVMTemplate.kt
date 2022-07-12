package com.maxplugin.codegen.model.template.mvvm

import com.maxplugin.codegen.model.Variable

val DEFAULT_VIEW_MODEL_INTERFACE_MVVM_TEMPLATE = """package ${Variable.PACKAGE_NAME.value}
                         
interface ${Variable.NAME.value}ViewModelInterface: ${Variable.FIND_CLASS.value}BaseViewModelInterface {

    fun onBackButtonClicked()

}
"""