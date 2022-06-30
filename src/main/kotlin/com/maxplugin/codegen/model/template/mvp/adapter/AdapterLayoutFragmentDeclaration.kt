package com.maxplugin.codegen.model.template.mvp.adapter

val ADAPTER_LAYOUT_FRAGMENT_DECLARATION = """
    recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context, androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
	recyclerView.adapter = adapter
"""