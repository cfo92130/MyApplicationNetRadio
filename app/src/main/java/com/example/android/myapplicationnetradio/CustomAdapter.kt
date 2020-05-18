/*
* Copyright (C) 2014 The Android Open Source Project
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package com.example.android.myapplicationnetradio

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.android.common.logger.Log

/**
 * Provide views to RecyclerView with data from dataSet.
 * Initialize the dataset of the Adapter.
 * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
 */
class CustomAdapter(private val dataSet: List<Radio>, myviewModel: MainViewModel ) :
        androidx.recyclerview.widget.RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    val localViewModel = myviewModel

    class ViewHolder(v: View, myViewModel: MainViewModel)  : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var textView: TextView

        init {
            // Define click listener for the ViewHolder's View.
             v.setOnClickListener {
                    Log.d(TAG, "Element $adapterPosition clicked.")
                    MainActivity.SelectedRadio = adapterPosition
                    myViewModel.selectedRadio.postValue(adapterPosition.toString())
              }
            textView = v.findViewById(R.id.radioDetail)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view.
        val v = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.radio_detail, viewGroup, false)
        return ViewHolder(v, localViewModel)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        Log.d(TAG, "Element $position set.")
        // Get element from your dataset at this position and replace the contents of the view
        // with that element
        viewHolder.textView.text = dataSet[position].title
       }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    companion object {
        private val TAG = "CustomAdapter"
    }
}
