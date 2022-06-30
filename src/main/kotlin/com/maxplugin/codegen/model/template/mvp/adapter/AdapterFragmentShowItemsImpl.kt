package com.maxplugin.codegen.model.template.mvp.adapter

import com.maxplugin.codegen.model.Variable


val ADAPTER_FRAGMENT_SHOW_ITEMS_IMPL_ITEMS_IMPL = """
   override fun showItems(list: List<${Variable.FIND_CLASS.value}ListViewModel>) {
		adapter.swapItems(list)
	}
"""