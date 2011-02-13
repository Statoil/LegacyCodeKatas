package com.statoil.xmlparser;

import java.io.File;
import java.util.Vector;

import com.statoil.xmlparser.io.XMLReader;

public class SessionManager implements ObservableInterface {
    private Vector <ObserverInterface> mObs = new Vector <ObserverInterface>();
    private boolean mEnableNotification = true;
    private Dataset mDataSet = null;

    public SessionManager() {
        this.enableNotifications(true);
    }

    public final void registerInterest(final ObserverInterface obs) {
        // Check for duplicates.
        for (ObserverInterface o : mObs) {
            if (o.equals(obs)) {
                return;
            }
        }

        mObs.addElement(obs);
    }

    public final void unregisterInterest(final ObserverInterface obs) {
        // Remove the observer from the list;
        for (ObserverInterface o : mObs) {
            if (o.equals(obs)) {
                mObs.remove(o);
                break;
            }
        }
    }

    public final void enableNotifications(final boolean enable) {
        if (!mEnableNotification && enable) {
            mEnableNotification = true;
        } else if (mEnableNotification && !enable) {
            mEnableNotification = false;
        }
    }

    public final void notifyObservers(final int event) {

        // Only if notifications are turned on.
        if (mEnableNotification) {
            System.out.println("NotifyObservers sending signal --> " + event);

            for (ObserverInterface o : mObs) {
                o.sendNotify(event);
            }
        }
    }

    public final void removeAllNotificationObservers() {
        mObs.removeAllElements();
    }

    public final void loadDataSetFromFile(final String dataSetFilePath)
            throws Exception {
        File f = null;
        String msg1 = "Unable to access file \n" + dataSetFilePath;
        String msg2 = "Unable to read file \n" + dataSetFilePath;

        try {
            f = new File(dataSetFilePath);
        } catch (Exception e) {
            throw new Exception(msg1  + "\n" + e.getMessage(), e);
        }

        // Reads the data set.
        try {
            XMLReader.readXmlFile(f, this);
        }  catch (Exception e) {
            throw new Exception(msg2  + "\n" + e.getMessage(), e);
        }

        // Remember file location for later reference.
        mDataSet.setDatasetAbsFilePath(f.getAbsolutePath());

        this.notifyObservers(ObservableInterface.NEW_DATASET);
    }

    public final Dataset getDataSet() {
        return mDataSet;
    }

    public final void setDataSet(final Dataset dataSet) {
        mDataSet = dataSet;
    }

}
