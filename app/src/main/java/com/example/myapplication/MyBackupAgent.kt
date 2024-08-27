package com.example.myapplication

import android.app.Application
import android.app.backup.BackupAgent
import android.app.backup.BackupDataInput
import android.app.backup.BackupDataOutput
import android.app.backup.FullBackupDataOutput
import android.os.ParcelFileDescriptor
import android.util.Log
import java.io.File
import java.io.FileWriter

class MyBackupAgent: BackupAgent() {

    init {
        Log.i("BackupAgent", "BackupAgent init")
    }

    override fun onBackup(
        oldState: ParcelFileDescriptor?,
        data: BackupDataOutput?,
        newState: ParcelFileDescriptor?
    ) {
        Log.i("BackupAgent", "onBackup called")
    }

    override fun onRestore(
        data: BackupDataInput?,
        appVersionCode: Int,
        newState: ParcelFileDescriptor?
    ) {
        Log.i("BackupAgent", "onRestore called")
    }

    override fun onFullBackup(data: FullBackupDataOutput?) {
        Log.i("BackupAgent", "onFullBackup called")

        val app = applicationContext as Application
        val newFile = File(app.filesDir, "newFile.txt")
        FileWriter(newFile).use {
            it.write("someFileContents")
        }
        fullBackupFile(newFile, data)
        newFile.delete()

        Log.i("BackupAgent", "backed up a file")
    }

    override fun onRestoreFile(
        data: ParcelFileDescriptor?,
        size: Long,
        destination: File?,
        type: Int,
        mode: Long,
        mtime: Long
    ) {
        Log.i("BackupAgent", "onRestoreFile called with file ${destination?.name ?: "null"}")
    }

    override fun onRestoreFinished() {
        Log.i("BackupAgent", "onRestoreFinished called")
        super.onRestoreFinished()
    }
}