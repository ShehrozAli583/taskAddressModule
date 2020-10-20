package com.example.taskaddressmodule.ui.addaddress.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.example.taskaddressmodule.R
import com.example.taskaddressmodule.models.response.Cities

class CityAdapter(context: Context, @LayoutRes private val layoutResource: Int, private val list: MutableList<Cities>) : ArrayAdapter<Cities>(context, layoutResource, list) {

    override fun getView(i: Int, view: View?, parent: ViewGroup): View {
        val v: TextView = view as TextView? ?: LayoutInflater.from(context)
            .inflate(layoutResource, parent, false) as TextView

        val names = v.findViewById(R.id.tv) as TextView
        names.text = list[i].name
        return v
    }

    override fun getItem(p0: Int): Cities {
        return list[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return list.size
    }
}