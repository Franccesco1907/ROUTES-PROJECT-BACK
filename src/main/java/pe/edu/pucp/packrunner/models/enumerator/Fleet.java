package pe.edu.pucp.packrunner.models.enumerator;

public enum Fleet {
    A(90),B(45),C(30);

    private int capacity;

    Fleet(int capacity) {
        this.capacity = capacity;
    }

    public int getCapacity() {
        return capacity;
    }
}
