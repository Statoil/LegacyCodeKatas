package com.statoil.xmlparser;

import java.util.ArrayList;

public class FaultDefinition {
    private String mFaultDefinitionID = null;
    private String mFaultDataFilePath = null;
    private double mFaultDefaultLateralPickUncertainty = Double.NaN;
    private double mFaultDefaultTimeLateralPickUncertainty = Double.NaN;
    private boolean mDefaultFaultDefinition = false;
    private String mFaultDefinitionComment = null;
    private ArrayList<FileHeader> mFaultLatPUFiles = null;
    private ArrayList<FileHeader> mFaultTimePUFiles = null;

    public FaultDefinition(final String faultDefID) {
        mFaultDefinitionID = faultDefID;
        mFaultLatPUFiles = new ArrayList<FileHeader>();
        mFaultTimePUFiles = new ArrayList<FileHeader>();
    }

    public final String getFaultDefinitionID() {
        return mFaultDefinitionID;
    }

    public final String getFaultDefinitionComment() {
        return mFaultDefinitionComment;
    }

    public final void setFaultDefinitionComment(final String fDefComm) {
        mFaultDefinitionComment = fDefComm;
    }

    public final double getFaultDefaultLateralPickUncertainty() {
        return mFaultDefaultLateralPickUncertainty;
    }

    public final void setFaultDefaultLateralPickUncertainty(final double lPU) {
        mFaultDefaultLateralPickUncertainty = lPU;
    }

    public final double getFaultDefaultTimePickUncertainty() {
        return mFaultDefaultTimeLateralPickUncertainty;
    }

    public final void setFaultDefaultTimePickUncertainty(final double lPU) {
        mFaultDefaultTimeLateralPickUncertainty = lPU;
    }

    public final void setDefaultFaultDefinition(final boolean def) {
        mDefaultFaultDefinition = def;
    }

    public final boolean isDefaultFaultDefinition() {
        return mDefaultFaultDefinition;
    }

    public final String getFaultDataFilePath() {
        return mFaultDataFilePath;
    }

    public final void setFaultDataFilePath(final String fDataFilePath) {
        mFaultDataFilePath = fDataFilePath;
    }

    public final ArrayList<FileHeader> getFaultLatPUFiles() {
        return mFaultLatPUFiles;
    }

    public final void addFaultLatPUFile(final FileHeader lpuf) {
        mFaultLatPUFiles.add(lpuf);
    }

    public final ArrayList<FileHeader> getFaultTimePUFiles() {
        return mFaultTimePUFiles;
    }

    public final void addFaultTimePUFile(final FileHeader tpuf) {
        mFaultTimePUFiles.add(tpuf);
    }
}
