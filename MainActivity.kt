package com.relevanttechnosketch.app

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var rvDesigns: RecyclerView
    private lateinit var btnQuickOrder: MaterialButton
    private lateinit var btnUploadOrderHero: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnQuickOrder = findViewById(R.id.btnQuickOrder)
        btnUploadOrderHero = findViewById(R.id.btnUploadOrderHero)
        rvDesigns = findViewById(R.id.rvDesignPortfolio)

        // Launch CAD Order / Upload Screen
        val launchOrder = {
            val intent = Intent(this, OrderCadActivity::class.java)
            startActivity(intent)
        }

        btnQuickOrder.setOnClickListener { launchOrder() }
        btnUploadOrderHero.setOnClickListener { launchOrder() }

        setupPortfolioRecyclerView()
    }

    private fun setupPortfolioRecyclerView() {
        val designList = listOf(
            DesignModel(
                id = "1",
                title = "Dual-Stage Spur Gearbox",
                software = "SOLIDWORKS 3D",
                fileFormats = "SLDPRT / STEP",
                description = "Full parametric mechanical assembly with 24 parts, EN8 steel shafts, and ISO 2768 tolerances.",
                specifications = "Ratio 4:1 • 1440 RPM Input • Bearings 6205",
                category = "SolidWorks"
            ),
            DesignModel(
                id = "2",
                title = "Centrifugal Impeller Blade",
                software = "PTC CREO",
                fileFormats = "PRT / IGES",
                description = "Class-A curved aerodynamic blade surfacing, styled for high-efficiency turbocharger air flow.",
                specifications = "12,000 RPM Max • Al 7075-T6 • CFD Mesh Ready",
                category = "Creo"
            ),
            DesignModel(
                id = "3",
                title = "Flange Coupling Blueprint",
                software = "AUTOCAD 2D",
                fileFormats = "DWG / DXF",
                description = "ASME Y14.5 compliant manufacturing 2D draft with full GD&T, surface finish (Ra 0.8), and BOM.",
                specifications = "Sheet A2 • Scale 1:1 • Tolerance H7/k6",
                category = "AutoCAD"
            ),
            DesignModel(
                id = "4",
                title = "IP65 Sheet Metal Enclosure",
                software = "SOLIDWORKS",
                fileFormats = "SLDPRT / DXF",
                description = "Precision sheet metal casing with laser cut flat pattern, bend allowances, and PEM fasteners.",
                specifications = "1.5mm CRCA Steel • CNC Brake Press Ready",
                category = "SolidWorks"
            )
        )

        rvDesigns.layoutManager = LinearLayoutManager(this)
        rvDesigns.adapter = DesignAdapter(designList) { selectedDesign ->
            val intent = Intent(this, OrderCadActivity::class.java).apply {
                putExtra("SELECTED_TITLE", selectedDesign.title)
                putExtra("SELECTED_SOFTWARE", selectedDesign.software)
            }
            startActivity(intent)
        }
    }
}
