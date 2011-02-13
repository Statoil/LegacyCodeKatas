package com.statoil.xmlparser.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

import com.statoil.xmlparser.Dataset;
import com.statoil.xmlparser.Fault;
import com.statoil.xmlparser.FaultDefinition;
import com.statoil.xmlparser.GeoPoint2D;
import com.statoil.xmlparser.Horizon;
import com.statoil.xmlparser.HorizonDefinition;
import com.statoil.xmlparser.Misc;
import com.statoil.xmlparser.SessionManager;
import com.statoil.xmlparser.Well;
import com.statoil.xmlparser.WellPick;

/**
 * Class for reading 3DPosModel related XML files. This class make use of
 * JDOM for parsing the XML.
 *
 */
public final class XMLReader {

    /**
     * Empty private constructor.
     */
    private XMLReader() { }

    /**
     * Reads and initializes a data set parsed from the selected
     * XML data set file.
     *
     * @param f takes a <code>File</code> containing the data set XML data.
     * @param sM takes a <code>PosModel3DSessionManager</code> which is the
     * class managing the 3DPosModel application.
     * @throws Exception if something in the read process goes wrong.
     */
    @SuppressWarnings("unchecked")
	public static void readXmlFile(final File f, SessionManager sM) throws Exception {
        DateFormat dateFormat = null;
        Date dateC = null;
        Date dateU = null;
        Dataset ds = null;
        SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(f);
        Element root = doc.getRootElement();

        if (!root.getName().equalsIgnoreCase("root")) {
            throw new Exception("Root element of the the XML document is not "
                    + "correct.\nGiven root in document is: " + root.getName()
                    + ".\nIt should have been \"root\"");
        }

        // Create and read data set common data.
        ds = new Dataset(root.getChild("ID").getTextTrim());

        // Set the dataset in the session manager.
        sM.setDataSet(ds);

        ds.setDatasetName(root.getChild("name").getTextTrim());
        ds.setCreatedBy(root.getChild("createdby").getTextTrim());

        // Format date
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            dateC = dateFormat.parse(
                    root.getChild("createddate").getTextTrim());
        } catch (ParseException e) {
            dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy",
                    Locale.US);
            dateC = dateFormat.parse(
                    root.getChild("createddate").getTextTrim());
        }

        ds.setCreatedDate(dateC);

        ds.setUpdatedBy(root.getChild("lastmodifiedby").getTextTrim());

        // Format date
        dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            dateU = dateFormat.parse(
                    root.getChild("lastmodifieddate").getTextTrim());
        } catch (ParseException e) {
            dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzzz yyyy",
                    Locale.US);
            dateU = dateFormat.parse(
                    root.getChild("lastmodifieddate").getTextTrim());
        }

        ds.setUpdatedDate(dateU);
        ds.setDatasetAbsFilePath(f.getAbsolutePath());

        // Read wells
        String wID = null;
		String wName = null;
		String wType = null;
		String wComm = null;
		Well tmpWell = null;
		List<Element> allWells = root.getChild("wells").getChildren();

		for (Element el : allWells) {
			wID = el.getChild("id").getTextTrim();
			wName = el.getChild("name").getTextTrim();
			wType = el.getChild("welltype").getTextTrim();
			wComm = el.getChild("comment").getTextTrim();

			// Fill object with data.
			tmpWell = new Well(wID);
			tmpWell.setWellName(wName);
			tmpWell.setWellType(wType);
			tmpWell.setWellComment(wComm);

			// Add object to list.
			sM.getDataSet().getDataSetWells().add(tmpWell);
		}

        // Read faults
        String fID = null;
		String fName = null;
		Fault tmpFault = null;
		List<Element> allFaults = root.getChild("faults").getChildren();
		
		for (Element el : allFaults) {
		
		    // Common for session and data set.
		    fID = el.getChild("id").getTextTrim();
		    fName = el.getChild("name").getTextTrim();
		
		    // Fill object with data.
		    tmpFault = new Fault(fID);
		    tmpFault.setFaultName(fName);
		
		    // Add object to vector.
		    sM.getDataSet().getDataSetFaults().add(tmpFault);
		}

        // Read fault files
        final double eps = 0.001;
		String ffID = null;
		String fID1 = null;
		String fileName = null;
		String ffComment = null;
		double defLatPU = Double.NaN;
		double defTimePU = Double.NaN;
		double tmpDefault = Double.NaN;
		boolean def = false;
		FaultDefinition tmpFaultDef = null;
		Fault tmpFault1 = null;
		
		List<Element> allFaultFiles = root.getChild("faultfiles").getChildren();
		
		for (Element el : allFaultFiles) {
		
		    // Common for session and data set.
		    ffID = el.getChild("id").getTextTrim();
		    fID1 = el.getChild("faultid").getTextTrim();
		    fileName = el.getChild("filename").getTextTrim();
		    defLatPU = Double.parseDouble(
		            el.getChild("defaultlateralpickuncertainty").getTextTrim());
		    defTimePU = Double.parseDouble(
		            el.getChild("defaulttimepickuncertainty").getTextTrim());
		    tmpDefault = Double.parseDouble(
		            el.getChild("default").getTextTrim());
		
		    // Convert sub seismic to boolean.
		    if (Misc.zeroIfLessThanEpsilon(tmpDefault, eps) != 0.0) {
		        def = true;
		    } else {
		        def = false;
		    }
		
		    // Fill object with data.
		    tmpFaultDef = new FaultDefinition(ffID);
		    tmpFaultDef.setFaultDataFilePath(fileName);
		    tmpFaultDef.setFaultDefaultLateralPickUncertainty(defLatPU);
		    tmpFaultDef.setFaultDefaultTimePickUncertainty(defTimePU);
		    tmpFaultDef.setFaultDefinitionComment(ffComment);
		    tmpFaultDef.setDefaultFaultDefinition(def);
		
		    // Connect fault and fault definition
		    for (Fault f1 : sM.getDataSet().getDataSetFaults()) {
		        if (f1.getFaultID().equalsIgnoreCase(fID1)) {
		            tmpFault1 = f1;
		            tmpFault1.setFaultDefinition(tmpFaultDef);
		            break;
		        }
		    }
		
		    if (tmpFault1 == null) {
		        throw new XmlParseException("Unable to locate a "
		                + "fault with fault ID " + fID1 + ".");
		  }
		}

        // Read fault well picks
        final double eps1 = 0.001;
		String wpID = null;
		String wpName = null;
		String wID1 = null;
		String fID2 = null;
		String interpreter = null;
		String obsNumber = null;
		String qualifier = null;
		String kind = null;
		String comm = null;
		double tmpGE = Double.NaN;
		boolean grossError = false;
		double n = Double.NaN;
		double e = Double.NaN;
		double tvdss = Double.NaN;
		double md = Double.NaN;
		double nn = Double.NaN;
		double ee = Double.NaN;
		double vv = Double.NaN;
		double ne = Double.NaN;
		double nv = Double.NaN;
		double ev = Double.NaN;
		Well tmpWell1 = null;
		Fault tmpFault2 = null;
		WellPick tmpWellPick = null;
		GeoPoint2D loc = null;
		
		List<Element> allWP = root.getChild("faultwellpicks").getChildren();
		
		for (Element el : allWP) {
		
		    // Common for session and data set.
		    wpID = el.getChild("id").getTextTrim();
		    wpName = el.getChild("name").getTextTrim();
		    wID1 = el.getChild("wellid").getTextTrim();
		    fID2 = el.getChild("surface").getTextTrim();
		
		    tmpGE = Double.parseDouble(el.getChild("grosserror").getTextTrim());
		
		    // Convert sub seismic to boolean.
		    if (Misc.zeroIfLessThanEpsilon(tmpGE, eps1) != 0.0) {
		        grossError = true;
		    } else {
		        grossError = false;
		    }
		
		    comm = el.getChild("comment").getTextTrim();
		    interpreter = el.getChild("interpreter").getTextTrim();
		    obsNumber = el.getChild("observationnumber").getTextTrim();
		    qualifier = el.getChild("qualifier").getTextTrim();
		    kind = el.getChild("kind").getTextTrim();
		
		    try {
		        n = Double.parseDouble(el.getChild("northing").getTextTrim());
		        e = Double.parseDouble(el.getChild("easting").getTextTrim());
		        tvdss = Double.parseDouble(el.getChild("tvdss").getTextTrim());
		        md = Double.parseDouble(el.getChild("md").getTextTrim());
		        nn = Double.parseDouble(
		                el.getChild("covariancenn").getTextTrim());
		        ee = Double.parseDouble(
		                el.getChild("covarianceee").getTextTrim());
		        vv = Double.parseDouble(
		                el.getChild("covariancevv").getTextTrim());
		        ne = Double.parseDouble(
		                el.getChild("covariancene").getTextTrim());
		        nv = Double.parseDouble(
		                el.getChild("covariancenv").getTextTrim());
		        ev = Double.parseDouble(
		                el.getChild("covarianceev").getTextTrim());
		    } catch (Exception ex) {
		        throw new XmlParseException("Unvalid fault well pick "
		                + "numeric input value detected in dataset.\n"
		                + "Fault well pick id is: " + wpID + ".\n"
		                + ex.getMessage());
		    }
		
		    // Connect well and well pick
		    for (Well w : sM.getDataSet().getDataSetWells()) {
		        if (w.getWellID().equalsIgnoreCase(wID1)) {
		            tmpWell1 = w;
		            break;
		        }
		    }
		
		    if (tmpWell1 == null) {
		        throw new XmlParseException("Cannot find well with well ID "
		                + wID1 + " referred to by fault well pick with ID "
		                + wpID + ".");
		    }
		
		    // Fill object with data.
		    tmpWellPick = new WellPick(wpID, tmpWell1);
		    tmpWellPick.setWellPickName(wpName);
		    loc = new GeoPoint2D();
		    loc.setNorthing(n);
		    loc.setEasting(e);
		    tmpWellPick.setLocation(loc);
		    tmpWellPick.setTVDSS(tvdss);
		    tmpWellPick.setMD(md);
		    tmpWellPick.setCovNN(nn);
		    tmpWellPick.setCovEE(ee);
		    tmpWellPick.setCovVV(vv);
		    tmpWellPick.setCovNE(ne);
		    tmpWellPick.setCovNV(nv);
		    tmpWellPick.setCovEV(ev);
		    tmpWellPick.setInterpreter(interpreter);
		    tmpWellPick.setObsNr(obsNumber);
		    tmpWellPick.setQualifier(qualifier);
		    tmpWellPick.setKind(kind);
		    tmpWellPick.setGrossError(grossError);
		    tmpWellPick.setComment(comm);
		
		    // Connect fault and fault well pick
		    for (Fault f1 : sM.getDataSet().getDataSetFaults()) {
		        if (f1.getFaultID().equalsIgnoreCase(fID2)) {
		            tmpFault2 = f1;
		            tmpFault2.getFaultWellPicks().add(tmpWellPick);
		            break;
		        }
		    }
		
		    if (tmpFault2 == null) {
		        throw new XmlParseException("Cannot find fault with fault"
		                + " ID " + fID2 + " referred to by fault well pick with "
		                + "ID " + wpID + ".");
		    }
		
		    // Add object to vector.
		    sM.getDataSet().getDataSetFaultWellPicks().add(tmpWellPick);
		}

        // Read horizons.
        final double eps2 = 0.001;
		String hID = null;
		String hName = null;
		String hFileName = null;
		String hPUFileName = null;
		double hTWTPickStddev = Double.NaN;
		double hLatCorrLength = Double.NaN;
		double hSubSeismic = Double.NaN;
		boolean hSS = false;
		Horizon tmpHorizon = null;
		List<Element> allHorizons = root.getChild("horizons").getChildren();
		Iterator<Element> horItr = allHorizons.iterator();
		
		while (horItr.hasNext()) {
		    Element el = horItr.next();
		
		    // Common for session and data set.
		    hID = el.getChild("id").getTextTrim();
		    hName = el.getChild("name").getTextTrim();
		    hSubSeismic = Double.parseDouble(
		            el.getChild("subseismic").getTextTrim());
		
		    // Convert sub seismic to boolean.
		    if (Misc.zeroIfLessThanEpsilon(hSubSeismic, eps2) != 0.0) {
		        hSS = true;
		    }
		
		    // Fill object with data.
		    tmpHorizon = new Horizon(hID);
		    tmpHorizon.setHorizonName(hName);
		    tmpHorizon.setHorizonSubSeismic(hSS);
		
		    // Add object to vector.
		    sM.getDataSet().getDataSetHorizons().add(tmpHorizon);
		}

        // Read horizon files
        final double eps3 = 0.001;
		String hfID = null;
		String hID1 = null;
		String hgID = null;
		String fileName1 = null;
		String sourceFileID = null;
		String comment = null;
		double interpolMethID = Double.NaN;
		double defPU = Double.NaN;
		double tmpDefault1 = Double.NaN;
		boolean def1 = false;
		HorizonDefinition tmpHorDef = null;
		Horizon tmpHor = null;

		List<Element> allHorFiles = root.getChild("horizonfiles").getChildren();
		Iterator<Element> hfItr = allHorFiles.iterator();

		while (hfItr.hasNext()) {
			Element el = hfItr.next();

			// Common for session and data set.
			hfID = el.getChild("id").getTextTrim();
			hID1 = el.getChild("horizonid").getTextTrim();
			hgID = el.getChild("horizongridid").getTextTrim();
			fileName1 = el.getChild("filename").getTextTrim();
			sourceFileID = el.getChild("sourcefileid").getTextTrim();
			interpolMethID = Double.parseDouble(el.getChild(
					"interpolationmethodid").getTextTrim());
			defPU = Double.parseDouble(el.getChild("defaultpickuncertainty")
					.getTextTrim());
			tmpDefault1 = Double.parseDouble(el.getChild("default")
					.getTextTrim());
			comment = el.getChild("comment").getTextTrim();

			// Convert sub seismic to boolean.
			if (Misc.zeroIfLessThanEpsilon(tmpDefault1, eps3) != 0.0) {
				def1 = true;
			} else {
				def1 = false;
			}

			// Fill object with data.
			tmpHorDef = new HorizonDefinition(hfID);
			tmpHorDef.setHorizonDataFileName(fileName1);
			// tmpHorDef.setHorizonSourceDataFileID(sourceFileID);
			tmpHorDef.setHorizonInterpolationMethodID(interpolMethID);
			tmpHorDef.setHorizonDefaultPickUncertainty(defPU);
			tmpHorDef.setDefaultHorDefinition(def1);
			tmpHorDef.setHorizonDefinitionComment(comment);

			// Connect horizon and horizon definition
			for (Horizon h : sM.getDataSet().getDataSetHorizons()) {
				if (h.getHorizonID().equalsIgnoreCase(hID1)) {
					tmpHor = h;
					tmpHor.setHorizonDefinition(tmpHorDef);
					break;
				}
			}

			if (tmpHor == null) {
				throw new XmlParseException("Unable to locate a "
						+ "horizon with horizon ID " + hID1 + ".");
			}

		}

        // Read horizon well picks
        final double eps4 = 0.001;
		String wpID1 = null;
		String wpName1 = null;
		String wID2 = null;
		String hID2 = null;
		String interpreter1 = null;
		String obsNumber1 = null;
		String qualifier1 = null;
		String kind1 = null;
		String comm1 = null;
		double tmpGE1 = Double.NaN;
		boolean grossError1 = false;
		double n1 = Double.NaN;
		double e1 = Double.NaN;
		double tvdss1 = Double.NaN;
		double md1 = Double.NaN;
		double nn1 = Double.NaN;
		double ee1 = Double.NaN;
		double vv1 = Double.NaN;
		double ne1 = Double.NaN;
		double nv1 = Double.NaN;
		double ev1 = Double.NaN;
		Well tmpWell2 = null;
		Horizon tmpHor1 = null;
		WellPick tmpWellPick1 = null;
		GeoPoint2D loc1 = null;
		
		List<Element> allWP1 = root.getChild("horizonwellpicks").getChildren();
		Iterator<Element> wpItr1 = allWP1.iterator();
		
		while (wpItr1.hasNext()) {
		    Element el = wpItr1.next();
		
		    // Common for session and data set.
		    wpID1 = el.getChild("id").getTextTrim();
		    wpName1 = el.getChild("name").getTextTrim();
		    wID2 = el.getChild("wellid").getTextTrim();
		    hID2 = el.getChild("surfaceid").getTextTrim();
		    tmpGE1 = Double.parseDouble(el.getChild("grosserror").getTextTrim());
		
		    // Convert sub seismic to boolean.
		    if (Misc.zeroIfLessThanEpsilon(tmpGE1, eps4) != 0.0) {
		        grossError1 = true;
		    } else {
		        grossError1 = false;
		    }
		
		    comm1 = el.getChild("comment").getTextTrim();
		    interpreter1 = el.getChild("interpreter").getTextTrim();
		    obsNumber1 = el.getChild("observationnumber").getTextTrim();
		    qualifier1 = el.getChild("qualifier").getTextTrim();
		    kind1 = el.getChild("kind").getTextTrim();
		
		    try {
		        n1 = Double.parseDouble(el.getChild("northing").getTextTrim());
		        e1 = Double.parseDouble(el.getChild("easting").getTextTrim());
		        tvdss1 = Double.parseDouble(el.getChild("tvdss").getTextTrim());
		        md1 = Double.parseDouble(el.getChild("md").getTextTrim());
		        nn1 = Double.parseDouble(
		                el.getChild("covariancenn").getTextTrim());
		        ee1 = Double.parseDouble(
		                el.getChild("covarianceee").getTextTrim());
		        vv1 = Double.parseDouble(
		                el.getChild("covariancevv").getTextTrim());
		        ne1 = Double.parseDouble(
		                el.getChild("covariancene").getTextTrim());
		        nv1 = Double.parseDouble(
		                el.getChild("covariancenv").getTextTrim());
		        ev1 = Double.parseDouble(
		                el.getChild("covarianceev").getTextTrim());
		    } catch (Exception ex) {
		        throw new XmlParseException("Unvalid horizon well pick "
		                + "numeric input value detected in dataset.\n"
		                + "Horizon well pick id is: " + wpID1 + ".\n"
		                + ex.getMessage());
		    }
		
		    // Connect well and well pick
		    for (Well w : sM.getDataSet().getDataSetWells()) {
		        if (w.getWellID().equalsIgnoreCase(wID2)) {
		            tmpWell2 = w;
		            break;
		        }
		    }
		
		    if (tmpWell2 == null) {
		        throw new XmlParseException("Cannot find well with well ID "
		                + wID2 + " referred to by fault well pick with ID "
		                + wpID1 + ".");
		    }
		
		    // Fill object with data.
		    tmpWellPick1 = new WellPick(wpID1, tmpWell2);
		    loc1 = new GeoPoint2D();
		    loc1.setNorthing(n1);
		    loc1.setEasting(e1);
		    tmpWellPick1.setLocation(loc1);
		    tmpWellPick1.setTVDSS(tvdss1);
		    tmpWellPick1.setMD(md1);
		    tmpWellPick1.setCovNN(nn1);
		    tmpWellPick1.setCovEE(ee1);
		    tmpWellPick1.setCovVV(vv1);
		    tmpWellPick1.setCovNE(ne1);
		    tmpWellPick1.setCovNV(nv1);
		    tmpWellPick1.setCovEV(ev1);
		    tmpWellPick1.setInterpreter(interpreter1);
		    tmpWellPick1.setObsNr(obsNumber1);
		    tmpWellPick1.setQualifier(qualifier1);
		    tmpWellPick1.setKind(kind1);
		    tmpWellPick1.setGrossError(grossError1);
		    tmpWellPick1.setComment(comm1);
		
		    // Connect horizon and horizon well pick
		    for (Horizon h : sM.getDataSet().getDataSetHorizons()) {
		        if (h.getHorizonID().equalsIgnoreCase(hID2)) {
		            tmpHor1 = h;
		            tmpHor1.getHorizonWellPicks().add(tmpWellPick1);
		            break;
		        }
		    }
		
		    if (tmpHor1 == null) {
		        throw new XmlParseException("Cannot find horizon with "
		                + "horizonID " + hID2
		                + " referred to by horizon well pick with "
		                + "ID " + wpID1 + ".");
		    }
		
		    // Add object to vector.
		    sM.getDataSet().getDataSetHorizonWellPicks().add(tmpWellPick1);
		}

    }

    /**
     * Prints data read from a session XML file.
     */
    private static void printSessionXML() {
        try {
            SAXBuilder builder = new SAXBuilder();
            Document doc = builder.build(new FileInputStream(
                    "G:\\D\\dart\\3DPosModel\\Test cases\\TestSession"
                    + "\\session.xml"));

            // Get top/root element
            Element root = doc.getRootElement();

            System.out.println();
            System.out.println("********************************************");
            System.out.println("************   Session Common  *************");
            System.out.println("********************************************");

            System.out.println("Root element of the doc is " + root.getName());

            System.out.println("Session ID: "
                    + root.getChild("id").getTextTrim());
            System.out.println("Session name: "
                    + root.getChild("name").getTextTrim());
            System.out.println("Session created by: "
                    + root.getChild("createdby").getTextTrim());
            System.out.println("Session created date: "
                    + root.getChild("createddate").getTextTrim());
            System.out.println("Session last modified by: "
                    + root.getChild("lastmodifiedby").getTextTrim());
            System.out.println("Session last modified date: "
                    + root.getChild("lastmodifieddate").getTextTrim());
            System.out.println("Site: "
                    + root.getChild("oilfield").getTextTrim());

            System.out.println();
            System.out.println("********************************************");
            System.out.println("***************   Dataset  *****************");
            System.out.println("********************************************");

            System.out.println("Dataset ID: "
                    + root.getChild("dataset").getChild("id").getTextTrim());
            System.out.println("Dataset name: "
                    + root.getChild("dataset").getChild("name").getTextTrim());
            System.out.println("Dataset file directory: "
                    + root.getChild("dataset").getChild(
                    "datafilesdir").getTextTrim());

            System.out.println();
            System.out.println("********************************************");
            System.out.println("**************   Work Area  ****************");
            System.out.println("********************************************");

            System.out.println("Work Area start north: " + root.getChild(
            "workarea").getChild("startnorth").getTextTrim());
            System.out.println("Work Area end north: " + root.getChild(
            "workarea").getChild("endnorth").getTextTrim());
            System.out.println("Work Area start east: " + root.getChild(
            "workarea").getChild("starteast").getTextTrim());
            System.out.println("Work Area end east: " + root.getChild(
            "workarea").getChild("endeast").getTextTrim());

            System.out.println();
            System.out.println("********************************************");
            System.out.println("**************   Plan Area  ****************");
            System.out.println("********************************************");

            System.out.println("Plan Area start north: " + root.getChild(
            "planarea").getChild("startnorth").getTextTrim());
            System.out.println("Plan Area end north: " + root.getChild(
            "planarea").getChild("endnorth").getTextTrim());
            System.out.println("Plan Area start east: " + root.getChild(
            "planarea").getChild("starteast").getTextTrim());
            System.out.println("Plan Area end east: " + root.getChild(
            "planarea").getChild("endeast").getTextTrim());

            System.out.println();
            System.out.println("********************************************");
            System.out.println("******  WellPick Correlation Model  ********");
            System.out.println("********************************************");

            System.out.println("WellPick Correlation Model Name: "
                    + root.getChild("wellpickcorrelationmodel").getChild(
                    "modelname").getTextTrim());
            System.out.println("WellPick Correlation length: " + root.getChild(
            "wellpickcorrelationmodel").getChild(
            "threedcorrelationlength").getTextTrim());

            System.out.println();
            System.out.println("********************************************");
            System.out.println("********  Depth convertion module **********");
            System.out.println("********************************************");

            System.out.println("Depth convertion Model: "
                    + root.getChild("depthconversionmodule").getChild(
                    "model").getTextTrim());
            System.out.println("Depth convertion Lateral Ray Path Variability: "
                    + root.getChild("depthconversionmodule").getChild(
                    "lateralraypathvariability").getTextTrim());
            System.out.println("Depth convertion Span Of Horizon Dip Calc: "
                    + root.getChild("depthconversionmodule").getChild(
                    "spanofhorizondipcalc").getTextTrim());
            System.out.println("Depth convertion Depth Conversion Method: "
                    + root.getChild("depthconversionmodule").getChild(
                    "depthconversionmethod").getTextTrim());

            System.out.println();
            System.out.println("********************************************");
            System.out.println("********  Adjustments **********************");
            System.out.println("********************************************");

            System.out.println("Direction Horizon: "
                    + root.getChild("adjustment").getChild(
                    "directionhorizon").getTextTrim());
            System.out.println("Point Selection Horizon: "
                    + root.getChild("adjustment").getChild(
                    "pointselectionhorizon").getTextTrim());
            System.out.println("Direction Fault: "
                    + root.getChild("adjustment").getChild(
                    "directionfault").getTextTrim());
            System.out.println("Point Selection Fault: "
                    + root.getChild("adjustment").getChild(
                    "pointselectionfault").getTextTrim());
            System.out.println("Geo Point Pick Radius: "
                    + root.getChild("adjustment").getChild(
                    "geopointpickradius").getTextTrim());

            System.out.println();
            System.out.println("********************************************");
            System.out.println("********  Horizon Grid *********************");
            System.out.println("********************************************");

            System.out.println("ID: "
                    + root.getChild("horizongrid").getChild(
                    "id").getTextTrim());
            System.out.println("Infinite Time: "
                    + root.getChild("horizongrid").getChild(
                    "infinitetime").getTextTrim());
            System.out.println("Grid Size Northing: "
                    + root.getChild("horizongrid").getChild(
                    "gridsizenorthing").getTextTrim());
            System.out.println("Grid Size Easting: "
                    + root.getChild("horizongrid").getChild(
                    "gridsizeeasting").getTextTrim());
            System.out.println("Resolution Northing: "
                    + root.getChild("horizongrid").getChild(
                    "resolutionnorthing").getTextTrim());
            System.out.println("Resolution Easting: "
                    + root.getChild("horizongrid").getChild(
                    "resolutioneasting").getTextTrim());
            System.out.println("Starting Point East: "
                    + root.getChild("horizongrid").getChild(
                    "startingpointeast").getTextTrim());
            System.out.println("Starting Point North: "
                    + root.getChild("horizongrid").getChild(
                    "startingpointnorth").getTextTrim());
            System.out.println("Ending Point East: "
                    + root.getChild("horizongrid").getChild(
                    "endingpointeast").getTextTrim());
            System.out.println("Ending Point North: "
                    + root.getChild("horizongrid").getChild(
                    "endingpointnorth").getTextTrim());
            System.out.println("Grid Azimuth: "
                    + root.getChild("horizongrid").getChild(
                    "gridazimuth").getTextTrim());

            System.out.println();
            System.out.println("********************************************");
            System.out.println("********  Horizons *********************");
            System.out.println("********************************************");

            List<Element> allHorizons = root.getChild("horizons").getChildren();
            Iterator<Element> horItr = allHorizons.iterator();

            while (horItr.hasNext()) {
                Element el = horItr.next();
                System.out.println("ID: " + el.getChild("id").getTextTrim());
                System.out.println("Name: "
                        + el.getChild("name").getTextTrim());
                System.out.println("File Name: "
                        + el.getChild("filename").getTextTrim());
                System.out.println("TWT Pick Std dev: "
                        + el.getChild("twtpickstddev").getTextTrim());
                System.out.println("Pick Uncertainty File Name: "
                        + el.getChild("pickuncertaintyfilename").getTextTrim());
                System.out.println("Lateral Correlation Length: "
                        + el.getChild(
                        "lateralcorrelationlength").getTextTrim());
                System.out.println("Sub Seismic: "
                        + el.getChild("subseismic").getTextTrim());

                System.out.println();
            }

            System.out.println();
            System.out.println("********************************************");
            System.out.println("***************  Wells *********************");
            System.out.println("********************************************");

            List<Element> allWells = root.getChild("wells").getChildren();
            Iterator<Element> wellItr = allWells.iterator();

            while (wellItr.hasNext()) {
                Element el = wellItr.next();

                System.out.println("ID: " + el.getChild("id").getTextTrim());
                System.out.println("Name: "
                        + el.getChild("name").getTextTrim());
                System.out.println("Well Type: "
                        + el.getChild("welltype").getTextTrim());
                System.out.println("Well Path File Name: "
                        + el.getChild("wellpathfilename").getTextTrim());
                System.out.println("Well Survey File Name: "
                        + el.getChild("wellsurveyfilename").getTextTrim());

                System.out.println();
            }

            System.out.println();
            System.out.println("********************************************");
            System.out.println("***************  Layers ********************");
            System.out.println("********************************************");

            List<Element> allLayers = root.getChild("layers").getChildren();
            Iterator<Element> layItr = allLayers.iterator();
            List<Element> velFiles = null;
            Iterator<Element> velItr = null;

            while (layItr.hasNext()) {
                Element el = layItr.next();

                System.out.println("ID: " + el.getChild("id").getTextTrim());
                System.out.println("Top Horizon ID: "
                        + el.getChild("tophorizonid").getTextTrim());
                System.out.println("Base Horizon ID: "
                        + el.getChild("basehorizonid").getTextTrim());
                System.out.println("P-Wave Interval Velocity: "
                        + el.getChild("pwaveintervalvelocity").getTextTrim());
                System.out.println("P-Wave Interval Velocity Std Dev: "
                        + el.getChild(
                        "pwaveintervalvelocitystddev").getTextTrim());

                velFiles = el.getChild("layervelocityfiles").getChildren();
                velItr =  velFiles.iterator();

                System.out.println("\n****** Layer Velociyty Files ******");

                while (velItr.hasNext()) {
                    Element el2 = velItr.next();

                    System.out.println("ID: " + el2.getChild(
                    "id").getTextTrim());
                    System.out.println("File Name: " + el2.getChild(
                    "filename").getTextTrim());
                    System.out.println("File Type: " + el2.getChild(
                    "filetype").getTextTrim());
                }

                System.out.println();
            }

            System.out.println();
            System.out.println("********************************************");
            System.out.println("********  Horizon Well Picks ***************");
            System.out.println("********************************************");

            List<Element> allHWP = root.getChild("horizonwellpicks").getChildren();
            Iterator<Element> hwpItr = allHWP.iterator();

            while (hwpItr.hasNext()) {
                Element el = hwpItr.next();

                System.out.println("ID: " + el.getChild("id").getTextTrim());
                System.out.println("Horizon ID: "
                        + el.getChild("horizonid").getTextTrim());
                System.out.println("Well ID: "
                        + el.getChild("wellid").getTextTrim());
                System.out.println("Measured Depth: "
                        + el.getChild("md").getTextTrim());
                System.out.println("TVD SubSurface: "
                        + el.getChild("tvdss").getTextTrim());
                System.out.println("Northing: "
                        + el.getChild("northing").getTextTrim());
                System.out.println("Easting: "
                        + el.getChild("easting").getTextTrim());
                System.out.println("Covariance NN: "
                        + el.getChild("covariancenn").getTextTrim());
                System.out.println("Covariance EE: "
                        + el.getChild("covarianceee").getTextTrim());
                System.out.println("Covariance VV: "
                        + el.getChild("covariancevv").getTextTrim());
                System.out.println("Covariance NE: "
                        + el.getChild("covariancene").getTextTrim());
                System.out.println("Covariance NV: "
                        + el.getChild("covariancenv").getTextTrim());
                System.out.println("Covariance EV: "
                        + el.getChild("covarianceev").getTextTrim());
                System.out.println("Gross Error Comment: "
                        + el.getChild("grosserrorcomment").getTextTrim());

                System.out.println();
            }

            System.out.println();
            System.out.println("********************************************");
            System.out.println("**************  Faults *********************");
            System.out.println("********************************************");

            List<Element> allFaults = root.getChild("faults").getChildren();
            Iterator<Element> faultItr = allFaults.iterator();

            while (faultItr.hasNext()) {
                Element el = faultItr.next();
                System.out.println("ID: " + el.getChild("id").getTextTrim());
                System.out.println("Name: "
                        + el.getChild("name").getTextTrim());
                System.out.println("File Name: "
                        + el.getChild("filename").getTextTrim());
                System.out.println("Lateral Pick Std dev: "
                        + el.getChild("lateralpickstddev").getTextTrim());
                System.out.println("Time Pick Std dev: "
                        + el.getChild("timepickstddev").getTextTrim());
                System.out.println("Pick Uncertainty File Name: "
                        + el.getChild(
                        "faultpickuncertaintyfilename").getTextTrim());
                System.out.println("Fault Correlation Length: "
                        + el.getChild(
                        "faultcorrelationlength").getTextTrim());

                System.out.println();
            }

            System.out.println();
            System.out.println("********************************************");
            System.out.println("**********  Fault Well Picks ***************");
            System.out.println("********************************************");

            List<Element> allFWP = root.getChild("faultwellpicks").getChildren();
            Iterator<Element> fwpItr = allFWP.iterator();

            while (fwpItr.hasNext()) {
                Element el = fwpItr.next();

                System.out.println("ID: " + el.getChild("id").getTextTrim());
                System.out.println("Fault ID: "
                        + el.getChild("faultid").getTextTrim());
                System.out.println("Well ID: "
                        + el.getChild("wellid").getTextTrim());
                System.out.println("Measured Depth: "
                        + el.getChild("md").getTextTrim());
                System.out.println("TVD SubSurface: "
                        + el.getChild("tvdss").getTextTrim());
                System.out.println("Northing: "
                        + el.getChild("northing").getTextTrim());
                System.out.println("Easting: "
                        + el.getChild("easting").getTextTrim());
                System.out.println("Covariance NN: "
                        + el.getChild("covariancenn").getTextTrim());
                System.out.println("Covariance EE: "
                        + el.getChild("covarianceee").getTextTrim());
                System.out.println("Covariance VV: "
                        + el.getChild("covariancevv").getTextTrim());
                System.out.println("Covariance NE: "
                        + el.getChild("covariancene").getTextTrim());
                System.out.println("Covariance NV: "
                        + el.getChild("covariancenv").getTextTrim());
                System.out.println("Covariance EV: "
                        + el.getChild("covarianceev").getTextTrim());
                System.out.println("Gross Error Comment: "
                        + el.getChild("grosserrorcomment").getTextTrim());

                System.out.println();
            }

            System.out.println();
            System.out.println("********************************************");
            System.out.println("********  Well Side Tracks *****************");
            System.out.println("********************************************");

            List<Element> allWellST = root.getChild("wellsidetracks").getChildren();
            Iterator<Element> wellSTItr = allWellST.iterator();

            while (wellSTItr.hasNext()) {
                Element el = wellSTItr.next();
                System.out.println("ID: " + el.getChild("id").getTextTrim());
                System.out.println("Mother Well ID: "
                        + el.getChild("motherwellid").getTextTrim());
                System.out.println("Child Well ID: "
                        + el.getChild("childwellid").getTextTrim());
                System.out.println("Kick off MD: "
                        + el.getChild("kickoffmd").getTextTrim());
                System.out.println();
            }

        } catch (IOException ioex) {
            ioex.printStackTrace();
        } catch (JDOMException jdex) {
            jdex.printStackTrace();
        }
    }
}
