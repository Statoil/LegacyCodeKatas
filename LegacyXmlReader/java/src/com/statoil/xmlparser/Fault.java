package com.statoil.xmlparser;

import java.util.ArrayList;

public class Fault implements Comparable <Fault> {
    private String mFaultID = null;
    private String mFaultName = null;
    private String mSessFaultFileName = null;
    private double mSessFaultCorrLength = Double.NaN;
    private double mSessLatPickStdDev = Double.NaN;
    private double mSessTimePickStdDev = Double.NaN;
    private String mSessFaultPUFileName = null;
    private String mSessComment = null;
    private FaultDefinition mFaultDefinition = null;
    private ArrayList<WellPick> mFaultWellPicks = null;

    public Fault(final String fID) {
        mFaultID = fID;
        mFaultWellPicks = new ArrayList<WellPick>();
    }

    public final int compareTo(final Fault anotherF) {
            return this.getFaultID().compareTo(anotherF.getFaultID());
    }

    public final String getFaultID() {
        return mFaultID;
    }

    public final String getFaultName() {
        return mFaultName;
    }

    public final void setFaultName(final String fName) {
        mFaultName = fName;
    }

    public final FaultDefinition getFaultDefinition() {
        return mFaultDefinition;
    }

    public final void setFaultDefinition(final FaultDefinition fDef) {
        mFaultDefinition = fDef;
    }

    public final ArrayList<WellPick> getFaultWellPicks() {
        return mFaultWellPicks;
    }

    public final void addFaultWellPick(final WellPick wp) {
        mFaultWellPicks.add(wp);
    }

    public final String getSessFaultFileName() {
        return mSessFaultFileName;
    }

    public final void setSessFaultFileName(final String sessFaultFileName) {
        mSessFaultFileName = sessFaultFileName;
    }

    public final double getSessFaultCorrLength() {
        return mSessFaultCorrLength;
    }

    public final void setSessFaultCorrLength(final double sessFaultCorrLength) {
        mSessFaultCorrLength = sessFaultCorrLength;
    }

    public final double getSessLatPickStdDev() {
        return mSessLatPickStdDev;
    }

    public final void setSessLatPickStdDev(final double sessLatPickStdDev) {
        mSessLatPickStdDev = sessLatPickStdDev;
    }

    public final double getSessTimePickStdDev() {
        return mSessTimePickStdDev;
    }

    public final void setSessTimePickStdDev(final double sessTimePickStdDev) {
        mSessTimePickStdDev = sessTimePickStdDev;
    }

    public final String getSessFaultPUFileName() {
        return mSessFaultPUFileName;
    }

    public final void setSessFaultPUFileName(final String sessFaultPUFileName) {
        mSessFaultPUFileName = sessFaultPUFileName;
    }

    public final String getSessComment() {
        return mSessComment;
    }

    public final void setSessComment(final String sessComment) {
        mSessComment = sessComment;
    }

    public final String toString() {
        return mFaultID;
    }
}
