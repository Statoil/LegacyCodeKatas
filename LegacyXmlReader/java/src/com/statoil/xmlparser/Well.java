package com.statoil.xmlparser;

public class Well implements Comparable <Well> {
    private String mWellID = null;
    private String mWellName = null;
    private String mWellType = null;
    private String mWellComm = null;
    private String mSessWellPathFileName = null;

    public Well(final String wID) {
        mWellID = wID;
    }

    public final int compareTo(final Well anotherW) {
            return this.getWellID().compareTo(anotherW.getWellID());
    }

    public final String getSessWellPathFileName() {
        return mSessWellPathFileName;
    }

    public final void setSessWellPathFileName(
            final String sessWellPathFileName) {
        mSessWellPathFileName = sessWellPathFileName;
    }

    public final String getWellID() {
        return mWellID;
    }

    public final String getWellName() {
        return mWellName;
    }

    public final void setWellName(final String wName) {
        mWellName = wName;
    }

    public final String getWellType() {
        return mWellType;
    }

    public final void setWellType(final String wType) {
        mWellType = wType;
    }

    public final String getWellComment() {
        return mWellComm;
    }

    public final void setWellComment(final String wC) {
        mWellComm = wC;
    }


    public final String toString() {
        return mWellID;
    }

    /**
     * Used for testing.
     *
     * @param args takes an array of <code>String</code>. Not used for this
     * simple test.
     */
    public static void main(final String[] args) {
        // Well
        Well w = new Well("W0001");
        //Well w = new Well(null);
        w.setWellName("Well1");
        //w.setWellName(null);

        System.out.println(w);
    }

}
