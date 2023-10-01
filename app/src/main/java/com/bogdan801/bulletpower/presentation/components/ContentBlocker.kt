package com.bogdan801.bulletpower.presentation.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bogdan801.bulletpower.data.util.login.AuthUIClient
import com.bogdan801.bulletpower.domain.util.ActionResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener

@Composable
fun ContentBlocker(
    databaseReference: DatabaseReference,
    authUIClient: AuthUIClient,
    content: @Composable () -> Unit = {}
) {
    val context = LocalContext.current
    var isAppLegal by rememberSaveable { mutableStateOf(true) }
    var illegalMessage by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(key1 = true){
        authUIClient.signInWithEmailAndPassword("bob123@gmail.com", "qwerty123")
        if(authUIClient.getSignedInUser() != null){
            addAppIsLegalListener(databaseReference, authUIClient) { flagResult ->
                if(flagResult is ActionResult.Success){
                    isAppLegal = flagResult.data ?: false
                }
                else {
                    Toast.makeText(context, flagResult.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
            addIllegalAppMessageListener(databaseReference, authUIClient) { messageResult ->
                if(messageResult is ActionResult.Success){
                    illegalMessage = messageResult.data.toString()
                }
                else {
                    Toast.makeText(context, messageResult.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    if(isAppLegal) content()
    else{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ){
            Text(
                modifier = Modifier.padding(horizontal = 16.dp),
                text = illegalMessage,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

private fun addAppIsLegalListener(
    databaseReference: DatabaseReference,
    authUIClient: AuthUIClient,
    listener: (ActionResult<Boolean?>) -> Unit
) {
    val user = authUIClient.getSignedInUser()
    if(user!=null){
        val valuesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.value as Boolean?
                if(data == null) databaseReference.child("isLegalApp").setValue(true)
                listener(ActionResult.Success(data = data))
            }
            override fun onCancelled(databaseError: DatabaseError) {
                listener(ActionResult.Error(databaseError.toException().message.toString()))
                databaseError.toException().printStackTrace()
            }
        }
        databaseReference.child("isLegalApp").addValueEventListener(valuesListener)
    }
}

private fun addIllegalAppMessageListener(
    databaseReference: DatabaseReference,
    authUIClient: AuthUIClient,
    listener: (ActionResult<String?>) -> Unit
) {
    val user = authUIClient.getSignedInUser()
    if(user!=null){
        val valuesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val data = dataSnapshot.value as String?
                if(data == null) databaseReference.child("illegalMessage").setValue("Так справи не роблять. Для розблокування додатку, оплатіть вартість його розробки!")
                listener(ActionResult.Success(data = data))
            }
            override fun onCancelled(databaseError: DatabaseError) {
                listener(ActionResult.Error(databaseError.toException().message.toString()))
                databaseError.toException().printStackTrace()
            }
        }
        databaseReference.child("illegalMessage").addValueEventListener(valuesListener)
    }
    else {
        listener(ActionResult.Error("User is not logged in!!!"))
    }
}
