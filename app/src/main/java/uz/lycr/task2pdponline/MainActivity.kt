package uz.lycr.task2pdponline

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import androidx.navigation.Navigation
import uz.lycr.task2pdponline.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val llFrag = findViewById<LinearLayout>(R.id.ll_frag)
        val llSplash = findViewById<LinearLayout>(R.id.ll_splash)

        Handler(Looper.getMainLooper()).postDelayed({
            llFrag.animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
            llSplash.animation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)

            llSplash.visibility = View.GONE
            llFrag.visibility = View.VISIBLE
        }, 2000)
    }

    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.my_nav_host_fragment).navigateUp()
    }

}