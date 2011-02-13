package com.statoil.xmlparser;

public class HorizonGrid {
	private String mHorizonGridID = null;
	private int mHorizonGridSizeN = 0;
	private int mHorizonGridSizeE = 0;
	private double mHorizonGridResolutionN = 0.0;
	private double mHorizonGridResolutionE = 0.0;
	private GeoPoint2D mHorizonGridStartingPoint = null;
	private GeoPoint2D mHorizonGridEndingPoint = null;
	private double mHorizonGridAzimuth = 0.0;
	private long mHorizonGridInfiniteTime = 0L;
	private String mHorizonGridFileTypeOrigin = null;

	public HorizonGrid(final String gID, final int gSizeN, final int gSizeE, 
			final double gResN, final double gResE, final GeoPoint2D gStart,
			final GeoPoint2D gEnd, final double gAz, final long gInfTime,
			final String gOrigin) {
		mHorizonGridID = gID;
		mHorizonGridSizeN = gSizeN;
		mHorizonGridSizeE = gSizeE;
		mHorizonGridResolutionN = gResN;
		mHorizonGridResolutionE = gResE;
		mHorizonGridStartingPoint = gStart;
		mHorizonGridEndingPoint = gEnd;
		mHorizonGridAzimuth = gAz;
		mHorizonGridInfiniteTime = gInfTime;
		mHorizonGridFileTypeOrigin = gOrigin;
	}

	public final String getHorizonGridID() {
		return mHorizonGridID;
	}

	public final int getHorizonGridSizeN() {
		return mHorizonGridSizeN;
	}

	public final int getHorizonGridSizeE() {
		return mHorizonGridSizeE;
	}

	public final double getHorizonGridResolutionN() {
		return mHorizonGridResolutionN;
	}

	public final double getHorizonGridResolutionE() {
		return mHorizonGridResolutionE;
	}

	public final GeoPoint2D getHorizonGridStartingPoint() {
		return mHorizonGridStartingPoint;
	}

	public final GeoPoint2D getHorizonGridEndingPoint() {
		return mHorizonGridEndingPoint;
	}

	public final double getHorizonGridAzimuth() {
		return mHorizonGridAzimuth;
	}

	public final long getHorizonGridInfiniteTime() {
		return mHorizonGridInfiniteTime;
	}

	public final String getHorizonGridFileTypeOrigin() {
		return mHorizonGridFileTypeOrigin;
	}

    public final String toString() {
        String nl = System.getProperty("line.separator");
    	StringBuilder result = new StringBuilder();
	    	    	
	    result.append(this.getClass().getName() + " Object {" + nl);
	    result.append(" Horizon grid ID: ");
	    
	    if (this.getHorizonGridID() != null) {
	    	result.append(this.getHorizonGridID() + nl);
	    } else {
	    	result.append("No horizon grid ID defined." + nl);
	    }

	    result.append(" Horizon grid size in north direction: " 
	    		+ this.getHorizonGridSizeN() + nl);
	    result.append(" Horizon grid size in east direction: " 
	    		+ this.getHorizonGridSizeE() + nl);
	    result.append(" Horizon grid resolution in north direction: " 
	    		+ this.getHorizonGridResolutionN() + nl);
	    result.append(" Horizon grid resolution in east direction: " 
	    		+ this.getHorizonGridResolutionE() + nl);
	    result.append(" Horizon grid azimuth: " 
	    		+ this.getHorizonGridAzimuth() + nl);
	    result.append(" Horizon grid \"infinite time\": " 
	    		+ this.getHorizonGridInfiniteTime() + nl);	    
	    
	    result.append(" Horizon grid file type origin: ");
	    
	    if (this.getHorizonGridFileTypeOrigin() != null) {
	    	result.append(this.getHorizonGridFileTypeOrigin() + nl);
	    } else {
	    	result.append("No horizon grid file type origin defined." + nl);
	    }

	    result.append(" Horizon grid starting point: ");
	    
	    if (this.getHorizonGridStartingPoint() != null) {
	    	result.append(nl);
	    	result.append(this.getHorizonGridStartingPoint().toString() + nl);
	    } else {
	    	result.append("No horizon grid starting point defined." + nl);
	    }

	    result.append(" Horizon grid ending point: ");
	    
	    if (this.getHorizonGridEndingPoint() != null) {
	    	result.append(nl);
	    	result.append(this.getHorizonGridEndingPoint().toString() + nl);
	    } else {
	    	result.append("No horizon grid ending point defined." + nl);
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
		
		System.out.println(horGrid);
	}
}
