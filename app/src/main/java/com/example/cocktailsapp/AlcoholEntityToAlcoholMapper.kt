package com.example.cocktailsapp

import com.example.cocktailsapp.home.business.AlcoholList
import com.example.cocktailsapp.home.business.AlcoholItem
import com.example.cocktailsapp.home.data.AlcoholListEntity
import javax.inject.Inject

class AlcoholEntityToAlcoholMapper @Inject constructor(
): Function1<AlcoholListEntity, AlcoholList> {

    override fun invoke(alcoholListEntity: AlcoholListEntity): AlcoholList {
        return AlcoholList(
            alcoholListEntity.drinks.map {
                AlcoholItem(
                    it.strAlcoholic,
                    ""
                )
            } ?: listOf()
        )
    }
}