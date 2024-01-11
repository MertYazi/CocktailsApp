package com.example.cocktailsapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.example.cocktailsapp.home.business.AlcoholItem
import com.example.cocktailsapp.home.business.CategoryItem
import com.example.cocktailsapp.home.business.GlassItem
import com.example.cocktailsapp.home.business.IngredientItem
import com.example.cocktailsapp.home.presentation.AlcoholFragment
import com.example.cocktailsapp.home.presentation.CategoryFragment
import com.example.cocktailsapp.home.presentation.GlassFragment
import com.example.cocktailsapp.home.presentation.IngredientFragment
import java.io.Serializable

open class ViewPagerAdapter(
    fragmentManager: FragmentManager,
    categoryList: ArrayList<CategoryItem>,
    glassList: ArrayList<GlassItem>,
    ingredientList: ArrayList<IngredientItem>,
    alcoholList: ArrayList<AlcoholItem>
) : FragmentStatePagerAdapter(fragmentManager) {
    private val fragmentList: ArrayList<Fragment> = ArrayList()
    private val titleList: ArrayList<String> = ArrayList()
    private val mCategoryList: ArrayList<CategoryItem> = categoryList
    private val mGlassList: ArrayList<GlassItem> = glassList
    private val mIngredientList: ArrayList<IngredientItem> = ingredientList
    private val mAlcoholList: ArrayList<AlcoholItem> = alcoholList

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        val fragment: Fragment = fragmentList[position]
        val bundle = Bundle()
        when(fragment) {
            is CategoryFragment -> {
                bundle.putSerializable("myCategoryList", mCategoryList as Serializable)
            }
            is GlassFragment -> {
                bundle.putSerializable("myGlassList", mGlassList as Serializable)
            }
            is IngredientFragment -> {
                bundle.putSerializable("myIngredientList", mIngredientList as Serializable)
            }
            is AlcoholFragment -> {
                bundle.putSerializable("myAlcoholList", mAlcoholList as Serializable)
            }
        }

        fragment.arguments = bundle
        return fragment
    }

    override fun getPageTitle(position: Int): CharSequence {
        return titleList[position]
    }

    override fun getItemPosition(`object`: Any): Int {
        return POSITION_NONE
    }

    fun addFragment(fragment: Fragment, title: String) {
        fragmentList.add(fragment)
        titleList.add(title)
    }
}