package io.github.anjoismysign.anjo.swing.listeners;

import io.github.anjoismysign.anjo.swing.AnjoComponent;

import java.util.function.Consumer;

public abstract class AnjoConsumerListener extends AnjoListener {
    private Consumer<AnjoComponent> consumer;

    public void setConsumer(Consumer<AnjoComponent> consumer) {
        this.consumer = consumer;
    }

    public AnjoConsumerListener consumer(Consumer<AnjoComponent> consumer) {
        setConsumer(consumer);
        return this;
    }

    public Consumer<AnjoComponent> getConsumer() {
        return consumer;
    }
}
