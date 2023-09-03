package com.unit_one.e_commerceapp.ui.home.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unit_one.e_commerceapp.data.model.HomeGroup
import com.unit_one.e_commerceapp.databinding.ItemHomeBinding
import com.unit_one.e_commerceapp.ui.groups.GroupType
import com.unit_one.e_commerceapp.ui.home.HomeViewModel
import com.unit_one.e_commerceapp.util.getClothes
import com.unit_one.e_commerceapp.util.getElectronics
import com.unit_one.e_commerceapp.util.getJewelery


class HomeFragmentAdapter(
    val viewModel: HomeViewModel?,
    private val lifecycleOwner: LifecycleOwner
) :
    RecyclerView.Adapter<HomeFragmentAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: ItemHomeBinding) : RecyclerView.ViewHolder(binding.root)

    lateinit var context: Context
    private val listener = viewModel

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context
        val binding = ItemHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            listener = this@HomeFragmentAdapter.listener
            var groupType = GroupType.CLOTHES

            viewModel?.products?.observe(this@HomeFragmentAdapter.lifecycleOwner) { products ->
                val clothesList = getClothes(products.toData())
                val electronicsList = getElectronics(products.toData())
                val jeweleryList = getJewelery(products.toData())

                when (position) {
                    0 -> {
                        rvGroup.adapter =
                            MainGroupAdapter(clothesList, listener, false)
                        groupType = GroupType.CLOTHES
                    }
                    1 -> {
                        rvGroup.adapter =
                            CarsGroupAdapter(electronicsList, listener, false)
                        groupType = GroupType.ELECTRONIC
                    }
                    2 -> {
                        rvGroup.adapter = CarsGroupAdapter(jeweleryList, listener, false)
                        groupType = GroupType.JEWELERY
                    }
                    3 -> {
                        val newProducts = products.toData()?.reversed() ?: emptyList()
                        val adapter = ProductAdapter(newProducts, listener)
                        rvGroup.adapter = adapter
                        rvGroup.clipToPadding = true
                        val horizontalLayoutManager = LinearLayoutManager(
                            context,
                            LinearLayoutManager.VERTICAL,
                            false
                        )
                        rvGroup.layoutManager = horizontalLayoutManager
                        imageGroup.visibility = View.GONE

                    }
                }

                val title = viewModel.getTitles()[position]
                item = HomeGroup(title, groupType)
            }

        }
    }


    override fun getItemCount(): Int = 4

}