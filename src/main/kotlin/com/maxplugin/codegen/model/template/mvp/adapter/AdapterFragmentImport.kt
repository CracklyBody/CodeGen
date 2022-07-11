package com.maxplugin.codegen.model.template.mvp.adapter

import com.maxplugin.codegen.model.Variable

val ADAPTER_FRAGMENT_IMPORT = """   import ${Variable.PACKAGE_NAME.value}.adapter.${Variable.FIND_CLASS.value}${Variable.NAME.value}Adapter"""