package com.example.cocktailsapp

import com.example.cocktailsapp.home.business.CategoryList
import com.example.cocktailsapp.home.business.CategoryItem
import com.example.cocktailsapp.home.data.CategoryListEntity
import javax.inject.Inject

class CategoryEntityToCategoryMapper @Inject constructor(
): Function1<CategoryListEntity, CategoryList> {

    override fun invoke(categoryListEntity: CategoryListEntity): CategoryList {
        return CategoryList(
            categoryListEntity.drinks.map {
                CategoryItem(
                    it.strCategory,
                    ""
                )
            } ?: listOf()
        )
    }
}