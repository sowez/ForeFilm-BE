package com.sowez.photo.entity

import com.sowez.photo.type.DownloadType
import com.sowez.photo.type.PayType
import com.sowez.photo.type.StoreType
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
class Store(
    name: String, type: StoreType, brand: Brand,
    addressInfo: Address,
    phoneNumber: String? = null,
    operatingTime: String? = null,
    payTypes: List<PayType>? = null,
    boothInfo: BoothInfo? = null
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "store_id")
    val id: Long = 0

    @Column(nullable = false, name = "store_created_datetime")
    val createdDatetime: LocalDateTime = LocalDateTime.now()
    @Column(nullable = false, name = "store_updated_datetime")
    var updatedDatetime: LocalDateTime = LocalDateTime.now()
        protected set

    @Column(nullable = false, name = "store_name")
    var name: String = name
        protected set
    @Column(nullable = false, name = "store_type")
    @Enumerated(EnumType.STRING)
    var type: StoreType = type
        protected set
    @Embedded
    var addressInfo: Address = addressInfo
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "store_brand_id")
    var brand: Brand = brand
        protected set

    @Column(name = "store_phone_num")
    var phoneNumber: String? = phoneNumber
        protected set
    @Column
    var operatingTime: String? = operatingTime
        protected set
    @Column
    var payTypes: String? = payTypes?.joinToString(",")
        protected set
    @Embedded
    var boothInfo: BoothInfo? = boothInfo
        protected set

    fun addBoothInfo(boothInfo: BoothInfo?) {
        this.boothInfo = boothInfo
    }

    fun getPayTypes(): Set<PayType> {
        val payTypeStrings: MutableSet<String> = mutableSetOf()
        this.payTypes?.split(",")
            ?.let { payTypeStrings.addAll(it) }
        return payTypeStrings.map {
            PayType.valueOf(it)
        }.toSet()
    }

}

@Embeddable
data class Address(
    @Column(nullable = false, name = "store_address")
    var address: String,
    @Column
    var latitude: Double? = null,
    @Column
    var longitude: Double? = null
)

@Embeddable
data class BoothInfo(
    @Column var boothCount: Int? = null,
    @Column var minPeopleCount: Int? = null,
    @Column var maxPeopleCount: Int? = null,
    @Column var downloadPeriod: String? = null,
    @Column var isReshoot: Boolean? = null,
    @Column var isRemote: Boolean? = null,
    @Column var isCurlingIron: Boolean? = null,
    @Column var isEnvelope: Boolean? = null,
    @Column var isFootrest: Boolean? = null
) {
    @Column var downloadTypes: String? = null

    fun setDownloadTypes(downloadTypes: Set<DownloadType>?) {
        this.downloadTypes = downloadTypes?.joinToString(",")
    }

    fun getDownloadTypes(): Set<DownloadType> {
        val downloadTypeStrings: MutableSet<String> = mutableSetOf()
        this.downloadTypes?.split(",")
            ?.let { downloadTypeStrings.addAll(it) }
        return downloadTypeStrings.map {
            DownloadType.valueOf(it)
        }.toSet()
    }
}