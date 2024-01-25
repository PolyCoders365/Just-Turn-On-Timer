package jtot.dev.feature.play

import android.content.Intent
import android.os.Bundle
import jtot.dev.R
import jtot.dev.base.BaseActivity
import jtot.dev.databinding.ActivityPlayBinding
import jtot.dev.feature.play.decoration.ContentDecoration
import jtot.dev.feature.timertodo.TimerTodoActivity
import jtot.dev.model.Schedule
import jtot.dev.utils.dpToPixel

class PlayActivity : BaseActivity<ActivityPlayBinding>(R.layout.activity_play) {
    private val schedule = Schedule().createDummy()

    private val contentAdapter: ContentAdapter by lazy {
        ContentAdapter().apply {
            setContentList(
                schedule.contents,
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

        binding.btnStart.setOnClickListener {
            Intent(this, TimerTodoActivity::class.java).apply {
                putExtra("schedule", schedule)
            }.run(::startActivity)
        }
        binding.btnEdit.setOnClickListener {
            // TODO: Schedule 작성 view로 이동
        }
    }
}
