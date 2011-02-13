/*
 * Filename:           $Workfile: Horizon.java $
 * Version:            $Revision: 11 $
 * Copyright:          Copyright (c) 2007, 3DPosModel Activity, StatoilHydro ASA
 * Creator:            Ståle Langeland (slange@statoil.com), Statoil ASA,
 *                     IT BAS RTS, +47 913 81 616
 * Last modified by:   $Author: Slange $
 * Last modified:      $Modtime: 10.06.08 14:40 $
 */
package com.statoil.xmlparser;

import java.util.ArrayList;

/**
 * Class representing a horizon in 3DPosModel context.
 *
 * @author    Ståle Langeland
 * @version   $Revision: 11 $ $Modtime: 10.06.08 14:40 $
 */
public class Horizon implements Comparable <Horizon> {

    /** 3DPosModel specific horizon ID. */
    private String mHorizonID = null;

    /** 3DPosModel horizon name. */
    private String mHorizonName = null;

    /** 3DPosModel horizon sub seismic flag. */
    private boolean mHorizonSubSeismic = false;

    /** How the horizon is defined. */
    private HorizonDefinition mHorizonDefinition = null;

    /** Well picks associated with the horizon. */
    private ArrayList<WellPick> mHorizonWellPicks = null;

    /** The horizon data file name. */
    private String mSessHorFilename = null;

    /** The TWT pick standard deviation value for the horizon. */
    private double mSessTWTPickStdDev = Double.NaN;

    /** Holds status if TWT pick standard deviation tuning is enabled or not. */
    private boolean mSessTWTPickStdDevTuningEnabled = false;

    /** The TWT pick standard deviation minimum value. */
    private double mSessTWTPickStdDevMin = Double.NaN;

    /** The TWT pick standard deviation maximum value. */
    private double mSessTWTPickStdDevMax = Double.NaN;

    /** The horizon pick uncertainty data file name. */
    private String mSessPUFilename = null;

    /** The lateral correlation length. */
    private double mSessLatCorrLength = Double.NaN;

    /** Holds status if lateral correlation length tuning is enabled or not. */
    private boolean mSessLatCorrLengthTuningEnabled = false;

    /** The lateral correlation length minimum value. */
    private double mSessLatCorrLengthMin = Double.NaN;

    /** The lateral correlation length maximum value. */
    private double mSessLatCorrLengthMax = Double.NaN;

    /** Session specific comment on horizon. */
    private String mSessComment = null;

    /**
     * Constructor initializing the collection of well-picks and horizon ID.
     *
     * @param horID takes a <code>String</code> representing a 3DPosModel
     * specific horizon ID.
     */
    public Horizon(final String horID) {
        mHorizonID = horID;
        mHorizonWellPicks = new ArrayList<WellPick>();
    }

    /**
     * Compares this object with the specified object for order.
     * Use the horizon ID for comparing.
     *
     * @param anotherH takes a <code>Horizon</code> object to be compared.
     * @return a negative <code>integer</code>, zero, or a positive
     * <code>integer</code> as this object is less than, equal to, or greater
     * than the specified object.
     */
    public final int compareTo(final Horizon anotherH) {
            return this.getHorizonID().compareTo(anotherH.getHorizonID());
    }

    /**
     * Returns the TWT pick standard deviation value for the horizon.
     *
     * @return a <code>double</code> representing the TWT pick standard
     * deviation value for the horizon.
     */
    public final double getSessTWTPickStdDev() {
        return mSessTWTPickStdDev;
    }




    /**
     * Sets the TWT pick standard deviation value for the horizon.
     *
     * @param sessTWTPickStdDev takes a <code>double</code> representing the
     * TWT pick standard deviation value for the horizon.
     */
    public final void setSessTWTPickStdDev(final double sessTWTPickStdDev) {
        mSessTWTPickStdDev = sessTWTPickStdDev;
    }




    /**
     * Returns status if TWT pick standard deviation tuning is enabled or not.
     *
     * @return a <code>boolean</code> true if TWT pick standard deviation is
     * enabled, false if it is not enabled.
     */
    public final boolean isSessTWTPickStdDevTuningEnabled() {
        return mSessTWTPickStdDevTuningEnabled;
    }




    /**
     * Sets the status if TWT pick standard deviation tuning is enabled or not.
     *
     * @param sessTWTPickStdDevTuningEnabled takes a <code>boolean</code> true
     * if TWT pick standard deviation is enabled, false if it is not enabled.
     */
    public final void setSessTWTPickStdDevTuningEnabled(
            final boolean sessTWTPickStdDevTuningEnabled) {
        mSessTWTPickStdDevTuningEnabled = sessTWTPickStdDevTuningEnabled;
    }




    /**
     * Returns TWT pick standard deviation minimum value.
     *
     * @return a <code>double</code> representing TWT pick standard deviation
     * minimum value.
     */
    public final double getSessTWTPickStdDevMin() {
        return mSessTWTPickStdDevMin;
    }




    /**
     * Sets TWT pick standard deviation minimum value.
     *
     * @param sessTWTPickStdDevMin takes a <code>double</code> representing
     * TWT pick standard deviation minimum value.
     */
    public final void setSessTWTPickStdDevMin(
            final double sessTWTPickStdDevMin) {
        mSessTWTPickStdDevMin = sessTWTPickStdDevMin;
    }




    /**
     * Returns TWT pick standard deviation maximum value.
     *
     * @return a <code>double</code> representing TWT pick standard deviation
     * maximum value.
     */
    public final double getSessTWTPickStdDevMax() {
        return mSessTWTPickStdDevMax;
    }




    /**
     * Sets TWT pick standard deviation maximum value.
     *
     * @param sessTWTPickStdDevMax takes a <code>double</code> representing
     * TWT pick standard deviation maximum value.
     */
    public final void setSessTWTPickStdDevMax(
            final double sessTWTPickStdDevMax) {
        mSessTWTPickStdDevMax = sessTWTPickStdDevMax;
    }




    /**
     * Returns the lateral correlation length.
     *
     * @return a <code>double</code> representing the lateral correlation
     * length.
     */
    public final double getSessLatCorrLength() {
        return mSessLatCorrLength;
    }




    /**
     * Sets the lateral correlation length.
     *
     * @param sessLatCorrLength takes a <code>double</code> representing the
     * lateral correlation length.
     */
    public final void setSessLatCorrLength(final double sessLatCorrLength) {
        mSessLatCorrLength = sessLatCorrLength;
    }




    /**
     * Returns status if lateral correlation length tuning is enabled or not.
     *
     * @return a <code>boolean</code> true if lateral correlation length is
     * enabled, false if it is not enabled.
     */
    public final boolean isSessLatCorrLengthTuningEnabled() {
        return mSessLatCorrLengthTuningEnabled;
    }




    /**
     * Sets status if lateral correlation length tuning is enabled or not.
     *
     * @param sessLatCorrLengthTuningEnabled takes a <code>boolean</code> true
     * if lateral correlation length is enabled, false if it is not enabled.
     */
    public final void setSessLatCorrLengthTuningEnabled(
            final boolean sessLatCorrLengthTuningEnabled) {
        mSessLatCorrLengthTuningEnabled = sessLatCorrLengthTuningEnabled;
    }




    /**
     * Returns lateral correlation length minimum value.
     *
     * @return a <code>double</code> representing lateral correlation
     * length minimum value.
     */
    public final double getSessLatCorrLengthMin() {
        return mSessLatCorrLengthMin;
    }




    /**
     * Sets lateral correlation length minimum value.
     *
     * @param sessLatCorrLengthMin takes a <code>double</code> representing
     * lateral correlation length minimum value.
     */
    public final void setSessLatCorrLengthMin(
            final double sessLatCorrLengthMin) {
        mSessLatCorrLengthMin = sessLatCorrLengthMin;
    }




    /**
     * Returns lateral correlation length maximum value.
     *
     * @return a <code>double</code> representing lateral correlation length
     * maximum value.
     */
    public final double getSessLatCorrLengthMax() {
        return mSessLatCorrLengthMax;
    }




    /**
     * Sets lateral correlation length maximum value.
     *
     * @param sessLatCorrLengthMax takes a <code>double</code> representing
     * lateral correlation length maximum value.
     */
    public final void setSessLatCorrLengthMax(
            final double sessLatCorrLengthMax) {
        mSessLatCorrLengthMax = sessLatCorrLengthMax;
    }




    /**
     * Returns the horizon data file name.
     *
     * @return a <code>String</code> representing horizon data file name.
     */
    public final String getSessHorFilename() {
        return mSessHorFilename;
    }

    /**
     * Sets the name of the horizon data file name.
     *
     * @param hFN takes a <code>String</code> representing the name of
     * the horizon data file name.
     */
    public final void setSessHorFilename(final String hFN) {
        mSessHorFilename = hFN;
    }

    /**
     * Returns the horizon pick uncertainty data file name
     *
     * @return a <code>String</code> representing horizon file name.
     */
    public final String getSessPUFilename() {
        return mSessPUFilename;
    }

    /**
     * Sets the name of the horizon pick uncertainty data file name.
     *
     * @param puFN takes a <code>String</code> representing the name of
     * the horizon.
     */
    public final void setSessPUFilename(final String puFN) {
        mSessPUFilename = puFN;
    }

    /**
     * Returns the session specific comment on the horizon.
     *
     * @return a <code>String</code> representing a session specific comment
     * on the horizon.
     */
    public final String getSessComment() {
        return mSessComment;
    }

    /**
     * Sets the session specific comment on the horizon.
     *
     * @param comm takes a <code>String</code> representing the session specific
     * comment on the horizon.
     */
    public final void setSessComment(final String comm) {
        mSessComment = comm;
    }

    /**
     * Returns the unique internal 3DPosModel horizon ID.
     *
     * @return a <code>String</code> representing the unique internal
     * 3DPosModel horizon ID.
     */
    public final String getHorizonID() {
        return mHorizonID;
    }

    /**
     * Returns the name of horizon.
     *
     * @return a <code>String</code> representing the name of horizon.
     */
    public final String getHorizonName() {
        return mHorizonName;
    }

    /**
     * Sets the name of horizon.
     *
     * @param horName takes a <code>String</code> representing the name of
     * the horizon.
     */
    public final void setHorizonName(final String horName) {
        mHorizonName = horName;
    }

    /**
     * Returns the horizon definition.
     *
     * @return a <code>HorizonDefinition</code> containing the definition
     * of horizon.
     */
    public final HorizonDefinition getHorizonDefinition() {
        return mHorizonDefinition;
    }

    /**
     * Sets the definition of horizon.
     *
     * @param horDef takes a <code>HorizonDefinition</code> representing the
     * definition of the horizon.
     */
    public final void setHorizonDefinition(final HorizonDefinition horDef) {
        mHorizonDefinition = horDef;
    }

    /**
     * Returns a flag telling if the horizon is a sub seismic horizon or not.
     *
     * @return a <code>boolean</code> false if the horizon is not a sub seismic
     * horizon. True if the horizon is a sub seismic horizon.
     */
    public final boolean isHorizonSubSeismic() {
        return mHorizonSubSeismic;
    }

    /**
     * Sets the flag telling if the horizon is a sub seismic horizon or not.
     *
     * @param horSubS takes a <code>boolean</code> flag telling if the
     * horizon is a sub seismic horizon or not.
     */
    public final void setHorizonSubSeismic(final boolean horSubS) {
        mHorizonSubSeismic = horSubS;
    }

    /**
     * Returns the list of well-picks associated with horizon.
     *
     * @return an <code>ArrayList</code> of <code>WellPick</code> containing
     * the Well Picks associated with the horizon.
     */
    public final ArrayList<WellPick> getHorizonWellPicks() {
        return mHorizonWellPicks;
    }

    /**
     * Adds a well-picks to the list of well-picks associated with the horizon.
     *
     * @param wp takes the <code>WellPick</code> to add to the list
     * of well-picks.
     */
    public final void addHorizonWellPick(final WellPick wp) {
        mHorizonWellPicks.add(wp);
    }

    /**
     * Returns the horizon ID.
     *
     * @return a <code>String</code> representing the horizon ID..
     */
    public final String toString() {
        return mHorizonID;
    }

    /**
     * Used for testing.
     *
     * @param args takes an array of <code>String</code>. Not used for this
     * simple test.
     */
    public static void main(final String[] args) {
    }
}
