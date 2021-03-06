package secretymus.id.newsapp.views

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_detail.*
import secretymus.id.newsapp.R
import secretymus.id.newsapp.databinding.FragmentDetailBinding
import secretymus.id.newsapp.model.Article
import secretymus.id.newsapp.news.DetailViewModel
import secretymus.id.newsapp.news.NewsActionListener
import secretymus.id.newsapp.utils.getProgressDrawable
import secretymus.id.newsapp.utils.toast

class DetailFragment : Fragment(), NewsActionListener {
    private lateinit var mArticle: Article
    private lateinit var viewModel: DetailViewModel
    private lateinit var dataBinding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        dataBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_detail, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            mArticle = DetailFragmentArgs.fromBundle(it).mArticle

            val urlToImg = mArticle.urlToImage
            Log.d("DetailFragment", urlToImg!!)

        }

        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.fetch(mArticle)
        observeViewModel()
    }
    
    private fun observeViewModel() {
        viewModel.newsLiveData.observe(viewLifecycleOwner, { article ->
            article?.let {
                dataBinding.article = mArticle

                it.urlToImage?.let {url ->
                    val options = RequestOptions()
                            .placeholder(getProgressDrawable(requireContext()))
                            .error(R.drawable.ic_photo)
                    Glide.with(requireContext())
                            .setDefaultRequestOptions(options)
                            .load(url)
                            .into(contentImages)
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        arguments?.let {
            mArticle = DetailFragmentArgs.fromBundle(it).mArticle
            if (mArticle.source?.id == "b_c_v"){
                return
            } else {
                inflater.inflate(R.menu.detail_menu, menu)
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_save -> {
                viewModel.saveNews(mArticle)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSavingNews() {
        activity?.toast("News has been saved")
    }


}