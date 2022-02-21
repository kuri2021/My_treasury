package com.kuri2021.Test_Project.iBeacon

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.icu.text.DecimalFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.io.Serializable
import java.lang.StringBuilder
import kotlin.experimental.and

class iBeaconClass {

    class iBeacon : Serializable {
        var name: String? = null
        var major = 0
        var minor = 0
        var proximityUuid: String? = null
        var bluetoothAddress: String? = null
        var txPower = 0
        var rssi = 0
        var distance: String? = null
    }

    companion object {
        private const val serialVersionUID = 1L

        var df = java.text.DecimalFormat("#0.00")
        @SuppressLint("DefaultLocale")
        fun fromScanData(device: BluetoothDevice?, rssi: Int, scanData: ByteArray): iBeacon? {
            var startByte = 2
            var patternFound = false
            while (startByte <= 5) {
                if (scanData[startByte + 2].toInt() and 0xff == 0x02 && scanData[startByte + 3].toInt() and 0xff == 0x15) {
                    // yes! This is an iBeacon
                    patternFound = true
                    break
                } else if (scanData[startByte].toInt() and 0xff == 0x2d && scanData[startByte + 1]
                        .toInt() and 0xff == 0x24 && scanData[startByte + 2].toInt() and 0xff == 0xbf && scanData[startByte + 3].toInt() and 0xff == 0x16
                ) {
                    val iBeacon = iBeacon()
                    iBeacon.major = 0
                    iBeacon.minor = 0
                    iBeacon.proximityUuid = "00000000-0000-0000-0000-000000000000"
                    iBeacon.txPower = -55
                    return iBeacon
                } else if (scanData[startByte].toInt() and 0xff == 0xad && scanData[startByte + 1]
                        .toInt() and 0xff == 0x77 && scanData[startByte + 2].toInt() and 0xff == 0x00 && scanData[startByte + 3].toInt() and 0xff == 0xc6
                ) {
                    val iBeacon = iBeacon()
                    iBeacon.major = 0
                    iBeacon.minor = 0
                    iBeacon.proximityUuid = "00000000-0000-0000-0000-000000000000"
                    iBeacon.txPower = -55
                    return iBeacon
                }
                startByte++
            }
            if (patternFound == false) {
                // This is not an iBeacon
                return null
            }
            val iBeacon = iBeacon()
            iBeacon.major =
                (scanData[startByte + 20] and 0xff.toByte()) * 0x100 + (scanData[startByte + 21] and 0xff.toByte())
            iBeacon.minor =
                (scanData[startByte + 22] and 0xff.toByte()) * 0x100 + (scanData[startByte + 23] and 0xff.toByte())
            iBeacon.txPower = scanData[startByte + 24].toInt() // this one is signed
            iBeacon.rssi = rssi
            if (device != null) {
                iBeacon.bluetoothAddress = device.address
                iBeacon.name = device.name
            }
            val proximityUuidBytes = ByteArray(16)
            System.arraycopy(scanData, startByte + 4, proximityUuidBytes, 0, 16)
            val hexString: String? = bytesToHexString(proximityUuidBytes)
            val sb = StringBuilder()
            if(hexString!=null){
                sb.append(hexString.substring(0, 8))
                sb.append("-")
                sb.append(hexString.substring(8, 12))
                sb.append("-")
                sb.append(hexString.substring(12, 16))
                sb.append("-")
                sb.append(hexString.substring(16, 20))
                sb.append("-")
                sb.append(hexString.substring(20, 32))
            }
            iBeacon.proximityUuid = sb.toString().toUpperCase()
            iBeacon.distance = df.format(
                calculateAccuracy(
                    scanData[startByte + 24].toInt(), rssi.toDouble()
                )
            )
            return iBeacon
        }
        private fun bytesToHexString(src: ByteArray?): String? {
            val stringBuilder = StringBuilder("")
            if (src == null || src.size <= 0) {
                return null
            }
            for (i in src.indices) {
                val v = (src[i] and 0xFF.toByte()).toInt()
                val hv = Integer.toHexString(v)
                if (hv.length < 2) {
                    stringBuilder.append(0)
                }
                stringBuilder.append(hv)
            }
            return stringBuilder.toString()
        }
        fun calculateAccuracy(txPower: Int, rssi: Double): Double {
            if (rssi > 0) {
                return -1.0 // if we cannot determine accuracy, return -1.
            }
            val ratio = rssi * 1.0 / txPower
            return if (ratio < 1.0) {
                Math.pow(ratio, 10.0)
            } else {
                0.89976 * Math.pow(ratio, 7.7095) + 0.111
            }
        }
    }

}