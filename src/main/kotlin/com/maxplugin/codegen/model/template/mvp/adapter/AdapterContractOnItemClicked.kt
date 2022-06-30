package com.maxplugin.codegen.model.template.mvp.adapter

import com.maxplugin.codegen.model.Variable

val ADAPTER_CONTRACT_ON_ITEM_CLICKED = """
		abstract fun onListItemClicked(item: ${Variable.FIND_CLASS.value}ListViewModel)
"""