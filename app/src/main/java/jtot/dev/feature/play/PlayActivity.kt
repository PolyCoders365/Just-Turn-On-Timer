package jtot.dev.feature.play

import android.content.Intent
import android.os.Bundle
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityPlayBinding
import jtot.dev.feature.play.decoration.ContentDecoration
import jtot.dev.feature.timertodo.TimerTodoActivity
import jtot.dev.model.Schedule
import jtot.dev.model.Todo
import jtot.dev.utils.createStringDummy
import jtot.dev.utils.dpToPixel

class PlayActivity : BaseActivity<ActivityPlayBinding>(R.layout.activity_play) {
    private val schedule = Schedule().createDummy()

    private val contentAdapter: ContentAdapter by lazy {
        ContentAdapter().apply {
            setContentList(
                listOf(
                    createStringDummy(),
                    Todo().createDummy(),
                    Todo().createStarDummy(),
                    createStringDummy(),
                    Todo().createStarDummy(),
                    createStringDummy(),
                    createStringDummy(),
                ),
            )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.title = schedule.title
        binding.rvContent.run {
            adapter = contentAdapter
            addItemDecoration(ContentDecoration(dpToPixel(16f).toInt()))
        }

        binding.btnEdit.setOnClickListener {
            Intent(this, TimerTodoActivity::class.java).apply {
                putExtra("schedule", schedule)
            }.run(::startActivity)
        }
    }
}
