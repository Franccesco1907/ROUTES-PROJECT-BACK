package pe.edu.pucp.packrunner.models.enumerator;

public enum TruckStatus {
    OPERATIONAL,
    IN_MAINTENANCE,
    MODERATE_BREAKDOWN, // The truck stops in the road for 12 hours
    SERIOUS_BREAKDOWN, // The truck can no longer continue, the load must be rescheduled
    INOPERABLE // The truck is destroyed
}
