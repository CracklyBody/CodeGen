package com.maxplugin.codegen.util

import com.maxplugin.codegen.model.Variable

private val KOTLIN_DEFAULT_TEMPLATE = "package ${Variable.PACKAGE_NAME.value}\n\nclass ${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"
private val LAYOUT_XML_DEFAULT_TEMPLATE = """
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:app="http://schemas.android.com/apk/res-auto"
	xmlns:tools="http://schemas.android.com/tools"
	android:id="@+id/bottomSheet"
	android:layout_width="match_parent"
	android:layout_height="match_parent">

    ${Variable.RECYCLER_VIEW_LAYOUT.value}

</LinearLayout>
"""
private val KOTLIN_DEFAULT_FILE_NAME = "${Variable.NAME.value}${Variable.SCREEN_ELEMENT.value}"
private val LAYOUT_XML_DEFAULT_FILE_NAME = "${Variable.ANDROID_COMPONENT_NAME_LOWER_CASE.value}_${Variable.NAME_SNAKE_CASE.value}"


enum class FileType(val displayName: String, val extension: String, val defaultTemplate: String, val defaultFileName: String) {
    KOTLIN("Kotlin", "kt", KOTLIN_DEFAULT_TEMPLATE, KOTLIN_DEFAULT_FILE_NAME),
    LAYOUT_XML("Layout XML", "xml", LAYOUT_XML_DEFAULT_TEMPLATE, LAYOUT_XML_DEFAULT_FILE_NAME);

    override fun toString() = displayName
}