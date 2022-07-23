package com.firstgroup.secondhand.ui.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.espresso.IdlingRegistry
import com.firstgroup.secondhand.utils.EspressoIdlingResource
import com.firstgroup.secondhand.utils.TestTag.BANNER_SECTION
import com.firstgroup.secondhand.utils.TestTag.CATEGORY_SECTION
import com.firstgroup.secondhand.utils.TestTag.FAKE_SEARCH_BAR
import com.firstgroup.secondhand.utils.TestTag.PRODUCT_LIST_SECTION
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain

@HiltAndroidTest
class MainActivityTest {

    private val hiltRule = HiltAndroidRule(this)
    private val composeTestRule = createAndroidComposeRule<MainActivity>()

    @get:Rule
    val rule: RuleChain = RuleChain
        .outerRule(hiltRule)
        .around(composeTestRule)

    @Before
    fun setUp() {
        IdlingRegistry.getInstance().register(EspressoIdlingResource.countingIdlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.countingIdlingResource)
    }

    @Test
    fun shouldDisplayHomeContent() {
        listOf(
            FAKE_SEARCH_BAR,
            BANNER_SECTION,
            CATEGORY_SECTION,
            PRODUCT_LIST_SECTION,
        ).onEach {
            composeTestRule.onNodeWithTag(it)
                .assertIsDisplayed()
        }
    }

}