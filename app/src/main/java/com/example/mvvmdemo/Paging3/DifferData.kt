package com.example.mvvmdemo.Paging3

interface DifferData {

    fun areItemsTheSame(data: DifferData): Boolean {
        return this == data
    }

    fun areContentsTheSame(data: DifferData): Boolean {
        return this == data
    }

    fun getChangePayload(data: DifferData): Any? {
        return null
    }

}