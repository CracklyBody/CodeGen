package com.maxplugin.codegen.model.template.mvp.adapter

import com.maxplugin.codegen.model.Variable


val ADAPTER_CONTRACT_SHOW_ITEMS = """
		fun showItems(list: List<${Variable.FIND_CLASS.value}ListViewModel>)
"""