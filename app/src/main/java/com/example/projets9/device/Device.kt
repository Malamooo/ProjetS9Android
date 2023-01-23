package com.example.projets9.device

import android.bluetooth.BluetoothDevice

data class Device (
    var name: String?,
    var mac: String?,
    var device: BluetoothDevice
) {
    override fun equals(other: Any?): Boolean {
        return other is Device && other.mac == this.mac
    }
}
