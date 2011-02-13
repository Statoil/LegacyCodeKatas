package com.statoil.xmlparser;

public class GeoArea2D {
	private GeoPoint2D mNorthWestCorner = null;
	private GeoPoint2D mNorthEastCorner = null;
	private GeoPoint2D mSouthEastCorner = null;
	private GeoPoint2D mSouthWestCorner = null;
		
	public GeoArea2D(final GeoPoint2D nw, final GeoPoint2D ne, 
			final GeoPoint2D se, final GeoPoint2D sw) {
		mNorthWestCorner = nw;
		mNorthEastCorner = ne;
		mSouthEastCorner = se;
		mSouthWestCorner = sw;
	}
	
	public GeoArea2D() { }
	
	public final GeoPoint2D getNorthWestCorner() {
		return mNorthWestCorner;
	}

	public final void setNorthWestCorner(final GeoPoint2D northWestCorner) {
		mNorthWestCorner = northWestCorner;
	}

	public final GeoPoint2D getNorthEastCorner() {
		return mNorthEastCorner;
	}

	public final void setNorthEastCorner(final  GeoPoint2D northEastCorner) {
		mNorthEastCorner = northEastCorner;
	}

	public final GeoPoint2D getSouthEastCorner() {
		return mSouthEastCorner;
	}

	public final void setSouthEastCorner(final GeoPoint2D southEastCorner) {
		mSouthEastCorner = southEastCorner;
	}

	public final GeoPoint2D getSouthWestCorner() {
		return mSouthWestCorner;
	}

	public final void setSouthWestCorner(final GeoPoint2D southWestCorner) {
		mSouthWestCorner = southWestCorner;
	}

    public final String toString() {
    	String nl = System.getProperty("line.separator");
    	StringBuilder result = new StringBuilder();
	    	    	
	    result.append(this.getClass().getName() + " Object {" + nl);
	    result.append(" GeoArea2D NorthWest corner: "  + nl 
	    		+ this.getNorthWestCorner().toString() + nl);
	    result.append(" GeoArea2D NorthEast corner: "  + nl 
	    		+ this.getNorthEastCorner().toString() + nl);
	    result.append(" GeoArea2D SouthEast corner: "  + nl
	    		+ this.getSouthEastCorner().toString() + nl);
	    result.append(" GeoArea2D SouthWest corner: "  + nl
	    		+ this.getSouthWestCorner().toString() + nl);	    	    
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
		final double n1 = 1000;
		final double n2 = 500;
		final double e1 = 200;
		final double e2 = 300;	
		GeoPoint2D nw = new GeoPoint2D(n1, e1);
		GeoPoint2D ne = new GeoPoint2D(n1, e2);
		GeoPoint2D se = new GeoPoint2D(n2, e2);
		GeoPoint2D sw = new GeoPoint2D(n2, e1);
		GeoArea2D ga = new GeoArea2D(nw, ne, se, sw);
		
		// Testing constructor.
		System.out.println(ga);
	}
}
