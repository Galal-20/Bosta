// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package com.galal.bosta.model

data class Search (
    val data: List<Datum>,
    val success: Boolean,
    val message: String
)

data class Datum (
    val cityOtherName: String,
    val cityName: String,
    val cityCode: String,
    val districts: List<District>,
    val dropOffAvailability: Boolean,
    val cityID: String,
    val pickupAvailability: Boolean
)

data class District (
    val coverage: Coverage,
    val districtID: String,
    val districtName: String,
    val notAllowedBulkyOrders: Boolean? = null,
    val zoneOtherName: String,
    val zoneID: String,
    val dropOffAvailability: Boolean,
    val zoneName: String,
    val districtOtherName: String,
    val pickupAvailability: Boolean
)

enum class Coverage {
    Bosta
}
