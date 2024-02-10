package org.example.springbatchjpa.batch;

public interface ItemProcessor<I, O> {

    O process(I item);

}
