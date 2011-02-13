package com.statoil.xmlparser;

public class FileHeader {
    private String mID = null;
    private String mFileName = null;
    private String mFileType = null;
    private boolean mDefault = false;
    private String mComment = null;

    public FileHeader(final String id) {
        mID = id;
    }

    public final String getID() {
        return mID;
    }

    public final String getFileName() {
        return mFileName;
    }

    public final void setFileName(final String dataFileName) {
        mFileName = dataFileName;
    }

    public final String getFileType() {
        return mFileType;
    }

    public final void setFileType(final String dataFileType) {
        mFileType = dataFileType;
    }

    public final void setDefaultFile(final boolean def) {
        mDefault = def;
    }

    public final boolean isDefaultFile() {
        return mDefault;
    }

    public final String getFileComment() {
        return mComment;
    }

    public final void setComment(final String fComm) {
        mComment = fComm;
    }
}
