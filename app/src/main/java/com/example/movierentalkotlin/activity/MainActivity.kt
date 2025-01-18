package com.example.movierentalkotlin.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.movierentalkotlin.R
import com.example.movierentalkotlin.database.MovieRentalDatabase
import com.example.movierentalkotlin.database.entity.Client
import com.example.movierentalkotlin.database.entity.ClientMovieRating
import com.example.movierentalkotlin.database.entity.Employee
import com.example.movierentalkotlin.database.entity.Movie
import com.example.movierentalkotlin.database.entity.MovieRental
import com.example.movierentalkotlin.databinding.ActivityMainBinding
import com.example.movierentalkotlin.util.SessionManager
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        val navHostFragment = supportFragmentManager
            .findFragmentById(binding.navHostFragment.id) as NavHostFragment
        val navController = navHostFragment.navController

        setupStartDestination(navController)

        NavigationUI.setupWithNavController(binding.navView, navController)

        binding.navView.setNavigationItemSelectedListener  { item ->
            when (item.itemId) {
                R.id.authorizationFragment -> {
                    binding.drawerLayout.closeDrawers()
                    handleLogout(navController)
                    true
                }
                else -> {
                    binding.drawerLayout.closeDrawers()
                    navController.navigate(item.itemId)
                    true
                }
            }
        }

//        insertAllData()
    }

    private fun setupStartDestination(navController: NavController) {
        val graph = navController.navInflater.inflate(R.navigation.nav_graph)
        graph.setStartDestination(
            if (sessionManager.isLoggedIn()) R.id.movieCatalogFragment else R.id.authorizationFragment
        )
        navController.graph = graph
    }

    private fun handleLogout(navController: NavController) {
        sessionManager.logout()

        if (navController.currentDestination?.id != R.id.authorizationFragment) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.nav_graph, inclusive = true)
                .build()

            navController.navigate(R.id.authorizationFragment, null, navOptions)
        }
    }

    private fun insertAllData() {
        lifecycleScope.launch {
            val application = this@MainActivity.application
            val movieDao = MovieRentalDatabase.getInstance(application).movieDao
            val clientDao = MovieRentalDatabase.getInstance(application).clientDao
            val employeeDao = MovieRentalDatabase.getInstance(application).employeeDao
            val clientMovieRatingDao =
                MovieRentalDatabase.getInstance(application).clientMovieRatingDao
            val movieRentalDao = MovieRentalDatabase.getInstance(application).movieRentalDao

            val movies = listOf(
                Movie(
                    title = "Начало",
                    releaseYear = "2010",
                    director = "Кристофер Нолан",
                    country = "США",
                    duration = 148.0,
                    rentalCost = 49.9,
                    averageRating = 0.0,
                    description = """
                        Кобб – талантливый вор, лучший из лучших в опасном искусстве извлечения: он крадет ценные секреты из глубин подсознания во время сна, когда человеческий разум наиболее уязвим. Редкие способности Кобба сделали его ценным игроком в привычном к предательству мире промышленного шпионажа, но они же превратили его в извечного беглеца и лишили всего, что он когда-либо любил.

                        И вот у Кобба появляется шанс исправить ошибки. Его последнее дело может вернуть все назад, но для этого ему нужно совершить невозможное – инициацию. Вместо идеальной кражи Кобб и его команда спецов должны будут провернуть обратное. Теперь их задача – не украсть идею, а внедрить ее. Если у них получится, это и станет идеальным преступлением.

                        Но никакое планирование или мастерство не могут подготовить команду к встрече с опасным противником, который, кажется, предугадывает каждый их ход. Врагом, увидеть которого мог бы лишь Кобб.
                    """.trimIndent(),
                    imageUrl = "https://kinopoisk-ru.clstorage.net/UW16E7149/896adeyy/w2klyFWg9yvWfPZkzWo02z_kGwy2-91yaczhjwroGRXFcq-wXrtVNmqScQNhlOqk1hY0DjpDzk0YEc1Mqmr9M3etAJlwtQY_JvyDhSmfAa4KIOqp05rc9uyooF4_PaznNnAsksmZNsTokLcXBYC70VMT0Aq6o3rtueX5i4neuxRgCTepxiKHX4YOhdA8mSb7z_X93EeryhLazTcf2fqJpgTgzCIJSfb0CsIHlhYivVHTImI5SWHKrx_EiPgpOhiFQjoV6mQypu50j8ZDDGilCG113J42SLviK_yFn9rKKLfXwL-CWjhXZFoSVOQEQ2yTguIj62nyyS0dZnq6Gnj691Ma9avVkzePh44VkG5qdMjeJX0uh7kZUKmOlD8L6NuENUO8ocvrB4dLwKSEJBKvQ5ERoNi4QOicbmf763raeIRhiBW59fCXPHXttSBuqoV6PUU-nzWZutGJrzfteAjZRnQQfRL5ySWkCUDnp9djn7FBU5GK6JD7X8xkmjtaCHrFYAklaVbTJR53r9dwv9qkW2z2nyy2u7twi02FPJiKSfa34h3SigqFNQqRFWYkMQ1RczJx-vqBqw2fRBpqmMoq5FGpVnpl4zUcJFxm064Ztakvpq_8JNvqwDlMJHwq2XtUl_NPgttrd8cJQNU0R7Odc0NyIog7cztcn3YKCSprqBZQGtR55eOUPwZdtiNuWtdLHNdubTeZqMJ4j-X86-nqhGSQ7uFre1dlOrMGhxWTj5KTwXKKG1MajW9F-BkpKZr2o9slW7TCpR0n7ybwrcjVSv-GPo8kKhqyWy_EDOr5yVeVYC2hq8jm5wrw5RcHUm_BsBHzeDtw-7yOhuiYmbu5dgLYBUtmk3T9Fk72IjxqF6gutS8M1wpKssn_V735aZh2dQCtg_sr1IQZwYXXZFF8k1PiYMmq42r_n8aIiwp5yxTjmFWZNMNEXiQeBiKNWTeZ7jb_vcSbiMBL_RZNKwtZFGexDpFLeYWWGkMFFRWDr2CiADObiWGrX46G-olbGhqkwiqVekWjJm1H_iTQXBoGGZ9HzvwUCKtwWM6nPpoJ-rY20hzCill3lXpQZ8dkgZ3yoWJiK4sDSa48R3qb2zu4hxLq1UhH0SdsFG4G039Zd1ncJDyPxzhJwMtc9H6KSks35OCPIbpLZXc48mfElfJP4GKgYtoq44jPrxb7-opoe-RxmOWYRXJEX6b-F8N_mzaqDQa_zEfKKZCZXXXey1hrR-SA7JJoSIRFSBFmlleSnAFBA4J6quBof-5WCGiba8r1kNrEiETjVK4XDNeSPWkkWb7Gzr7UScnzOA9lvrvaGbT3IC5jqNkU1ovSt0dlUb2jAXASGrnByy6dhkh6OEmIFhGLdMoUsse-B4wngO6phKtMtwyNZsgr0VvNVy3qujv15qEOs0h712U6I6Z2tFCtIaIhY3kKstpNL5VIuBsLqiZSeeeYV6BWTHS95zKcKLWbreXuzHUKKvBJ73Z8uju5JWaBfMKYiMSUuUCXRgSRLjCgEXO5-sP6fqxWWDlZOthksQqEiCQw50_EnkeSXXplSF31_L526hnw-T2Vrxj7yWTUgE-x6HpVN_hzdZbGUe5xYyJR-ZjTau39pZgqqNmrRPD4FlmFUnYvByxWIx4qhxs_h11cJriIAPqcNW_amknUV3BM4Kr7p7coAVXVRfGOA0LwE6v68pjd3Kf5qDjoO1QASNR4tfP07ZSN9fGNiTZLvQbtT3UIefALzLWveUrr9oVRTMB4uNeWWpCkVMbDLeOgs0IJKXLJDd1mOaoKuYiW8asHy5bRdjwkjJXRjFp1C44VXU7UK9qyKt0ULJoY2zdFQ6wwKetHpFjxF7YV4a2TIhAyiciA6O9MRupaWKprxNHZ1MhFEISMB_-m8A37pIj_d71cpFjb8Lj_tz7JKakkdXCeUjvLt4X40BXVJcO_4pIDQVtqYIieX_Xqu-iaG-SR6jV4hKOmf7fMVGLsCpVKbsT9fIfrObBrv-Xs-JiZ1uXhXtOJexcHGiMn5SaQ79PAASP6yoDbPq11uDn5i_vXsbiGupQQdG-GX5RwzbllaZ9HjTz3CelCab11bLkYSnRHo6xCCDs3FQmy5HSm4Y3QwrORiKowu65-h_oY6Qnqt3NIdFkH4NcNl_4XoF9pBZo9V40v1lhLYHofZY-Kubm1ZAJ94qs6xQdYAmd0R4PcMbNh0ar4obkcXdTqKWrIOlTxSgaYhYGEnJX_JvBcmjZ6PaT_PVbYCcC5H1Zc2vgphnUCncH5SmcUW7N0xOZinyNS8eJoi2Cqz_w2GFho6-hkYCj3K7VCxg9WPNVwTLo3eQ_FvW83KPqAag_nftr7W6QncC_imCskh2uQBcTlca5SoaOB6Ipzy_89djt6C7qYhAHZdXmEE-SNpE6VIj-6xggPZQ9-tIuIoVstVs74iIkE1IJNI2rJhZQbgha3FCOfQ2LAcnraoajffUYJerjL-rUjWecZVsC0r8T-18BuyPRo38bNTGc6OvN4PAfda3p5lqegn4Ipe7eWmBJHF2exX3CRIBP5KMLIr54ku7t7Ook3oljG68Qwdz93nGVjP_imeY6XfO42acuiy6-VnhkriOS0At6g-8inlhmQ15ZFop_jMzPhqBoDyzyNhhr66xrb1VIYtXvGwyYNhE8lc02olShe1V1NVtqLo4sc5f9b6Hp1ReKP8Gt5VSRaEAVUNmFMIaKhAOta4XjdbyW4CNtIeCTganXKF-GVbea8pnLsexTaTMScfUSL21JYrjc_elvrVBYiDNP4mVTF-_BXpKSx7UOjMSO7qGNJLS4VqBlrijpmAYtkuIXixG62DeUCzJlXuT9Hray1iDmgOa1VrTnbaUYl8y-DiVplJyvhBOc0028hs9EiSMjQi4wOlHv5aDprRnIIF-hG8LSOtM8kMY5ZdInfRb1v9Si64InPh4yrKtpHhyJ-IikahsUZ8kcFRbFNg0ITYBvqkau_fHeoQ"
                ),
                Movie(
                    title = "Матрица",
                    releaseYear = "1999",
                    director = "Братья Вачовски",
                    country = "США",
                    duration = 136.0,
                    rentalCost = 39.9,
                    averageRating = 0.0,
                    description = """
                        Жизнь Томаса Андерсона разделена на две части: днём он — самый обычный офисный работник, получающий нагоняи от начальства, а ночью превращается в хакера по имени Нео, и нет места в сети, куда он бы не смог проникнуть. Но однажды всё меняется. Томас узнаёт ужасающую правду о реальности.
                    """.trimIndent(),
                    imageUrl = "https://kinopoisk-ru.clstorage.net/UW16E7149/896adeyy/w2klyFWg9yvWfPZkzWo02z_kGwy2-91yaczhjwroGRXFcq-wXrtVNmqScQMxhLp05uYkC4o2_kh4BPjsrxrdQ7etBYwV5QYKMznjhQyaAW4PgK_8Q9qs5uyooF4_PaznNnAsksmZNsTokLcXBYC70VMT0Aq6o3rtueX5i4neuxRgCTepxiKHX4YOhdA8mSb7z_X93EeryhLazTcf2fqJpgTgzCIJSfb0CsIHlhYivVHTImI5SWHKrx_EiPgpOhiFQjoV6mQypu50j8ZDDGilCG113J42SLviK_yFn9rKKLfXwL-CWjhXZFoSVOQEQ2yTguIj62nyyS0dZnq6Gnj691Ma9avVkzePh44VkG5qdMjeJX0uh7kZUKmOlD8L6NuENUO8ocvrB4dLwKSEJBKvQ5ERoNi4QOicbmf763raeIRhiBW59fCXPHXttSBuqoV6PUU-nzWZutGJrzfteAjZRnQQfRL5ySWkCUDnp9djn7FBU5GK6JD7X8xkmjtaCHrFYAklaVbTJR53r9dwv9qkW2z2nyy2u7twi02FPJiKSfa34h3SigqFNQqRFWYkMQ1RczJx-vqBqw2fRBpqmMoq5FGpVnpl4zUcJFxm064Ztakvpq_8JNvqwDlMJHwq2XtUl_NPgttrd8cJQNU0R7Odc0NyIog7cztcn3YKCSprqBZQGtR55eOUPwZdtiNuWtdLHNdubTeZqMJ4j-X86-nqhGSQ7uFre1dlOrMGhxWTj5KTwXKKG1MajW9F-BkpKZr2o9slW7TCpR0n7ybwrcjVSv-GPo8kKhqyWy_EDOr5yVeVYC2hq8jm5wrw5RcHUm_BsBHzeDtw-7yOhuiYmbu5dgLYBUtmk3T9Fk72IjxqF6gutS8M1wpKssn_V735aZh2dQCtg_sr1IQZwYXXZFF8k1PiYMmq42r_n8aIiwp5yxTjmFWZNMNEXiQeBiKNWTeZ7jb_vcSbiMBL_RZNKwtZFGexDpFLeYWWGkMFFRWDr2CiADObiWGrX46G-olbGhqkwiqVekWjJm1H_iTQXBoGGZ9HzvwUCKtwWM6nPpoJ-rY20hzCill3lXpQZ8dkgZ3yoWJiK4sDSa48R3qb2zu4hxLq1UhH0SdsFG4G039Zd1ncJDyPxzhJwMtc9H6KSks35OCPIbpLZXc48mfElfJP4GKgYtoq44jPrxb7-opoe-RxmOWYRXJEX6b-F8N_mzaqDQa_zEfKKZCZXXXey1hrR-SA7JJoSIRFSBFmlleSnAFBA4J6quBof-5WCGiba8r1kNrEiETjVK4XDNeSPWkkWb7Gzr7UScnzOA9lvrvaGbT3IC5jqNkU1ovSt0dlUb2jAXASGrnByy6dhkh6OEmIFhGLdMoUsse-B4wngO6phKtMtwyNZsgr0VvNVy3qujv15qEOs0h712U6I6Z2tFCtIaIhY3kKstpNL5VIuBsLqiZSeeeYV6BWTHS95zKcKLWbreXuzHUKKvBJ73Z8uju5JWaBfMKYiMSUuUCXRgSRLjCgEXO5-sP6fqxWWDlZOthksQqEiCQw50_EnkeSXXplSF31_L526hnw-T2Vrxj7yWTUgE-x6HpVN_hzdZbGUe5xYyJR-ZjTau39pZgqqNmrRPD4FlmFUnYvByxWIx4qhxs_h11cJriIAPqcNW_amknUV3BM4Kr7p7coAVXVRfGOA0LwE6v68pjd3Kf5qDjoO1QASNR4tfP07ZSN9fGNiTZLvQbtT3UIefALzLWveUrr9oVRTMB4uNeWWpCkVMbDLeOgs0IJKXLJDd1mOaoKuYiW8asHy5bRdjwkjJXRjFp1C44VXU7UK9qyKt0ULJoY2zdFQ6wwKetHpFjxF7YV4a2TIhAyiciA6O9MRupaWKprxNHZ1MhFEISMB_-m8A37pIj_d71cpFjb8Lj_tz7JKakkdXCeUjvLt4X40BXVJcO_4pIDQVtqYIieX_Xqu-iaG-SR6jV4hKOmf7fMVGLsCpVKbsT9fIfrObBrv-Xs-JiZ1uXhXtOJexcHGiMn5SaQ79PAASP6yoDbPq11uDn5i_vXsbiGupQQdG-GX5RwzbllaZ9HjTz3CelCab11bLkYSnRHo6xCCDs3FQmy5HSm4Y3QwrORiKowu65-h_oY6Qnqt3NIdFkH4NcNl_4XoF9pBZo9V40v1lhLYHofZY-Kubm1ZAJ94qs6xQdYAmd0R4PcMbNh0ar4obkcXdTqKWrIOlTxSgaYhYGEnJX_JvBcmjZ6PaT_PVbYCcC5H1Zc2vgphnUCncH5SmcUW7N0xOZinyNS8eJoi2Cqz_w2GFho6-hkYCj3K7VCxg9WPNVwTLo3eQ_FvW83KPqAag_nftr7W6QncC_imCskh2uQBcTlca5SoaOB6Ipzy_89djt6C7qYhAHZdXmEE-SNpE6VIj-6xggPZQ9-tIuIoVstVs74iIkE1IJNI2rJhZQbgha3FCOfQ2LAcnraoajffUYJerjL-rUjWecZVsC0r8T-18BuyPRo38bNTGc6OvN4PAfda3p5lqegn4Ipe7eWmBJHF2exX3CRIBP5KMLIr54ku7t7Ook3oljG68Qwdz93nGVjP_imeY6XfO42acuiy6-VnhkriOS0At6g-8inlhmQ15ZFop_jMzPhqBoDyzyNhhr66xrb1VIYtXvGwyYNhE8lc02olShe1V1NVtqLo4sc5f9b6Hp1ReKP8Gt5VSRaEAVUNmFMIaKhAOta4XjdbyW4CNtIeCTganXKF-GVbea8pnLsexTaTMScfUSL21JYrjc_elvrVBYiDNP4mVTF-_BXpKSx7UOjMSO7qGNJLS4VqBlrijpmAYtkuIXixG62DeUCzJlXuT9Hray1iDmgOa1VrTnbaUYl8y-DiVplJyvhBOc0028hs9EiSMjQi4wOlHv5aDprRnIIF-hG8LSOtM8kMY5ZdInfRb1v9Si64InPh4yrKtpHhyJ-IikahsUZ8kcFRbFNg0ITYBvqkau_fHeoQ"
                ),
                Movie(
                    title = "Интерстеллар",
                    releaseYear = "2014",
                    director = "Кристофер Нолан",
                    country = "США",
                    duration = 169.0,
                    rentalCost = 59.9,
                    averageRating = 0.0,
                    description = """
                        Когда засуха, пыльные бури и вымирание растений приводят человечество к продовольственному кризису, коллектив исследователей и учёных отправляется сквозь червоточину (которая предположительно соединяет области пространства-времени через большое расстояние) в путешествие, чтобы превзойти прежние ограничения для космических путешествий человека и найти планету с подходящими для человечества условиями.
                    """.trimIndent(),
                    imageUrl = "https://kinopoisk-ru.clstorage.net/UW16E7149/896adeyy/w2klyFWg9yvWfPZkzWo02z_kGwy2-91yaczhjwroGRXFcq-wXrtVNmqScQNhlMo0hsZEDv9m7thIJIj8qnrtI6etAFwANQYPZumzgDnPMR46sJ_5c8q55uyooF4_PaznNnAsksmZNsTokLcXBYC70VMT0Aq6o3rtueX5i4neuxRgCTepxiKHX4YOhdA8mSb7z_X93EeryhLazTcf2fqJpgTgzCIJSfb0CsIHlhYivVHTImI5SWHKrx_EiPgpOhiFQjoV6mQypu50j8ZDDGilCG113J42SLviK_yFn9rKKLfXwL-CWjhXZFoSVOQEQ2yTguIj62nyyS0dZnq6Gnj691Ma9avVkzePh44VkG5qdMjeJX0uh7kZUKmOlD8L6NuENUO8ocvrB4dLwKSEJBKvQ5ERoNi4QOicbmf763raeIRhiBW59fCXPHXttSBuqoV6PUU-nzWZutGJrzfteAjZRnQQfRL5ySWkCUDnp9djn7FBU5GK6JD7X8xkmjtaCHrFYAklaVbTJR53r9dwv9qkW2z2nyy2u7twi02FPJiKSfa34h3SigqFNQqRFWYkMQ1RczJx-vqBqw2fRBpqmMoq5FGpVnpl4zUcJFxm064Ztakvpq_8JNvqwDlMJHwq2XtUl_NPgttrd8cJQNU0R7Odc0NyIog7cztcn3YKCSprqBZQGtR55eOUPwZdtiNuWtdLHNdubTeZqMJ4j-X86-nqhGSQ7uFre1dlOrMGhxWTj5KTwXKKG1MajW9F-BkpKZr2o9slW7TCpR0n7ybwrcjVSv-GPo8kKhqyWy_EDOr5yVeVYC2hq8jm5wrw5RcHUm_BsBHzeDtw-7yOhuiYmbu5dgLYBUtmk3T9Fk72IjxqF6gutS8M1wpKssn_V735aZh2dQCtg_sr1IQZwYXXZFF8k1PiYMmq42r_n8aIiwp5yxTjmFWZNMNEXiQeBiKNWTeZ7jb_vcSbiMBL_RZNKwtZFGexDpFLeYWWGkMFFRWDr2CiADObiWGrX46G-olbGhqkwiqVekWjJm1H_iTQXBoGGZ9HzvwUCKtwWM6nPpoJ-rY20hzCill3lXpQZ8dkgZ3yoWJiK4sDSa48R3qb2zu4hxLq1UhH0SdsFG4G039Zd1ncJDyPxzhJwMtc9H6KSks35OCPIbpLZXc48mfElfJP4GKgYtoq44jPrxb7-opoe-RxmOWYRXJEX6b-F8N_mzaqDQa_zEfKKZCZXXXey1hrR-SA7JJoSIRFSBFmlleSnAFBA4J6quBof-5WCGiba8r1kNrEiETjVK4XDNeSPWkkWb7Gzr7UScnzOA9lvrvaGbT3IC5jqNkU1ovSt0dlUb2jAXASGrnByy6dhkh6OEmIFhGLdMoUsse-B4wngO6phKtMtwyNZsgr0VvNVy3qujv15qEOs0h712U6I6Z2tFCtIaIhY3kKstpNL5VIuBsLqiZSeeeYV6BWTHS95zKcKLWbreXuzHUKKvBJ73Z8uju5JWaBfMKYiMSUuUCXRgSRLjCgEXO5-sP6fqxWWDlZOthksQqEiCQw50_EnkeSXXplSF31_L526hnw-T2Vrxj7yWTUgE-x6HpVN_hzdZbGUe5xYyJR-ZjTau39pZgqqNmrRPD4FlmFUnYvByxWIx4qhxs_h11cJriIAPqcNW_amknUV3BM4Kr7p7coAVXVRfGOA0LwE6v68pjd3Kf5qDjoO1QASNR4tfP07ZSN9fGNiTZLvQbtT3UIefALzLWveUrr9oVRTMB4uNeWWpCkVMbDLeOgs0IJKXLJDd1mOaoKuYiW8asHy5bRdjwkjJXRjFp1C44VXU7UK9qyKt0ULJoY2zdFQ6wwKetHpFjxF7YV4a2TIhAyiciA6O9MRupaWKprxNHZ1MhFEISMB_-m8A37pIj_d71cpFjb8Lj_tz7JKakkdXCeUjvLt4X40BXVJcO_4pIDQVtqYIieX_Xqu-iaG-SR6jV4hKOmf7fMVGLsCpVKbsT9fIfrObBrv-Xs-JiZ1uXhXtOJexcHGiMn5SaQ79PAASP6yoDbPq11uDn5i_vXsbiGupQQdG-GX5RwzbllaZ9HjTz3CelCab11bLkYSnRHo6xCCDs3FQmy5HSm4Y3QwrORiKowu65-h_oY6Qnqt3NIdFkH4NcNl_4XoF9pBZo9V40v1lhLYHofZY-Kubm1ZAJ94qs6xQdYAmd0R4PcMbNh0ar4obkcXdTqKWrIOlTxSgaYhYGEnJX_JvBcmjZ6PaT_PVbYCcC5H1Zc2vgphnUCncH5SmcUW7N0xOZinyNS8eJoi2Cqz_w2GFho6-hkYCj3K7VCxg9WPNVwTLo3eQ_FvW83KPqAag_nftr7W6QncC_imCskh2uQBcTlca5SoaOB6Ipzy_89djt6C7qYhAHZdXmEE-SNpE6VIj-6xggPZQ9-tIuIoVstVs74iIkE1IJNI2rJhZQbgha3FCOfQ2LAcnraoajffUYJerjL-rUjWecZVsC0r8T-18BuyPRo38bNTGc6OvN4PAfda3p5lqegn4Ipe7eWmBJHF2exX3CRIBP5KMLIr54ku7t7Ook3oljG68Qwdz93nGVjP_imeY6XfO42acuiy6-VnhkriOS0At6g-8inlhmQ15ZFop_jMzPhqBoDyzyNhhr66xrb1VIYtXvGwyYNhE8lc02olShe1V1NVtqLo4sc5f9b6Hp1ReKP8Gt5VSRaEAVUNmFMIaKhAOta4XjdbyW4CNtIeCTganXKF-GVbea8pnLsexTaTMScfUSL21JYrjc_elvrVBYiDNP4mVTF-_BXpKSx7UOjMSO7qGNJLS4VqBlrijpmAYtkuIXixG62DeUCzJlXuT9Hray1iDmgOa1VrTnbaUYl8y-DiVplJyvhBOc0028hs9EiSMjQi4wOlHv5aDprRnIIF-hG8LSOtM8kMY5ZdInfRb1v9Si64InPh4yrKtpHhyJ-IikahsUZ8kcFRbFNg0ITYBvqkau_fHeoQ"
                )
                ,
                Movie(
                    title = "1+1",
                    releaseYear = "2011",
                    director = "Оливье Накаш",
                    country = "Франция",
                    duration = 112.0,
                    rentalCost = 50.0,
                    averageRating = 0.0,
                    description = """
                        Пострадав в результате несчастного случая, богатый аристократ Филипп нанимает в помощники человека, который менее всего подходит для этой работы, – молодого жителя предместья Дрисса, только что освободившегося из тюрьмы. Несмотря на то, что Филипп прикован к инвалидному креслу, Дриссу удается привнести в размеренную жизнь аристократа дух приключений.
                    """.trimIndent(),
                    imageUrl = "https://kinopoisk-ru.clstorage.net/UW16E7149/896adeyy/w2klyFWg9yvWfPZkzWo02z_kGwy2-91yaczhjwroGRXFcq-wXrtVNmqScQNhZIpUptakC5o2fu0oQb2Mrz-tw6etAMxA5QOPdumjgBzftFta5a_JRoq81uyooF4_PaznNnAsksmZNsTokLcXBYC70VMT0Aq6o3rtueX5i4neuxRgCTepxiKHX4YOhdA8mSb7z_X93EeryhLazTcf2fqJpgTgzCIJSfb0CsIHlhYivVHTImI5SWHKrx_EiPgpOhiFQjoV6mQypu50j8ZDDGilCG113J42SLviK_yFn9rKKLfXwL-CWjhXZFoSVOQEQ2yTguIj62nyyS0dZnq6Gnj691Ma9avVkzePh44VkG5qdMjeJX0uh7kZUKmOlD8L6NuENUO8ocvrB4dLwKSEJBKvQ5ERoNi4QOicbmf763raeIRhiBW59fCXPHXttSBuqoV6PUU-nzWZutGJrzfteAjZRnQQfRL5ySWkCUDnp9djn7FBU5GK6JD7X8xkmjtaCHrFYAklaVbTJR53r9dwv9qkW2z2nyy2u7twi02FPJiKSfa34h3SigqFNQqRFWYkMQ1RczJx-vqBqw2fRBpqmMoq5FGpVnpl4zUcJFxm064Ztakvpq_8JNvqwDlMJHwq2XtUl_NPgttrd8cJQNU0R7Odc0NyIog7cztcn3YKCSprqBZQGtR55eOUPwZdtiNuWtdLHNdubTeZqMJ4j-X86-nqhGSQ7uFre1dlOrMGhxWTj5KTwXKKG1MajW9F-BkpKZr2o9slW7TCpR0n7ybwrcjVSv-GPo8kKhqyWy_EDOr5yVeVYC2hq8jm5wrw5RcHUm_BsBHzeDtw-7yOhuiYmbu5dgLYBUtmk3T9Fk72IjxqF6gutS8M1wpKssn_V735aZh2dQCtg_sr1IQZwYXXZFF8k1PiYMmq42r_n8aIiwp5yxTjmFWZNMNEXiQeBiKNWTeZ7jb_vcSbiMBL_RZNKwtZFGexDpFLeYWWGkMFFRWDr2CiADObiWGrX46G-olbGhqkwiqVekWjJm1H_iTQXBoGGZ9HzvwUCKtwWM6nPpoJ-rY20hzCill3lXpQZ8dkgZ3yoWJiK4sDSa48R3qb2zu4hxLq1UhH0SdsFG4G039Zd1ncJDyPxzhJwMtc9H6KSks35OCPIbpLZXc48mfElfJP4GKgYtoq44jPrxb7-opoe-RxmOWYRXJEX6b-F8N_mzaqDQa_zEfKKZCZXXXey1hrR-SA7JJoSIRFSBFmlleSnAFBA4J6quBof-5WCGiba8r1kNrEiETjVK4XDNeSPWkkWb7Gzr7UScnzOA9lvrvaGbT3IC5jqNkU1ovSt0dlUb2jAXASGrnByy6dhkh6OEmIFhGLdMoUsse-B4wngO6phKtMtwyNZsgr0VvNVy3qujv15qEOs0h712U6I6Z2tFCtIaIhY3kKstpNL5VIuBsLqiZSeeeYV6BWTHS95zKcKLWbreXuzHUKKvBJ73Z8uju5JWaBfMKYiMSUuUCXRgSRLjCgEXO5-sP6fqxWWDlZOthksQqEiCQw50_EnkeSXXplSF31_L526hnw-T2Vrxj7yWTUgE-x6HpVN_hzdZbGUe5xYyJR-ZjTau39pZgqqNmrRPD4FlmFUnYvByxWIx4qhxs_h11cJriIAPqcNW_amknUV3BM4Kr7p7coAVXVRfGOA0LwE6v68pjd3Kf5qDjoO1QASNR4tfP07ZSN9fGNiTZLvQbtT3UIefALzLWveUrr9oVRTMB4uNeWWpCkVMbDLeOgs0IJKXLJDd1mOaoKuYiW8asHy5bRdjwkjJXRjFp1C44VXU7UK9qyKt0ULJoY2zdFQ6wwKetHpFjxF7YV4a2TIhAyiciA6O9MRupaWKprxNHZ1MhFEISMB_-m8A37pIj_d71cpFjb8Lj_tz7JKakkdXCeUjvLt4X40BXVJcO_4pIDQVtqYIieX_Xqu-iaG-SR6jV4hKOmf7fMVGLsCpVKbsT9fIfrObBrv-Xs-JiZ1uXhXtOJexcHGiMn5SaQ79PAASP6yoDbPq11uDn5i_vXsbiGupQQdG-GX5RwzbllaZ9HjTz3CelCab11bLkYSnRHo6xCCDs3FQmy5HSm4Y3QwrORiKowu65-h_oY6Qnqt3NIdFkH4NcNl_4XoF9pBZo9V40v1lhLYHofZY-Kubm1ZAJ94qs6xQdYAmd0R4PcMbNh0ar4obkcXdTqKWrIOlTxSgaYhYGEnJX_JvBcmjZ6PaT_PVbYCcC5H1Zc2vgphnUCncH5SmcUW7N0xOZinyNS8eJoi2Cqz_w2GFho6-hkYCj3K7VCxg9WPNVwTLo3eQ_FvW83KPqAag_nftr7W6QncC_imCskh2uQBcTlca5SoaOB6Ipzy_89djt6C7qYhAHZdXmEE-SNpE6VIj-6xggPZQ9-tIuIoVstVs74iIkE1IJNI2rJhZQbgha3FCOfQ2LAcnraoajffUYJerjL-rUjWecZVsC0r8T-18BuyPRo38bNTGc6OvN4PAfda3p5lqegn4Ipe7eWmBJHF2exX3CRIBP5KMLIr54ku7t7Ook3oljG68Qwdz93nGVjP_imeY6XfO42acuiy6-VnhkriOS0At6g-8inlhmQ15ZFop_jMzPhqBoDyzyNhhr66xrb1VIYtXvGwyYNhE8lc02olShe1V1NVtqLo4sc5f9b6Hp1ReKP8Gt5VSRaEAVUNmFMIaKhAOta4XjdbyW4CNtIeCTganXKF-GVbea8pnLsexTaTMScfUSL21JYrjc_elvrVBYiDNP4mVTF-_BXpKSx7UOjMSO7qGNJLS4VqBlrijpmAYtkuIXixG62DeUCzJlXuT9Hray1iDmgOa1VrTnbaUYl8y-DiVplJyvhBOc0028hs9EiSMjQi4wOlHv5aDprRnIIF-hG8LSOtM8kMY5ZdInfRb1v9Si64InPh4yrKtpHhyJ-IikahsUZ8kcFRbFNg0ITYBvqkau_fHeoQ"
                )
            )

            val clients = listOf(
                Client(
                    fullName = "Иван Иванов",
                    dateOfBirth = "15.05.1990",
                    address = "Улица Главная, 123, Москва",
                    phoneNumber = "+7 123 456 7890",
                    dateOfRegistration = "10.01.2023",
                    imageUrl = ""
                ),
                Client(
                    fullName = "Мария Петрова",
                    dateOfBirth = "25.10.2002",
                    address = "Улица Липовая, 456, Москва",
                    phoneNumber = "+7 987 654 3210",
                    dateOfRegistration = "05.11.2022",
                    imageUrl = "https://w.wallhaven.cc/full/4y/wallhaven-4ykm1x.png"
                )
            )

            val employees = listOf(
                Employee(
                    fullName = "Алиса Иванова",
                    dateOfBirth = "12.03.1988",
                    address = "Улица Сосновая, 789, Москва",
                    phoneNumber = "+7 654 321 0987",
                    dateOfHire = "01.07.2021",
                    dateOfDismissal = "",
                    salary = 35000.0,
                    imageUrl = "https://w.wallhaven.cc/full/xl/wallhaven-xlj113.png"
                ),
                Employee(
                    fullName = "Борис Смирнов",
                    dateOfBirth = "22.09.1975",
                    address = "Улица Дубовая, 321, Москва",
                    phoneNumber = "+7 555 666 7777",
                    dateOfHire = "15.03.2019",
                    dateOfDismissal = "",
                    salary = 42000.0,
                    imageUrl = "https://w.wallhaven.cc/full/r7/wallhaven-r7km5q.jpg"
                ),
                Employee(
                    fullName = "Илья Ворожейкин",
                    dateOfBirth = "31.10.1999",
                    address = "Улица Ленина, 121, Москва",
                    phoneNumber = "+7 987 663 7277",
                    dateOfHire = "15.03.2019",
                    dateOfDismissal = "20.12.2023",
                    salary = 30000.0,
                    imageUrl = "https://w.wallhaven.cc/full/2k/wallhaven-2kg97y.jpg"
                )
            )

            val clientMovieRatings = listOf(
                ClientMovieRating(
                    clientId = 1,
                    movieId = 1,
                    rating = 8.0,
                    comment = "Потрясающий фильм! Умопомрачительные повороты сюжета."
                ),
                ClientMovieRating(
                    clientId = 2,
                    movieId = 1,
                    rating = 9.0,
                    comment = "Отличный фильм, но местами немного запутанный."
                ),
                ClientMovieRating(
                    clientId = 1,
                    movieId = 2,
                    rating = 9.5,
                    comment = "Классика! Абсолютно влюбился в этот фильм."
                )
            )

            val movieRentals = listOf(
                MovieRental(
                    clientId = 1,
                    employeeId = 1,
                    movieId = 1,
                    dateOfReceipt = "01.12.2023",
                    dateOfReturn = "05.12.2023"
                ),
                MovieRental(
                    clientId = 2,
                    employeeId = 2,
                    movieId = 2,
                    dateOfReceipt = "10.01.2024",
                    dateOfReturn = "15.01.2024"
                )
            )

            movieDao.insertAll(movies)
            clientDao.insertAll(clients)
            employeeDao.insertAll(employees)
            clientMovieRatingDao.insertAll(clientMovieRatings)
            movieRentalDao.insertAll(movieRentals)
        }
    }
}