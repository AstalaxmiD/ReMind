package com.example.remind // Your package

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.remind.databinding.ItemJournalEntryBinding
import java.text.SimpleDateFormat
import java.util.Locale

class JournalAdapter(private var entries: List<JournalEntry>) :
    RecyclerView.Adapter<JournalAdapter.JournalViewHolder>() {

    // SimpleDateFormat for formatting dates
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

    inner class JournalViewHolder(val binding: ItemJournalEntryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(entry: JournalEntry) {
            binding.textViewJournalContent.text = entry.content
            binding.textViewJournalDate.text = dateFormat.format(entry.date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JournalViewHolder {
        val binding = ItemJournalEntryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JournalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JournalViewHolder, position: Int) {
        holder.bind(entries[position])
    }

    override fun getItemCount(): Int = entries.size

    // Function to update the list of entries in the adapter
    fun updateEntries(newEntries: List<JournalEntry>) {
        entries = newEntries.sortedByDescending { it.date } // Sort by date, newest first
        notifyDataSetChanged() // For simplicity; use DiffUtil for better performance
    }
}
