package com.example.akif.devinfo.models

/**
 * Created by akif on 9/26/17.
 */

class CPU(var processor: String, var vendorID: String, var cpuFamily: String, var model: String,
          var modelName: String, var stepping: String, var microCode: String, var cpuMHz: String,
          var cacheSize: String, var physicalID: String, var siblings: String, var coreID: String,
          var cpuCores: String, var apiCid: String, var initialApiCid: String, var fDivBug: String,
          var f00Bug: String, var comeBug: String, var fpu: String, var fpuException: String,
          var cpuIdLevel: String, var wp: String, var flags: String, var bugs: String, var bojomips: String,
          var clFlushSize: String, var cacheAlignment: String, var addressSizes: String, var powerManagement: String) {
}
