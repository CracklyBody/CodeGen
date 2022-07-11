package com.maxplugin.codegen.model

enum class Variable(val value: String, val description: String) {
    NAME("%screenName%", "Name of the screen, e.g. ScreenName"),
    NAME_SNAKE_CASE("%screenNameSnakeCase%", "Name of the screen written in snake case, e.g. screen_name"),
    NAME_LOWER_CASE("%screenNameLowerCase%", "Name of the screen written in camel case starting with lower case, e.g. screenName"),
    SCREEN_ELEMENT("%screenElement%", "Screen element's name, e.g. Presenter"),
    PACKAGE_NAME("%packageName%", "Full package name, e.g. com.sample"),
    BASE_PACKAGE_NAME("%basePackageName%", "base package name, e.g. com.sample"),
    ANDROID_COMPONENT_NAME("%component%", "Android component's name, e.g. Activity or Fragment"),
    FIND_CLASS("%findClass%", "Class that need to be found"),
    ANDROID_COMPONENT_NAME_LOWER_CASE("%componentLowerCase%", "Android component's name written in camel case starting with lower case, e.g. activity or fragment"),
    RECYCLER_VIEW_LAYOUT("%recyclerViewLayout%", "Place where need to add recycler view"),
    RECYCLER_VIEW_ADAPTER_DECLARATION("%recyclerViewAdapter%", "Something like val adapter: MyAdapter"),
    RECYCLER_VIEW_ADAPTER_FRAGMENT_IMPORT("%recyclerViewAdapterFragmentImport%", "import com.example.adapter.MyAdapter"),
    RECYCLER_VIEW_LAYOUT_DECLARATION("%recyclerViewLayoutDeclaration%", "Declaration of recyclerView"),
    RECYCLER_VIEW_ADAPTER_SWAP_ITEMS_PRESENTER("%recyclerViewAdapterSwapItemsImplPresenter%", "swap items func"),
    RECYCLER_VIEW_ADAPTER_SWAP_ITEMS_IMPL("%recyclerViewAdapterSwapItems%", "swap items func impl"),
    RECYCLER_VIEW_ADAPTER_SWAP_ITEMS_CONTRACT("%recyclerViewAdapterSwapItemsContract%", "contract swap items"),
    RECYCLER_VIEW_ADAPTER_ON_ITEM_CLICKED_CONTRACT("%recyclerViewAdapterOnItemClickedContract%", "contract onListItemClicked"),
    RECYCLER_VIEW_ADAPTER_ON_ITEM_CLICKED_PRESENTER_IMPL("%recyclerViewAdapterOnItemClickedPresenterImpl%", "Presenter onListItemClicked impl"),
}
