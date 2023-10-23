package com.easynewsvideomaker.easynewsvideomaker.news_poster

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.easynewsvideomaker.easynewsvideomaker.R
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityNewsPosterBinding
import com.easynewsvideomaker.easynewsvideomaker.databinding.ActivityNotificationBinding
import com.easynewsvideomaker.easynewsvideomaker.fragment.HomeFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.AdvtFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.BAFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.BreakingFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.ElcationFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.ListFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.NewsFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.NewsPaperFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.NewsRoomFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.PosterFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.QuotationFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.TvMediaFragment
import com.easynewsvideomaker.easynewsvideomaker.news_poster.fragment.WishesFragment

class News_Poster_Activity : AppCompatActivity() {

    lateinit var newsPosterBinding: ActivityNewsPosterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsPosterBinding = ActivityNewsPosterBinding.inflate(layoutInflater)
        setContentView(newsPosterBinding.root)

        horizontalLayout()

        newsPosterBinding.imgBack.setOnClickListener {
            onBackPressed()
        }
        fragmentSet(PosterFragment())  // fragment set
    }


    private fun horizontalLayout() {
        newsPosterBinding.linPoster.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(PosterFragment())  // fragment set
        }

        newsPosterBinding.linNewsPaper.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(NewsPaperFragment())  // fragment set
        }

        newsPosterBinding.linNews.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(NewsFragment())  // fragment set
        }

        newsPosterBinding.linQuotation.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(QuotationFragment())  // fragment set
        }
        newsPosterBinding.linList.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(ListFragment())  // fragment set
        }

        newsPosterBinding.linBreaking.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(BreakingFragment())  // fragment set
        }
        newsPosterBinding.linTVMedia.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(TvMediaFragment())  // fragment set

        }
        newsPosterBinding.linNewsRoom.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(NewsRoomFragment())  // fragment set
        }
        newsPosterBinding.linAdvt.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(AdvtFragment())  // fragment set
        }
        newsPosterBinding.linWishes.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(WishesFragment())  // fragment set
        }
        newsPosterBinding.linBA.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_red_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_black_round)

            fragmentSet(BAFragment())  // fragment set
        }
        newsPosterBinding.linElection.setOnClickListener {
            newsPosterBinding.linPoster.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsPaper.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNews.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linQuotation.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linList.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBreaking.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linTVMedia.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linNewsRoom.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linAdvt.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linWishes.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linBA.setBackgroundResource(R.drawable.news_black_round)
            newsPosterBinding.linElection.setBackgroundResource(R.drawable.news_red_round)

            fragmentSet(ElcationFragment())  // fragment set
        }
    }


    private fun fragmentSet(fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val transaction: FragmentTransaction = manager.beginTransaction()
        transaction.replace(R.id.frameNews, fragment)
        transaction.commit()
    }
}