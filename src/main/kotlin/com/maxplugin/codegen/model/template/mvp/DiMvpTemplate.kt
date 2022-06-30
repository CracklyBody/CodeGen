package com.maxplugin.codegen.model.template.mvp

import android.databinding.tool.ext.toCamelCase
import com.maxplugin.codegen.model.Variable


val DEFAULT_DI_MVP_TEMPLATE = """package ${Variable.PACKAGE_NAME.value}
    
@dagger.Subcomponent(modules = [${Variable.NAME.value}Module::class])
interface ${Variable.NAME.value}Component {

	fun inject(fragment: ${Variable.NAME.value}Fragment)

}

@dagger.Module
class ${Variable.NAME.value}Module() {

	@dagger.Provides
	fun presenter(${Variable.NAME.value.toCamelCase()}Presenter: ${Variable.NAME.value}Presenter): ${Variable.NAME.value}Contract.Presenter {
		return ${Variable.NAME.value.toCamelCase()}Presenter
	}

}
        """