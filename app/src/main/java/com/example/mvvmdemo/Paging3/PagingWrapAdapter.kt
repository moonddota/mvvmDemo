package com.example.mvvmdemo.Paging3

import android.view.ViewGroup
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers

class PagingWrapAdapter<T : DifferData, VH : RecyclerView.ViewHolder>(
    private val innerAdapter: RecyclerView.Adapter<VH>,
    private val callback: ((List<T>) -> Unit)
) : RecyclerView.Adapter<VH>() {
    private val differ = AsyncPagingDataDiffer<T>(
        diffCallback = DifferCallback(),
        updateCallback = AdapterListUpdateCallback(this),
        mainDispatcher = Dispatchers.Main,
        workerDispatcher = Dispatchers.Default
    )

    init {
        differ.addLoadStateListener {
            if (it.append is LoadState.NotLoading) {
                val items = differ.snapshot().items
                callback.invoke(items)
            }
        }
    }

    //重试
    fun retry() {
        differ.retry()
    }

    //设置数据
    suspend fun submitList(pagingData: PagingData<T>) {
        differ.submitData(pagingData)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return innerAdapter.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        differ.getItem(position)
        innerAdapter.onBindViewHolder(holder, position)
    }

    override fun getItemCount(): Int {
        return innerAdapter.itemCount
    }


}
