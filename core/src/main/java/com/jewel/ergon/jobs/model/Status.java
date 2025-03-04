package com.jewel.ergon.jobs.model;


/*
Encapsulates state-specific behavior: Each status (e.g., OFFER, SENT, INPROGRESS) can have different behaviors (e.g., transitions, validations).
Prevents large if-else or switch statements: Instead of checking status in multiple places, behavior is encapsulated in concrete state classes.
Encourages the Open/Closed Principle: You can add new statuses and behaviors without modifying existing code.
Makes transitions explicit: Prevents invalid state transitions by defining them within the state classes.
 */
public enum Status {
    OFFER ,SENT, INPROGRESS, ACCEPTED, REJECTED, REPORTED, CLOSED
}
