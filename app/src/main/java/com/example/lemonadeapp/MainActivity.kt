package com.example.lemonadeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.lemonadeapp.ui.theme.LemonadeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    LemonadeApp(modifier=Modifier)
                }
            }
        }
    }
}

@Composable
fun LemonadeApp( modifier: Modifier = Modifier) {

    var lemonAppState by remember {
        mutableStateOf(1)
    }

    var squeezeCount by remember { mutableStateOf(0) }

    val imageRes = when (lemonAppState){
        1 -> R.drawable.lemon_tree
        2 -> R.drawable.lemon_squeeze
        3 -> R.drawable.lemon_drink
        else -> R.drawable.lemon_restart
    }

    when(lemonAppState){
        1 -> LemonadeAItem(
            title = R.string.tap_lemon,
            imagePath = imageRes,
            imageContentDesc = R.string.lemon_tree,
            onImageClick = {
                lemonAppState = 2
                squeezeCount = (2..4).random()
            })
        2 -> LemonadeAItem(
            title = R.string.keep_tapping,
            imagePath = imageRes,
            imageContentDesc = R.string.lemon,
            onImageClick = {
                squeezeCount--
                println("squeezeCount -> $squeezeCount")
                if(squeezeCount==0){
                    lemonAppState = 3
                }
            })
        3 -> LemonadeAItem(
            title = R.string.tap_lemonade,
            imagePath = imageRes ,
            imageContentDesc = R.string.glass_of_lemonade,
            onImageClick = {
                    lemonAppState = 4
            })

        4 -> LemonadeAItem(
            title = R.string.tap_to_empty,
            imagePath = imageRes ,
            imageContentDesc = R.string.empty_glass,
            onImageClick = {
                lemonAppState = 1
            })
    }
}


@Composable
fun LemonadeAItem(title:Int,imagePath:Int, imageContentDesc:Int, onImageClick:()->Unit, modifier:Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box (
            contentAlignment = Alignment.Center,
            modifier = modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    color = Color.DarkGray,
                ).padding(16.dp)
                .clickable {
                    onImageClick()
                },


        ) {
            Image(painter = painterResource(id = imagePath),
                contentDescription = stringResource(id = imageContentDesc,
                ),

                )
        }
        Spacer(modifier = Modifier.size(16.dp))
        Text(text = stringResource(id = title),modifier = modifier)
    }



}