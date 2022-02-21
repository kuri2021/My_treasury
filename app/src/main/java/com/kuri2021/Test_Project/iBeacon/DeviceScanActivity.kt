//package com.kuri2021.Test_Project.iBeacon
//
//import android.app.ListActivity
//import android.bluetooth.BluetoothAdapter
//import android.bluetooth.BluetoothManager
//import android.content.pm.PackageManager
////import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.os.Handler
//import android.view.*
//import android.widget.BaseAdapter
//import android.widget.TextView
//import android.widget.Toast
////import androidx.recyclerview.widget.RecyclerView
//import com.kuri2021.Test_Project.R
//import java.util.*
//import kotlin.collections.ArrayList
//
//class DeviceScanActivity : ListActivity() {
//
//    private val mLeDeviceListAdapter: LeDeviceListAdapter
//        get() {
//            TODO()
//        }
//    private var mBluetoothAdapter: BluetoothAdapter? = null
//    private var mScanning = false
//    private val mHandler: Handler? = null
//
//    private val REQUEST_ENABLE_BT = 1
//    private val SCAN_PERIOD: Long = 10000
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
////        setContentView(R.layout.activity_device_scan)
//
//        /**
//         * Check if the current mobile phone supports ble Bluetooth, if you do
//         * not support the exit program
//         * 블루투스 사용 가능 체크
//         */
//        if (!packageManager.hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
//            Toast.makeText(this, R.string.ble_not_supported, Toast.LENGTH_SHORT).show()
//            finish()
//        }
//
//        /**
//         * Adapter Bluetooth, get a reference to the Bluetooth adapter (API),
//         * which must be above android4.3 or above.
//         * 블루투스 어뎁터 android4.3이상이여야 가능
//         */
//        val bluetoothManager = getSystemService(BLUETOOTH_SERVICE) as BluetoothManager
//        mBluetoothAdapter = bluetoothManager.adapter
//        mBluetoothAdapter = bluetoothManager.adapter
//
//        /**
//         * Check whether the device supports Bluetooth
//         * 블루투스 지원 확인->지원을 안할경우 종료
//         */
//        if (mBluetoothAdapter == null) {
//            Toast.makeText(this, R.string.error_bluetooth_not_supported, Toast.LENGTH_SHORT).show()
//            finish()
//            return
//        }
//    }
//
//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_devicescan, menu)
//        if (menu != null) {
//            if (!mScanning) {
//                menu.findItem(R.id.menu_stop).setVisible(false)
//                menu.findItem(R.id.menu_scan).setVisible(true)
//                menu.findItem(R.id.menu_refresh).setActionView(null)
//            } else {
//                menu.findItem(R.id.menu_stop).setVisible(true)
//                menu.findItem(R.id.menu_scan).setVisible(false)
//                menu.findItem(R.id.menu_refresh).setActionView(R.layout.actionbar_indeterminate_progress)
//            }
//
//        }
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.itemId) {
//            R.id.menu_scan -> {
////                mLeDeviceListAdapter.clear()
//                scanLeDevice(true)
//            }
//            R.id.menu_stop -> scanLeDevice(false)
//        }
//        return true
//    }
//
//    fun scanLeDevice(enable:Boolean){
//        if(enable){
//            if (mHandler != null) {
//                mHandler.postDelayed({
//                    mScanning=false
//                    mBluetoothAdapter?.stopLeScan(mLeScanCallback)
//                    invalidateOptionsMenu()
//                },SCAN_PERIOD)
//                mScanning=true
//                mBluetoothAdapter?.startLeScan(mLeScanCallback)
//            }else{
//                mScanning=false
//                mBluetoothAdapter?.stopLeScan(mLeScanCallback)
//            }
//            invalidateOptionsMenu()
//        }
//    }
//
//    private val mLeScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
//        /**
//         * Package data into iBeacon
//         * iBeacon에 데이터 패키징
//         */
//        /**
//         * Package data into iBeacon
//         * iBeacon에 데이터 패키징
//         */
//        /**
//         * Package data into iBeacon
//         * iBeacon에 데이터 패키징
//         */
//        /**
//         * Package data into iBeacon
//         * iBeacon에 데이터 패키징
//         */
////            val ibeacon: iBeaconClass.iBeacon = iBeaconClass.fromScanData(device, rssi, scanRecord)
//        val ibeacon: iBeaconClass.iBeacon? = iBeaconClass.fromScanData(device, rssi, scanRecord)
//        runOnUiThread {
//            if (ibeacon != null) {
////                mLeDeviceListAdapter.addDevice(ibeacon)
//            }
//            mLeDeviceListAdapter.notifyDataSetChanged()
//        }
//    }
//
//    private class LeDeviceListAdapter : BaseAdapter() {
//        private var mLeDevices: ArrayList<iBeaconClass.iBeacon>? = null
//        private var mInflator: LayoutInflater? = null
//
//        fun LeDeviceListAdapter(){
//            mLeDevices= ArrayList()
//            mInflator=DeviceScanActivity().layoutInflater
//        }
//
//        override fun getCount(): Int {
//            return mLeDevices!!.size
//        }
//
//        override fun getItem(position: Int): iBeaconClass.iBeacon? {
//            return mLeDevices?.get(position)
//        }
//
//        override fun getItemId(position: Int): Long {
//            return position.toLong()
//        }
//
//        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//            val viewHolder:ViewHolder
//            if(convertView==null){
//                viewHolder= ViewHolder()
//
//
//            }
//            return viewHolder
//        }
//    }
//
//    internal class ViewHolder {
//        var txt_name: TextView? = null
//        var txt_major: TextView? = null
//        var txt_minor: TextView? = null
//        var txt_uuid: TextView? = null
//        var txt_mac: TextView? = null
//        var txt_rssi: TextView? = null
//    }
//}