package com.statoil.xmlparser;

import java.util.ArrayList;

public class HorizonDefinition {
    private String mID = null;
    private String mHorizonDataFileName = null;
    private double mHorizonInterpolationMethodID = Double.NaN;
    private double mHorizonDefPickUncertainty = Double.NaN;
    private boolean mDefaultHorDefinition = false;
    private String mHorizonDefinitionComment = null;
    private ArrayList<FileHeader> mHorPUFiles = null;
    private FileHeader mHorSourceFile = null;
    private HorizonGrid mHorizonGrid = null;

     public HorizonDefinition(final String horDefID) {
        mID = horDefID;
        mHorPUFiles = new ArrayList<FileHeader>();
    }

     public final ArrayList<FileHeader> getHorPUFiles() {
         return mHorPUFiles;
     }

     public final void addHorPUFile(final FileHeader puf) {
         mHorPUFiles.add(puf);
     }

     public final String getHorizonDefinitionComment() {
         return mHorizonDefinitionComment;
     }

     public final void setHorizonDefinitionComment(final String hDefComm) {
         mHorizonDefinitionComment = hDefComm;
     }


     public final void setDefaultHorDefinition(final boolean def) {
         mDefaultHorDefinition = def;
     }

     public final boolean isDefaultHorDefinition() {
         return mDefaultHorDefinition;
     }

     public final String getHorizonDataFileName() {
         return mHorizonDataFileName;
     }

     public final void setHorizonDataFileName(final String hDataFileName) {
         mHorizonDataFileName = hDataFileName;
     }

     public final FileHeader getHorizonSourceDataFile() {
         return mHorSourceFile;
     }

     public final void setHorizonSourceDataFileHeader(final FileHeader hSH) {
         mHorSourceFile = hSH;
     }

    public final HorizonGrid getHorizonGrid() {
        return mHorizonGrid;
    }

    public final void setHorizonGrid(final HorizonGrid horGrid) {
        mHorizonGrid = horGrid;
    }

    public final double getHorizonDefaultPickUncertainty() {
        return mHorizonDefPickUncertainty;
    }

    public final void setHorizonDefaultPickUncertainty(
            final double horDefPU) {
        mHorizonDefPickUncertainty = horDefPU;
    }

    public final double getHorizonInterpolationMethodID() {
        return mHorizonInterpolationMethodID;
    }

    public final void setHorizonInterpolationMethodID(
            final double horIMID) {
        mHorizonInterpolationMethodID = horIMID;
    }

    public final String getID() {
        return mID;
    }

    public final String toString() {
        String nl = System.getProperty("line.separator");
        StringBuilder result = new StringBuilder();

        result.append(this.getClass().getName() + " Object {" + nl);
        result.append(" Horizon definition ID: ");

        if (this.getID() != null) {
            result.append(this.getID() + nl);
        } else {
            result.append("No horizon definition ID defined." + nl);
        }

        result.append(" Horizon definition grid data file path: ");

        result.append(" Horizon definition grid: ");

        if (this.getHorizonGrid() != null) {
            result.append(nl);
            result.append(this.getHorizonGrid().toString() + nl);
        } else {
            result.append("No horizon definition grid exist." + nl);
        }

        result.append("}");

        return result.toString();
    }


    /**
     * Used for testing.
     *
     * @param args takes an array of <code>String</code>. Not used for this
     * simple test.
       */
    public static void main(final String[] args) {
        // Horizon grid
        final String gID = "HG001";
        final int gsN = 21;
        final int gsE = 21;
        final double grN = 100;
        final double grE = 100;
        final double gA = 0;
        final long gIT = 9999900L;
        final String gO = "IRAP";
        final GeoPoint2D gS = new GeoPoint2D(7200000, 400000);
        final GeoPoint2D gE = new GeoPoint2D(7202000, 402000);

        HorizonGrid horGrid = new HorizonGrid(gID, gsN, gsE, grN, grE,
                gS, gE, gA, gIT, gO);

        // Horizon definition
        HorizonDefinition horDef = new HorizonDefinition("HF001");
        horDef.setHorizonGrid(horGrid);

        System.out.println(horDef);
    }
}
