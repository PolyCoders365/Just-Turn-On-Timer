package jtot.dev.feature.main

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import jtot.dev.base.BaseViewModel
import jtot.dev.model.Folder

class MainViewModel : BaseViewModel() {
    private val database = FirebaseDatabase.getInstance()
    private val myRef = database.getReference("folders")
    val folderLiveData = MutableLiveData<Folder>()
    val errorMessage = MutableLiveData<String>()

    fun writeFolder() {
        val folder = Folder("work", listOf()).createDummy()
        myRef.setValue(folder)
    }

    fun readFolder() {
        myRef.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val oldFolder = dataSnapshot.getValue(Folder::class.java)
                    val docs = oldFolder?.docs?.map { it as Any } ?: listOf()
                    val newFolder = Folder(oldFolder?.title ?: "", docs)
                    folderLiveData.value = newFolder
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Log a message
                    errorMessage.value = databaseError.message
                }
            },
        )
    }
}
