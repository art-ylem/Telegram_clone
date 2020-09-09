package com.example.telegram.ui.screens.main_list

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.telegram.R
import com.example.telegram.database.*
import com.example.telegram.models.CommonModel
import com.example.telegram.utils.APP_ACTIVITY
import com.example.telegram.utils.AppValueEventListener
import com.example.telegram.utils.hideKeyboard
import kotlinx.android.synthetic.main.fragment_main_list.*

class MainListFragment : Fragment(R.layout.fragment_main_list) {

    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mAdapter: MainListAdapter
    private var mRefMainList = REF_DATABASE_ROOT.child(NODE_MAIN_LIST).child(CURRENT_UID)
    private var mRefUsers = REF_DATABASE_ROOT.child(NODE_USERS)
    private var mRefMessages = REF_DATABASE_ROOT.child(NODE_MESSAGES).child(CURRENT_UID)
    private var mListItems = listOf<CommonModel>()


    override fun onResume() {
        super.onResume()
        APP_ACTIVITY.title = "Telegram"
        APP_ACTIVITY.mAppDrawer.enableDrawer()
        hideKeyboard()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        mRecyclerView = main_list_recycler_view
        mAdapter = MainListAdapter()

        // first request
        mRefMainList.addListenerForSingleValueEvent(AppValueEventListener { dataSnapshot ->
            mListItems = dataSnapshot.children.map { it.getCommonModel() }
            mListItems.forEach { model ->

                // second request
                mRefUsers.child(model.id).addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot1 ->
                    val newModel = dataSnapshot1.getCommonModel()

                    // third request
                    mRefMessages.child(model.id).limitToLast(1)
                        .addListenerForSingleValueEvent(AppValueEventListener{ dataSnapshot2 ->
                            val tempList = dataSnapshot2.children.map { it.getCommonModel() }
                            newModel.lastMessage = tempList[0].text

                            if(newModel.fullname.isEmpty()) newModel.fullname = newModel.phone
                            mAdapter.updateListItems(newModel)
                        })
                })
            }
        })

        mRecyclerView.adapter = mAdapter
    }
}