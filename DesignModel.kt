package com.relevanttechnosketch.app

data class DesignModel(
    val id: String,
    val title: String,
    val software: String,       // SolidWorks, Creo, AutoCAD
    val fileFormats: String,    // SLDPRT, STEP, PRT, DWG
    val description: String,
    val specifications: String, // Material, Tolerance, Parts count
    val category: String        // Gearbox, Surfacing, Drafting, SheetMetal
)
