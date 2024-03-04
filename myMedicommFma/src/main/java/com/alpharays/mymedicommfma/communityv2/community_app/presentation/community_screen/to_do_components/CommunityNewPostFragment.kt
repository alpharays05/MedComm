package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.to_do_components


//class CommunityNewPostFragment : Fragment() {
//    private var _binding: FragmentCommunityNewPostBinding? = null
//    private val binding get() = _binding!!
//    private var newPostContentTextWatcher: TextWatcher? = null
//    private var newPostTitleTextWatcher: TextWatcher? = null
//    private var postContent = ""
//    private var postTitle = ""
//    private var communityPostsList: List<AllCommunityPosts> = emptyList()
//    private val handler by lazy { Handler(Looper.getMainLooper()) }
//    private lateinit var communityViewModel: CommunityViewModel // TODO : will crash - viewmodel not initialized
//    private val communityViewModel: CommunityViewModel by viewModels {
//        CustomViewModelFactory(
//            MedicoApp.getInstance().getAlphaInjector().getCommunityListUseCase()
//        )
//    }
//    private lateinit var authTokenSharedPref: SharedPreferences
//    private var authTokenSharedPrefValue = ""
//
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        authTokenSharedPref = requireActivity()
//            .getSharedPreferences(
//                "authTokenSharedPrefHighPriority",
//                AppCompatActivity.MODE_PRIVATE
//            )
//        authTokenSharedPrefValue = authTokenSharedPref.getString("authToken", null).toString()
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        // Inflate the layout for this fragment
//        _binding = FragmentCommunityNewPostBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        bindViews(authTokenSharedPrefValue)
//        bindObservers()
//    }
//
//
//    private fun bindViews(token: String) {
//        binding.addNewPostBtn.setOnClickListener {
//            val addNewCommunityPost = AddNewCommunityPost(postTitle, postContent)
//            if (postTitle.isEmpty() || postContent.isEmpty()) {
//                showToast("Title or Post can not be empty")
//                return@setOnClickListener
//            }
//            if (postContent.length < 25) {
//                showToast("Post content must be greater than 25 characters")
//                return@setOnClickListener
//            }
//            communityViewModel.addNewCommunityPost(token, addNewCommunityPost)
//        }
//        textWatcherFun()
//    }
//
//
//    @SuppressLint("ClickableViewAccessibility")
//    private fun textWatcherFun() {
//        with(binding) {
//            newPostContent.setOnTouchListener { v, event ->
//                newPostContent.gravity = Gravity.START and Gravity.TOP
//                false
//            }
//            newPostContentTextWatcher = object : TextWatcher {
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                    // This method is called before the text is changed.
//                }
//
//                override fun onTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    before: Int,
//                    count: Int
//                ) {
//                    // This method is called when the text is changed.
//                    postContent = s.toString()
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                    // This method is called after the text is changed.
//                }
//            }
//            newPostContent.addTextChangedListener(newPostContentTextWatcher)
//
//            newPostTitleTextWatcher = object : TextWatcher {
//                override fun beforeTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    count: Int,
//                    after: Int
//                ) {
//                    // This method is called before the text is changed.
//                }
//
//                override fun onTextChanged(
//                    s: CharSequence?,
//                    start: Int,
//                    before: Int,
//                    count: Int
//                ) {
//                    // This method is called when the text is changed.
//                    postTitle = s.toString()
//                }
//
//                override fun afterTextChanged(s: Editable?) {
//                    // This method is called after the text is changed.
//                }
//            }
//            newPostTitle.addTextChangedListener(newPostTitleTextWatcher)
//        }
//    }
//
//
//    private fun bindObservers() {
////        communityViewModel.addNewPostLiveData.observe(viewLifecycleOwner) {
////            when (it) {
////                is ResponseResult.Loading -> {
////                    showToast("Posting...")
////                }
////
////                is ResponseResult.Success -> {
////                    if (it.data?.success == "1") {
////                        showToast("Posted Successfully")
////                        findNavController().navigate(R.id.action_communityNewPostFragment_to_communityFragment)
////                    }
////                }
////
////                is ResponseResult.Error -> {
////                    showToast(it.message.toString())
////                }
////            }
////        }
//    }
//
//
//    private fun showToast(msg: String) {
//        CustomToast.showToast(requireContext(), msg)
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//        _binding = null
//    }
//}