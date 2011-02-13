package com.statoil.xmlparser;

public class IntervalVelocity {
    private String mIntervalVelocityID = null;
    private Horizon mIntervalTopHorizon = null;
    private Horizon mIntervalBottomHorizon = null;
    private double mIntervalPWaveVelocity = 0.0;
    private double mIntervalPWaveVelocityPickUncert = 0.0;

    public IntervalVelocity(final String intVelID) {
    	mIntervalVelocityID = intVelID;
    }

    public final String getIntervalVelocityID() {
        return mIntervalVelocityID;
    }

    public final Horizon getIntervalTopHorizon() {
    	return mIntervalTopHorizon;
    }

    public final void setIntervalTopHorizon(
        final Horizon intervalTopHorizon) {
    mIntervalTopHorizon = intervalTopHorizon;
    }

    public final Horizon getIntervalBottomHorizon() {
    	return mIntervalBottomHorizon;
    }

    public final void setIntervalBottomHorizon(final Horizon intervalBottomHorizon) {
    	mIntervalBottomHorizon = intervalBottomHorizon;
    }

    public final double getIntervalPWaveVelocity() {
        return mIntervalPWaveVelocity;
    }

    public final void setIntervalPWaveVelocity(final double intervalPWaveVelocity) {
        mIntervalPWaveVelocity = intervalPWaveVelocity;
    }

    public final double getIntervalPWaveVelocityPickUncert() {
        return mIntervalPWaveVelocityPickUncert;
    }

    public final void setIntervalPWaveVelocityPickUncert(
        final double intervalPWaveVelocityPickUncert) {
        mIntervalPWaveVelocityPickUncert = intervalPWaveVelocityPickUncert;
    }

    public final String toString() {
        String nl = System.getProperty("line.separator");
        StringBuilder result = new StringBuilder();

        result.append(this.getClass().getName() + " Object {" + nl);
        result.append(" Interval velocity ID: ");

        if (this.getIntervalVelocityID() != null) {
            result.append(this.getIntervalVelocityID() + nl);
        } else {
            result.append("No interval velocity ID defined." + nl);
        }

        result.append(" Interval P-Wave velocity: "
                + this.getIntervalPWaveVelocity() + nl);

        result.append(" Interval P-Wave velocity pick uncertainty: "
                + this.getIntervalPWaveVelocityPickUncert() + nl);

        result.append(" Interval top horizon: ");

        if (this.getIntervalTopHorizon() != null) {
            result.append(nl);
            result.append(this.getIntervalTopHorizon().toString() + nl);
        } else {
            result.append("No top horizon defined." + nl);
        }

        result.append(" Interval bottom horizon: ");

        if (this.getIntervalBottomHorizon() != null) {
            result.append(nl);
            result.append(this.getIntervalBottomHorizon().toString() + nl);
        } else {
            result.append("No bottom horizon defined." + nl);
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
        // Geographic location
        final double n = 7201050;
        final double e = 400800;
        GeoPoint2D nw = new GeoPoint2D(n, e);

        // Well
        Well w = new Well("W0001");
        w.setWellName("Well1");

        //WellPick horizon
        final double tvd = 1100;
        final double md = 1111;
        final double cNN = 10;
        final double cEE = 11;
        final double cVV = 12;
        final double cNE = 0.1;
        final double cEV = 0.2;
        final double cNV = 0.3;
        WellPick wp = new WellPick("WPH0001", w);

        wp.setLocation(nw);
        wp.setTVDSS(tvd);
        wp.setMD(md);
        wp.setCovNN(cNN);
        wp.setCovEE(cEE);
        wp.setCovVV(cVV);
        wp.setCovNE(cNE);
        wp.setCovEV(cEV);
        wp.setCovNV(cNV);

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
//        horDef.setHorizonGridDataFilePath("C:/Temp/Dataset_HorizonGrids.txt");
//        horDef.setHorizonGridPickUncertainty(1.0);
        horDef.setHorizonGrid(horGrid);

        // Horizon top
        Horizon hort = new Horizon("H001");
        hort.setHorizonName("Layer1");
//        hort.setHorizonDataFilePath("C:/Temp/Dataset_Horizons.txt");
        hort.setHorizonDefinition(horDef);
        hort.addHorizonWellPick(wp);
        hort.addHorizonWellPick(wp);

        // Horizon bottom
        Horizon horb = new Horizon("H002");
        horb.setHorizonName("Layer1");
//        horb.setHorizonDataFilePath("C:/Temp/Dataset_Horizons.txt");
        horb.setHorizonDefinition(horDef);
        horb.addHorizonWellPick(wp);
        horb.addHorizonWellPick(wp);

        // Interval velocity
        final String ivID = "SIV001";
        final double pWV = 1000;
        final double pWVPU = 10;
        IntervalVelocity intVel = new IntervalVelocity(ivID);

        intVel.setIntervalTopHorizon(hort);
        intVel.setIntervalBottomHorizon(horb);
        intVel.setIntervalPWaveVelocity(pWV);
        intVel.setIntervalPWaveVelocityPickUncert(pWVPU);

        System.out.println(intVel);
    }
}
