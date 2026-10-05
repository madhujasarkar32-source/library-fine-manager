package com.jain.library;

/**
 * Library Fine Manager.
 * Provides operations to compute overdue days, fines, and the final payable
 * amount for a borrowed library book.
 */
public class LibraryFineService {

    /** Fine per day for the first 7 overdue days (in rupees). */
    public static final double RATE_FIRST_WEEK = 2.0;

    /** Fine per day after the first 7 overdue days (in rupees). */
    public static final double RATE_AFTER_FIRST_WEEK = 5.0;

    /** Maximum fine that can be charged for a single book (in rupees). */
    public static final double MAX_FINE = 500.0;

    /**
     * Operation 1: calculates the number of overdue days.
     *
     * @param daysBorrowed number of days the book was kept
     * @param allowedDays  number of days the library allows
     * @return overdue days (0 if returned on time)
     */
    public int calculateOverdueDays(int daysBorrowed, int allowedDays) {
        if (daysBorrowed < 0 || allowedDays < 0) {
            throw new IllegalArgumentException("Days cannot be negative");
        }
        return Math.max(0, daysBorrowed - allowedDays);
    }

    /**
     * Operation 2: calculates the fine for the given overdue days.
     * Rs.2/day for the first 7 days, Rs.5/day afterwards, capped at Rs.500.
     *
     * @param overdueDays number of overdue days
     * @return fine amount
     */
    public double calculateFine(int overdueDays) {
        if (overdueDays < 0) {
            throw new IllegalArgumentException("Overdue days cannot be negative");
        }
        double fine;
        if (overdueDays <= 7) {
            fine = overdueDays * RATE_FIRST_WEEK;
        } else {
            fine = 7 * RATE_FIRST_WEEK + (overdueDays - 7) * RATE_AFTER_FIRST_WEEK;
        }
        return Math.min(fine, MAX_FINE);
    }

    /**
     * Operation 3: applies a membership discount to the fine.
     * Students get 50% off, staff get 25% off, others pay in full.
     *
     * @param fine       the calculated fine
     * @param memberType "STUDENT", "STAFF" or anything else
     * @return payable amount after discount
     */
    public double applyMemberDiscount(double fine, String memberType) {
        if (fine < 0) {
            throw new IllegalArgumentException("Fine cannot be negative");
        }
        if (memberType == null) {
            return fine;
        }
        switch (memberType.toUpperCase()) {
            case "STUDENT":
                return fine * 0.50;
            case "STAFF":
                return fine * 0.75;
            default:
                return fine;
        }
    }

    /**
     * Operation 4: convenience method that computes the final payable amount.
     */
    public double calculatePayableAmount(int daysBorrowed, int allowedDays, String memberType) {
        int overdue = calculateOverdueDays(daysBorrowed, allowedDays);
        double fine = calculateFine(overdue);
        return applyMemberDiscount(fine, memberType);
    }
}
