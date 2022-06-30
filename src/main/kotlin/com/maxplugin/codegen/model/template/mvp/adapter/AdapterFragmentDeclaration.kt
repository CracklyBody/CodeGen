package com.maxplugin.codegen.model.template.mvp.adapter

import com.maxplugin.codegen.model.Variable


val ADAPTER_FRAGMENT_DECLARATION = """
   @javax.inject.Inject
	lateinit var adapter: ${Variable.FIND_CLASS.value}${Variable.NAME.value}Adapter
"""