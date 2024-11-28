package ru.itis.homework3

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import ru.itis.homework3.base.BaseActivity
import ru.itis.homework3.databinding.ActivityMainBinding
import ru.itis.homework3.utils.NavigationAction
import ru.itis.homework3.utils.ScreenTags

class MainActivity : BaseActivity() {

    private val viewBinding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override val mainContainerId: Int = R.id.main_fragment_container

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigate(
                destination = QuestionnaireFragment(),
                destinationTag = ScreenTags.MULTIPLE_TYPES_LIST_TAG,
                action = NavigationAction.ADD
            )
        }
    }
}