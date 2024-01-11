package com.example.cocktailsapp

import com.example.cocktailsapp.home.business.GlassList
import com.example.cocktailsapp.home.business.GlassItem
import com.example.cocktailsapp.home.data.GlassListEntity
import javax.inject.Inject

class GlassEntityToGlassMapper @Inject constructor(
): Function1<GlassListEntity, GlassList> {
    override fun invoke(glassListEntity: GlassListEntity): GlassList {
        return GlassList(
            glassListEntity.drinks.map {
                GlassItem(
                    it.strGlass,
                    ""
                )
            } ?: listOf()
        )
    }
}