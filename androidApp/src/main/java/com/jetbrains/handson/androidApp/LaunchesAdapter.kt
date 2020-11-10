package com.jetbrains.handson.androidApp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.androidApp.databinding.ItemLaunchBinding
import com.jetbrains.handson.kmm.shared.entity.RocketLaunch

class LaunchesAdapter : RecyclerView.Adapter<LaunchesAdapter.ViewHolder>() {

    var launches: List<RocketLaunch> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ItemLaunchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            .run(::ViewHolder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(launches[position])
    }

    override fun getItemCount() = launches.size

    inner class ViewHolder(private val binding: ItemLaunchBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindData(launch: RocketLaunch) {
            val context = binding.root.context
            binding.missionName.text =
                context.getString(R.string.mission_name_field, launch.missionName)
            binding.launchYear.text =
                context.getString(R.string.launch_year_field, launch.launchYear.toString())
            binding.details.text = context.getString(R.string.details_field, launch.details ?: "")
            when (launch.launchSuccess) {
                true -> {
                    binding.launchSuccess.text = context.getString(R.string.successful)
                    binding.launchSuccess.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorSuccessful
                        )
                    )
                }
                false -> {
                    binding.launchSuccess.text = context.getString(R.string.unsuccessful)
                    binding.launchSuccess.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorUnsuccessful
                        )
                    )
                }
                null -> {
                    binding.launchSuccess.text = context.getString(R.string.no_data)
                    binding.launchSuccess.setTextColor(
                        ContextCompat.getColor(
                            context,
                            R.color.colorNoData
                        )
                    )
                }
            }
        }
    }

}