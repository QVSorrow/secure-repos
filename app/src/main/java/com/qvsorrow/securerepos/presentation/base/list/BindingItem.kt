package com.qvsorrow.securerepos.presentation.base.list

interface BindingItem<out T> {
    fun getLayoutId(): Int
    fun getItem(): T
}