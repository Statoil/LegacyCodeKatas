package com.statoil.xmlparser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Dataset {
    private String mDatasetID = null;
    private String mDatasetName = null;
    private String mAbsFilePath = null;
    private String mCreatedBy = null;
    private String mUpdatedBy = null;
    private Date mCreatedDate = null;
    private Date mUpdatedDate = null;
    private List<Horizon> mHorizons = null;
    private List<Fault> mFaults = null;
    private List<Well> mWells = null;
    private List<WellPick> mHorWellPicks = null;
    private List<WellPick> mFaultWellPicks = null;
    private List<HorizonGrid> mGrids = null;
    private List<IntervalVelocity> mIntervalVelocities = null;

    public Dataset(final String dsID) {
        mDatasetID = dsID;
        mHorizons = new ArrayList<Horizon>();
        mFaults = new ArrayList<Fault>();
        mWells = new ArrayList<Well>();
        mHorWellPicks = new ArrayList<WellPick>();
        mFaultWellPicks = new ArrayList<WellPick>();
        mGrids = new ArrayList<HorizonGrid>();
        mIntervalVelocities = new ArrayList<IntervalVelocity>();
    }

    public final List<Horizon> getDataSetHorizons() {
        return mHorizons;
    }

    public final List<Fault> getDataSetFaults() {
        return mFaults;
    }

    public final List<Well> getDataSetWells() {
        return mWells;
    }

    public final List<WellPick> getDataSetHorizonWellPicks() {
        return mHorWellPicks;
    }

    public final List<WellPick> getDataSetFaultWellPicks() {
        return mFaultWellPicks;
    }

    public final List<HorizonGrid> getDataSetHorizonGrids() {
        return mGrids;
    }

    public final List<IntervalVelocity> getDataSetIntervalVelocities() {
        return mIntervalVelocities;
    }

    public final String getDatasetID() {
        return mDatasetID;
    }

    public final String getDatasetName() {
        return mDatasetName;
    }

    public final void setDatasetName(final String dsName) {
        mDatasetName = dsName;
    }

    public final String getDatasetAbsFilePath() {
        return mAbsFilePath;
    }

    public final void setDatasetAbsFilePath(final String dsFilePath) {
        mAbsFilePath = dsFilePath;
    }

    public final String getCreatedBy() {
        return mCreatedBy;
    }

    public final void setCreatedBy(final String createdBy) {
        mCreatedBy = createdBy;
    }

    public final String getUpdatedBy() {
        return mUpdatedBy;
    }

    public final void setUpdatedBy(final String updatedBy) {
        mUpdatedBy = updatedBy;
    }

    public final Date getCreatedDate() {
        return mCreatedDate;
    }

    public final void setCreatedDate(final Date createdDate) {
        mCreatedDate = createdDate;
    }

    public final Date getUpdatedDate() {
        return mUpdatedDate;
    }

    public final void setUpdatedDate(final Date updatedDate) {
        mUpdatedDate = updatedDate;
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
