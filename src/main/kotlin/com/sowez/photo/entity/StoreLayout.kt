package com.sowez.photo.entity

import jakarta.persistence.*

@Entity
class StoreLayout(
        store: Store,
        layout: Layout,
) {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "store_layout_id")
    val id: Long = 0

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "store_id")
    var store: Store = store
        protected set

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "layout_id")
    var layout: Layout = layout
        protected set

}