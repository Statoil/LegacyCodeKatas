package com.statoil.xmlparser.io;

public class XmlParseException extends Exception {

    private static final long serialVersionUID = -1167470796582891756L;

    public XmlParseException(final String reason) {
        super(reason);
    }

    public XmlParseException() {
        super();
    }
}
