package com.statoil.xmlparser;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.TimeZone;

public final class Misc {

    private Misc() {
    }

    /**
     * The functions checks if the input is less than the given epsilon value.
     * If it is, 0.0 is returned. If not the input is returned.
     *
     * This function is used to balance double numbers.
     * In some calculation types the answers get very close to zero, but not 
     * exactly zero.
     * This function sets the answers to zero if they are close enough to zero.
     *
     * @param input takes a <code>double</code> representing the input value 
     * to be checked against the epsilon zero limit.
     * @param epsilon takes a <code>double</code> representing the zero limit 
     * to adjust to.
     * @return a <code>double</code> representing the adjusted input.s
     *
     */
    public static double zeroIfLessThanEpsilon(final double input, 
    		final double epsilon) {
      if (Math.abs(input) < epsilon) {
        return 0.0;
      } else {
        return input;
      }
    }

    /**
     * Searches for filename in the path.
     * For each matching file found a file object is returned.
     *
     * @param filename name of file to search for
     * @return an array of file objects, one for each file found, or empty array
     * if no file is found.
     */
    public static File [] findFileInPath(final String filename) {
        String tmp;
        String libraryPath = System.getProperty("java.library.path");
        String pathSeparator = System.getProperty("path.separator");
        StringTokenizer st = new StringTokenizer(libraryPath, pathSeparator);
        ArrayList < File > res = new ArrayList < File >();
        File [] result = null;
        File f = null;
        File [] fa = null;

        if (filename == null || filename.compareToIgnoreCase("") == 0) {
            return new File [0];
        }

        // Check classpath
        while (st.hasMoreTokens()) {
            tmp = st.nextToken();
            f = new File(tmp);

            if (f.isDirectory() && f.canRead()) {

                // List all files in the directory and process them
                fa = f.listFiles();

                if (fa != null && fa.length > 0) {
                    for (int i = 0; i < fa.length; i++) {
                        if (fa[i].getName().equalsIgnoreCase(filename)) {
                            // System.out.println("Found " + filepath
                            // + " in path: " + fa[i].getAbsolutePath());
                            res.add(fa[i]);
                        }
                    }
                }
            }
        }

        if (res != null && res.size() > 0) {
            result = new File [res.size()];
            result = (File []) res.toArray(result);
        }

        res.clear();
        res = null;

        return result;
    }

    /**
     * Returns double parameter formatted as a String with decimal sign ','
     * and defined numbers after the decimal sign.
     *
     * @param val takes a <code>double</code> to be formatted.
     * @param numbDec takes an <code>int</code> representing numbers after the
     * decimal sign.
     * @return val formatted as a <code>String</code> with numbDec decimals and
     * ',' as decimal separator.
     */
    public static String doubleToGUIString(final double val,
            final int numbDec) {
        BigDecimal myDecimal = null;
        String myString = null;

        if (Double.isNaN(val)) {
            return "---";
        }

        myDecimal = new BigDecimal(val);
        myDecimal = myDecimal.setScale(numbDec, BigDecimal.ROUND_HALF_UP);
        myString = myDecimal.toString();

        return myString.replace('.', ',');
    }

    /**
     * Returns <code>String</code> parameter as a double converting possible
     * ',' decimal separator.
     *
     * @param val takes a <code>String</code> to be converted.
     * @return the <code>String</code> converted to a double including
     * conversion a possible ',' as decimal separator.
     */
    public static double guiStringTodouble(final String val) {
        return Double.parseDouble(val.replace(',', '.'));
    }

    /**
     * Returns a <code>String</code> containing the entire stack trace of the
     * input <code>Throwable</code> parameter.
     *
     * @param t takes the <code>Throwable</code> containing the stack trace.
     * @return a <code>String</code> with the stack trace.
     */
    public static String getStackTrace(final Throwable t) {
        String stackTrace = null;
        StringWriter sw = new StringWriter();

        try {
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
            System.out.println(sw.getBuffer().toString());
        }

        return stackTrace;
    }

    /**
     * Truncates string if longer than 6 characters and if so adds ...
     *
     * @param input takes a <code>String</code> to be checked and if longer
     * than 6 characters be truncated.
     * @return the input <code>String</code>, truncated if longer than 6
     * characters.
     */
    public static String getTruncatedString(final String input) {
        final int maxLength = 6;
        String helperString = null;

        helperString = input;
        helperString = helperString.trim();

        if (helperString.length() > maxLength) {
            helperString = helperString.substring(0, maxLength - 1) + "...";
        }

        return helperString;
    }

    /**
     * Validates file name for the OpenWorks database.
     *
     * @param name takes a <code>String</code> representing a suggested OW file
     * name.
     * @return true if input string parameter is between 1 and 40 characters, is
     * not null and do not contain any "Norwegian" characters.
     * Otherwise false is returned.
     */
    public static boolean validateOWFileName(final String name) {
        final int maxLength = 40;

        if (name == null || name.equals("")) {
            return false;
        }

        if (name.length() > maxLength) {
            return false;
        }

        if (name.contains("æ") || name.contains("ø") || name.contains("å")
                || name.contains("Æ") || name.contains("Ø")
                || name.contains("Å")) {
            return false;
        }

        return true;
    }

    /**
     * Returns a <code>String</code> containing the input parameter mapped to
     * a HTML red text.
     *
     * @param s takes the <code>String</code> to be mapped.
     * @return the input parameter mapped as HTML red text.
     */
    public static String getHTMLRedText(final String s) {
       String resString = "<HTML><p style=color:red>" + s + "</p></HTML>";

       return resString;
    }

    /**
     * Returns a <code>String</code> containing the input parameter mapped to
     * a HTML black text.
     *
     * @param s takes the <code>String</code> to be mapped.
     * @return the input parameter mapped as HTML black text.
     */
    public static String getHTMLBlackText(final String s) {
        String resString = "<HTML><p style=color:black>" + s + "</p></HTML>";

        return resString;
     }

    /**
     * Returns the dimension specified by parameters percentWidth and
     * percentHeight relative to the screensize.
     *
     * @param percentWidth takes a <code>double</code> describing width
     * precent of total screen.
     * @param percentHeight takes a <code>double</code> describing height
     * precent of total screen.
     * @return the dimension specified by parameters percentWidth and
     * percentHeight relative to the screensize.
     */
    public static Dimension getPercentOfScreenDimension(
            final double percentWidth, final double percentHeight) {
        final double max = 100;
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        double width = d.getWidth() * (percentWidth / max);
        double height = d.getHeight() * (percentHeight / max);

        return new Dimension((int) width, (int) height);
    }

    /**
     * Returns the current date - time.
     *
     * @return a <code>String</code> representing current time and date
     */
    public static String getCurrentTimeString() {
        String dateFormat = "dd.MM HH:mm";
        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat);

        /*
         *  In some JDK, the default TimeZone is wrong we must set the
         *  TimeZone manually!!!
         *   sdf.setTimeZone(TimeZone.getTimeZone("EST"));
         */
        sdf.setTimeZone(TimeZone.getDefault());

        return sdf.format(cal.getTime());
    }
}

