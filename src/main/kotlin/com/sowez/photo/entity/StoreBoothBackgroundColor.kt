package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class StoreBoothBackgroundColor(
    store: Store,
    boothBackgroundColor: BoothBackgroundColor
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "store_booth_background_color_id")
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "store_id")
    var store: Store = store
        protected set
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "booth_background_color_id")
    var boothBackgroundColor: BoothBackgroundColor = boothBackgroundColor
        protected set

}