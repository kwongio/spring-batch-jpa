package org.example.springbatchjpa.batch;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum BatchStatus {

    STARTING,
    FAILED,
    COMPLETED
}
