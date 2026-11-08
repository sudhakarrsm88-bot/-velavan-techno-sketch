package com.relevanttechnosketch.app

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class OrderCadActivity : AppCompatActivity() {

    private lateinit var rgSoftware: RadioGroup
    private lateinit var etProjectNotes: TextInputEditText
    private lateinit var etCustomerName: TextInputEditText
    private lateinit var etCustomerPhone: TextInputEditText
    private lateinit var btnChooseFile: MaterialButton
    private lateinit var btnSubmitCadOrder: MaterialButton
    private lateinit var tvUploadStatus: TextView
    private lateinit var ivUploadPreview: ImageView

    private var selectedFileUri: Uri? = null

    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            selectedFileUri = uri
            tvUploadStatus.text = "File Attached: ${uri.lastPathSegment ?: "design_sketch.png"}"
            tvUploadStatus.setTextColor(getColor(R.color.accent_cyan))
            ivUploadPreview.setImageResource(android.R.drawable.checkbox_on_background)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_cad)

        val toolbar = findViewById<Toolbar>(R.id.orderToolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        rgSoftware = findViewById(R.id.rgSoftware)
        etProjectNotes = findViewById(R.id.etProjectNotes)
        etCustomerName = findViewById(R.id.etCustomerName)
        etCustomerPhone = findViewById(R.id.etCustomerPhone)
        btnChooseFile = findViewById(R.id.btnChooseFile)
        btnSubmitCadOrder = findViewById(R.id.btnSubmitCadOrder)
        tvUploadStatus = findViewById(R.id.tvUploadStatus)
        ivUploadPreview = findViewById(R.id.ivUploadPreview)

        // Pre-fill if navigated from specific design card
        val selectedTitle = intent.getStringExtra("SELECTED_TITLE")
        val selectedSoftware = intent.getStringExtra("SELECTED_SOFTWARE")
        if (selectedTitle != null) {
            etProjectNotes.setText("Customization request for: $selectedTitle\nSpecifications / Changes: ")
        }

        btnChooseFile.setOnClickListener {
            filePickerLauncher.launch("*/*")
        }

        btnSubmitCadOrder.setOnClickListener {
            submitOrder()
        }
    }

    private fun submitOrder() {
        val name = etCustomerName.text?.toString()?.trim().orEmpty()
        val phone = etCustomerPhone.text?.toString()?.trim().orEmpty()
        val notes = etProjectNotes.text?.toString()?.trim().orEmpty()

        if (name.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Please provide your Name and WhatsApp phone number", Toast.LENGTH_SHORT).show()
            return
        }

        val checkedSoftwareId = rgSoftware.checkedRadioButtonId
        val softwareName = findViewById<RadioButton>(checkedSoftwareId)?.text?.toString() ?: "SolidWorks"

        // Compose WhatsApp direct routing message
        val orderMessage = """
            *New CAD Request - Relevant Techno Sketch*
            👤 Customer: $name
            📞 Phone: $phone
            💻 Target CAD: $softwareName
            📝 Requirements: $notes
            📎 Attachment: ${if (selectedFileUri != null) "File Attached" else "None"}
        """.trimIndent()

        Toast.makeText(this, "Order received! Connecting to Relevant Techno Sketch engineering team...", Toast.LENGTH_LONG).show()

        // Open WhatsApp directly with prefilled order request
        try {
            val waIntent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://api.whatsapp.com/send?text=${Uri.encode(orderMessage)}")
            }
            startActivity(waIntent)
            finish()
        } catch (e: Exception) {
            finish()
        }
    }
}
