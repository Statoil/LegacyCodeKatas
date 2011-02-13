package com.statoil.xmlparser;

public class GeoPoint2D {
	private double mNorthing = 0.0;
	private double mEasting = 0.0;
	
	public GeoPoint2D(final double northing, final double easting) {
		mNorthing = northing;
		mEasting = easting;
	}

	public GeoPoint2D() { }
	
	public final double getNorthing() {
		return mNorthing;
	}

	public final void setNorthing(final double northing) {
		mNorthing = northing;
	}

	public final double getEasting() {
		return mEasting;
	}

	public final void setEasting(final double easting) {
		mEasting = easting;
	}

    public final String toString() {
    	String nl = System.getProperty("line.separator");
    	StringBuilder result = new StringBuilder();
	    	    	
	    result.append(this.getClass().getName() + " Object {" + nl);
	    result.append(" GeoPoint2D northing coordinate: " 
	    		+ this.getNorthing() + nl);
	    result.append(" GeoPoint2D easting coordinate: " 
	    		+ this.getEasting() + nl);
	    result.append("}");

	    return result.toString();
	  }
	  
    /**
     * Used for testing.
     * 
     * @param args takes an array of <code>String</code>. Not used for this 
     * test.
     */
    public static void main(final String[] args) {
    	final double n = 1000;
    	final double s = 500;
    	final double w = 200;
    	final double e = 300;
    	
    	// Testing constructor.
    	GeoPoint2D nw = new GeoPoint2D(n, w);
    	GeoPoint2D ne = new GeoPoint2D(n, e);
    	GeoPoint2D se = new GeoPoint2D(s, e);
    	GeoPoint2D sw = new GeoPoint2D(s, w);
    	
    	System.out.println(nw);
    	System.out.println(ne);
    	System.out.println(se);
    	System.out.println(sw);
    	
    	// Testing methods.
    	nw.setNorthing(1);
    	nw.setEasting(2);	
    	ne.setNorthing(1);
    	ne.setEasting(2);
    	se.setNorthing(1);
    	se.setEasting(2);
    	sw.setNorthing(1);
    	sw.setEasting(2);
    	
    	System.out.println(nw);
    	System.out.println(ne);
    	System.out.println(se);
    	System.out.println(sw);			
    }
}
