package org.example.springbatchjpa.batch;

public interface ItemWriter <O>{

    void write(O item);

}
