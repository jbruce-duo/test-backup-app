#!/bin/bash -eu

adb shell bmgr enable true
echo "initializing local transport"
adb shell bmgr transport com.android.localtransport/.LocalTransport | grep -q "Selected transport" || (echo "Error: error selecting local transport"; exit 1)
adb shell bmgr init com.android.localtransport/.LocalTransport
adb shell settings put secure backup_local_transport_parameters 'is_encrypted=true'
adb shell bmgr backupnow "com.example.myapplication"
adb shell bmgr transport com.google.android.gms/.backup.BackupTransportService
echo "Done"
