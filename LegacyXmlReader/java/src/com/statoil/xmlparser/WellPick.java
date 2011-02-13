
package com.statoil.xmlparser;

public class WellPick implements Comparable <WellPick> {
    private String mWellPickID = null;
    private String mWellPickName = null;
    private Well mWell = null;
    private double mMD = Double.NaN;
    private double mTVDSS = Double.NaN;
    private GeoPoint2D mLocation = null;
    private double mCovNN = Double.NaN;
    private double mCovEE = Double.NaN;
    private double mCovVV = Double.NaN;
    private double mCovNE = Double.NaN;
    private double mCovNV = Double.NaN;
    private double mCovEV = Double.NaN;
    private double mPickUncertainty = Double.NaN;
    private String mComment = null;
    private String mInterpreter = null;
    private String mObsNr = null;
    private String mQual = null;
    private String mKind = null;
    private boolean mGrossError = false;

    public WellPick(final String wpID, final Well w) {
        mWellPickID = wpID;
        mWell = w;
    }

    public final int compareTo(final WellPick anotherWP) {
            return this.getWellPickID().compareTo(anotherWP.getWellPickID());
    }

    public final String getWellPickID() {
        return mWellPickID;
    }

    public final String getWellPickName() {
        return mWellPickName;
    }
    
    public final void setWellPickName(String wellPickName) {
    	mWellPickName = wellPickName;
    }

    public final Well getWell() {
        return mWell;
    }

    public final String getComment() {
        return mComment;
    }

    public final void setComment(final String comm) {
        mComment = comm;
    }

    public final String getInterpreter() {
        return mInterpreter;
    }

    public final void setInterpreter(final String intr) {
        mInterpreter = intr;
    }

    public final String getObsNr() {
        return mObsNr;
    }

    public final void setObsNr(final String onr) {
        mObsNr = onr;
    }

    public final String getQualifier() {
        return mQual;
    }

    public final void setQualifier(final String q) {
        mQual = q;
    }

    public final String getKind() {
        return mKind;
    }

    public final void setKind(final String k) {
        mKind = k;
    }

    public final void setGrossError(final boolean ge) {
        mGrossError = ge;
    }

    public final boolean isGrossError() {
        return mGrossError;
    }

    public final double getMD() {
        return mMD;
    }

    public final void setMD(final double md) {
        mMD = md;
    }

    public final double getTVDSS() {
        return mTVDSS;
    }

    public final void setTVDSS(final double tvdss) {
        mTVDSS = tvdss;
    }

    public final GeoPoint2D getLocation() {
        return mLocation;
    }

    public final void setLocation(final GeoPoint2D location) {
        mLocation = location;
    }

    public final double getCovNN() {
        return mCovNN;
    }

    public final void setCovNN(final double covNN) {
        mCovNN = covNN;
    }

    public final double getCovEE() {
        return mCovEE;
    }

    public final void setCovEE(final double covEE) {
        mCovEE = covEE;
    }

    public final double getCovVV() {
        return mCovVV;
    }

    public final void setCovVV(final double covVV) {
        mCovVV = covVV;
    }

    public final double getCovNE() {
        return mCovNE;
    }

    public final void setCovNE(final double covNE) {
        mCovNE = covNE;
    }

    public final double getCovNV() {
        return mCovNV;
    }

    public final void setCovNV(final double covNV) {
        mCovNV = covNV;
    }

    public final double getCovEV() {
        return mCovEV;
    }

    public final void setCovEV(final double covEV) {
        mCovEV = covEV;
    }

    public final double getPickUncertainty() {
        return mPickUncertainty;
    }

    public final void setPickUncertainty(final double pickUncertainty) {
        mPickUncertainty = pickUncertainty;
    }

    public final String toString() {
        return mWellPickID;
    }
}
