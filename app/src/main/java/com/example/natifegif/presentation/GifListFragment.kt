package com.example.natifegif.presentation

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Rect
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.natifegif.R
import com.example.natifegif.data.database.GifData
import com.example.natifegif.databinding.FragmentGifListBinding
import com.example.natifegif.presentation.adapter.RcAdapter
import javax.inject.Inject


class GifListFragment : Fragment(), RcAdapter.Listener {
    private lateinit var binding: FragmentGifListBinding
    private lateinit var textWatcher: TextWatcher
    private var edText: EditText? = null
    private lateinit var adapter: RcAdapter
    lateinit var model: MainViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private var state: Boolean = false
    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.infect(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGifListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        model.stateValue()
        addBar()
        init()
        scrollListener()
        model.state.observe(viewLifecycleOwner) {
            if (it.isEmpty()) {
                model.gifList.observe(viewLifecycleOwner) { list ->
                    if (list.isEmpty()) model.getItem()
                    Log.d("MyLog", list.size.toString())
                    adapter.submitList(list)

                }
            } else {
                Toast.makeText(requireContext(), "Check your internet connection", Toast.LENGTH_LONG).show()
                model.gifList.observe(viewLifecycleOwner) { list ->
                    adapter.submitList(list)
                }
            }
        }
    }

    private fun init() {
        binding.apply {
            adapter = RcAdapter(this@GifListFragment, requireContext())
            recView.layoutManager = LinearLayoutManager(requireContext())
            recView.adapter = adapter
            setupSwipeListener(recView)
        }
    }

    private fun addBar() {
        binding.apply {
            fragToolbar.inflateMenu(R.menu.frag_tool_menu)
            val findItem = fragToolbar.menu.findItem(R.id.new_item)
            edText = findItem.actionView?.findViewById(R.id.findGif) as? EditText
            findItem.setOnActionExpandListener(expandActionView())
            textWatcher = textWatcher()
        }
    }

    private fun expandActionView(): MenuItem.OnActionExpandListener {
        return object : MenuItem.OnActionExpandListener {
            override fun onMenuItemActionExpand(p0: MenuItem): Boolean {
                edText?.addTextChangedListener(textWatcher)
              return true
            }
            override fun onMenuItemActionCollapse(p0: MenuItem): Boolean {
                edText?.removeTextChangedListener(textWatcher)
                edText?.setText("")
                model.gifList.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                }
                model.philterModelList.removeObservers(viewLifecycleOwner)
                state = false
                return true
            }
        }
    }

    private fun textWatcher(): TextWatcher {
        return object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0 != null) {
                    model.gifList.removeObservers(viewLifecycleOwner)
                    model.philterModelList.observe(viewLifecycleOwner) {
                        adapter.submitList(it)
                    }
                    model.getToDb("%$p0%")
                    state = true
                }
            }
            override fun afterTextChanged(p0: Editable?) {
            }
        }
    }

    private fun scrollListener() = with(binding) {
        recView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(SCROLL_DOWN)
                    && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!state) {
                        model.getItem()
                    } else Toast.makeText(
                        requireContext(),
                        "You are in search mode",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        })
    }

    override fun onClickItem(gifData: GifData) {
        val intent = Intent(requireActivity(), GifActivity::class.java)
        intent.putExtra("id", gifData.id)
        startActivity(intent)
    }

    private fun setupSwipeListener(rvView: RecyclerView) {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onChildDraw(c: Canvas,recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float, dY: Float,actionState: Int, isCurrentlyActive: Boolean
            ) {
                val trashBinIcon = ContextCompat.getDrawable(requireContext(),
                    R.drawable.ic_baseline_delete_outline_24)
                c.clipRect(dX, viewHolder.itemView.top.toFloat(),
                    viewHolder.itemView.right.toFloat() , viewHolder.itemView.bottom.toFloat())
                trashBinIcon?.bounds = Rect(
                    viewHolder.itemView.right -150 ,
                    viewHolder.itemView.top +100,
                    viewHolder.itemView.right-30,
                    viewHolder.itemView.bottom-100
                )
                trashBinIcon?.draw(c)

                super.onChildDraw(c,recyclerView,viewHolder,dX,dY,actionState,
                    isCurrentlyActive
                )
            }
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                model.insertDeleted(item)
                model.deleteGif(item.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvView)
    }
    companion object {
        const val SCROLL_DOWN = 1
    }
}