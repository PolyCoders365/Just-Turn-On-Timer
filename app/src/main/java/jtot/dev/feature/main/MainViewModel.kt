package jtot.dev.feature.main

import androidx.lifecycle.LiveData
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

    private val _folderLiveData = MutableLiveData<Folder>()
    val folderLiveData: LiveData<Folder> get() = _folderLiveData
    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> get() = _errorMessage

    fun writeFolder(folder: Folder) {
        myRef.setValue(folder)
    }

    fun readFolder() {
        myRef.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val folderData = dataSnapshot.getValue(Folder::class.java)
                    val docs = folderData?.docs?.map { it as Any } ?: listOf()
                    folderData?.let {
                        _folderLiveData.value = it.copy(docs = docs)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Log a message
                    _errorMessage.value = databaseError.message
                }
            },
        )
    }
}
