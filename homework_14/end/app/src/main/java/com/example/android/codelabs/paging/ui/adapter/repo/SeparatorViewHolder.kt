/*
 * Copyright (C) 2020 The Android Open Source Project
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

package com.example.android.codelabs.paging.ui.adapter.repo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.codelabs.paging.R

class SeparatorViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val description: TextView = view.findViewById(R.id.separator_description)

    fun bind(separatorText: String) {
        description.text = separatorText
    }

    companion object {
        fun create(parent: ViewGroup): SeparatorViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.separator_view_item, parent, false)
            return SeparatorViewHolder(view)
        }
    }
}
