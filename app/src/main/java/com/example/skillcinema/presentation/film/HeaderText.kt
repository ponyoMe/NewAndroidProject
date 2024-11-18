package com.example.skillcinema.presentation.film

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testing.R

@Composable
fun HeaderText(content: String, listSize: Int, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(26.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = content,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight(600),
                color = Color(39,39,39)
            )
        )

        Row (
            modifier = Modifier
                .clickable{
                    onClick()
                },
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = listSize.toString(),
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    color = Color(61,59,255)
                )
            )
            Spacer(modifier = Modifier.width(4.dp))
            IconButton( onClick = {}
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_forward),
                    contentDescription = null
                )
            }
        }
    }
}