package com.maxplugin.codegen.model.template.mvp.adapter

import com.maxplugin.codegen.model.Variable


val ADAPTER_PRESENTER_ON_ITEM_CLICKED_IMPL = """
    override fun onListItemClicked(item: ${Variable.FIND_CLASS.value}ListViewModel) {
		when (item) {
			
		}
	}
"""