package com.jain.library;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class LibraryFineServiceTest {

    private LibraryFineService service;

    @BeforeEach
    void setUp() {
        service = new LibraryFineService();
    }

    @Test
    void overdueDays_returnedLate() {
        assertEquals(5, service.calculateOverdueDays(19, 14));
    }

    @Test
    void overdueDays_returnedOnTime() {
        assertEquals(0, service.calculateOverdueDays(10, 14));
    }

    @Test
    void fine_withinFirstWeek() {
        assertEquals(10.0, service.calculateFine(5), 0.001);
    }

    @Test
    void fine_afterFirstWeek() {
        // 7 * 2 + 3 * 5 = 29
        assertEquals(29.0, service.calculateFine(10), 0.001);
    }

    @Test
    void fine_isCappedAtMaximum() {
        assertEquals(500.0, service.calculateFine(1000), 0.001);
    }

    @Test
    void discount_student() {
        assertEquals(50.0, service.applyMemberDiscount(100.0, "student"), 0.001);
    }

    @Test
    void discount_staff() {
        assertEquals(75.0, service.applyMemberDiscount(100.0, "STAFF"), 0.001);
    }

    @Test
    void discount_regularMemberPaysFull() {
        assertEquals(100.0, service.applyMemberDiscount(100.0, "PUBLIC"), 0.001);
    }

    @Test
    void payableAmount_endToEnd() {
        // 24 days kept, 14 allowed -> 10 overdue -> fine 29 -> student 14.5
        assertEquals(14.5, service.calculatePayableAmount(24, 14, "STUDENT"), 0.001);
    }

    @Test
    void negativeInput_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> service.calculateFine(-1));
    }
}
