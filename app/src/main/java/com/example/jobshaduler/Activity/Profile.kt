package com.example.jobshaduler.Activity

import android.app.Activity
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.example.jobshaduler.R
import com.example.jobshaduler.classes.singleton.emailandpass
import com.example.jobshaduler.databinding.ActivityProfileBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.UUID

class Profile : AppCompatActivity() {
    lateinit var b: ActivityProfileBinding
    var isedit = false
    lateinit var db: FirebaseFirestore
    lateinit var userEmailPass: HashMap<String, Any?>
    private lateinit var imageView: ImageView
    private lateinit var storage: FirebaseStorage
    private var uploadedImageUrl: String? = null
    private val PICK_IMAGE_REQUEST = 1
    private lateinit var progressDialog: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(b.root)

        storage = FirebaseStorage.getInstance()
        imageView = b.profiledp
        db = Firebase.firestore
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Loading...")
        progressDialog.setCancelable(false)

        Log.d("ProfileActivity", "Before setting text views")
        b.lundkball.text = emailandpass.usename ?: "Default Username"
        b.maderchodemail.text = emailandpass.email ?: "Default Email"
        b.passb.text = emailandpass.pass ?: "Default Password"
        b.profilejob.text = emailandpass.jobtype ?: "Default Job Type"
        emailandpass.image?.let {
            Glide.with(this).load(it).into(b.profiledp)
        } ?: Glide.with(this).load(R.drawable.accunt).into(b.profiledp)
        Log.d("ProfileActivity", "After setting text views")

        b.editbutton.setOnClickListener {
            isedit = !isedit
            if (isedit) {
                b.editbutton.setBackgroundColor(getColor(R.color.card1))
            } else {
                b.editbutton.setBackgroundColor(getColor(R.color.card2))
            }
        }

        b.phonenoblock.setOnClickListener {
            editblock("edit phone No", b.profilephonenumber)
        }

        b.passb.setOnClickListener {
            editblock("change password", b.passb)
        }

        b.profilenameemail.setOnClickListener {
            editblock("change name", b.lundkball)
        }

        b.logout.setOnClickListener {
            Firebase.auth.signOut()
            emailandpass.compani = null
            emailandpass.jobtype = null
            emailandpass.email = null
            emailandpass.pass = null
            emailandpass.usename = null
            emailandpass.phone = null
            startActivity(Intent(this, Authactivity::class.java))
        }

        b.backtomain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }

        b.profiledp.setOnClickListener {
            editblock("image url", b.lundkball)
        }

        val selectImageLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                progressDialog.show()

                // Perform a long-running task

                imageView.setImageURI(it)
                uploadImageToFirebase(it)
            } ?: Toast.makeText(this, "Failed to load image", Toast.LENGTH_SHORT).show()
        }

        imageView.setOnClickListener {
            selectImageLauncher.launch("image/*")
        }
    }

    private fun uploadImageToFirebase(fileUri: Uri) {
        val storageRef = storage.reference
        val imageRef = storageRef.child("images/${UUID.randomUUID()}.jpg")

        imageRef.putFile(fileUri)
            .addOnSuccessListener {
                imageRef.downloadUrl.addOnSuccessListener { uri ->
                    uploadedImageUrl = uri.toString()
                    imageset(uploadedImageUrl!!)
                    Toast.makeText(this, "Image uploaded successfully. URL: $uploadedImageUrl", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Image upload failed: ${it.message}", Toast.LENGTH_SHORT).show()
                Log.e("imageupload", "Image upload failed: ${it.message}")
            }
    }

    private fun editblock(label: String, view: TextView) {
        if (isedit) {
            b.editblock.visibility = View.VISIBLE
            b.layoutbuttonsanctionlayoutlable.text = label

            b.editblocksubmit.setOnClickListener {
                view.text = b.layoutbuttonsanctionlayoutedit.text
                b.editblock.visibility = View.GONE
                settoemailpass(label, b.layoutbuttonsanctionlayoutedit.text.toString())
            }

            b.editblockcancle.setOnClickListener {
                b.editblock.visibility = View.GONE
            }
        }
    }



    private fun settoemailpass(type: String, setdata: String) {
        when (type) {
            "edit phone No" -> {
                emailandpass.phone = setdata
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image
                )
                dpupdate()
            }
            "change password" -> {
                emailandpass.pass = setdata
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image
                )
                dpupdate()
            }
            "change name" -> {
                emailandpass.usename = setdata
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image
                )
                dpupdate()
            }
            "image url" -> {
                emailandpass.image = setdata
                userEmailPass = hashMapOf(
                    "email" to emailandpass.email,
                    "password" to emailandpass.pass,
                    "phoneNo" to emailandpass.phone,
                    "compani" to emailandpass.compani,
                    "job_type" to emailandpass.jobtype,
                    "username" to emailandpass.usename,
                    "dp" to emailandpass.image
                )
                dpupdate()
                Glide.with(this).load(emailandpass.image).into(b.profiledp)
            }
        }
    }

    private fun imageset(setdata: String) {
        emailandpass.image = setdata
        savedp(setdata)
        userEmailPass = hashMapOf(
            "email" to emailandpass.email,
            "password" to emailandpass.pass,
            "phoneNo" to emailandpass.phone,
            "compani" to emailandpass.compani,
            "job_type" to emailandpass.jobtype,
            "username" to emailandpass.usename,
            "dp" to emailandpass.image
        )
        dpupdate()
        Glide.with(this).load(emailandpass.image).into(b.profiledp)
    }

    private fun dpupdate() {
        db.collection(emailandpass.email!!).document("personalinfo").set(userEmailPass)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "updated", Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Toast.makeText(this, "${it.cause}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun selectImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            val selectedImageUri = data?.data
            selectedImageUri?.let {
                CoroutineScope(Dispatchers.Main).launch {
                    val bitmap = loadImageAsync(it)
                    // Do something with the loaded bitmap, such as displaying it in an ImageView
                }
            }
        }
    }

    private suspend fun loadImageAsync(imageUri: Uri): Bitmap = withContext(Dispatchers.IO) {
        val inputStream = contentResolver.openInputStream(imageUri)
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
        bitmap
    }

    fun savedp(url:String){
        Firebase.database.reference.child("employ/${emailandpass.empid}/personal/dp")
            .setValue(url).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT)
                        .show()
                    progressDialog.dismiss()
                } else {
                    Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show()
                    Log.e("update", "Failed to update data: ${it.exception?.message}")
                }
            }
    }
}
