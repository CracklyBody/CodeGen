package com.maxplugin.codegen.model.template.mvp.adapter


val ADAPTER_PRESENTER_SHOW_ITEMS_IMPL = """
    private fun showItems() {
		val listViewModel = getAllUsersChat()
		viewState.showData(listViewModel)
	}
"""