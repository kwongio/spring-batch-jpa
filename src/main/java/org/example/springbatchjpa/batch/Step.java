package org.example.springbatchjpa.batch;

public class Step {
    private final Tasklet tasklet;

    public Step(Tasklet tasklet) {
        this.tasklet = tasklet;
    }

    public Step(ItemReader<?> reader, ItemProcessor<?, ?> processor, ItemWriter<?> writer) {
        this.tasklet = new SimpleTasklet(reader, processor, writer);
    }

    public void execute() {
        tasklet.execute();
    }
}
