package com.maxplugin.codegen.model.template.mvp.adapter

import com.maxplugin.codegen.model.Variable


val ADAPTER_TEMPLATE_MVP = """
class ${Variable.NAME.value}Adapter @javax.inject.Inject constructor() : ${Variable.FIND_CLASS.value}DiffAdapter() {

	init {
//		delegatesManager.addDelegate(itemDialogViewModelDelegate)
	}
}
"""