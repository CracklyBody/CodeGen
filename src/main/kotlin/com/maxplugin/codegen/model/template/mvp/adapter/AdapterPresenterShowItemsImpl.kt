package com.maxplugin.codegen.model.template.mvp.adapter

import com.maxplugin.codegen.model.Variable


val ADAPTER_PRESENTER_SHOW_ITEMS_IMPL = """
    private fun showItems() {
		val listViewModel = listOf<${Variable.FIND_CLASS.value}ListViewModel>()
		viewState.showItems(listViewModel)
	}
"""