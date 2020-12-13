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

package com.example.android.BluOsControler

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.fragment_reader.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.concurrent.thread


/**
 * Provide views to RecyclerView with data from dataSet.
 * Initialize the dataset of the Adapter.
 * @param dataSet String[] containing the data to populate views to be used by RecyclerView.
 */
class CustomAdapter(private val dataSet: List<Album>, myviewModel: MainViewModel ) :
        androidx.recyclerview.widget.RecyclerView.Adapter<CustomAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    val localViewModel = myviewModel

    class ViewHolder(v: View, myViewModel: MainViewModel)  : androidx.recyclerview.widget.RecyclerView.ViewHolder(v) {
        var album: TextView
        var artist: TextView
        var art: ImageView
        var BluOsInstance = BluOs()

        init {
            // Define click listener for the ViewHolder's View.
             v.setOnClickListener {
                    Log.d(TAG, "Element $adapterPosition clicked.")
                    MainActivity.SelectedAlbum = adapterPosition
                    myViewModel.selectedAlbum.postValue(adapterPosition.toString())
              }
            album = v.findViewById(R.id.album)
            artist = v.findViewById(R.id.artist)
            art = v.findViewById(R.id.imageView)
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
        viewHolder.album.text = dataSet[position].title
        viewHolder.artist.text = dataSet[position].artist
        runBlocking {
            val job = GlobalScope.launch {
                var art = viewHolder.BluOsInstance.GetImage("Qobuz","/Artwork?service=Qobuz&albumid="+dataSet[position].albumId)
                viewHolder.art.setImageBitmap(art)
            }
            job.join()
        }

    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    companion object {
        private val TAG = "CustomAdapter"
    }
}
